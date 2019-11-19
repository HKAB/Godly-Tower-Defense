package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.towerpicker.AbstractTowerPicker;
import mrmathami.thegame.towerpicker.TowerPlacing;

import javax.annotation.Nonnull;

public class TowerPickerDrawer {
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull AbstractTowerPicker towerPicker, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double fieldZoom) {
        if (towerPicker.getPickingState() == towerPicker.NOT_BEING_PICKED) return;

        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, (towerPicker.getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((towerPicker.getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);
        graphicsContext.drawImage(newImage, screenPosX, screenPosY);

        if (towerPicker.getPickingState() == towerPicker.NOT_PICKABLE) {
            graphicsContext.setFill(Color.rgb(255, 0, 0, 0.4));
            graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
        }
    }
}
