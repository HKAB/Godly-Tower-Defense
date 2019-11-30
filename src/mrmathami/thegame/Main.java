package mrmathami.thegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;
import mrmathami.thegame.audio.GameAudio;

import java.io.FileNotFoundException;
import java.security.Provider;
import java.util.concurrent.CountDownLatch;

/**
 * Main class. Entry point of the game.
 */

public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

//	    Group root = new Group();
		StackPane stackPane = new StackPane();
	    // Prepare game Canvas
		final Canvas menuCanvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		final GraphicsContext menuGraphicsContext = menuCanvas.getGraphicsContext2D();
//		final MenuController menuController = new MenuController(menuGraphicsContext, stackPane);
//		menuCanvas.setFocusTraversable(true);
		menuGraphicsContext.setFontSmoothingType(FontSmoothingType.LCD);

		// Creating the scene
//		menuPane.getChildren().add(menuCanvas);
		Scene mainScene = new Scene(stackPane);
		menuCanvas.setFocusTraversable(false);
		stackPane.getChildren().add(menuCanvas);


		final MenuController menuController = new MenuController(menuGraphicsContext, stackPane);

		// Event handler
		menuCanvas.setOnMouseClicked(menuController::mouseClickHandler);
		menuCanvas.setOnMouseMoved(menuController::mouseMoveHandler);

		primaryStage.setResizable(false);
		primaryStage.setTitle(Config.GAME_NAME);
        primaryStage.setScene(mainScene);
		primaryStage.show();
        primaryStage.setOnCloseRequest(menuController::closeRequestHandler);
        menuController.start();
		(new GameAudio()).playThemeSong();
	}
}