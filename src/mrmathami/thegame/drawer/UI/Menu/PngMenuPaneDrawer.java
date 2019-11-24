package mrmathami.thegame.drawer.UI.Menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.ui.menu.PngMenuPane;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class PngMenuPaneDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        String imageUri = ((PngMenuPane)entity).getImageUri();
        Image image = new Image(getClass().getResourceAsStream(imageUri));
        graphicsContext.drawImage(image, screenPosX, screenPosY, screenWidth, screenHeight);
    }
}
