
package mrmathami.thegame.drawer.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.NormalAircraft;

import javax.annotation.Nonnull;

public final class NormalAircraftDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        screenPosX += Config.OFFSET;
        screenPosY += Config.OFFSET;
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, (int)((((NormalAircraft)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((NormalAircraft)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        reader = newImage.getPixelReader();
        WritableImage aircraft = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2), (int)Config.NORMAL_AIRCRAFT_ENEMY_WIDTH, (int)Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT);
        ((NormalAircraft) entity).rotate(graphicsContext, aircraft, screenPosX, screenPosY, ((NormalAircraft) entity).getAngle());
        if (((NormalAircraft)entity).getHealth() < Config.NORMAL_AIRCRAFT_ENEMY_HEALTH) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, Config.NORMAL_AIRCRAFT_ENEMY_WIDTH, 7);

            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, ((NormalAircraft) entity).getHealth() * 1.0 / Config.NORMAL_AIRCRAFT_ENEMY_HEALTH * Config.NORMAL_AIRCRAFT_ENEMY_WIDTH, 7);
        }
    }
}
