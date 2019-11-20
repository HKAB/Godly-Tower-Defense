package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.NormalBullet;

import javax.annotation.Nonnull;

public final class NormalBulletDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		screenPosX += Config.OFFSET;
		screenPosY += Config.OFFSET;
		Image img = GameDrawer.getSheetImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();
		WritableImage bulletImage = new WritableImage(reader, (int)((((NormalBullet)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((NormalBullet)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
		reader = bulletImage.getPixelReader();
		WritableImage t_bulletImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NORMAL_BULLET_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.NORMAL_BULLET_HEIGHT/2), (int)(Config.NORMAL_BULLET_HEIGHT), (int)(Config.NORMAL_BULLET_HEIGHT));
		graphicsContext.drawImage(t_bulletImage, screenPosX, screenPosY);
	}
}
