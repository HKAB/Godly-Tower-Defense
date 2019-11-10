package mrmathami.thegame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * Main class. Entry point of the game.
 */

public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
	    Group root = new Group();

	    // Prepare game Canvas
		final Canvas menuCanvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		final GraphicsContext menuGraphicsContext = menuCanvas.getGraphicsContext2D();
		final MenuController menuController = new MenuController(menuGraphicsContext, root);
		menuCanvas.setFocusTraversable(true);
		menuGraphicsContext.setFontSmoothingType(FontSmoothingType.LCD);

		// Creating the scene
		Scene mainScene = new Scene(root);
		root.getChildren().add(menuCanvas);

		// Event handler
		menuCanvas.setOnMouseClicked(menuController::mouseClickHandler);

		primaryStage.setResizable(false);
		primaryStage.setTitle(Config.GAME_NAME);
        primaryStage.setScene(mainScene);
		primaryStage.show();
        primaryStage.setOnCloseRequest(menuController::closeRequestHandler);
        menuController.start();
	}
}