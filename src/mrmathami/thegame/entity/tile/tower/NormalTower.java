package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalAircraft;

import javax.annotation.Nonnull;

public final class NormalTower extends AbstractTower<NormalBullet, NormalAircraft> {
	public NormalTower(long createdTick, long posX, long posY, double angle) {
		super(createdTick, posX, posY, Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_SPEED, angle, Config.NORMAL_TOWER_LEVEL1_GID, NormalAircraft.class);
	}

	@Nonnull
	@Override
	protected final NormalBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
		return new NormalBullet(createdTick, posX - Config.NORMAL_BULLET_WIDTH/(2*Config.TILE_SIZE), posY - Config.NORMAL_BULLET_HEIGHT/(2*Config.TILE_SIZE), deltaX, deltaY, enemyTarget);
	}
}
