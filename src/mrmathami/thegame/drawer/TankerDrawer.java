
package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.Tanker;
import mrmathami.thegame.entity.enemy.Tanker;
import mrmathami.thegame.entity.tile.tower.NormalTower;

import javax.annotation.Nonnull;
import java.util.WeakHashMap;

public final class TankerDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {

        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        
        WritableImage baseImage = new WritableImage(reader, (((Tanker)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), (Math.round((((Tanker)entity).getGID() - 1) / maxTileWidth) * (int)(Config.TILE_SIZE)), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        WritableImage barrelImage = new WritableImage(reader, (((Tanker)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), (Math.round((((Tanker)entity).getGID() - 1) / maxTileWidth + 1) * (int)(Config.TILE_SIZE)), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        WritableImage tankerImage = new WritableImage((int)Config.TILE_SIZE, (int)Config.TILE_SIZE);
        PixelReader baseImagePixelReader =  baseImage.getPixelReader();
        PixelReader barrelImagePixelReader =  barrelImage.getPixelReader();
        PixelWriter pixelWriterTanker = tankerImage.getPixelWriter();

        for (int y = 0; y < baseImage.getHeight(); y++) {
            for (int x = 0; x < baseImage.getWidth(); x++) {
                pixelWriterTanker.setColor(x, y, baseImagePixelReader.getColor(x, y));
            }
        }

        for (int y = 0; y < tankerImage.getHeight(); y++) {
            for (int x = 0; x < tankerImage.getWidth(); x++) {
                if (!barrelImagePixelReader.getColor(x, y).equals(Color.rgb(0, 0, 0, 0)))
                    pixelWriterTanker.setColor(x, y, barrelImagePixelReader.getColor(x, y));
            }
        }
        
        ((Tanker)entity).rotate(graphicsContext, tankerImage, screenPosX, screenPosY, ((Tanker)entity).getAngle() - 90);
    }
}
