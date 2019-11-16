package mrmathami.thegame.ui.button;

import mrmathami.thegame.Config;

public class TowerButton extends AbstractButton {
    private int GID;
    private String towerType;

    public TowerButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height, String towerType) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
        this.towerType = towerType;

        if (towerType.equals("NormalTower")) this.GID = Config.NORMAL_TOWER_LEVEL1_GID;
        else if (towerType.equals("RocketLauncherTower")) this.GID = Config.ROCKET_TOWER_LEVEL1_GID;
        else if (towerType.equals("MachineGunTower")) this.GID = Config.MACHINE_GUN_TOWER_LEVEL1_GID;
        else this.GID = Config.LOCKED_GID;
    }

    public int getGID () {
        return GID;
    }

    @Override
    public String onClick () {
        return towerType;
    }
}
