package mrmathami.thegame.towerpicker;

import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Road;

import java.util.HashMap;
import java.util.Map;

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
        final Map<Integer, int[]> DELTA_INDEX_MAP = new HashMap<>(Map.ofEntries(
                Map.entry(26 , new int[] {0, 3}),
                Map.entry(164, new int[] {0, 3}),
                Map.entry(233, new int[] {0, 3}),
                Map.entry(48 , new int[] {0, 1}),
                Map.entry(186, new int[] {0, 1}),
                Map.entry(255, new int[] {0, 1}),
                Map.entry(4  , new int[] {0, 5}),
                Map.entry(142, new int[] {0, 5}),
                Map.entry(211, new int[] {0, 5}),
                Map.entry(47 , new int[] {0, 1, 7}),
                Map.entry(185, new int[] {0, 1, 7}),
                Map.entry(254, new int[] {0, 1, 7}),
                Map.entry(3  , new int[] {0, 3, 8}),
                Map.entry(141, new int[] {0, 3, 8}),
                Map.entry(210, new int[] {0, 3, 8}),
                Map.entry(49 , new int[] {0, 1, 3, 5}),
                Map.entry(187, new int[] {0, 1, 3, 5}),
                Map.entry(256, new int[] {0, 1, 3, 5})
        ));

        final Road road = (Road)entity;
        int[] deltaIndex = {0};

        if (DELTA_INDEX_MAP.containsKey(road.getGID())) {
            deltaIndex = DELTA_INDEX_MAP.get(road.getGID());
        }
        else if (DELTA_INDEX_MAP.containsKey(road.getGID() - 5)) {
            deltaIndex = DELTA_INDEX_MAP.get(road.getGID() - 5);
        }
        else if (DELTA_INDEX_MAP.containsKey(road.getGID() - 10)) {
            deltaIndex = DELTA_INDEX_MAP.get(road.getGID() - 10);
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
        return entity.isBeingOverlapped(posX, posY, 1, 1);
    }

    public abstract void update (GameField field);
}
