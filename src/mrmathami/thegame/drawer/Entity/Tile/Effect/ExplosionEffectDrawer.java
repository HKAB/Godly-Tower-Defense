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
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class ExplosionEffectDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Image imgSheet = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(imgSheet.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(imgSheet.getHeight()/Config.TILE_SIZE);
        PixelReader imgSheetPixelReader = imgSheet.getPixelReader();

        WritableImage explosionImage = new WritableImage(imgSheetPixelReader, (((ExplosionEffect)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, (((ExplosionEffect)entity).getGID() - 1) / maxTileWidth * (int)screenHeight, (int)screenWidth, (int)screenHeight);
        PixelReader explosionImagePixelReader = explosionImage.getPixelReader();
        PixelWriter explosionImagePixelWriter = explosionImage.getPixelWriter();

        for (int y = 0; y < explosionImage.getHeight(); y++) {
            for (int x = 0; x < explosionImage.getWidth(); x++) {
                if (!explosionImagePixelReader.getColor(x, y).equals(Color.rgb(0, 0, 0, 0))) {
                    explosionImagePixelWriter.setColor(x, y, Color.rgb((int) (explosionImagePixelReader.getColor(x, y).getRed() * 255), (int) (explosionImagePixelReader.getColor(x, y).getGreen() * 255), (int) (explosionImagePixelReader.getColor(x, y).getBlue() * 255), ((ExplosionEffect) entity).getBlur()));
                }
            }
        }

        graphicsContext.drawImage(explosionImage, screenPosX, screenPosY);
    }

}
