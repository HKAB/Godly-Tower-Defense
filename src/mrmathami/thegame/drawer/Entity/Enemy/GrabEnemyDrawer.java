
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
import mrmathami.thegame.entity.enemy.GrabEnemy;

import javax.annotation.Nonnull;

public final class GrabEnemyDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        if (((GrabEnemy) entity).getAngle() == Double.MIN_VALUE) return;
        screenPosX += Config.OFFSET;
        screenPosY += Config.OFFSET;
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, (int)((((GrabEnemy)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((GrabEnemy)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        reader = newImage.getPixelReader();
        WritableImage pirateShip = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.GRAB_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.GRAB_ENEMY_HEIGHT/2), (int)Config.GRAB_ENEMY_WIDTH, (int)Config.GRAB_ENEMY_HEIGHT);
        ((GrabEnemy) entity).rotate(graphicsContext, pirateShip, screenPosX, screenPosY, ((GrabEnemy) entity).getAngle());
        if (((GrabEnemy)entity).getHealth() < Config.GRAB_ENEMY_HEALTH) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, Config.GRAB_ENEMY_WIDTH, 7);

            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, ((GrabEnemy) entity).getHealth() * 1.0 / Config.GRAB_ENEMY_HEALTH * Config.GRAB_ENEMY_WIDTH, 7);
        }
    }
}
