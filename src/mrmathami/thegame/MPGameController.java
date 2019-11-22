package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.GameStage;
import mrmathami.thegame.GameUI;
import mrmathami.thegame.drawer.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.tile.Bush;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.Rock;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;
import mrmathami.thegame.net.MPConfig;
import mrmathami.thegame.net.MPGameField;
import mrmathami.thegame.net.MPSocketController;
import mrmathami.thegame.towerpicker.AbstractTowerPicker;
import mrmathami.thegame.towerpicker.TowerPlacing;
import mrmathami.thegame.towerpicker.TowerSelling;
import mrmathami.thegame.towerpicker.TowerUpgrading;
import mrmathami.thegame.ui.ingame.button.ContextButton;
import mrmathami.thegame.ui.ingame.button.TowerButton;
import mrmathami.thegame.ui.ingame.context.AbstractUIContext;
import mrmathami.thegame.ui.ingame.context.ButtonUIContext;
import mrmathami.thegame.ui.ingame.context.NormalUIContext;
import mrmathami.thegame.ui.ingame.context.TowerUIContext;
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
	 * Game drawer. Responsible to draw the field every tick.
	 * Kinda advance, modify if you are sure about your change.
	 */
	private GameDrawer drawer;

	/**
	 * Game UI. Contains UI elements.
	 */
	private GameUI gameUI;

	/**
	 * Game Client socket wrapper. Used to send data to Server.
	 */
	private MPSocketController socket;

	/**
	 * Tower Placing Class. Using for place a tower.
	 */
	private AbstractTowerPicker towerPicker;

	/**
	 * UI Context. Used to display game info on the sidebar
	 */
	private AbstractUIContext UIContext;

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
	 * The constructor.
	 *
	 * @param graphicsContext the screen to draw on
	 */
	public MPGameController(GraphicsContext graphicsContext) throws FileNotFoundException {
		// The screen to draw on
		this.graphicsContext = graphicsContext;

		// Socket. To send data between peers.
		this.socket = MPSocketController.getCurrentInstance();

		// The game field. Please consider create another way to load a game field.
		this.field = new GameField(GameStage.load("/stage/mapMP.txt", false));

		// Opponent's field, for updating opponent state.
		this.opponentField = new MPGameField(GameStage.load("/stage/mapMP.txt", true));

		this.gameUI = new GameUI("/ui/MPButtonConfig.dat");

		this.towerPicker = null;

		this.UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0,0);

		// The drawer. Nothing fun here.
		this.drawer = new GameDrawer(graphicsContext, field, opponentField, gameUI, towerPicker, UIContext,"/stage/sheet.png", "/ui/button.png");

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

		//update the values in context so it match the current field, as fast as possible
		UIContext.fieldUpdate(field.getMoney(), field.getTargetHealth(), 0, 0);

		// draw a new frame, as fast as possible.
		try {
			drawer.render();
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

	final void mouseClickHandler(MouseEvent mouseEvent) {
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();
		if (mousePosX < MPConfig.OPPONENT_START_PX_X) {
			Collection<UIEntity> UIEntities = this.gameUI.getEntities();
			Collection<GameEntity> gameEntities = this.field.getEntities();
			for (UIEntity entity : UIEntities) {
				double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
				double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
				double endX = startX + entity.getWidth() * drawer.getFieldZoom();
				double endY = startY + entity.getHeight() * drawer.getFieldZoom();
				if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
						&& Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
					if (entity instanceof TowerButton) {
						if (!entity.onClick().equals("Locked")) {
							if ((towerPicker instanceof TowerPlacing) && (entity.onClick().equals(((TowerPlacing) towerPicker).getTowerType()))) {
								towerPicker = null;
								drawer.setTowerPicker(null);
							} else {
								towerPicker = new TowerPlacing(entity.onClick());
								drawer.setTowerPicker(towerPicker);
							}
						}
					} else if (entity instanceof ContextButton) {
						switch (entity.onClick()) {
							case "UpgradeButton":
								if (towerPicker instanceof TowerUpgrading) {
									towerPicker = null;
									drawer.setTowerPicker(null);
								} else {
									towerPicker = new TowerUpgrading();
									drawer.setTowerPicker(towerPicker);
								}
								break;
							case "SellButton":
								if (towerPicker instanceof TowerSelling) {
									towerPicker = null;
									drawer.setTowerPicker(null);
								} else {
									towerPicker = new TowerSelling();
									drawer.setTowerPicker(towerPicker);
								}
								break;
						}
					}
					return;
				}
			}
			mousePosX = (long) ((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom());
			mousePosY = (long) ((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom());
			final boolean inField = (mousePosX < Config.TILE_HORIZONTAL) && (mousePosY < Config.TILE_VERTICAL);
			if (!inField) return;

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
					} else {
						towerType = 0;
					}
					this.socket.sendPlace(towerType, mousePosX, mousePosY);
					// END: multi-player

					field.doSpawn(tower);
					field.setMoney(field.getMoney() - ((TowerPlacing) towerPicker).getTowerPrice());
					towerPicker = null;
					drawer.setTowerPicker(null);
				} else {
					for (GameEntity entity : gameEntities) {
						if ((entity instanceof AbstractTower) && (towerPicker.isOverlappedWithTower(entity))) {
							if (towerPicker instanceof TowerUpgrading) {
								if (((TowerUpgrading) towerPicker).getUpgradePrice(entity) <= field.getMoney()) {
									((AbstractTower) entity).upgrade();
									field.setMoney(field.getMoney() - ((TowerUpgrading) towerPicker).getUpgradePrice(entity));
									this.socket.sendUpgrade(mousePosX, mousePosY);
								}
							} else if (towerPicker instanceof TowerSelling) {
								((AbstractTower) entity).doDestroy();
								field.setMoney(field.getMoney() + ((TowerSelling) towerPicker).getSellPrice(entity));
								this.socket.sendSell(mousePosX, mousePosY);
							}
							break;
						}
					}
					towerPicker = null;
					drawer.setTowerPicker(null);
				}
			}
		}
	}

	final void mouseMoveHandler(MouseEvent mouseEvent) {
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();
		if (mousePosX < MPConfig.OPPONENT_START_PX_X) {
			Collection<UIEntity> UIEntities = this.gameUI.getEntities();
			Collection<GameEntity> gameEntities = this.field.getEntities();
			boolean onButton = false;

			/*
			 * Check if the cursor is on any button
			 */
			for (UIEntity entity : UIEntities) {
				double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
				double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
				double endX = startX + entity.getWidth() * drawer.getFieldZoom();
				double endY = startY + entity.getHeight() * drawer.getFieldZoom();
				if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
						&& Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
					entity.onFocus();
					onButton = true;
					if ((entity instanceof TowerButton) && (!((TowerButton) entity).getTowerType().equals("Locked"))) {
						UIContext = new ButtonUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0, ((TowerButton) entity).getTowerType());
					} else {
						UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0);
					}
					drawer.setUIContext(UIContext);
				} else {
					entity.outFocus();
				}
			}

			if (onButton) {
				if (towerPicker != null) towerPicker.setPickingState(towerPicker.NOT_BEING_PICKED);
				return;
			}
			UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0);
			drawer.setUIContext(UIContext);

			mousePosX = (long) ((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom());
			mousePosY = (long) ((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom());
			boolean inField = (mousePosX < Config.TILE_HORIZONTAL) && (mousePosY < Config.TILE_VERTICAL);

			if (towerPicker != null) {
				if (!inField) towerPicker.setPickingState(towerPicker.NOT_BEING_PICKED);
				else {
					if (towerPicker instanceof TowerPlacing) {
						((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).PLACEABLE);
						towerPicker.setPosition((long) mousePosX, (long) mousePosY);
					} else {
						towerPicker.setPickingState(towerPicker.NOT_PICKABLE);
						towerPicker.setPosition((long) mousePosX, (long) mousePosY);
					}
				}
			}

			if ((towerPicker != null) && (towerPicker instanceof TowerPlacing) && (((TowerPlacing) towerPicker).getTowerPrice() > field.getMoney())) {
				((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
				return;
			}

			boolean onTower = false;
			for (GameEntity entity : gameEntities) {
				if (entity instanceof Road) {
					if ((towerPicker != null) && (towerPicker instanceof TowerPlacing) && (towerPicker.isOverlappedWithRoad(entity))) {
						((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
						break;
					}
				} else if (entity instanceof AbstractTower) {
					if (entity.isBeingOverlapped(mousePosX, mousePosY, 1, 1)) {
						UIContext = new TowerUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0, (AbstractTower) entity);
						drawer.setUIContext(UIContext);
						onTower = true;
					}
					if (towerPicker != null) {
						if (towerPicker.isOverlappedWithTower(entity)) {
							if (towerPicker instanceof TowerPlacing) {
								((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
							} else {
								towerPicker.setPickingState(towerPicker.PICKABLE);
							}
							break;
						}
					}
				} else if ((entity instanceof Rock) || (entity instanceof Bush)) {
					if ((towerPicker != null) && (towerPicker.isOverlappedWithTower(entity))) {
						if (towerPicker instanceof TowerPlacing) {
							((TowerPlacing) towerPicker).setPlacingState(((TowerPlacing) towerPicker).NOT_PLACEABLE);
						}
						break;
					}
				}
			}

			if ((towerPicker == null) && (!onTower)) {
				UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0);
				drawer.setUIContext(UIContext);
			}
			if ((towerPicker != null) && (towerPicker instanceof TowerPlacing)) {
				UIContext = new ButtonUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0, ((TowerPlacing) towerPicker).getTowerType());
				drawer.setUIContext(UIContext);
			}
		}
	}
}
