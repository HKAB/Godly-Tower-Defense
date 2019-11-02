package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.tower.NormalTower;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class NormalTowerDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		Image img = GameDrawer.getSheetImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();

		WritableImage newImage = new WritableImage(reader, (((NormalTower)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((((NormalTower)entity).getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);
		((NormalTower)entity).rotate(graphicsContext, newImage, screenPosX, screenPosY, ((NormalTower)entity).getAngle());
	}

}
