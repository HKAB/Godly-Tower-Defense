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
import mrmathami.thegame.entity.enemy.boss.BossEnemy;

import javax.annotation.Nonnull;

public class BossEnemyDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        BossEnemy bossEnemy = (BossEnemy)entity;
        if (bossEnemy.getAngle() == Double.MIN_VALUE) return;
        screenPosX += Config.OFFSET;
        screenPosY += Config.OFFSET;
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        WritableImage newImage = new WritableImage(reader, (int)((bossEnemy.getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((bossEnemy.getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        reader = newImage.getPixelReader();
        WritableImage bossImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - bossEnemy.getBossWidth()/2), (int)(Config.TILE_SIZE/2 - bossEnemy.getBossHeight()/2), (int)bossEnemy.getBossWidth(), (int)bossEnemy.getBossHeight());
        bossEnemy.rotate(graphicsContext, bossImage, screenPosX, screenPosY, bossEnemy.getAngle());
        if (bossEnemy.getHealth() < bossEnemy.getMaxHealth()) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, bossEnemy.getBossWidth(), 7);

            graphicsContext.setFill(Color.LIGHTGOLDENRODYELLOW);
            graphicsContext.fillRect(screenPosX, screenPosY - 5, bossEnemy.getHealth() * 1.0 / bossEnemy.getMaxHealth() * bossEnemy.getBossWidth(), 7);
        }
    }
}
