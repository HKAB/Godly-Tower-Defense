package mrmathami.thegame.drawer.UI.Popup.Components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.PopupSlideBar;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class PopupSlideBarDrawer implements UIEntityDrawer{

    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setFill(Color.TRANSPARENT);
        graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
        graphicsContext.setFill(Color.rgb(205, 97, 51));
        graphicsContext.fillRect(screenPosX, screenPosY, screenWidth - (1 - ((PopupSlideBar)entity).getInitialValue()/100)*screenWidth, screenHeight);
        graphicsContext.setLineWidth(1);
        graphicsContext.strokeRect(screenPosX, screenPosY, screenWidth, screenHeight);


    }
}
