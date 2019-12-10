package mrmathami.thegame.drawer.UI.Popup.Components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;

import javax.annotation.Nonnull;

public class PopupPaneDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity,
                     double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        Color paneColor = Color.rgb(255, 255,255);
        graphicsContext.setFill(paneColor);
        graphicsContext.fillRect(screenPosX, screenPosY, entity.getWidth(), entity.getHeight());
    }
}
