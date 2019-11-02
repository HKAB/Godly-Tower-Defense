package mrmathami.thegame.bar;

import mrmathami.thegame.GameEntities;
import mrmathami.thegame.entity.AbstractEntity;
import mrmathami.thegame.entity.GameEntity;

public abstract class AbstractButton implements GameEntity {
    private String pngName;
    private final long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;

    protected AbstractButton(long createdTick, double posX, double posY, double width, double height, String pngName) {
        this.createdTick = createdTick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.pngName = pngName + ".png";
    }
    public abstract void onClick();

    public String getPngName() {
        return pngName;
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
