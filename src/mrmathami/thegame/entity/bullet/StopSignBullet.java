
package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

public final class StopSignBullet extends AbstractBullet {
    public StopSignBullet(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        super(createdTick, posX, posY, deltaX, deltaY,
                Config.STOP_SIGN_BULLET_WIDTH/Config.TILE_SIZE,
                Config.STOP_SIGN_BULLET_HEIGHT/Config.TILE_SIZE,
                Config.STOP_SIGN_BULLET_SPEED,
                Config.STOP_SIGN_BULLET_STRENGTH,
                Config.STOP_SIGN_BULLET_TTL,
                Config.STOP_SIGN_BULLET_GID,
                enemyTarget);
    }
}
