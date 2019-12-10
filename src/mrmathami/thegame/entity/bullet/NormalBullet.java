package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

public final class NormalBullet extends AbstractBullet {
	public NormalBullet(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
		super(createdTick, posX, posY, deltaX, deltaY,
				Config.NORMAL_BULLET_WIDTH /Config.TILE_SIZE,
				Config.NORMAL_BULLET_HEIGHT /Config.TILE_SIZE,
				Config.NORMAL_BULLET_SPEED,
				Config.NORMAL_BULLET_STRENGTH,
				Config.NORMAL_BULLET_TTL,
				Config.NORMAL_BULLET_GID,
				enemyTarget);
	}
}
