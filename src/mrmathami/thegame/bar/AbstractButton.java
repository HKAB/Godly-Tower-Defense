package mrmathami.thegame.bar;

import mrmathami.thegame.GameEntities;
import mrmathami.thegame.entity.AbstractEntity;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;

public abstract class AbstractButton implements UIEntity {
    private final long createdTick;
    private double assetPosX;
    private double assetPosY;
    private double posX;
    private double posY;
    private double width;
    private double height;
    private double normalAssetPosX;
    private double normalAssetPosY;

    protected AbstractButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        this.createdTick = createdTick;
        this.assetPosX = assetPosX;
        this.assetPosY = assetPosY;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.normalAssetPosX = assetPosX;
        this.normalAssetPosY = assetPosY;
    }

    public abstract void onClick();
    public abstract void onFocus();
    public abstract void outFocus();

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

    public void setAssetPosX(double assetPosX) {
        this.assetPosX = assetPosX;
    }

    public void setAssetPosY(double assetPosY) {
        this.assetPosY = assetPosY;
    }

    public double getNormalAssetPosX() {
        return normalAssetPosX;
    }

    public double getNormalAssetPosY() {
        return normalAssetPosY;
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
