package mrmathami.thegame.ui.button;

import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractButton implements UIEntity {
    private final long createdTick;
    private double posX;
    private double posY;
    private double width;
    private double height;
    private double assetPosX;
    private double assetPosY;
    private double normalAssetPosX;
    private double normalAssetPosY;

    protected AbstractButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        this.createdTick = createdTick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.assetPosX = assetPosX;
        this.assetPosY = assetPosY;
        this.normalAssetPosX = assetPosX;
        this.normalAssetPosY = assetPosY;
    }

    public double getAssetPosX() {
        return assetPosX;
    }

    public double getAssetPosY() {
        return assetPosY;
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

    public abstract void onClick();

    public void onFocus() {
        if (this.assetPosX == this.normalAssetPosX) {
            this.assetPosX = this.assetPosX + width;
        }
    }

    public void outFocus() {
        if (this.assetPosX > this.normalAssetPosX) {
            this.assetPosX = this.normalAssetPosX;
        }
    }
}
