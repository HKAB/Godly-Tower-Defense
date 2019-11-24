package mrmathami.thegame.ui.popup.components;

import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.ui.menu.AbstractMenuPane;

public class PopupLabel implements UIEntity {
    private String text;
    private double fontSize;
    private double posX;
    private double posY;
    private double createdTick;
    public PopupLabel(long createdTick, double posX, double posY, double fontSize, String text) {
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.fontSize = fontSize;
    }

    @Override
    public long getCreatedTick() {
        return 0;
    }

    @Override
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
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

    public double getFontSize() {
        return fontSize;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }
}
