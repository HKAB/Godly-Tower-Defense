package mrmathami.thegame.towerpicker;

import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

public class TowerUpgrading extends AbstractTowerPicker {
    private int GID;

    public TowerUpgrading () {
        this.GID = 17;
        super.setPickingState(NOT_BEING_PICKED);
    }

    public long getUpgradePrice (GameEntity entity) {
        if (entity instanceof AbstractTower) {
            return ((AbstractTower)entity).getUpgradePrice();
        }
        return 0;
    }

    @Override
    public int getGID() {
        return GID;
    }
}