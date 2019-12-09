package mrmathami.thegame.drawer.Entity.Tile.Effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.effect.AlertEffect;

import javax.annotation.Nonnull;

public final class AlertEffectDrawer implements EntityDrawer {
    private int offset = 0;
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity,
                     double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        screenPosX += Config.OFFSET;
        Image imgSheet = GameDrawer.getSheetImage();
        Image imgEmote = GameDrawer.getEmoteImage();

        int maxTileWidth = (int)Math.round(imgEmote.getWidth()/32);
        int maxTileHeight = (int)Math.round(imgEmote.getHeight()/38);
        PixelReader imgEmotePixelReader = imgEmote.getPixelReader();

        WritableImage emoteImage = new WritableImage(imgEmotePixelReader,
                (((AlertEffect)entity).getGID() - 1) % maxTileWidth * (int)screenWidth,
                (((AlertEffect)entity).getGID() - 1) / maxTileWidth * (int)screenHeight,
                (int)screenWidth, (int)screenHeight);
        PixelReader emoteImagePixelReader = emoteImage.getPixelReader();
        PixelWriter emoteImagePixelWriter = emoteImage.getPixelWriter();

        for (int y = 0; y < emoteImage.getHeight(); y++) {
            for (int x = 0; x < emoteImage.getWidth(); x++) {
                if (!emoteImagePixelReader.getColor(x, y).equals(Color.rgb(0, 0, 0, 0))) {
                    emoteImagePixelWriter.setColor(x, y, Color.rgb((int) (emoteImagePixelReader.getColor(x, y).getRed() * 255),
                            (int)(emoteImagePixelReader.getColor(x, y).getGreen() * 255),
                            (int)(emoteImagePixelReader.getColor(x, y).getBlue() * 255)));
                }
            }
        }

        if (((AlertEffect) entity).getContentGID() != 0)
        {
            maxTileWidth = (int)Math.round(imgSheet.getWidth()/Config.TILE_SIZE);
            maxTileHeight = (int)Math.round(imgSheet.getHeight()/Config.TILE_SIZE);

            PixelReader imgSheetPixelReader = imgSheet.getPixelReader();
            WritableImage contentImage = new WritableImage(imgSheetPixelReader, (((AlertEffect) entity).getContentGID() - 1) % maxTileWidth * (int)Config.TILE_SIZE, (((AlertEffect) entity).getContentGID() - 1) / maxTileWidth * (int)Config.TILE_SIZE, (int)Config.TILE_SIZE, (int)Config.TILE_SIZE);
            ImageView imageView = new ImageView(contentImage);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(Config.TILE_SIZE/2.0);
            imageView.setFitWidth(Config.TILE_SIZE/2.0);
            Image contentScaleImage = imageView.snapshot(null, null);
            PixelReader contentImagePixelReader = contentScaleImage.getPixelReader();

            for (int y = 0; y < contentScaleImage.getHeight(); y++) {
                for (int x = 0; x < contentScaleImage.getWidth(); x++) {
                    if (!contentImagePixelReader.getColor(x, y).equals(Color.rgb(255, 255, 255))) {
                        emoteImagePixelWriter.setColor(x, y, Color.rgb((int) (contentImagePixelReader.getColor(x, y).getRed() * 255), (int) (contentImagePixelReader.getColor(x, y).getGreen() * 255), (int) (contentImagePixelReader.getColor(x, y).getBlue() * 255)));
                    }
                }
            }
        }

        graphicsContext.drawImage(emoteImage, screenPosX, screenPosY+ 2*Math.sin(offset));
        offset++;
    }

}
