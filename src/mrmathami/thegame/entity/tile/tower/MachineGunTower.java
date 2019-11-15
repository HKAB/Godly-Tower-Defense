
package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.Tanker;

import javax.annotation.Nonnull;

public final class MachineGunTower extends AbstractTower<MachineGunBullet, Tanker> {
    public MachineGunTower(long createdTick, long posX, long posY, double angle) {
        super(createdTick, posX, posY, Config.MACHINE_GUN_TOWER_RANGE, Config.MACHINE_GUN_TOWER_SPEED, angle, Config.MACHINE_GUN_TOWER_LEVEL1_GID, Tanker.class);
    }

    @Nonnull
    @Override
    protected final MachineGunBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        return new MachineGunBullet(createdTick, posX - Config.MACHINE_GUN_BULLET_WIDTH/(2*Config.TILE_SIZE), posY - Config.MACHINE_GUN_BULLET_HEIGHT/(2*Config.TILE_SIZE), deltaX, deltaY, enemyTarget);
    }



}
