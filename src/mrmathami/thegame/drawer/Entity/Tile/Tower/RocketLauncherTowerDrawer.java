package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;

import javax.annotation.Nonnull;

public final class RocketLauncherTowerDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        Image img = GameDrawer.getSheetImage();
        Image imgRank = GameDrawer.getRankImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();

        WritableImage rocketLauncherImage = new WritableImage(reader, (((RocketLauncherTower)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((((RocketLauncherTower)entity).getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);
        ((RocketLauncherTower)entity).rotate(graphicsContext, rocketLauncherImage, screenPosX, screenPosY, ((RocketLauncherTower)entity).getAngle());

        maxTileWidth = (int)Math.round(imgRank.getWidth()/ Config.TILE_SIZE);
        maxTileHeight = (int)Math.round(imgRank.getHeight()/Config.TILE_SIZE);
        reader = imgRank.getPixelReader();
        WritableImage rankImage;
        switch (((RocketLauncherTower)entity).getLevel())
        {
            case 0:
                rankImage = new WritableImage(reader, (Config.TOWER_RANK_1_GID - 1) % maxTileWidth * (int)(Config.TILE_SIZE), ((Config.TOWER_RANK_1_GID - 1) / maxTileWidth) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
                break;
            case 1:
                rankImage = new WritableImage(reader, (Config.TOWER_RANK_2_GID - 1) % maxTileWidth * (int)(Config.TILE_SIZE), ((Config.TOWER_RANK_2_GID - 1) / maxTileWidth) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
                break;
            case 2:
                rankImage = new WritableImage(reader, (Config.TOWER_RANK_3_GID - 1) % maxTileWidth * (int)(Config.TILE_SIZE), (Config.TOWER_RANK_3_GID - 1) / maxTileWidth * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ((RocketLauncherTower) entity).getLevel());
        }


        ((RocketLauncherTower)entity).rotate(graphicsContext, rankImage, screenPosX, screenPosY - 32, 90);
    }
}

