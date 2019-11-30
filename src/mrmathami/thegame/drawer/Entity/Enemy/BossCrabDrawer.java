
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
import mrmathami.thegame.entity.enemy.BossCrab;

import javax.annotation.Nonnull;

public final class BossCrabDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        if (((BossCrab) entity).getAngle() == Double.MIN_VALUE) return;
        screenPosX += Config.OFFSET;
        screenPosY += Config.OFFSET;
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, (int)((((BossCrab)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((BossCrab)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        reader = newImage.getPixelReader();
        WritableImage bossAxolotl = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.BOSS_CRAB_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.BOSS_CRAB_HEIGHT/2), (int)Config.BOSS_CRAB_WIDTH, (int)Config.BOSS_CRAB_HEIGHT);
        ((BossCrab) entity).rotate(graphicsContext, bossAxolotl, screenPosX, screenPosY, ((BossCrab) entity).getAngle());
        if (((BossCrab)entity).getHealth() < Config.BOSS_CRAB_HEALTH) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, Config.BOSS_CRAB_WIDTH, 7);

            graphicsContext.setFill(Color.LIGHTGOLDENRODYELLOW);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, ((BossCrab) entity).getHealth() * 1.0 / Config.BOSS_CRAB_HEALTH * Config.BOSS_CRAB_WIDTH, 7);
        }
    }
}


