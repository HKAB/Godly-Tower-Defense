package mrmathami.thegame;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;

public class TowerPlacing {
    public final int NOT_BEING_PLACED = 0;
    public final int NOT_PLACEABLE = 1;
    public final int PLACEABLE = 2;

    private int placingState;
    private String towerType;
    private long posX;
    private long posY;

    public TowerPlacing (String towerType) {
        this.towerType = towerType;
        this.placingState = NOT_BEING_PLACED;
        this.posX = 0;
        this.posY = 0;
    }

    public AbstractTower getTower() {
        switch (towerType) {
            case "NormalTower":
                return new NormalTower(0, posX, posY, 90);
            case "RocketLauncherTower":
                return new RocketLauncherTower(0, posX, posY, 90);
            case "MachineGunTower":
                return new MachineGunTower(0, posX, posY, 90);
        }
        return null;
    }

    public void setPosition (long posX, long posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public long getTowerPrice() {
        switch (towerType) {
            case "NormalTower":
                return Config.NORMAL_TOWER_PRICE;
            case "RocketLauncherTower":
                return Config.ROCKET_TOWER_PRICE;
            case "MachineGunTower":
                return Config.MACHINE_GUN_TOWER_PRICE;
        }
        return 0;
    }

    public int getPlacingState() {
        return placingState;
    }

    public void setPlacingState(int placingState) {
        this.placingState = placingState;
    }

    public boolean isOverlappedWithRoad (GameEntity entity) {
        final double[][] DELTA_DIRECTION_ARRAY = {
            {0.0, 0.0},
            {0.0, -1.0}, {0.0, 1.0}, {-1.0, 0.0}, {1.0, 0.0},
            {-1.0, -1.0}, {1.0, 1.0}, {1.0, -1.0}, {-1.0, 1.0}
        };

        final Road road = (Road)entity;
        int[] deltaIndex = {0};

        switch (road.getGID()) {
            case 26:
                deltaIndex = new int[] {0, 3};
                break;
            case 48:
                deltaIndex = new int[] {0, 1};
                break;
            case 4:
                deltaIndex = new int[] {0, 5};
                break;
            case 47:
                deltaIndex = new int[] {0, 1, 7};
                break;
            case 49:
                deltaIndex = new int[] {0, 1, 3, 5};
                break;
            case 3:
                deltaIndex = new int[] {0, 3, 8};
                break;
        }
        for (int index: deltaIndex) {
            double[] deltaDirection = DELTA_DIRECTION_ARRAY[index];
            final double currentPosX = posX + deltaDirection[0];
            final double currentPosY = posY + deltaDirection[1];
            if (entity.isBeingOverlapped(currentPosX, currentPosY, 1, 1)) return true;
        }
        return false;
    }

    public boolean isOverlappedWithTower (GameEntity entity) {
        if (entity.isBeingOverlapped(posX, posY, 1, 1)) return true;
        else return false;
    }
}