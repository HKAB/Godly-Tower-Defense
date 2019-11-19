package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Bush;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.util.Random;

public final class BushDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();

        WritableImage bushImage = new WritableImage(reader, (((Bush)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((((Bush)entity).getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);

        graphicsContext.drawImage(bushImage, screenPosX, screenPosY);
    }
}
