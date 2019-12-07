package mrmathami.thegame.towerpicker;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.tile.tower.*;

public class TowerPlacing extends AbstractTowerPicker {
    public final int NOT_BEING_PLACED = 0;
    public final int NOT_PLACEABLE = 1;
    public final int PLACEABLE = 2;

    private String towerType;
    private int GID;
    private long towerPrice;

    public TowerPlacing (String towerType) {
        this.towerType = towerType;
        super.setPickingState(NOT_BEING_PLACED);

        switch (towerType) {
            case "NormalTower":
                this.GID = Config.NORMAL_TOWER_LEVEL1_GID;
                this.towerPrice = Config.NORMAL_TOWER_PRICE;
                break;
            case "RocketLauncherTower":
                this.GID = Config.ROCKET_TOWER_LEVEL1_GID;
                this.towerPrice = Config.ROCKET_TOWER_PRICE;
                break;
            case "MachineGunTower":
                this.GID = Config.MACHINE_GUN_TOWER_LEVEL1_GID;
                this.towerPrice = Config.MACHINE_GUN_TOWER_PRICE;
                break;
            case "RobotPoliceTower":
                this.GID = Config.ROBOT_POLICE_TOWER_LEVEL1_GID;
                this.towerPrice = Config.ROBOT_POLICE_TOWER_PRICE;
                break;
            case "CardboardBoxTower":
                this.GID = Config.CARDBOARD_BOX_TOWER_LEVEL1_GID;
                this.towerPrice = Config.CARDBOARD_BOX_TOWER_PRICE;
                break;
        }
    }

    public AbstractTower getTower() {
        switch (towerType) {
            case "NormalTower":
                return new NormalTower(0, getPosX(), getPosY(), 90);
            case "RocketLauncherTower":
                return new RocketLauncherTower(0, getPosX(), getPosY(), 90);
            case "MachineGunTower":
                return new MachineGunTower(0, getPosX(), getPosY(), 90);
            case "RobotPoliceTower":
                return new RobotPoliceTower(0, getPosX(), getPosY(), 90);
            case "CardboardBoxTower":
                return new CardboardBoxTower(0, getPosX(), getPosY(), 90);
        }
        return null;
    }

    public double getRange() {
        switch (towerType) {
            case "NormalTower":
                return Config.NORMAL_TOWER_RANGE;
            case "RocketLauncherTower":
                return Config.ROCKET_TOWER_RANGE;
            case "MachineGunTower":
                return Config.MACHINE_GUN_TOWER_RANGE;
            case "RobotPoliceTower":
                return Config.ROBOT_POLICE_TOWER_RANGE;
            case "CardboardBoxTower":
                return Config.CARDBOARD_BOX_TOWER_RANGE;
        }
        return 0;
    }

    public String getTowerType() {
        return towerType;
    }

    public long getTowerPrice() {
        return towerPrice;
    }

    public int getPlacingState() {
        return super.getPickingState();
    }

    public void setPlacingState(int placingState) {
        super.setPickingState(placingState);
    }

    @Override
    public int getGID() {
        return GID;
    }
}