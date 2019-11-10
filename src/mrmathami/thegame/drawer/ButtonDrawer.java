package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.bar.NormalButton;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class ButtonDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        String imageUri = ((NormalButton)entity).getImageUri();
        Image image = new Image(getClass().getResourceAsStream(imageUri));
        graphicsContext.drawImage(image, screenPosX, screenPosY, 64, 64);
    }
}
