package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;

import javax.annotation.Nonnull;

public final class MachineGunTowerDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		Image imgSheet = GameDrawer.getSheetImage();
		Image imgRank = GameDrawer.getRankImage();
		int maxTileWidth = (int)Math.round(imgSheet.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(imgSheet.getHeight()/Config.TILE_SIZE);
		PixelReader reader = imgSheet.getPixelReader();

		WritableImage towerImage = new WritableImage(reader, (((MachineGunTower)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), Math.round((((MachineGunTower)entity).getGID() - 1) / maxTileWidth) * (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
		((MachineGunTower)entity).rotate(graphicsContext, towerImage, screenPosX, screenPosY, ((MachineGunTower)entity).getAngle());


		maxTileWidth = (int)Math.round(imgRank.getWidth()/ Config.TILE_SIZE);
		maxTileHeight = (int)Math.round(imgRank.getHeight()/Config.TILE_SIZE);
		reader = imgRank.getPixelReader();
		WritableImage rankImage;
		switch (((MachineGunTower)entity).getLevel())
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
				throw new IllegalStateException("Unexpected value: " + ((MachineGunTower) entity).getLevel());
		}


		((MachineGunTower)entity).rotate(graphicsContext, rankImage, screenPosX, screenPosY - 32, 90);
//		graphicsContext.drawImage(rankImage, screenPosX, screenPosY  - 32);
	}
}
