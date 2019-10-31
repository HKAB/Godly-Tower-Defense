package mrmathami.thegame.bar;

import mrmathami.thegame.entity.GameEntity;

public abstract class AbstractBar implements GameEntity {
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

    @Override
    public final boolean isContaining(double posX, double posY, double width, double height) {
        return this.posX <= posX
                && this.posY <= posY
                && this.posX + this.width >= posX + width
                && this.posY + this.height >= posY + height;
    }

    @Override
    public final boolean isBeingContained(double posX, double posY, double width, double height) {
        return posX <= this.posX
                && posY <= this.posY
                && posX + width >= this.posX + this.width
                && posY + height >= this.posY + this.height;
    }

    @Override
    public final boolean isBeingOverlapped(double posX, double posY, double width, double height) {
        return posX < this.posX + this.width
                && posY < this.posY + this.height
                && posX + width > this.posX
                && posY + height > this.posY;
    }
}
