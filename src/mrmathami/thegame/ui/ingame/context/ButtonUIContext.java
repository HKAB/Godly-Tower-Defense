package mrmathami.thegame.ui.ingame.context;

import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;

public class ButtonUIContext extends AbstractUIContext {

    private AbstractTower tower;

    public ButtonUIContext (long createdTick, long money, long targetHealth, long currentWave, long countdown, String towerType) {
        super(createdTick, money, targetHealth, currentWave, countdown);
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
        }
    }

    public AbstractTower getTower() {
        return tower;
    }
}
