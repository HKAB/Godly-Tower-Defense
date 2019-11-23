package mrmathami.thegame.ui.ingame.context;

import mrmathami.thegame.entity.tile.tower.AbstractTower;

public class TowerUIContext extends AbstractUIContext {

    private AbstractTower tower;
    private long money;

    public TowerUIContext (long createdTick, double[] pos, long money, AbstractTower tower) {
        super(createdTick, pos);
        this.tower = tower;
        this.money = money;
    }

    public AbstractTower getTower() {
        return tower;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
