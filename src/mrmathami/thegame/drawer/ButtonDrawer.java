package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.bar.button.AbstractButton;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;

public class ButtonDrawer implements UIDrawer {

    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double fieldZoom) {
        Image img = GameDrawer.getButtonImage();
        PixelReader reader = img.getPixelReader();

        AbstractButton button = (AbstractButton)entity;

        WritableImage buttonImage = new WritableImage(reader, (int)button.getAssetPosX() * (int)fieldZoom, (int)button.getAssetPosY() * (int)fieldZoom, (int)screenWidth, (int)screenHeight);
        graphicsContext.drawImage(buttonImage, screenPosX, screenPosY);
    }
}
