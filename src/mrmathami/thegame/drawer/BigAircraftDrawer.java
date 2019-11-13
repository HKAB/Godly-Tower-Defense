package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.BigAircraft;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.Tanker;

import javax.annotation.Nonnull;
import java.util.WeakHashMap;

public final class BigAircraftDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, (int)((((BigAircraft)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((BigAircraft)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        reader = newImage.getPixelReader();
        WritableImage aircraft = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.BIG_AIRCRAFT_ENEMY_WIDTH), (int)(Config.BIG_AIRCRAFT_ENEMY_HEIGHT));
        ((BigAircraft) entity).rotate(graphicsContext, aircraft, screenPosX, screenPosY, ((BigAircraft) entity).getAngle() - 90);
        if (((BigAircraft)entity).getHealth() < Config.BIG_AIRCRAFT_ENEMY_HEALTH) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, Config.BIG_AIRCRAFT_ENEMY_WIDTH, 7);

            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, ((BigAircraft) entity).getHealth() * 1.0 / Config.BIG_AIRCRAFT_ENEMY_HEALTH * Config.BIG_AIRCRAFT_ENEMY_WIDTH, 7);
        }
    }
}
