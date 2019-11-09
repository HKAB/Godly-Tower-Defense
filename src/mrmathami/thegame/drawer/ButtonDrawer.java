package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.bar.NormalButton;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class ButtonDrawer implements UIEntityDrawer {
    private String baseUrl = "/bar/button/";
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
//        System.out.println("Button drawer called");
        String pngName = ((NormalButton)entity).getPngName();
        Image image = new Image(getClass().getResourceAsStream(baseUrl + pngName));
        graphicsContext.drawImage(image, screenPosX, screenPosY, 64, 64);
//        graphicsContext.setFill(Color.GOLD);
//        graphicsContext.fillRect(entity.getPosX(), entity.getPosY(), 50, 50);
    }
}
