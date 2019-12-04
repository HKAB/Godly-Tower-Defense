package mrmathami.thegame;

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

import mrmathami.thegame.drawer.UI.Popup.PopupDrawer;
import mrmathami.thegame.entity.tile.Bush;
import mrmathami.thegame.entity.tile.effect.UpgradeEffect;
import mrmathami.thegame.towerpicker.AbstractTowerPicker;
import mrmathami.thegame.towerpicker.*;
import mrmathami.thegame.ui.ingame.button.*;
import mrmathami.thegame.ui.ingame.button.TowerButton;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.ui.ingame.context.*;
import mrmathami.thegame.ui.popup.AfterCreditPopup;
import mrmathami.thegame.ui.popup.GameOverPopup;
import mrmathami.thegame.ui.popup.WinPopup;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A game controller. Everything about the game should be managed in here.
 */
public final class GameController extends AnimationTimer {
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
	 * Current Map count. Use when load a new map
	 */
	private int currentMap;

	/**
	 * The constructor.
	 *
	 * @param graphicsContext the screen to draw on
	 */

	private boolean isWonPopupShowUp = false;
	public GameController(GraphicsContext graphicsContext, StackPane stackPane) throws FileNotFoundException {
		// The screen to draw on
		this.graphicsContext = graphicsContext;
		this.stackPane = stackPane;
		// Just a few acronyms.
		final long width = Config.TILE_HORIZONTAL;
		final long height = Config.TILE_VERTICAL;

		this.currentMap = 1;

		// The game field. Please consider create another way to load a game field.
		this.field = new GameField(GameStage.load("/stage/map" + currentMap + ".txt", false));

		this.gameUI = new GameUI("/ui/buttonConfig.dat");

		this.towerPicker = null;
		this.pause = false;

		this.contextArea = new ContextArea(Config.UI_CONTEXT_POS_X, Config.UI_CONTEXT_POS_Y);
		contextArea.setUpperContext(new NormalUIContext(field.getTickCount(), contextArea.getUpperContextPos(), field.getMoney(), field.getHealth()));
		contextArea.setLowerContext(null);

		// The drawer. Nothing fun here.
		this.drawer = new GameDrawer(graphicsContext, field, null, gameUI, towerPicker, contextArea,"/stage/sheet.png", "/ui/button.png");

		this.popupDrawer = null;
		// Field view region is a rectangle region
		// [(posX, posY), (posX + SCREEN_WIDTH / zoom, posY + SCREEN_HEIGHT / zoom)]
		// that the drawer will select and draw everything in it in an self-defined order.
		// Can be modified to support zoom in / zoom out of the map.
		drawer.setFieldViewRegion(Config.FIELD_START_POS_X, Config.FIELD_START_POS_Y, Config.TILE_SIZE);
	}

	public void nextMap() {
		this.currentMap++;
		if (this.currentMap > Config.MAX_LEVEL_COUNT){
			AfterCreditPopup afterCreditPopup = new AfterCreditPopup(0, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, stackPane);
			afterCreditPopup.setGameController(this);
			popupDrawer = new PopupDrawer(afterCreditPopup.getPopupCanvas().getGraphicsContext2D(), afterCreditPopup.getPopupEntities());
			afterCreditPopup.getPopupCanvas().toFront();
		}
		else {
			isWonPopupShowUp = false;
			this.field = new GameField(GameStage.load("/stage/map" + currentMap + ".txt", false));

			this.towerPicker = null;
			if (pause) gamePause();

			contextArea.setUpperContext(new NormalUIContext(field.getTickCount(), contextArea.getUpperContextPos(), field.getMoney(), field.getHealth()));
			contextArea.setLowerContext(null);
			// The drawer. Nothing fun here.
			drawer.setTowerPicker(null);
			drawer.setGameField(field);
		}
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

		//update the values in context so it match the current field, as fast as possible
		contextArea.updateContext(field.getMoney(), field.getHealth(), towerPicker);
		if (field.getHealth() == 0)
		{
			GameOverPopup gameOverPopup = new GameOverPopup(0, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, stackPane);
			gameOverPopup.setGameController(this);
			popupDrawer = new PopupDrawer(gameOverPopup.getPopupCanvas().getGraphicsContext2D(), gameOverPopup.getPopupEntities());
			gamePause();
		}
		else if (field.isWon() && !isWonPopupShowUp)
		{
			isWonPopupShowUp = true;
			WinPopup winPopup = new WinPopup(0, 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, stackPane);
			popupDrawer = new PopupDrawer(winPopup.getPopupCanvas().getGraphicsContext2D(), winPopup.getPopupEntities());
			winPopup.setGameController(this);
		}

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
	final void keyDownHandler(KeyEvent keyEvent) {
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
			case T:
				nextMap();
				break;
		}
		drawer.setTowerPicker(towerPicker);
	}

	/**
	 * Key up handler.
	 *
	 * @param keyEvent the key that you release up.
	 */
	final void keyUpHandler(KeyEvent keyEvent) {
		final KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.W) {
		} else if (keyCode == KeyCode.S) {
		} else if (keyCode == KeyCode.A) {
		} else if (keyCode == KeyCode.D) {
		} else if (keyCode == KeyCode.I) {
		} else if (keyCode == KeyCode.J) {
		} else if (keyCode == KeyCode.K) {
		} else if (keyCode == KeyCode.L) {
		}
	}

	/**
	 * Mouse down handler.
	 *
	 * @param mouseEvent the mouse button you press down.
	 */
	final void mouseDownHandler(MouseEvent mouseEvent) { }

	/**
	 * Mouse up handler.
	 *
	 * @param mouseEvent the mouse button you release up.
	 */
	final void mouseUpHandler(MouseEvent mouseEvent) { }

	final void mouseClickHandler(MouseEvent mouseEvent) {
		Collection<UIEntity> UIEntities = this.gameUI.getEntities();
		Collection<GameEntity> gameEntities = this.field.getEntities();
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();

		if ((Double.compare(mousePosX, (double)Config.TILE_HORIZONTAL * drawer.getFieldZoom()) < 0)
				&& (Double.compare(mousePosY, (double)Config.TILE_VERTICAL * drawer.getFieldZoom()) < 0)) {
			//infield
			if (mouseEvent.getButton() == MouseButton.PRIMARY) {
				if ((towerPicker != null) && (towerPicker.getPickingState() == towerPicker.PICKABLE)) {
					if (towerPicker instanceof TowerPlacing) {
						field.doSpawn(((TowerPlacing) towerPicker).getTower());
						field.setMoney(field.getMoney() - ((TowerPlacing) towerPicker).getTowerPrice());
					} else {
						for (GameEntity entity : gameEntities) {
							if ((entity instanceof AbstractTower) && (towerPicker.isOverlappedWithTower(entity))) {
								if (towerPicker instanceof TowerUpgrading) {
									if (((TowerUpgrading) towerPicker).getUpgradePrice(entity) <= field.getMoney()) {
										((AbstractTower) entity).upgrade();
										// Effect
										this.field.addSFX(new UpgradeEffect(0, entity.getPosX(), entity.getPosY()));
										field.setMoney(field.getMoney() - ((TowerUpgrading) towerPicker).getUpgradePrice(entity));
									}
								} else if (towerPicker instanceof TowerSelling) {
									((AbstractTower) entity).doDestroy();
									((AbstractTower) entity).onDestroy(field);
									field.setMoney(field.getMoney() + ((TowerSelling) towerPicker).getSellPrice(entity));
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

	final void mouseMoveHandler(MouseEvent mouseEvent) {
		Collection<UIEntity> UIEntities = this.gameUI.getEntities();
		Collection<GameEntity> gameEntities = this.field.getEntities();
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();

		if ((Double.compare(mousePosX, (double)Config.TILE_HORIZONTAL * drawer.getFieldZoom()) < 0)
				&& (Double.compare(mousePosY, (double)Config.TILE_VERTICAL * drawer.getFieldZoom()) < 0)) {
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

	public void moveToMenuScene() {
		scheduledFuture.cancel(true);
		stop();
		Canvas menuCanvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		GraphicsContext graphicsContext = menuCanvas.getGraphicsContext2D();
		GameController gameController = null;
		MenuController menuController = new MenuController(graphicsContext, stackPane);
		// prevent user press tab to change focus
		menuCanvas.setFocusTraversable(false);
		menuCanvas.setOnMouseClicked(menuController::mouseClickHandler);
		menuCanvas.setOnMouseMoved(menuController::mouseMoveHandler);
		menuCanvas.setOnKeyPressed(menuController::keyDownHandler);
        stackPane.getChildren().clear();
		stackPane.getChildren().add(menuCanvas);
		menuController.start();
		stackPane.getChildren().get(0).toFront();
	}
}
