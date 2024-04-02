package mrmathami.thegame.ui.ingame.context;

import mrmathami.thegame.entity.tile.tower.*;

public class ButtonUIContext extends AbstractUIContext {

    private AbstractTower tower;
    private long money;

    public ButtonUIContext (long createdTick, double[] pos, long money, String towerType) {
        super(createdTick, pos);
        switch (towerType) {
            case "NormalTower":
                this.tower = new NormalTower(0, 0, 0, 90);
                break;
            case "RocketLauncherTower":
                this.tower = new RocketLauncherTower(0, 0, 0, 90);
                break;
            case "MachineGunTower":
                this.tower = new MachineGunTower(0, 0, 0, 90);
                break;
            case "RobotPoliceTower":
                this.tower = new RobotPoliceTower(0, 0, 0, 90);
                break;
            case "CardboardBoxTower":
                this.tower = new CardboardBoxTower(0, 0, 0, 90);
                break;
        }
        this.money = money;
    }

    public AbstractTower getTower() {
        return tower;
    }

    public long getMoney() {
        return money;
    }

    @Override
    public void setMoney(long money) {
        this.money = money;
    }
}
