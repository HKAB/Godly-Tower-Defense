package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;

import javax.annotation.Nonnull;

public final class RocketLauncherTowerDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();

        WritableImage rocketLauncherImage = new WritableImage(reader, (((RocketLauncherTower)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((((RocketLauncherTower)entity).getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);
        ((RocketLauncherTower)entity).rotate(graphicsContext, rocketLauncherImage, screenPosX, screenPosY, ((RocketLauncherTower)entity).getAngle());
    }
}

