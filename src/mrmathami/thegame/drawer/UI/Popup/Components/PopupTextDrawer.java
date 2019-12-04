package mrmathami.thegame.drawer.UI.Popup.Components;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.PopupText;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;

public class PopupTextDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {
        graphicsContext.setTextAlign(((PopupText)entity).getTextAlignment());
        graphicsContext.setTextBaseline(VPos.TOP);
//        Color paneColor = Color.rgb(255, 255,255);
//        graphicsContext.setFill(paneColor);
        graphicsContext.setFont(Font.loadFont(new File("res/ui/Tomorrow-Italic.ttf").toURI().toString(), ((PopupText)entity).getFontSize()));
        graphicsContext.setFill(((PopupText) entity).getColor());
        graphicsContext.fillText(((PopupText)entity).getText(), screenPosX, screenPosY);
    }
}
