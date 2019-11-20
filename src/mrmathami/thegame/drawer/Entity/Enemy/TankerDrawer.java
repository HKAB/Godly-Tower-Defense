
package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.Entity.EntityDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.Tanker;

import javax.annotation.Nonnull;

public final class TankerDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {

        screenPosX += Config.OFFSET;
        screenPosY += Config.OFFSET;


//        graphicsContext.setStroke(Color.DARKMAGENTA);
//        graphicsContext.setLineWidth(4);
//        graphicsContext.strokeRect(screenPosX, screenPosY, Config.TANKER_ENEMY_WIDTH, Config.TANKER_ENEMY_HEIGHT);

        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
        
        WritableImage baseImage = new WritableImage(reader, (((Tanker)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), (Math.round((((Tanker)entity).getGID() - 1) / maxTileWidth) * (int)(Config.TILE_SIZE)), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
        WritableImage barrelImage = new WritableImage(reader, (((Tanker)entity).getGID() - 1) % maxTileWidth * (int)(Config.TILE_SIZE), (Math.round((((Tanker)entity).getGID() - 1) / maxTileWidth + 1) * (int)(Config.TILE_SIZE)), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));

        reader = baseImage.getPixelReader();
        WritableImage t_baseImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.TANKER_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.TANKER_ENEMY_HEIGHT/2), (int)(Config.TANKER_ENEMY_WIDTH), (int)(Config.TANKER_ENEMY_HEIGHT));
        reader = barrelImage.getPixelReader();
        WritableImage t_barrelImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.TANKER_BARREL_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.TANKER_BARREL_ENEMY_HEIGHT/2), (int)(Config.TANKER_BARREL_ENEMY_WIDTH), (int)(Config.TANKER_BARREL_ENEMY_HEIGHT));


        WritableImage tankerImage = new WritableImage((int)Config.TANKER_ENEMY_WIDTH, (int)(Config.TANKER_ENEMY_HEIGHT));
        PixelReader baseImagePixelReader =  t_baseImage.getPixelReader();
        PixelReader barrelImagePixelReader =  t_barrelImage.getPixelReader();
        PixelWriter pixelWriterTanker = tankerImage.getPixelWriter();


        for (int y = 0; y < t_baseImage.getHeight(); y++) {
            for (int x = 0; x < t_baseImage.getWidth(); x++) {
                pixelWriterTanker.setColor(x, y, baseImagePixelReader.getColor(x, y));
            }
        }

        for (int y = 0; y < t_barrelImage.getHeight(); y++) {
            for (int x = 0; x < t_barrelImage.getWidth(); x++) {
                if (!barrelImagePixelReader.getColor(x, y).equals(Color.rgb(0, 0, 0, 0)))
                    pixelWriterTanker.setColor(x, y + (int)((Config.TANKER_ENEMY_HEIGHT - Config.TANKER_BARREL_ENEMY_HEIGHT)/2), barrelImagePixelReader.getColor(x, y));
            }
        }
        
        ((Tanker)entity).rotate(graphicsContext, tankerImage, screenPosX, screenPosY, ((Tanker)entity).getAngle());
        if (((Tanker)entity).getHealth() < Config.TANKER_ENEMY_HEALTH) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(screenPosX, screenPosY - 10, Config.TANKER_ENEMY_WIDTH, 7);

            graphicsContext.setFill(Color.GREEN);
            graphicsContext.fillRect(screenPosX, screenPosY - 10, ((Tanker) entity).getHealth() * 1.0 / Config.TANKER_ENEMY_HEALTH * Config.TANKER_ENEMY_WIDTH, 7);
        }
    }
}
