package mrmathami.thegame.ui.ingame.button;

import mrmathami.thegame.Config;
import mrmathami.thegame.ui.button.AbstractButton;

public class TowerButton extends AbstractButton {
    private int GID;
    private String towerType;
    private String keyBinding;

    public TowerButton (long createdTick, double assetPosX, double assetPosY, double posX, double posY, double width, double height, String towerType) {
        super(createdTick, assetPosX, assetPosY, posX, posY, width, height);
        this.towerType = towerType;

        switch (towerType) {
            case "NormalTower":
                this.GID = Config.NORMAL_TOWER_LEVEL1_GID;
                this.keyBinding = "Q";
                break;
            case "RocketLauncherTower":
                this.GID = Config.ROCKET_TOWER_LEVEL1_GID;
                this.keyBinding = "W";
                break;
            case "MachineGunTower":
                this.GID = Config.MACHINE_GUN_TOWER_LEVEL1_GID;
                this.keyBinding = "E";
                break;
            case "RobotPoliceTower":
                this.GID = Config.ROBOT_POLICE_TOWER_LEVEL1_GID;
                this.keyBinding = "R";
                break;
            default:
                this.GID = Config.LOCKED_GID;
                this.keyBinding = "";
        }
    }

    public int getGID () {
        return GID;
    }

    public String getTowerType() {
        return towerType;
    }

    public String getKeyBinding() {
        return keyBinding;
    }

    @Override
    public void onClick () {

    }
}
