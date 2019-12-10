package mrmathami.thegame.drawer.UI.Popup.Components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.PopupImage;

import javax.annotation.Nonnull;

public class PopupImageDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity,
                     double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        Image img = ((PopupImage)entity).getImage();
        graphicsContext.drawImage(img, screenPosX, screenPosY);
    }
}
