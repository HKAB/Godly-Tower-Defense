package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.ui.button.AbstractIngameButton;
import mrmathami.thegame.ui.button.TowerButton;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;

public class ButtonDrawer implements UIEntityDrawer {

    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double fieldZoom) {
        Image img = GameDrawer.getButtonImage();
        PixelReader reader = img.getPixelReader();

        AbstractIngameButton button = (AbstractIngameButton)entity;

        WritableImage buttonImage = new WritableImage(reader, (int)button.getAssetPosX() * (int)fieldZoom, (int)button.getAssetPosY() * (int)fieldZoom, (int)screenWidth, (int)screenHeight);
        graphicsContext.drawImage(buttonImage, screenPosX, screenPosY);

        if (entity instanceof TowerButton) {
            img = GameDrawer.getSheetImage();
            reader = img.getPixelReader();

            TowerButton towerButton = (TowerButton)entity;
            int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
            int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);

            buttonImage = new WritableImage(reader, (towerButton.getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((towerButton.getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);
            graphicsContext.drawImage(buttonImage, screenPosX, screenPosY);
        }
    }
}
