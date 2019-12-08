package mrmathami.thegame.towerpicker;

import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Bush;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

import java.util.Collection;

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

    @Override
    public void update(GameField field) {
        if (getPickingState() != NOT_BEING_PICKED) {
            Collection<GameEntity> gameEntities = field.getEntities();
            setPickingState(NOT_PICKABLE);

            for (GameEntity entity : gameEntities) {
                if (entity instanceof AbstractTower) {
                    if (isOverlappedWithTower(entity)) {
                        if (field.getMoney() < ((AbstractTower) entity).getUpgradePrice()) {
                            setPickingState(NOT_PICKABLE);
                        }
                        else {
                            setPickingState(PICKABLE);
                        }
                        return;
                    }
                }
            }
        }
    }
}