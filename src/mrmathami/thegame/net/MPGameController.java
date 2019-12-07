package mrmathami.thegame.net;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import mrmathami.thegame.*;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.drawer.UI.Popup.PopupDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.tile.Bush;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.effect.TowerDestroyEffect;
import mrmathami.thegame.entity.tile.effect.UpgradeEffect;
import mrmathami.thegame.entity.tile.tower.*;
import mrmathami.thegame.towerpicker.AbstractTowerPicker;
import mrmathami.thegame.towerpicker.*;
import mrmathami.thegame.ui.ingame.button.*;
import mrmathami.thegame.ui.ingame.context.*;
import mrmathami.thegame.ui.popup.MPDisconnectPopup;
import mrmathami.thegame.ui.popup.MPGameOverPopup;
import mrmathami.thegame.ui.popup.MPWinPopup;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A game controller for Multi-Player. Everything about the game should be managed in here.
 */
public final class MPGameController extends AnimationTimer {
	/**
	 * Advance stuff. Just don't touch me. Google me if you are curious.
	 */
	private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(
			new ThreadFactoryBuilder()
					.setNamePrefix("Tick")
					.setDaemon(true)
					.setPriority(Thread.NORM_PRIORITY)
					.build()
	);

	/**
	 * The screen to draw on. Just don't touch me. Google me if you are curious.
	 */
	private final GraphicsContext graphicsContext;

	/**
	 * Game field. Contain everything in the current game field.
	 * Responsible to update the field every tick.
	 * Kinda advance, modify if you are sure about your change.
	 */
	private GameField field;

	/**
	 * Opponent's Game field. Use in multi-player mode.
	 */
	private GameField opponentField;

	/**
	 * Main StackPane. Used for changing scene.
	 */
	private StackPane stackPane;

	/**
	 * Game drawer. Responsible to draw the field every tick.
	 * Kinda advance, modify if you are sure about your change.
	 */
	private GameDrawer drawer;

	/**
	 * Popup Drawer. Draw popup every tick if exist.
	 */
	private PopupDrawer popupDrawer;

	/**
	 * Game UI. Contains UI elements.
	 */
	private GameUI gameUI;

	/**
	 * Socket wrapper. Used to send and receive data from opponent.
	 */
	private MPSocketController socket;

	/**
	 * Tower Placing Class. Using for place a tower.
	 */
	private AbstractTowerPicker towerPicker;

	/**
	 * Context Area. Used to display game info on the sidebar
	 */
	private ContextArea contextArea;

	/**
	 * Beat-keeper Manager. Just don't touch me. Google me if you are curious.
	 */
	private ScheduledFuture<?> scheduledFuture;

	/**
	 * The tick value. Just don't touch me.
	 * Google me if you are curious, especially about volatile.
	 * WARNING: Wall of text.
	 */
	private volatile long tick;

	/**
	 * Pause checker. Check if the game is paused or not
	 */
	private boolean pause;

	/**
	 * The constructor.
	 *
	 * @param graphicsContext the screen to draw on
	 */
	public MPGameController(GraphicsContext graphicsContext, StackPane stackPane) throws FileNotFoundException {
		this.graphicsContext = graphicsContext;
		this.stackPane = stackPane;

		// Socket. To send data between peers.
		this.socket = MPSocketController.getCurrentInstance();

		// The game field.
		this.field = new GameField(GameStage.load("/maps/mapMP.txt", false));

		// Set the multi-player mode for our main game field, this will be checked when our target is harmed by enemy.
		field.setMultiplayer(true);

		// Opponent's field, for updating opponent state, run like a separated game.
		this.opponentField = new MPGameField(GameStage.load("/maps/mapMP.txt", true));

		this.gameUI = new GameUI("/stage/ui/MPButtonConfig.dat");

		this.towerPicker = null;
		this.pause = false;

		this.contextArea = new ContextArea(Config.UI_CONTEXT_POS_X_MP, Config.UI_CONTEXT_POS_Y);
		contextArea.setUpperContext(new MPNormalUIContext(field.getTickCount(), contextArea.getUpperContextPos(), field.getMoney(), field.getHealth(), opponentField.getHealth()));
		contextArea.setLowerContext(null);

		// The drawer. Nothing fun here.
		this.drawer = new GameDrawer(graphicsContext, field, opponentField, gameUI, towerPicker, contextArea,"/stage/sheet.png", "/stage/ui/button.png");

		// Field view region is a rectangle region
		// [(posX, posY), (posX + SCREEN_WIDTH / zoom, posY + SCREEN_HEIGHT / zoom)]
		// that the drawer will select and draw everything in it in an self-defined order.
		// Can be modified to support zoom in / zoom out of the map.
		drawer.setFieldViewRegion(0.0, 0.0, Config.TILE_SIZE);
	}

	/**
	 * Beat-keeper. Just don't touch me.
	 */
	private void tick() {
		//noinspection NonAtomicOperationOnVolatileField
		this.tick += 1;
	}

	private void gamePause() {
		if (pause) {
			super.start();
		}
		else {
			stop();
		}
		pause = !pause;
	}

	/**
	 * A JavaFX loop.
	 * Kinda advance, modify if you are sure about your change.
	 *
	 * @param now not used. It is a timestamp in nanosecond.
	 */
	@Override
	public void handle(long now) {
		// don't touch me.
		final long currentTick = tick;
		final long startNs = System.nanoTime();

		// do a tick, as fast as possible
		field.tick();
		opponentField.tick();

		// Check if one of the players died.
		if (opponentField.isLoss()) {
			MPWinPopup winPopup = new MPWinPopup(0, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, this.stackPane);
			winPopup.setGameController(this);
			popupDrawer = new PopupDrawer(winPopup.getPopupCanvas().getGraphicsContext2D(), winPopup.getPopupEntities());
			gamePause();
		} else if (field.isLoss()) {
			MPGameOverPopup gameOverPopup = new MPGameOverPopup(0, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, this.stackPane);
			gameOverPopup.setGameController(this);
			popupDrawer = new PopupDrawer(gameOverPopup.getPopupCanvas().getGraphicsContext2D(), gameOverPopup.getPopupEntities());
			gamePause();
		}

		// Check connection status.
		ensureConnection();

		//update the values in context so it match the current field, as fast as possible
		contextArea.updateMPContext(field.getMoney(), field.getHealth(), opponentField.getHealth(), towerPicker);

		// draw a new frame, as fast as possible.
		try {
			drawer.render();
			if (stackPane.getChildren().size() > 1)
			{
				if (popupDrawer != null)
					popupDrawer.render();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// MSPT display. MSPT stand for Milliseconds Per Tick.
		// It means how many ms your game spend to update and then draw the game once.
		// Draw it out mostly for debug
		final double mspt = (System.nanoTime() - startNs) / 1000000.0;
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.setTextAlign(TextAlignment.LEFT);
		graphicsContext.setTextBaseline(VPos.TOP);
		graphicsContext.setFont(new Font(12));
		graphicsContext.fillText(String.format("MSPT: %3.2f", mspt), 0, 0);

		// if we have time to spend, do a spin
		while (currentTick == tick) Thread.onSpinWait();
	}

	/**
	 * Start rendering and ticking. Just don't touch me.
	 * Anything that need to initialize should be in the constructor.
	 */
	@Override
	public void start() {
		// Start the beat-keeper. Start to call this::tick at a fixed rate.
		this.scheduledFuture = SCHEDULER.scheduleAtFixedRate(this::tick, 0, Config.GAME_NSPT, TimeUnit.NANOSECONDS);
		// start the JavaFX loop.
		super.start();
	}

	/**
	 * Send keep-alive packets. Makesure the opponent is still connected.
	 */
	private void ensureConnection() {
		if (this.tick % MPConfig.TICK_PER_KEEPALIVE == 0) {
			boolean isConnected = this.socket.sendKeepAlive();
			if (!isConnected) {
				MPDisconnectPopup disconnectPopup = new MPDisconnectPopup(0, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, this.stackPane);
				disconnectPopup.setGameController(this);
				popupDrawer = new PopupDrawer(disconnectPopup.getPopupCanvas().getGraphicsContext2D(), disconnectPopup.getPopupEntities());
				gamePause();
			}
		}
	}

	/**
	 * Move from game scene to menu scene.
	 */
	public void moveToMenuScene() {
		this.socket.closeConnection();
		scheduledFuture.cancel(true);
		stop();
		Canvas menuCanvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		GraphicsContext graphicsContext = menuCanvas.getGraphicsContext2D();
		MenuController menuController = new MenuController(graphicsContext, stackPane);
		// Prevent user from pressing 'tab' to change focus
		menuCanvas.setFocusTraversable(false);
		menuCanvas.setOnMouseClicked(menuController::mouseClickHandler);
		menuCanvas.setOnMouseMoved(menuController::mouseMoveHandler);
		menuCanvas.setOnKeyPressed(menuController::keyDownHandler);
		stackPane.getChildren().clear();
		stackPane.getChildren().add(menuCanvas);
		menuController.start();
		stackPane.getChildren().get(0).toFront();
	}

	/**
	 * On window close request.
	 * Kinda advance, modify if you are sure about your change.
	 *
	 * @param windowEvent currently not used
	 */
	final void closeRequestHandler(WindowEvent windowEvent) {
		scheduledFuture.cancel(true);
		stop();
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Key down handler.
	 *
	 * @param keyEvent the key that you press down
	 */
	public final void keyDownHandler(KeyEvent keyEvent) {
		final KeyCode keyCode = keyEvent.getCode();
		switch (keyCode) {
			case Q:
				towerPicker = new TowerPlacing("NormalTower");
				break;
			case W:
				towerPicker = new TowerPlacing("MachineGunTower");
				break;
			case E:
				towerPicker = new TowerPlacing("RocketLauncherTower");
				break;
			case R:
				towerPicker = new TowerPlacing("RobotPoliceTower");
				break;
			case A:
			case S:
			case D:
			case F:
				break;
			case Z:
				towerPicker = new TowerUpgrading();
				break;
			case X:
				towerPicker = new TowerSelling();
				break;
			case ESCAPE:
				towerPicker = null;
				break;
		}
		drawer.setTowerPicker(towerPicker);
	}

	/**
	 * Mouse click handler.
	 *
	 * @param mouseEvent the mouse event
	 */
	public final void mouseClickHandler(MouseEvent mouseEvent) {
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();
		Collection<UIEntity> UIEntities = this.gameUI.getEntities();
		Collection<GameEntity> gameEntities = this.field.getEntities();

		if (Double.compare(mousePosX, (double) MPConfig.OPPONENT_START_X * drawer.getFieldZoom()) > 0) {
			if (towerPicker != null) towerPicker.setPickingState(towerPicker.NOT_BEING_PICKED);
			contextArea.setLowerContext(null);
			return;
		}

		if ((Double.compare(mousePosX, (double)MPConfig.TILE_HORIZONTAL * drawer.getFieldZoom()) < 0)
				&& (Double.compare(mousePosY, (double)MPConfig.TILE_VERTICAL * drawer.getFieldZoom()) < 0)) {
			//infield
			if (mouseEvent.getButton() == MouseButton.PRIMARY) {
				if ((towerPicker != null) && (towerPicker.getPickingState() == towerPicker.PICKABLE)) {
					if (towerPicker instanceof TowerPlacing) {
						AbstractTower tower = ((TowerPlacing) towerPicker).getTower();

						// START: multi-player
						int towerType = 0;
						if (tower instanceof NormalTower) {
							towerType = 1;
						} else if (tower instanceof MachineGunTower) {
							towerType = 2;
						} else if (tower instanceof RocketLauncherTower) {
							towerType = 3;
						} else if (tower instanceof RobotPoliceTower) {
							towerType = 4;
						} else {
							towerType = 0;
						}
						this.socket.sendPlace(towerType,
								(long)((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom()),
								(long)((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom()));
						// END: multi-player

						field.doSpawn(tower);
						field.setMoney(field.getMoney() - ((TowerPlacing) towerPicker).getTowerPrice());
					} else {
						for (GameEntity entity : gameEntities) {
							if ((entity instanceof AbstractTower) && (towerPicker.isOverlappedWithTower(entity))) {
								if (towerPicker instanceof TowerUpgrading) {
									if (((TowerUpgrading) towerPicker).getUpgradePrice(entity) <= field.getMoney()) {
										((AbstractTower) entity).doUpgrade();
										// Effect
										this.field.addSFX(new UpgradeEffect(0, entity.getPosX(), entity.getPosY()));
										field.setMoney(field.getMoney() - ((TowerUpgrading) towerPicker).getUpgradePrice(entity));

										// START: multi-player
										this.socket.sendUpgrade((long)((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom()),
												(long)((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom()));
										// END: multi-player
									}
								} else if (towerPicker instanceof TowerSelling) {
									((AbstractTower) entity).doDestroy();
									field.addSFX(new TowerDestroyEffect(0, entity.getPosX(), entity.getPosY()));
									field.setMoney(field.getMoney() + ((TowerSelling) towerPicker).getSellPrice(entity));

									// START: multi-player
									this.socket.sendSell((long)((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom()),
											(long)((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom()));
									// END: multi-player
								}
								break;
							}
						}
					}
					towerPicker = null;
					drawer.setTowerPicker(null);
				}
			}
			else {
				towerPicker = null;
				drawer.setTowerPicker(null);
			}
		}
		else {
			//outfield
			for (UIEntity entity: UIEntities) {
				double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
				double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
				double endX = startX + entity.getWidth() * drawer.getFieldZoom();
				double endY = startY + entity.getHeight() * drawer.getFieldZoom();
				if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
						&& Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
					if (entity instanceof TowerButton) {
						String towerType = ((TowerButton) entity).getTowerType();
						if (!((TowerButton) entity).getTowerType().equals("Locked")) {
							towerPicker = new TowerPlacing(towerType);
							drawer.setTowerPicker(towerPicker);
						}
					}
					else if (entity instanceof BackButton) {
						moveToMenuScene();
					}
					else if (entity instanceof PauseButton) {
						gamePause();
					}
					else if (entity instanceof UpgradeButton) {
						towerPicker = new TowerUpgrading();
						drawer.setTowerPicker(towerPicker);
					}
					else if (entity instanceof SellButton) {
						towerPicker = new TowerSelling();
						drawer.setTowerPicker(towerPicker);
					}
					return;
				}
			}
		}
	}

	public final void mouseMoveHandler(MouseEvent mouseEvent) {
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();
		Collection<UIEntity> UIEntities = this.gameUI.getEntities();
		Collection<GameEntity> gameEntities = this.field.getEntities();

		if (Double.compare(mousePosX, (double) MPConfig.OPPONENT_START_X * drawer.getFieldZoom()) > 0) {
			if (towerPicker != null) towerPicker.setPickingState(towerPicker.NOT_BEING_PICKED);
			contextArea.setLowerContext(null);
			return;
		}

		if ((Double.compare(mousePosX, (double)MPConfig.TILE_HORIZONTAL * drawer.getFieldZoom()) < 0)
				&& (Double.compare(mousePosY, (double)MPConfig.TILE_VERTICAL * drawer.getFieldZoom()) < 0)) {
			//infield
			mousePosX = (long)((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom());
			mousePosY = (long)((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom());

			contextArea.setLowerContext(null);

			if (towerPicker != null) {
				towerPicker.setPosition((long) mousePosX, (long) mousePosY);
				if (towerPicker instanceof TowerPlacing) {
					contextArea.setLowerContext(new ButtonUIContext(field.getTickCount(), contextArea.getLowerContextPos(), field.getMoney(), ((TowerPlacing) towerPicker).getTowerType()));

					if (((TowerPlacing) towerPicker).getTowerPrice() > field.getMoney()) {
						((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
						return;
					}
					else {
						((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).PLACEABLE);
					}
				}
				else {
					towerPicker.setPickingState(towerPicker.NOT_PICKABLE);
				}
			}

			for (GameEntity entity : gameEntities) {
				if (entity instanceof Road) {
					if ((towerPicker != null) && (towerPicker instanceof TowerPlacing) && (towerPicker.isOverlappedWithRoad(entity))) {
						((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
						break;
					}
				}
				else if (entity instanceof AbstractTower) {
					if (entity.isBeingOverlapped(mousePosX, mousePosY, 1, 1)) {
						contextArea.setLowerContext(new TowerUIContext(field.getTickCount(), contextArea.getLowerContextPos(), field.getMoney(), (AbstractTower)entity));
					}
					if ((towerPicker != null) && (towerPicker.isOverlappedWithTower(entity))) {
						if (towerPicker instanceof TowerPlacing) {
							((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
						}
						else {
							towerPicker.setPickingState(towerPicker.PICKABLE);
						}
						break;
					}
				}
				else if (entity instanceof Bush) {
					if ((towerPicker != null) && (towerPicker.isOverlappedWithTower(entity))) {
						if (towerPicker instanceof TowerPlacing) {
							((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
						}
						break;
					}
				}
			}

			if ((towerPicker != null) && (towerPicker instanceof TowerPlacing)) {
				contextArea.setLowerContext(new ButtonUIContext(field.getTickCount(), contextArea.getLowerContextPos(), field.getMoney(), ((TowerPlacing) towerPicker).getTowerType()));
			}

			for (UIEntity entity: UIEntities) {
				entity.outFocus();
			}
		}
		else {
			//outfield
			if (towerPicker != null) towerPicker.setPickingState(towerPicker.NOT_BEING_PICKED);
			boolean onTowerButton = false;

			for (UIEntity entity: UIEntities) {
				double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
				double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
				double endX = startX + entity.getWidth() * drawer.getFieldZoom();
				double endY = startY + entity.getHeight() * drawer.getFieldZoom();
				if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
						&& Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
					entity.onFocus();
					if ((entity instanceof TowerButton) && (!((TowerButton)entity).getTowerType().equals("Locked"))) {
						contextArea.setLowerContext(new ButtonUIContext(field.getTickCount(), contextArea.getLowerContextPos(), field.getMoney(), ((TowerButton) entity).getTowerType()));
						onTowerButton = true;
					}
				} else {
					entity.outFocus();
				}
			}
			if (!onTowerButton) {
				contextArea.setLowerContext(null);
			}
		}
	}
}
