package mrmathami.thegame.bar.button;

import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractUnclickableButton implements UIEntity {
    private long createdTick;
    private double assetPosX;
    private double assetPosY;
    private double posX;
    private double posY;
    private double width;
    private double height;

    public AbstractUnclickableButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        this.createdTick = createdTick;
        this.assetPosX = assetPosX;
        this.assetPosY = assetPosY;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    @Override
    public long getCreatedTick() {
        return createdTick;
    }

    public double getAssetPosX() {
        return assetPosX;
    }

    public double getAssetPosY() {
        return assetPosY;
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
}
