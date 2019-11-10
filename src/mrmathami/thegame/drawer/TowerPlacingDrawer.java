package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.TowerPlacing;
import mrmathami.thegame.entity.enemy.NormalEnemy;

import javax.annotation.Nonnull;

public class TowerPlacingDrawer {
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull TowerPlacing towerPlacing, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double fieldZoom) {
        if (towerPlacing.getPlacingState() == towerPlacing.NOT_BEING_PLACED) return;

        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, (towerPlacing.getTower().getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((towerPlacing.getTower().getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);
        graphicsContext.drawImage(newImage, screenPosX, screenPosY);

        if (towerPlacing.getPlacingState() == towerPlacing.NOT_PLACEABLE) {
            graphicsContext.setFill(Color.rgb(255, 0, 0, 0.4));
            graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
        }
    }
}
