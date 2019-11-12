package mrmathami.thegame.drawer.menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.drawer.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.menu.OptionsButton;
import mrmathami.thegame.ui.menu.PlayButton;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class OptionsButtonDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        String imageUri = ((OptionsButton)entity).getImageUri();
        Image image = new Image(getClass().getResourceAsStream(imageUri));
        graphicsContext.drawImage(image, screenPosX, screenPosY, screenWidth, screenHeight);
    }
}
