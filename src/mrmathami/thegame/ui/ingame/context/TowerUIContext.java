package mrmathami.thegame.ui.ingame.context;

import mrmathami.thegame.entity.tile.tower.AbstractTower;

public class TowerUIContext extends AbstractUIContext {

    private AbstractTower tower;

    public TowerUIContext (long createdTick, long money, long targetHealth, long currentWave, long countdown, AbstractTower tower) {
        super(createdTick, money, targetHealth, currentWave, countdown);
        this.tower = tower;
    }

    public AbstractTower getTower() {
        return tower;
    }
}
