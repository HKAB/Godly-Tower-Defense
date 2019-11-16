package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.tower.NormalTower;

import javax.annotation.Nonnull;

public final class NormalTowerDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		Image img = GameDrawer.getSheetImage();
		Image imgRank = GameDrawer.getRankImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();

		WritableImage towerImage = new WritableImage(reader, (int)((((NormalTower)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((NormalTower)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
		((NormalTower)entity).rotate(graphicsContext, towerImage, screenPosX, screenPosY, ((NormalTower)entity).getAngle());

		maxTileWidth = (int)Math.round(imgRank.getWidth()/ Config.TILE_SIZE);
		maxTileHeight = (int)Math.round(imgRank.getHeight()/Config.TILE_SIZE);
		reader = imgRank.getPixelReader();
		WritableImage rankImage;
		switch (((NormalTower)entity).getLevel())
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
				throw new IllegalStateException("Unexpected value: " + ((NormalTower) entity).getLevel());
		}


		((NormalTower)entity).rotate(graphicsContext, rankImage, screenPosX, screenPosY - 32, 90);
	}

}
