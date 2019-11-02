package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.bar.NormalButton;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class ButtonDrawer implements EntityDrawer {
    private String baseUrl = "/bar/button/";
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
//        System.out.println("Button drawer called");
//        String pngName = ((NormalButton)entity).getPngName();
//        graphicsContext.drawImage(new Image("file:" + baseUrl + pngName), entity.getPosX(), entity.getPosY());
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(entity.getPosX(), entity.getPosY(), 50, 50);
    }
}
