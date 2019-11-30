package mrmathami.thegame.ui.popup.components;

import mrmathami.thegame.Config;
import mrmathami.thegame.ui.menu.AbstractMenuPane;

public class PopupInput extends AbstractMenuPane {
    private String text = "";
    private boolean isFocus = false;
    private int fontSize;
    public PopupInput(long createdTick, double posX, double posY, double width, double height, int fontSize) {
        //draw at center
        super(createdTick, posX - width/2/Config.TILE_SIZE, posY, width, height);
        this.fontSize = fontSize;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onFocus() {

    }

    @Override
    public void outFocus() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }


    public int getFontSize() {
        return fontSize;
    }
}
