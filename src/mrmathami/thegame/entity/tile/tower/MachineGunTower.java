
package mrmathami.thegame.entity.tile.tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.Tanker;

import javax.annotation.Nonnull;

public final class MachineGunTower extends AbstractTower<MachineGunBullet, Tanker> {
    public MachineGunTower(long createdTick, long posX, long posY, double angle) {
        super(createdTick, posX, posY, Config.MACHINE_GUN_TOWER_RANGE, Config.MACHINE_GUN_TOWER_SPEED, angle, Config.MACHINE_GUN_TOWER_LEVEL1_GID, Tanker.class);
    }

    @Nonnull
    @Override
    protected final MachineGunBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        return new MachineGunBullet(createdTick, posX + 0.4, posY + 0.4, deltaX, deltaY, enemyTarget);
    }



}
