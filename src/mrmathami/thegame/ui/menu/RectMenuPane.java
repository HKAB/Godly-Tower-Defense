package mrmathami.thegame.ui.menu;

import javafx.scene.paint.Color;

public class RectMenuPane extends AbstractMenuPane {
    private Color rectColor;
    public RectMenuPane(long createdTick, double posX, double posY, double width, double height, Color rectColor) {
        super(createdTick, posX, posY, width, height);
        this.rectColor = rectColor;
    }

    public Color getRectColor() {
        return rectColor;
    }

    @Override
    public void onClick() {}

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }
}
