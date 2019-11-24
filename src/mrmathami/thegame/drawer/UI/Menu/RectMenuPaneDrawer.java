package mrmathami.thegame.drawer.UI.Menu;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.ui.menu.RectMenuPane;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class RectMenuPaneDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Color rectColor = ((RectMenuPane)entity).getRectColor();
        graphicsContext.setFill(rectColor);
        graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
    }
}
