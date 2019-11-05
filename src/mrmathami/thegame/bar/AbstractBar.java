package mrmathami.thegame.bar;

import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractBar implements UIEntity {
    private final long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;

    protected AbstractBar(long createdTick, double posX, double posY, double width, double height) {
        this.createdTick = createdTick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public long getCreatedTick() {
        return createdTick;
    }
}
