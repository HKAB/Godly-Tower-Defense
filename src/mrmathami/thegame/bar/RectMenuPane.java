package mrmathami.thegame.bar;

import javafx.scene.paint.Color;

public class RectMenuPane extends AbstractPane {
    private Color rectColor;
    public RectMenuPane(long createdTick, double posX, double posY, double width, double height, Color rectColor) {
        super(createdTick, posX, posY, width, height);
        this.rectColor = rectColor;
    }

    public Color getRectColor() {
        return rectColor;
    }

    @Override
    public String onClick() { return ""; }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }
}
