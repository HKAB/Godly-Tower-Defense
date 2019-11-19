package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import mrmathami.thegame.drawer.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.TowerPlacing;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;
import mrmathami.thegame.net.MPConfig;
import mrmathami.thegame.net.MPGameField;
import mrmathami.thegame.net.MPSocketController;
import mrmathami.thegame.ui.button.TowerButton;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
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
	 * Tower Placing Class. Using for place a tower.
	 */
	private TowerPlacing towerPlacing = null;

	/**
	 * Game Client socket wrapper. Used to send data to Server.
	 */
	private MPSocketController socket;

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

		// Get current socket connection instance.
		this.socket = MPSocketController.getCurrentInstance();

		this.field = new GameField(GameStage.load("/stage/mapMP.txt", false));
		this.opponentField = new MPGameField(GameStage.load("/stage/mapMP.txt", true));

		this.gameUI = new GameUI("/ui/MPButtonConfig.dat");

		// The drawer. Nothing fun here.
		this.drawer = new GameDrawer(graphicsContext, field, opponentField, gameUI, towerPlacing,"/stage/sheet.png", "/ui/button.png");

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
		final long currentTick = tick;
		final long startNs = System.nanoTime();

		field.tick();
		opponentField.tick();

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
		graphicsContext.fillText(String.format("MSPT: %3.2f", mspt), 0, 12);

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
					if ((entity instanceof TowerButton) && (!entity.onClick().equals("Locked"))) {
						towerPlacing = new TowerPlacing(entity.onClick());
						drawer.setTowerPlacing(towerPlacing);
					}
					return;
				}
			}
			mousePosX = (long) ((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom());
			mousePosY = (long) ((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom());
			final boolean lg = (mousePosX < Config.TILE_HORIZONTAL) && (mousePosY < Config.TILE_VERTICAL);
			if (!lg) return;

			for (GameEntity entity : gameEntities) {
				if (!(entity instanceof Mountain)) {
					if (entity.isBeingOverlapped(mousePosX, mousePosY, 1, 1)) {
						if (entity instanceof AbstractTower) {
							//call onClick() of tower
							return;
						}
					}
				}
			}
			if ((towerPlacing != null) && (towerPlacing.getPlacingState() == towerPlacing.PLACEABLE)) {
				AbstractTower tower = towerPlacing.getTower();
				this.field.doSpawn(tower);

				// Send data to server.
				String towerType = "";
				if (tower instanceof NormalTower) towerType = "1";
				else if (tower instanceof MachineGunTower) towerType = "2";
				else if (tower instanceof RocketLauncherTower) towerType = "3";
				else towerType = "0";
				socket.sendCommand(List.of("PLACE", towerType, String.format("%.0f", mousePosX), String.format("%.0f", mousePosY)));
				towerPlacing = null;
				drawer.setTowerPlacing(towerPlacing);
			}
		}
	}

	final void mouseMoveHandler(MouseEvent mouseEvent) {
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();
		if (mousePosX < MPConfig.OPPONENT_START_PX_X) {
			Collection<UIEntity> UIEntities = this.gameUI.getEntities();
			Collection<GameEntity> gameEntities = this.field.getEntities();
			boolean lg = false;

			for (UIEntity entity : UIEntities) {
				double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
				double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
				double endX = startX + entity.getWidth() * drawer.getFieldZoom();
				double endY = startY + entity.getHeight() * drawer.getFieldZoom();
				if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
						&& Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
					entity.onFocus();
					lg = true;
				} else {
					entity.outFocus();
				}
			}
			if (lg && (this.towerPlacing != null)) towerPlacing.setPlacingState(towerPlacing.NOT_BEING_PLACED);
			if (lg || (towerPlacing == null)) return;

			mousePosX = (long) ((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom());
			mousePosY = (long) ((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom());
			lg = (mousePosX < Config.TILE_HORIZONTAL) && (mousePosY < Config.TILE_VERTICAL);
			if (!lg) towerPlacing.setPlacingState(towerPlacing.NOT_BEING_PLACED);
			else {
				towerPlacing.setPlacingState(towerPlacing.PLACEABLE);
				towerPlacing.setPosition(mousePosX, mousePosY);
			}

			for (GameEntity entity : gameEntities) {
				if (entity instanceof Road) {
					if (towerPlacing.isOverlappedWithRoad(entity)) {
						towerPlacing.setPlacingState(towerPlacing.NOT_PLACEABLE);
						break;
					}
				}
				if (entity instanceof AbstractTower) {
					if (towerPlacing.isOverlappedWithTower(entity)) {
						towerPlacing.setPlacingState(towerPlacing.NOT_PLACEABLE);
						break;
					}
				}
			}
			drawer.setTowerPlacing(this.towerPlacing);
		}
	}
}
