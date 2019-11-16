package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;

import javax.annotation.Nonnull;

public final class MachineGunTowerDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		Image img = GameDrawer.getSheetImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();
//		System.out.println("MachineGunTower " + screenPosX + " " + screenPosY);

		WritableImage newImage = new WritableImage(reader, (((MachineGunTower)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), Math.round((((MachineGunTower)entity).getGID() - 1) / maxTileWidth) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
		((MachineGunTower)entity).rotate(graphicsContext, newImage, screenPosX, screenPosY, ((MachineGunTower)entity).getAngle());
	}
}
