package mrmathami.thegame.towerpicker;

import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

public class TowerSelling extends AbstractTowerPicker {
    private int GID;

    public TowerSelling () {
        this.GID = 18;
        super.setPickingState(NOT_BEING_PICKED);
    }

    public long getSellPrice (GameEntity entity) {
        if (entity instanceof AbstractTower) {
            return ((AbstractTower)entity).getSellPrice();
        }
        return 0;
    }

    @Override
    public int getGID() {
        return GID;
    }
}
