package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.EntityDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Outside;
import mrmathami.thegame.entity.tile.Road;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class OutsideDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
//		graphicsContext.setFill(Color.DARKGREEN);
//		graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
        Image img = new Image(new FileInputStream("/home/hkab/IdeaProjects/testjavaFX/out/production/testjavaFX/sample/sheet.png"));
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();

        WritableImage newImage = new WritableImage(reader, (((Outside)entity).getGID() - 1) % maxTileWidth * (int)screenWidth, Math.round((((Outside)entity).getGID() - 1) / maxTileWidth) * (int)screenHeight, (int)screenWidth, (int)screenHeight);

        graphicsContext.drawImage(newImage, screenPosX, screenPosY);
    }
}
