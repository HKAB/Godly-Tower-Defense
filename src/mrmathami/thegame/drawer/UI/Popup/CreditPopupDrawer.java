package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.CreditPopup;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class CreditPopupDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        StackPane stackPane = ((CreditPopup)entity).getStackPane();
        Pane pane = new Pane();
        Rectangle rectangle = new Rectangle((int)screenPosX, (int)screenPosY, (int)screenWidth, (int)screenHeight);
        rectangle.setFill(Color.rgb(255, 255, 255));
        pane.setStyle("-fx-background-color: rgba(200, 214, 229, 0.7)");
        pane.getChildren().add(rectangle);
        stackPane.getChildren().add(pane);
    }
}
