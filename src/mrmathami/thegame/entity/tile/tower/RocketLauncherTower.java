
package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.RocketBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.BigAircraft;

import javax.annotation.Nonnull;

public final class RocketLauncherTower extends AbstractTower<RocketBullet, BigAircraft> {
    public RocketLauncherTower(long createdTick, long posX, long posY, double angle) {
        super(createdTick, posX, posY, Config.ROCKET_TOWER_RANGE, Config.ROCKET_TOWER_SPEED, angle, Config.ROCKET_TOWER_LEVEL1_GID, BigAircraft.class);
    }

    @Nonnull
    @Override
    protected final RocketBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        return new RocketBullet(createdTick, posX - Config.ROCKET_BULLET_WIDTH/(2*Config.TILE_SIZE), posY - Config.ROCKET_BULLET_HEIGHT/(2*Config.TILE_SIZE), deltaX, deltaY, enemyTarget);
    }



}
