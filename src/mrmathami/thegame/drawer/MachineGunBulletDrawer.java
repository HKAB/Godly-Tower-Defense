package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.MachineGunBullet;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class MachineGunBulletDrawer implements EntityDrawer {

	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		Image img = GameDrawer.getSheetImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();
		WritableImage bulletImage = new WritableImage(reader, (((MachineGunBullet)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), Math.round((((MachineGunBullet)entity).getGID() - 1) / maxTileWidth) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
		reader = bulletImage.getPixelReader();
		WritableImage t_bulletImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.MACHINE_GUN_BULLET_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.MACHINE_GUN_BULLET_HEIGHT/2), (int)(Config.MACHINE_GUN_BULLET_HEIGHT), (int)(Config.MACHINE_GUN_BULLET_HEIGHT));
		graphicsContext.drawImage(t_bulletImage, screenPosX, screenPosY);
	}
}
