package mrmathami.thegame.drawer.UI.Popup.Components;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.popup.components.PopupButton;

import javax.annotation.Nonnull;
import java.io.File;

public class PopupButtonDrawer implements UIEntityDrawer {
    @Override
    public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull UIEntity entity,
                     double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
        graphicsContext.setLineWidth(3);
        graphicsContext.setFill(Color.rgb(205, 97, 51));
        graphicsContext.fillRect(screenPosX, screenPosY, entity.getWidth(), entity.getHeight());
        graphicsContext.setLineWidth(1);
        graphicsContext.setStroke(Color.rgb(0, 0, 0));
        graphicsContext.strokeRect(screenPosX, screenPosY, entity.getWidth(), entity.getHeight());

        graphicsContext.setFill(Color.rgb(0, 0, 0));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.setFont(Font.loadFont(new File("res/font/icon.ttf").toURI().toString(), ((PopupButton)entity).getFontSize()));
        graphicsContext.fillText(((PopupButton)entity).getContent(), screenPosX + screenWidth/2, screenPosY  + screenHeight/2);
    }
}
