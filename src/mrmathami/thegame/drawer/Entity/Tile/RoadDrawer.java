package mrmathami.thegame.drawer.Entity.Tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.drawer.Entity.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Road;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class RoadDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
		Image img = GameDrawer.getSheetImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();

		WritableImage roadImage = new WritableImage(reader, (((Road)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((((Road)entity).getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);

		graphicsContext.drawImage(roadImage, screenPosX, screenPosY);

//		graphicsContext.setFill(Color.RED);
//		graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
	}
}
