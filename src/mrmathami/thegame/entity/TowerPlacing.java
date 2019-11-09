package mrmathami.thegame.entity;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;

public class TowerPlacing {
    public final int NOT_BEING_PLACED = 0;
    public final int NOT_PLACEABLE = 1;
    public final int PLACEABLE = 2;

    private int placingState;
    private AbstractTower tower;

    public TowerPlacing (String towerType) {
        if (towerType.equals("NormalTower")) tower = new NormalTower(0, 0, 0, 0, Config.NORMAL_TOWER_LV1_GID);
//        else if (towerType.equals("MachineGunTower"))
//        else if (towerType.equals("RocketLauncherTower"))
        this.placingState = NOT_BEING_PLACED;
    }

    public AbstractTower getTower() {
        return tower;
    }

    public void setPosition (double posX, double posY) {
        tower.setPosX(posX);
        tower.setPosY(posY);
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
        for (double[] deltaDirection : DELTA_DIRECTION_ARRAY) {
            final double posX = this.tower.getPosX() + deltaDirection[0];
            final double posY = this.tower.getPosY() + deltaDirection[1];
            if (entity.isBeingOverlapped(posX, posY, 1, 1)) return true;
        }
        return false;
    }

    public boolean isOverlappedWithTower (GameEntity entity) {
        if (entity.isBeingOverlapped(this.tower.getPosX(), this.tower.getPosY(), 1, 1)) return true;
        else return false;
    }
}