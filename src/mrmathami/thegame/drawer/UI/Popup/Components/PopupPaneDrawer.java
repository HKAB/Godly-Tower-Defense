package mrmathami.thegame.drawer.UI.Popup;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;

public class PopupPaneDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        Color paneColor = Color.rgb(255, 255,255);
        graphicsContext.setFill(paneColor);
        graphicsContext.fillRect(entity.getPosX()*Config.TILE_SIZE, entity.getPosY()*Config.TILE_SIZE, entity.getWidth(), entity.getHeight());
    }
}
