package mrmathami.thegame.ui.button;

import mrmathami.thegame.ui.AbstractButton;

public abstract class AbstractIngameButton extends AbstractButton {
    private double assetPosX;
    private double assetPosY;
    private double normalAssetPosX;
    private double normalAssetPosY;

    protected AbstractIngameButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, posX, posY, width, height);
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

    public abstract String onClick();

    @Override
    public void onFocus() {
        if (this.assetPosY == this.normalAssetPosY) {
            this.assetPosY = this.assetPosY + 1;
        }
    }

    @Override
    public void outFocus() {
        if (this.assetPosY > this.normalAssetPosY) {
            this.assetPosY = this.normalAssetPosY;
        }
    }
}
