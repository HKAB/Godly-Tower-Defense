package mrmathami.thegame.drawer.UI.Popup.Components;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import mrmathami.thegame.Config;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.PopupLabel;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;

public class PopupLabelDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) throws FileNotFoundException {

        graphicsContext.setTextAlign(TextAlignment.CENTER);
        Color paneColor = Color.rgb(255, 255,255);
        graphicsContext.setFill(paneColor);
        graphicsContext.setFont(Font.loadFont(new File("/home/hkab/Documents/Fonts/dont.ttf").toURI().toString(), ((PopupLabel)entity).getFontSize()));
        graphicsContext.setFill(Color.rgb(0, 0, 0));
        graphicsContext.fillText(((PopupLabel)entity).getText(), screenPosX, screenPosY);
    }
}
