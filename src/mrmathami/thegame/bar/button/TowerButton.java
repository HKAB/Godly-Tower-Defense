package mrmathami.thegame.bar.button;

import mrmathami.thegame.Config;

public class TowerButton extends AbstractButton {
    private int GID;
    private String towerType;

    public TowerButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height, String towerType) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
        this.towerType = towerType;
        if (towerType.equals("NormalTower")) this.GID = Config.NORMAL_TOWER_LV1_GID;
    }

    @Override
    public String onClick () {
        return towerType;
    }
}
