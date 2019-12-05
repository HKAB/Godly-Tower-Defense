package mrmathami.thegame.drawer.Entity.Tile.Spawner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;

public final class SpawnerDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//		graphicsContext.setStroke(Color.DARKBLUE);
//		graphicsContext.setLineWidth(4);
//		graphicsContext.strokeRect(screenPosX, screenPosY, screenWidth, screenHeight);
		screenPosX += Config.TILE_SIZE/2.0;
		screenPosY += Config.TILE_SIZE/2.0;
		Image img = GameDrawer.getSheetImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();

		WritableImage roadImage = new WritableImage(reader, (Config.SPAWNER_GID - 1) % maxTileWidth * (int)screenWidth, (Config.SPAWNER_GID - 1)/maxTileWidth * (int)screenHeight, (int)screenWidth, (int)screenHeight);

		graphicsContext.drawImage(roadImage, screenPosX, screenPosY);
	}
}
