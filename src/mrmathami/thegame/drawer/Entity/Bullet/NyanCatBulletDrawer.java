package mrmathami.thegame.drawer.Entity.Bullet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.NyanCatBullet;

import javax.annotation.Nonnull;

public class NyanCatBulletDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        screenPosX += Config.OFFSET;
        screenPosY += Config.OFFSET;
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage nyanCatImage = new WritableImage(reader, (((NyanCatBullet)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), Math.round((((NyanCatBullet)entity).getGID() - 1) / maxTileWidth) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        WritableImage rainbowImage = new WritableImage(reader, (((NyanCatBullet)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), Math.round((((NyanCatBullet)entity).getGID() - 1) / maxTileWidth + 1) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));

        reader = nyanCatImage.getPixelReader();
        WritableImage t_nyanCatImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NYAN_CAT_BULLET_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.NYAN_CAT_BULLET_HEIGHT/2), (int)(Config.NYAN_CAT_BULLET_WIDTH), (int)(Config.NYAN_CAT_BULLET_HEIGHT));
        reader = rainbowImage.getPixelReader();
        WritableImage t_rainbowImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NYAN_CAT_RAINBOW_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.NYAN_CAT_RAINBOW_HEIGHT/2), (int)(Config.NYAN_CAT_RAINBOW_WIDTH), (int)(Config.NYAN_CAT_RAINBOW_HEIGHT));


        WritableImage fullNyanCatImage = new WritableImage((int)Config.NYAN_CAT_RAINBOW_WIDTH, (int)(Config.NYAN_CAT_RAINBOW_HEIGHT  + Config.NYAN_CAT_BULLET_HEIGHT));
        PixelReader nyanCatImagePixelReader =  t_nyanCatImage.getPixelReader();
        PixelReader rainbowImagePixelReader =  t_rainbowImage.getPixelReader();
        PixelWriter pixelWriterFullNyanCat = fullNyanCatImage.getPixelWriter();

        System.out.println(t_nyanCatImage.getHeight() + " " + t_rainbowImage.getHeight() +  " " + fullNyanCatImage.getHeight());

        for (int y = 0; y < t_rainbowImage.getHeight(); y++) {
            for (int x = 0; x < t_rainbowImage.getWidth(); x++) {
                pixelWriterFullNyanCat.setColor(x, (int) (y + Config.NYAN_CAT_BULLET_HEIGHT), rainbowImagePixelReader.getColor((int) (t_rainbowImage.getWidth()) - x - 1, (int) (t_rainbowImage.getHeight()) - y - 1));
            }
        }

        for (int y = 0; y < t_nyanCatImage.getHeight(); y++) {
            for (int x = 0; x < t_nyanCatImage.getWidth(); x++) {
                pixelWriterFullNyanCat.setColor(x, y, nyanCatImagePixelReader.getColor(x, y));
            }
        }

        ((NyanCatBullet)entity).rotate(graphicsContext, fullNyanCatImage, screenPosX, screenPosY, ((NyanCatBullet)entity).getAngle());
    }
}
