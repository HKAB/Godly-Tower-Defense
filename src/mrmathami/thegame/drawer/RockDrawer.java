package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Rock;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class RockDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();

        WritableImage rockImage = new WritableImage(reader, (((Rock)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((((Rock)entity).getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);

        graphicsContext.drawImage(rockImage, screenPosX, screenPosY);

//		graphicsContext.setFill(Color.RED);
//		graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
    }
}
