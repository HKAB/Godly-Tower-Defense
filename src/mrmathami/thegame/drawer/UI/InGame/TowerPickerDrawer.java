package mrmathami.thegame.drawer.UI.InGame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.GameDrawer;
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

        if (towerPicker instanceof TowerPlacing) {
            graphicsContext.setFill(Color.rgb(255, 255, 255, 0.2));
            System.out.println(screenPosX / fieldZoom + " " + screenPosY / fieldZoom);
            int tilePosX = (int)(screenPosX / fieldZoom);
            int tilePosY = (int)(screenPosY / fieldZoom);
            System.out.println(((TowerPlacing) towerPicker).getRange() + " " + tilePosX + " " + tilePosY);
            for (int posX = (int)Math.max(0, tilePosX - ((TowerPlacing) towerPicker).getRange()); posX <= Math.min(Config.TILE_HORIZONTAL, tilePosX + ((TowerPlacing) towerPicker).getRange()); posX++)
                for (int posY = (int)Math.max(0, tilePosY - ((TowerPlacing) towerPicker).getRange()); posY <= Math.min(Config.TILE_VERTICAL, tilePosY + ((TowerPlacing) towerPicker).getRange()); posY++)
                    if (!((posX == tilePosX) && (posY == tilePosY))) {
                        graphicsContext.fillRect(posX * fieldZoom, posY * fieldZoom, screenWidth, screenHeight);
                    }
        }

        if (towerPicker.getPickingState() == towerPicker.NOT_PICKABLE) {
            graphicsContext.setFill(Color.rgb(255, 0, 0, 0.4));
            graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
        }
    }
}
