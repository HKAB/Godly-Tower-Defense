package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalAircraft;

import javax.annotation.Nonnull;

public final class NormalTower extends AbstractTower<NormalBullet, NormalAircraft> {
	public int[] GID = new int[] {Config.NORMAL_TOWER_LEVEL1_GID, Config.NORMAL_TOWER_LEVEL2_GID, Config.NORMAL_TOWER_LEVEL3_GID};
	public NormalTower(long createdTick, long posX, long posY, double angle) {
		super(createdTick, posX, posY, Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_SPEED, angle, Config.NORMAL_TOWER_LEVEL1_GID, NormalAircraft.class);
		upgrade();
		upgrade();
	}

	@Nonnull
	@Override
	protected final NormalBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
		return new NormalBullet(createdTick, posX - Config.NORMAL_BULLET_WIDTH/(2*Config.TILE_SIZE), posY - Config.NORMAL_BULLET_HEIGHT/(2*Config.TILE_SIZE), deltaX, deltaY, enemyTarget);
	}

	@Override
	public boolean upgrade() {
		if (getLevel() == 2) return false;
		else
		{
			setLevel(getLevel() + 1);
			setRange(getRange() + 1);
			setSpeed((long) (getSpeed()/Math.pow(2, getLevel())));
			setGID(GID[getLevel()]);
			return true;
		}
	}

}
