package mrmathami.thegame.drawer.UI.Popup;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class ClosePopupButtonDrawer implements UIEntityDrawer {


    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Image buttonImage = new Image(getClass().getResourceAsStream("/ui/close-button.png"));
        PixelReader reader = buttonImage.getPixelReader();
        WritableImage closeButton = new WritableImage(reader, 0, 0, (int) Config.TILE_SIZE, (int)Config.TILE_SIZE);
        graphicsContext.drawImage(closeButton, screenPosX, screenPosY);
    }
}
