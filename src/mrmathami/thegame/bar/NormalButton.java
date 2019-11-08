package mrmathami.thegame.bar;

import mrmathami.thegame.Config;

public class NormalButton extends AbstractButton {
    public NormalButton(long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
    }

    @Override
    public void onClick() {
        System.out.println("Button Clicked");
    }

    @Override
    public void onFocus() {
        if (this.getAssetPosY() == this.getNormalAssetPosY()) {
            this.setAssetPosY(this.getAssetPosY() + Config.TILE_SIZE);
        }
    }

    @Override
    public void outFocus() {
        if (this.getAssetPosY() > this.getNormalAssetPosY()) {
            this.setAssetPosY(this.getNormalAssetPosY());
        }
    }
}
