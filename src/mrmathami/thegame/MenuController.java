package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.WindowEvent;
import mrmathami.thegame.drawer.UI.Menu.MenuDrawer;
import mrmathami.thegame.drawer.UI.Popup.PopupDrawer;
import mrmathami.thegame.entity.UIEntity;
<<<<<<< HEAD
import mrmathami.thegame.ui.menu.*;
=======
import mrmathami.thegame.net.MPConfig;
import mrmathami.thegame.net.MPGameController;
import mrmathami.thegame.net.MPSocketController;
import mrmathami.thegame.ui.menu.CreditsButton;
import mrmathami.thegame.ui.menu.MultiPlayerButton;
import mrmathami.thegame.ui.menu.SettingsButton;
import mrmathami.thegame.ui.menu.SinglePlayerButton;
>>>>>>> a0e807c48e1cb079850b1d248c731f9fc9997635
import mrmathami.thegame.ui.popup.CreditPopup;
import mrmathami.thegame.ui.popup.MPPopup;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
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
     * Root. Needed for changing scene.
     */
    private StackPane stackPane;

    /**
     * The screen to draw on. Just don't touch me. Google me if you are curious.
     */
    private final GraphicsContext graphicsContext;

    /**
     * Game drawer. Responsible to draw the field every tick.
     * Kinda advance, modify if you are sure about your change.
     */
    private MenuDrawer drawer;

    /**
     * Popup Drawer. Draw popup every tick if exist.
     */
    private PopupDrawer popupDrawer;

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

    public MenuController(GraphicsContext graphicsContext, StackPane stackPane) {
        this.graphicsContext = graphicsContext;
        this.stackPane = stackPane;

        // Just a few acronyms.
        final long width = Config.TILE_HORIZONTAL;
        final long height = Config.TILE_VERTICAL;

        this.menuUI = new MenuUI("/menu/buttonConfig.dat");
        this.drawer = new MenuDrawer(graphicsContext, menuUI, "/menu/background.png", "/menu/button.png");
        this.popupDrawer = null;
        drawer.setFieldViewRegion(0.0, 0.0, Config.TILE_SIZE);
    }

    /**
     * Beat-keeper. Just don't touch me.
     */
    private void tick() {
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

        // draw a new frame, as fast as possible.
        try {
            drawer.render();
            if (stackPane.getChildren().size() > 1) {
                if (popupDrawer != null) {
                    popupDrawer.render();
                }
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

    private void moveToGameScene() {
        scheduledFuture.cancel(true);
        stop();
        Canvas gameCanvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        GraphicsContext graphicsContext = gameCanvas.getGraphicsContext2D();
        GameController gameController = null;
        try {
            gameController = new GameController(graphicsContext, stackPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnMouseClicked(gameController::mouseClickHandler);
        gameCanvas.setOnMouseMoved(gameController::mouseMoveHandler);
        gameCanvas.setOnKeyPressed(gameController::keyDownHandler);
        stackPane.getChildren().add(gameCanvas);
        gameController.start();
    }
<<<<<<< HEAD
=======

>>>>>>> a0e807c48e1cb079850b1d248c731f9fc9997635

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
    public final void keyDownHandler(KeyEvent keyEvent) { }

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
    final void mouseDownHandler(MouseEvent mouseEvent) { }

    /**
     * Mouse up handler.
     *
     * @param mouseEvent the mouse button you release up.
     */
    final void mouseUpHandler(MouseEvent mouseEvent) { }

    public final void mouseMoveHandler(MouseEvent mouseEvent) {
        Collection<UIEntity> UIEntities = this.menuUI.getEntities();
        double mousePosX = mouseEvent.getX();
        double mousePosY = mouseEvent.getY();

        for (UIEntity entity: UIEntities) {
            double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
            double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
            double endX = startX + entity.getWidth() * drawer.getFieldZoom();
            double endY = startY + entity.getHeight() * drawer.getFieldZoom();
            if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
                    && Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
                entity.onFocus();
            } else {
                entity.outFocus();
            }
        }
    }

    public final void mouseClickHandler(MouseEvent mouseEvent) {
        Collection<UIEntity> UIEntities = this.menuUI.getEntities();
        double mousePosX = mouseEvent.getX();
        double mousePosY = mouseEvent.getY();

        Iterator<UIEntity> iterator = UIEntities.iterator();
        while (iterator.hasNext()){
            UIEntity entity = iterator.next();
            double startX = (entity.getPosX() - drawer.getFieldStartPosX()) * drawer.getFieldZoom();
            double startY = (entity.getPosY() - drawer.getFieldStartPosY()) * drawer.getFieldZoom();
            double endX = startX + entity.getWidth() * drawer.getFieldZoom();
            double endY = startY + entity.getHeight() * drawer.getFieldZoom();
            if (Double.compare(mousePosX, startX) >= 0 && Double.compare(mousePosX, endX) <= 0
                    && Double.compare(mousePosY, startY) >= 0 && Double.compare(mousePosY, endY) <= 0) {
                if (entity instanceof SinglePlayerButton) {
                    moveToGameScene();
                    break;
                } else if (entity instanceof MultiPlayerButton) {
                    MPPopup mpPopup = new MPPopup(0,(Config.SCREEN_WIDTH - Config.CREDIT_POPUP_WIDTH)/2, (Config.SCREEN_HEIGHT - Config.CREDIT_POPUP_HEIGHT)/2, Config.CREDIT_POPUP_WIDTH, Config.CREDIT_POPUP_HEIGHT, stackPane);
                    popupDrawer = new PopupDrawer(mpPopup.getPopupCanvas().getGraphicsContext2D(), mpPopup.getPopupEntities());
                    break;
                } else if (entity instanceof SettingsButton) {
                    break;
                } else if (entity instanceof CreditsButton) {
                    CreditPopup creditPopup = new CreditPopup(0,(Config.SCREEN_WIDTH - Config.CREDIT_POPUP_WIDTH)/2, (Config.SCREEN_HEIGHT - Config.CREDIT_POPUP_HEIGHT)/2, Config.CREDIT_POPUP_WIDTH, Config.CREDIT_POPUP_HEIGHT, stackPane);
                    popupDrawer = new PopupDrawer(creditPopup.getPopupCanvas().getGraphicsContext2D(), creditPopup.getPopupEntities());
                    break;
                }
            }
        }
    }
}
