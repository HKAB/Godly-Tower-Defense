
package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

public final class MachineGunBullet extends AbstractBullet {
    public MachineGunBullet(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        super(createdTick, posX, posY, deltaX, deltaY, Config.MACHINE_GUN_BULLET_SPEED, Config.MACHINE_GUN_BULLET_STRENGTH, Config.MACHINE_GUN_BULLET_TTL, Config.MACHINE_GUN_BULLET_GID, enemyTarget);
    }
}
