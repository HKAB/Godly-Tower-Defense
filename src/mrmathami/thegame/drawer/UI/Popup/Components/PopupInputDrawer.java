package mrmathami.thegame.drawer.UI.Popup.Components;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.PopupInput;

import javax.annotation.Nonnull;
import java.io.File;

public class PopupInputDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
//        Color paneColor = Color.rgb(255, 255,255);
//        graphicsContext.setFill(paneColor);
//        graphicsContext.fillRect(screenPosX, screenPosY, entity.getWidth(), entity.getHeight());
        graphicsContext.setStroke(Color.rgb(205, 97, 51));
        graphicsContext.setLineWidth(3);
        if (((PopupInput)entity).isFocus())
            graphicsContext.setLineWidth(6);
        graphicsContext.strokeRect(screenPosX, screenPosY, entity.getWidth(), entity.getHeight());

        graphicsContext.setFill(Color.rgb(0, 0, 0));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.setFont(Font.loadFont(new File("res/font/shitfont.ttf").toURI().toString(), ((PopupInput)entity).getFontSize()));
        graphicsContext.fillText(((PopupInput)entity).getText(), screenPosX + screenWidth/2, screenPosY  + screenHeight/2);
    }
}
