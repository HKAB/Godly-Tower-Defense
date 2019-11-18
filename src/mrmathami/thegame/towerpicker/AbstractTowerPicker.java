package mrmathami.thegame.towerpicker;

import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Road;

public abstract class AbstractTowerPicker {
    private long posX;
    private long posY;
    private int pickingState;

    public final int NOT_BEING_PICKED = 0;
    public final int NOT_PICKABLE = 1;
    public final int PICKABLE = 2;

    public AbstractTowerPicker() {
        this.posX = 0;
        this.posY = 0;
    }

    public long getPosX() {
        return posX;
    }

    public long getPosY() {
        return posY;
    }

    public abstract int getGID();

    public void setPosition (long posX, long posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPickingState() {
        return pickingState;
    }

    public void setPickingState(int pickingState) {
        this.pickingState = pickingState;
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
            case 243:
                deltaIndex = new int[] {0, 3};
                break;
            case 48:
            case 265:
                deltaIndex = new int[] {0, 1};
                break;
            case 4:
            case 221:
                deltaIndex = new int[] {0, 5};
                break;
            case 47:
            case 264:
                deltaIndex = new int[] {0, 1, 7};
                break;
            case 49:
            case 266:
                deltaIndex = new int[] {0, 1, 3, 5};
                break;
            case 3:
            case 220:
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
