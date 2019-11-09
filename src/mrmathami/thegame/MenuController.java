package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import mrmathami.thegame.drawer.UIDrawer;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class MenuController extends AnimationTimer {
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
     * Game drawer. Responsible to draw the field every tick.
     * Kinda advance, modify if you are sure about your change.
     */
    private UIDrawer drawer;

    /**
     * Menu UI. Contains UI elements.
     */
    private MenuUI menuUI;

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
    public MenuController(GraphicsContext graphicsContext) throws FileNotFoundException {
        this.graphicsContext = graphicsContext;

        // Just a few acronyms.
        final long width = Config.TILE_HORIZONTAL;
        final long height = Config.TILE_VERTICAL;

        this.menuUI = new MenuUI();
        this.drawer = new UIDrawer(graphicsContext, menuUI, "/menu/blurry_background.png");
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

//		// if it's too late to draw a new frame, skip it.
//		// make the game feel really laggy, so...
//		if (currentTick != tick) return;

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

    /**
     * Key down handler.
     *
     * @param keyEvent the key that you press down
     */
    final void keyDownHandler(KeyEvent keyEvent) { }

    /**
     * Key up handler.
     *
     * @param keyEvent the key that you release up.
     */
    final void keyUpHandler(KeyEvent keyEvent) { }

    /**
     * Mouse down handler.
     *
     * @param mouseEvent the mouse button you press down.
     */
    final void mouseDownHandler(MouseEvent mouseEvent) {
//		mouseEvent.getButton(); // which mouse button?
//		// Screen coordinate. Remember to convert to field coordinate
//		drawer.screenToFieldPosX(mouseEvent.getX());
//		drawer.screenToFieldPosY(mouseEvent.getY());
    }

    /**
     * Mouse up handler.
     *
     * @param mouseEvent the mouse button you release up.
     */
    final void mouseUpHandler(MouseEvent mouseEvent) {
//		mouseEvent.getButton(); // which mouse button?
//		// Screen coordinate. Remember to convert to field coordinate
//		drawer.screenToFieldPosX(mouseEvent.getX());
//		drawer.screenToFieldPosY(mouseEvent.getY());
    }

    final void mouseMoveHandler(MouseEvent mouseEvent) {

    }

    final void mouseClickHandler(MouseEvent mouseEvent) {
        System.out.println("Mouse clicked");
    }
}
