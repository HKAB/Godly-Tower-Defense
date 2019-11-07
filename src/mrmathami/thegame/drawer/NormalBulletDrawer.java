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
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.NormalEnemy;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public final class NormalBulletDrawer implements EntityDrawer {
	private final RadialGradient gradient = new RadialGradient(
			0.0,
			0.0,
			0.5,
			0.5,
			1.0,
			true,
			CycleMethod.NO_CYCLE,
			new Stop(0.0, Color.YELLOW),
			new Stop(0.5, Color.RED),
			new Stop(1.0, Color.WHITE)
	);

	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//		graphicsContext.setFill(gradient);
//		graphicsContext.fillOval(screenPosX, screenPosY, screenWidth, screenHeight);

		graphicsContext.setStroke(Color.RED);
		graphicsContext.setLineWidth(4);
		graphicsContext.strokeRect(screenPosX, screenPosY, screenWidth, screenHeight);

		Image img = GameDrawer.getSheetImage();
		int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
		int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
		PixelReader reader = img.getPixelReader();
//		System.out.println("screenWidth: " + screenWidth);
		WritableImage bulletImage = new WritableImage(reader, (int)((((NormalBullet)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((NormalBullet)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
		reader = bulletImage.getPixelReader();
		WritableImage t_bulletImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NORMAL_BULLET_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.NORMAL_BULLET_HEIGHT/2), (int)(Config.NORMAL_BULLET_HEIGHT), (int)(Config.NORMAL_BULLET_HEIGHT));
		graphicsContext.drawImage(t_bulletImage, screenPosX, screenPosY);
	}
}
