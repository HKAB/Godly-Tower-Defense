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

import mrmathami.thegame.ui.ingame.button.TowerButton;
import mrmathami.thegame.drawer.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
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
	public GameController(GraphicsContext graphicsContext) throws FileNotFoundException {
		// The screen to draw on
		this.graphicsContext = graphicsContext;

		// Just a few acronyms.
		final long width = Config.TILE_HORIZONTAL;
		final long height = Config.TILE_VERTICAL;

		// The game field. Please consider create another way to load a game field.
		// TODO: I don't have much time, so, spawn some wall then :)
		this.field = new GameField(GameStage.load("/stage/map1.txt"));

		this.gameUI = new GameUI("/ui/buttonConfig.dat");

		this.UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0,0);

		// The drawer. Nothing fun here.
		this.drawer = new GameDrawer(graphicsContext, field, gameUI, towerPlacing, UIContext,"/stage/sheet.png", "/ui/button.png");

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

	/**
	 * Key down handler.
	 *
	 * @param keyEvent the key that you press down
	 */
	final void keyDownHandler(KeyEvent keyEvent) {
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

		for (UIEntity entity: UIEntities) {
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
        mousePosX = (long)((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom());
        mousePosY = (long)((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom());
        final boolean inField = (mousePosX < Config.TILE_HORIZONTAL) && (mousePosY < Config.TILE_VERTICAL);
        if (!inField) return;

        for (GameEntity entity: gameEntities) {
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
        	this.field.doSpawn(towerPlacing.getTower());
			towerPlacing = null;
			drawer.setTowerPlacing(null);
		}
	}

	final void mouseMoveHandler(MouseEvent mouseEvent) {
		Collection<UIEntity> UIEntities = this.gameUI.getEntities();
		Collection<GameEntity> gameEntities = this.field.getEntities();
		double mousePosX = mouseEvent.getX();
		double mousePosY = mouseEvent.getY();
		boolean onButton = false;

		/**
		 * check if the cursor is on any button
		 */
		for (UIEntity entity: UIEntities) {
			double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
			double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
			double endX = startX + entity.getWidth() * drawer.getFieldZoom();
			double endY = startY + entity.getHeight() * drawer.getFieldZoom();
			if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
					&& Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
				entity.onFocus();
				onButton = true;
				if ((entity instanceof TowerButton) && (!((TowerButton)entity).getTowerType().equals("Locked"))) {
					UIContext = new ButtonUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0, ((TowerButton)entity).getTowerType());
				}
				else {
					UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0);
				}
				drawer.setUIContext(UIContext);
			} else {
				entity.outFocus();
			}
		}
		if (onButton) {
			if (towerPlacing != null) towerPlacing.setPlacingState(towerPlacing.NOT_BEING_PLACED);
			return;
		}
		UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0);

		mousePosX = (long)((mousePosX - drawer.getFieldStartPosX()) / drawer.getFieldZoom());
		mousePosY = (long)((mousePosY - drawer.getFieldStartPosY()) / drawer.getFieldZoom());
		boolean inField = (mousePosX < Config.TILE_HORIZONTAL) && (mousePosY < Config.TILE_VERTICAL);
		if (towerPlacing != null) {
			if (!inField) towerPlacing.setPlacingState(towerPlacing.NOT_BEING_PLACED);
			else {
				towerPlacing.setPlacingState(towerPlacing.PLACEABLE);
				towerPlacing.setPosition((long) mousePosX, (long) mousePosY);
			}
		}

		boolean onTower = false;
		for (GameEntity entity : gameEntities) {
			if (entity instanceof Road) {
				if ((towerPlacing != null) && (towerPlacing.isOverlappedWithRoad(entity))) {
					towerPlacing.setPlacingState(towerPlacing.NOT_PLACEABLE);
					break;
				}
			} else if (entity instanceof AbstractTower) {
				if (towerPlacing != null) {
					if (towerPlacing.isOverlappedWithTower(entity)) {
						towerPlacing.setPlacingState(towerPlacing.NOT_PLACEABLE);
						break;
					}
				}
				else {
					if (entity.isBeingOverlapped(mousePosX, mousePosY, 1, 1)) {
						UIContext = new TowerUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0, (AbstractTower)entity);
						drawer.setUIContext(UIContext);
						onTower = true;
					}
				}
			}
		}
		if ((towerPlacing == null) && (!onTower)) {
			UIContext = new NormalUIContext(field.getTickCount(), field.getMoney(), field.getTargetHealth(), 0, 0);
			drawer.setUIContext(UIContext);
		}
	}
}
