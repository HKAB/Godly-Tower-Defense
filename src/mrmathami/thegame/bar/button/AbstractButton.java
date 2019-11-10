package mrmathami.thegame.bar.button;

import mrmathami.thegame.Config;
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

    public abstract String onClick();

    public void onFocus() {
        if (this.assetPosY == this.normalAssetPosY) {
            this.assetPosY = this.assetPosY + 1;
        }
    }

    public void outFocus() {
        if (this.assetPosY > this.normalAssetPosY) {
            this.assetPosY = this.normalAssetPosY;
        }
    }
}
