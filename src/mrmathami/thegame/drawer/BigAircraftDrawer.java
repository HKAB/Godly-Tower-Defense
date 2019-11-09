package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.BigAircraft;
import mrmathami.thegame.entity.enemy.BigAircraft;
import mrmathami.thegame.entity.tile.tower.NormalTower;

import javax.annotation.Nonnull;
import java.util.WeakHashMap;

public final class BigAircraftDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//		graphicsContext.setFill(Color.DARKMAGENTA);
//		graphicsContext.fillRoundRect(screenPosX, screenPosY, screenWidth, screenHeight, 4, 4);

//        graphicsContext.setStroke(Color.BLUE);
//        graphicsContext.setLineWidth(4);
//        graphicsContext.strokeRect(screenPosX, screenPosY, screenWidth, screenHeight);

        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
//		System.out.println(screenWidth + " " + screenHeight);
//        WritableImage newImage = new WritableImage(reader, (((BigAircraft)entity).getGID() - 1) % maxTileWidth * (int)(screenWidth), Math.round((((BigAircraft)entity).getGID() - 1) / maxTileWidth) * (int)(screenHeight), (int)(screenWidth), (int)(screenHeight));
        WritableImage newImage = new WritableImage(reader, (int)((((BigAircraft)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((BigAircraft)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
//		ImageView imageView = new ImageView(newImage);
//		imageView.setPreserveRatio(true);
//		imageView.setFitWidth(screenWidth);
//		imageView.setFitHeight(screenHeight);
//		graphicsContext.drawImage(newImage, screenPosX, screenPosY);
//        WritableImage shadowImage = new WritableImage(reader, (((BigAircraft)entity).getGID() - 1) % maxTileWidth * (int)(screenWidth), Math.round((((BigAircraft)entity).getGID() - 1) / maxTileWidth + 1) * (int)(screenHeight), (int)(screenWidth), (int)(screenHeight));
        reader = newImage.getPixelReader();
//        WritableImage aircraft = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.BIG_AIRCRAFT_ENEMY_WIDTH), (int)(Config.BIG_AIRCRAFT_ENEMY_HEIGHT));
        WritableImage aircraft = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.BIG_AIRCRAFT_ENEMY_WIDTH), (int)(Config.BIG_AIRCRAFT_ENEMY_HEIGHT));
        System.out.println("screenPosX: " + screenPosX + "screenPosY: " + screenPosY);
        if (screenPosY/Config.TILE_SIZE < 5 && screenPosX/Config.TILE_SIZE < 2.0001)
        {
            double area = (5 - screenPosY/Config.TILE_SIZE);
            System.out.println("Angle: " + area*45);
//            WritableImage aircraftImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_WIDTH/2 - 32*Math.sin(area*85*Math.PI/180)), (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.BIG_AIRCRAFT_ENEMY_WIDTH), (int)(Config.BIG_AIRCRAFT_ENEMY_HEIGHT));
            ((BigAircraft)entity).rotate(graphicsContext, aircraft, screenPosX + Config.BIG_AIRCRAFT_ENEMY_WIDTH/2*Math.sin(area*45*Math.PI/180), screenPosY, ((BigAircraft)entity).getAngle() + area*45 - 90);

        }
        else if (screenPosY/Config.TILE_SIZE < 5 && screenPosX/Config.TILE_SIZE < 3.001)
        {
            double area = (3 - screenPosX/Config.TILE_SIZE);
            System.out.println("Angle: " + area*45);
//            WritableImage aircraftImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_WIDTH/2 - 32*Math.sin(area*85*Math.PI/180)), (int)(Config.TILE_SIZE/2 - Config.BIG_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.BIG_AIRCRAFT_ENEMY_WIDTH), (int)(Config.BIG_AIRCRAFT_ENEMY_HEIGHT));
            ((BigAircraft)entity).rotate(graphicsContext, aircraft, screenPosX + Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2*Math.sqrt(2)/2, screenPosY, ((BigAircraft)entity).getAngle() - area*45 - 90);

        }
        else {
            ((BigAircraft) entity).rotate(graphicsContext, aircraft, screenPosX, screenPosY, ((BigAircraft) entity).getAngle() - 90);
        }
//        graphicsContext.save();
//        Rotate r = new Rotate(((BigAircraft)entity).getAngle() - 90, screenPosX + shadowImage.getWidth()/2, screenPosY + shadowImage.getHeight()/2);
//        graphicsContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
//        graphicsContext.drawImage(shadowImage, screenPosX - 40, screenPosY - 40);
//        graphicsContext.restore();
    }
}
