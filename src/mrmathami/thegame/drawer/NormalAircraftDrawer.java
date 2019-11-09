
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
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.tile.tower.NormalTower;

import javax.annotation.Nonnull;
import java.util.WeakHashMap;

public final class NormalAircraftDrawer implements EntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//		graphicsContext.setFill(Color.DARKMAGENTA);
//		graphicsContext.fillRoundRect(screenPosX, screenPosY, screenWidth, screenHeight, 4, 4);

//        graphicsContext.setStroke(Color.DARKMAGENTA);
//        graphicsContext.setLineWidth(4);
//        graphicsContext.strokeRect(screenPosX, screenPosY, Config.NORMAL_AIRCRAFT_ENEMY_WIDTH, Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT);

        Image img = GameDrawer.getSheetImage();
        int maxTileWidth = (int)Math.round(img.getWidth()/ Config.TILE_SIZE);
        int maxTileHeight = (int)Math.round(img.getHeight()/Config.TILE_SIZE);
        PixelReader reader = img.getPixelReader();
//		System.out.println(screenWidth + " " + screenHeight);
        WritableImage newImage = new WritableImage(reader, (int)((((NormalAircraft)entity).getGID() - 1) % maxTileWidth * Config.TILE_SIZE), (int)(Math.round((((NormalAircraft)entity).getGID() - 1) / maxTileWidth) * Config.TILE_SIZE), (int)(Config.TILE_SIZE), (int)(Config.TILE_SIZE));
//		ImageView imageView = new ImageView(newImage);
//		imageView.setPreserveRatio(true);
//		imageView.setFitWidth(screenWidth);
//		imageView.setFitHeight(screenHeight);
//		graphicsContext.drawImage(newImage, screenPosX, screenPosY);
        reader = newImage.getPixelReader();
        WritableImage aircraft = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2), (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2), (int)Config.NORMAL_AIRCRAFT_ENEMY_WIDTH, (int)Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT);
//        if (screenPosY/Config.TILE_SIZE < 5 && screenPosX/Config.TILE_SIZE < 2.0001)
//        if (screenPosY/Config.TILE_SIZE > 3.9999 && screenPosX/Config.TILE_SIZE > 5.0001 && screenPosX/Config.TILE_SIZE < 6.0001)
//        {
//            // right1
////            double area = (5 - screenPosY/Config.TILE_SIZE);
////            System.out.println("Angle: " + area*45);
//////            WritableImage aircraftImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2 - 32*Math.sin(area*85*Math.PI/180)), (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.NORMAL_AIRCRAFT_ENEMY_WIDTH), (int)(Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT));
////            System.out.println("Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2*Math.sin(area*45*Math.PI/180): " + Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2*Math.sin(area*45*Math.PI/180));
////            ((NormalAircraft)entity).rotate(graphicsContext, aircraft, screenPosX + Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2*Math.sin(area*45*Math.PI/180), screenPosY, ((NormalAircraft)entity).getAngle() + area*45 - 90);
////            ((NormalAircraft)entity).rotate(graphicsContext, aircraft, screenPosX + Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2*Math.sin(area*45*Math.PI/180), screenPosY, ((NormalAircraft)entity).getAngle() + area*45 - 90);
//            // end right1
//
//            // right2
//            double area = (1 + screenPosX/Config.TILE_SIZE - 6);
//            System.out.println("Angle: " + -area*45);
////            WritableImage aircraftImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2 - 32*Math.sin(area*85*Math.PI/180)), (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.NORMAL_AIRCRAFT_ENEMY_WIDTH), (int)(Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT));
//            System.out.println("Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2*Math.sin(area*45*Math.PI/180): " + Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2*Math.sin(area*45*Math.PI/180));
////            ((NormalAircraft)entity).rotate(graphicsContext, aircraft, screenPosX + Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2*Math.sin(area*45*Math.PI/180), screenPosY, ((NormalAircraft)entity).getAngle() + area*45 - 90);
//            ((NormalAircraft)entity).rotate(graphicsContext, aircraft, screenPosX + Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2*Math.sin(area*45*Math.PI/180), screenPosY, ((NormalAircraft)entity).getAngle() - area*45 - 90);
//            // end right2
//
//        }
//        else if (screenPosY/Config.TILE_SIZE > 3 && screenPosY/Config.TILE_SIZE < 4 && screenPosX/Config.TILE_SIZE < 6.001)
//        {
//            double area = (4 - (screenPosY/Config.TILE_SIZE));
//            System.out.println("Angle: " + area*45);
////            WritableImage aircraftImage = new WritableImage(reader, (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2 - 32*Math.sin(area*85*Math.PI/180)), (int)(Config.TILE_SIZE/2 - Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/2), (int)(Config.NORMAL_AIRCRAFT_ENEMY_WIDTH), (int)(Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT));
//            ((NormalAircraft)entity).rotate(graphicsContext, aircraft, screenPosX + Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/2*Math.sqrt(2)/2, screenPosY, ((NormalAircraft)entity).getAngle() - area*45 - 45);
//
//        }
//        else {
            ((NormalAircraft) entity).rotate(graphicsContext, aircraft, screenPosX, screenPosY, ((NormalAircraft) entity).getAngle() - 90);
//        }
//        ((NormalAircraft)entity).rotate(graphicsContext, aircraft, screenPosX, screenPosY, ((NormalAircraft)entity).getAngle() - 90);
    }
}
