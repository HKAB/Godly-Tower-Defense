package mrmathami.thegame.drawer.Entity.Tile.Effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.effect.TowerDestroyEffect;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class TowerDestroyEffectDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Image imgSheet = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(imgSheet.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(imgSheet.getHeight()/Config.TILE_SIZE);
        PixelReader imgSheetPixelReader = imgSheet.getPixelReader();

        WritableImage towerDestroyImage = new WritableImage(imgSheetPixelReader, (((TowerDestroyEffect)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, (((TowerDestroyEffect)entity).getGID() - 1) / maxTileWidth * (int)screenHeight, (int)screenWidth, (int)screenHeight);
        PixelReader towerDestroyImagePixelReader = towerDestroyImage.getPixelReader();
        PixelWriter towerDestroyImagePixelWriter = towerDestroyImage.getPixelWriter();
        // With this specific effect, color is (0, 0, 0, opacity), so we had to use its opacity.
        for (int y = 0; y < towerDestroyImage.getHeight(); y++) {
            for (int x = 0; x < towerDestroyImage.getWidth(); x++) {
                if (!towerDestroyImagePixelReader.getColor(x, y).equals(Color.rgb(0, 0, 0, 0))) {
                    towerDestroyImagePixelWriter.setColor(x, y, Color.rgb((int) (towerDestroyImagePixelReader.getColor(x, y).getRed() * 255), (int) (towerDestroyImagePixelReader.getColor(x, y).getGreen() * 255.0), (int) (towerDestroyImagePixelReader.getColor(x, y).getBlue() * 255.0), towerDestroyImagePixelReader.getColor(x, y).getOpacity()*((TowerDestroyEffect)entity).getBlur()));
                }
            }
        }

        graphicsContext.drawImage(towerDestroyImage, screenPosX, screenPosY);
    }

}
