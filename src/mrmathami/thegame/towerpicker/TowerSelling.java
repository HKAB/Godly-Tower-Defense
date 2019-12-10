package mrmathami.thegame.towerpicker;

import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Bush;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

import java.util.Collection;

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

    @Override
    public void update(GameField field) {
        if (getPickingState() != NOT_BEING_PICKED) {
            Collection<GameEntity> gameEntities = field.getEntities();
            setPickingState(NOT_PICKABLE);

            for (GameEntity entity : gameEntities) {
                if (entity instanceof AbstractTower) {
                    if (isOverlappedWithTower(entity)) {
                        setPickingState(PICKABLE);
                        return;
                    }
                }
            }
        }
    }
}
