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
import mrmathami.thegame.entity.tile.effect.UpgradeEffect;

import javax.annotation.Nonnull;

public final class UpgradeEffectDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity,
                     double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        screenPosX += 16;
        Image imgSheet = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(imgSheet.getWidth()/Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(imgSheet.getHeight()/Config.TILE_SIZE);
        PixelReader imgSheetPixelReader = imgSheet.getPixelReader();

        WritableImage upgradeImage = new WritableImage(imgSheetPixelReader,
                (((UpgradeEffect)entity).getGID() - 1) % maxTileWidth * (int)screenWidth,
                (((UpgradeEffect)entity).getGID() - 1) / maxTileWidth * (int)screenHeight,
                (int)screenWidth, (int)screenHeight);
        PixelReader upgradeImagePixelReader = upgradeImage.getPixelReader();
        PixelWriter upgradeImagePixelWriter = upgradeImage.getPixelWriter();

        for (int y = 0; y < upgradeImage.getHeight(); y++) {
            for (int x = 0; x < upgradeImage.getWidth(); x++) {
                if (!upgradeImagePixelReader.getColor(x, y).equals(Color.rgb(0, 0, 0, 0))) {
                    upgradeImagePixelWriter.setColor(x, y, Color.rgb((int) (upgradeImagePixelReader.getColor(x, y).getRed() * 255),
                            (int)(upgradeImagePixelReader.getColor(x, y).getGreen() * 255),
                            (int) (upgradeImagePixelReader.getColor(x, y).getBlue() * 255),
                            ((UpgradeEffect) entity).getBlur()));
                }
            }
        }
        ((UpgradeEffect)entity).setPosY(entity.getPosY() - 0.1);

        graphicsContext.drawImage(upgradeImage, screenPosX, screenPosY);
    }

}
