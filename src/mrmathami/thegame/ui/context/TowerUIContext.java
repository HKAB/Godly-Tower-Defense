package mrmathami.thegame.ui.context;

import mrmathami.thegame.entity.tile.tower.AbstractTower;

public class TowerUIContext extends AbstractUIContext {

    private AbstractTower tower;

    public TowerUIContext (long createdTick, double posX, double posY, double width, double height, AbstractTower tower) {
        super(createdTick, posX, posY, width, height);
        this.tower = tower;
    }

    public AbstractTower getTower() {
        return tower;
    }
}
