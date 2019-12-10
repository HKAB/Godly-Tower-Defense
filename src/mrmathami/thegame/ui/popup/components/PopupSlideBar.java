package mrmathami.thegame.ui.popup.components;

import mrmathami.thegame.entity.UIEntity;

public class PopupSlideBar implements UIEntity {
    private double posX;
    private double posY;
    private double width;
    private double height;
    private double initialValue;

    public PopupSlideBar(double posX, double posY, double width, double height, double initialValue) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.initialValue = initialValue;
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
        return width;
    }

    @Override
    public double getHeight() {
        return height;
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

    public double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(double initialValue) {
        if (initialValue < 0)
            this.initialValue = 0;
        else if (initialValue > 100) this.initialValue = 100;
        else
            this.initialValue = initialValue;
    }
}
