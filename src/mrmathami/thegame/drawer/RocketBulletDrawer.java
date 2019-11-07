
package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.bullet.RocketBullet;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;

import java.awt.*;
import java.awt.image.BufferedImage;


import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class RocketBulletDrawer implements EntityDrawer {
    private final RadialGradient gradient = new RadialGradient(
            0.0,
            0.0,
            0.5,
            0.5,
            1.0,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(0.0, Color.WHITE),
            new Stop(0.5, Color.YELLOW),
            new Stop(1.0, Color.RED)
    );

    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage rocketImage = new WritableImage(reader, (((RocketBullet)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), Math.round((((RocketBullet)entity).getGID() - 1) / maxTileWidth) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        WritableImage fireImage = new WritableImage(reader, (((RocketBullet)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), Math.round((((RocketBullet)entity).getGID() - 1) / maxTileWidth + 2) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));

        reader = rocketImage.getPixelReader();
        WritableImage t_rocketImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.ROCKET_BULLET_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.ROCKET_BULLET_HEIGHT/2), (int)(Config.ROCKET_BULLET_WIDTH), (int)(Config.ROCKET_BULLET_HEIGHT));
        reader = fireImage.getPixelReader();
        WritableImage t_fireImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.FIRE1_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.FIRE1_HEIGHT/2), (int)(Config.FIRE1_WIDTH), (int)(Config.FIRE1_HEIGHT));


        WritableImage rocketAndFireImage = new WritableImage((int)Config.FIRE1_WIDTH, (int)(Config.FIRE1_HEIGHT  + Config.ROCKET_BULLET_HEIGHT));
        PixelReader rocketImagePixelReader =  t_rocketImage.getPixelReader();
        PixelReader fireImagePixelReader =  t_fireImage.getPixelReader();
        PixelWriter pixelWriterRockAndFire = rocketAndFireImage.getPixelWriter();

        for (int y = 0; y < t_rocketImage.getHeight(); y++) {
            for (int x = 0; x < t_rocketImage.getWidth(); x++) {
                pixelWriterRockAndFire.setColor(x, y, rocketImagePixelReader.getColor(x, y));
            }
        }

        for (int y = 0; y < t_fireImage.getHeight(); y++) {
            for (int x = 0; x < t_fireImage.getWidth(); x++) {
//                if (!fireImagePixelReader.getColor((int) (t_fireImage.getWidth() - x), (int) (t_fireImage.getHeight() - y)).equals(Color.rgb(0, 0, 0, 0)))
                pixelWriterRockAndFire.setColor(x, (int) (y + t_rocketImage.getHeight()), fireImagePixelReader.getColor((int) (t_fireImage.getWidth()) - x - 1, (int) (t_fireImage.getHeight()) - y - 1));
            }
        }

//        graphicsContext.setStroke(Color.DARKBLUE);
//        graphicsContext.setLineWidth(4);
//        graphicsContext.strokeRect(screenPosX, screenPosY, (int)Config.FIRE1_WIDTH, (int)(Config.FIRE1_HEIGHT  + Config.ROCKET_BULLET_HEIGHT));

//        BufferedImage rocketAndFire = new BufferedImage(64, 128, BufferedImage.TYPE_INT_RGB);
//        Graphics graphicsContext1 = rocketAndFire.createGraphics();


        ((RocketBullet)entity).rotate(graphicsContext, rocketAndFireImage, screenPosX, screenPosY, ((RocketBullet)entity).getAngle());
//        ((RocketBullet)entity).rotate(graphicsContext, fireImage, screenPosX + 32, screenPosY - 64, ((RocketBullet)entity).getAngle() + 180);

//        graphicsContext.drawImage(rocketAndFireImage, 0, 0);
    }
}
