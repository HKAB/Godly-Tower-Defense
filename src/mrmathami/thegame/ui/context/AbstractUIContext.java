package mrmathami.thegame.ui.context;

import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractUIContext implements UIEntity {

    private long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;

    public AbstractUIContext (long createdTick, double posX, double posY, double width, double height) {
        this.createdTick = createdTick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    @Override
    public long getCreatedTick() {
        return createdTick;
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

    public abstract String onClick();
    public abstract void onFocus();
    public abstract void outFocus();
}
