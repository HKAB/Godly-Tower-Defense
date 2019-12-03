package mrmathami.thegame.entity.tile.tower;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalAircraft;

import javax.annotation.Nonnull;
import java.io.File;

public final class NormalTower extends AbstractTower<NormalBullet> {
	public int[] GID = new int[] {Config.NORMAL_TOWER_LEVEL1_GID, Config.NORMAL_TOWER_LEVEL2_GID, Config.NORMAL_TOWER_LEVEL3_GID};
	private long[] SELL_PRICE = {Config.NORMAL_TOWER_LEVEL1_SELL_PRICE, Config.NORMAL_TOWER_LEVEL2_SELL_PRICE, Config.NORMAL_TOWER_LEVEL3_SELL_PRICE};
	private long[] UPGRADE_PRICE = {Config.NORMAL_TOWER_LEVEL1_UPGRADE_PRICE, Config.NORMAL_TOWER_LEVEL2_UPGRADE_PRICE, 0};

	public NormalTower(long createdTick, long posX, long posY, double angle) {
		super(createdTick, posX, posY, Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_SPEED, angle, Config.NORMAL_TOWER_LEVEL1_GID, NormalBullet.class);
	}

	@Nonnull
	@Override
	protected final NormalBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
		GameAudio.getInstance().playSound(new AudioClip(GameAudio.normalBulletSound));
		return new NormalBullet(createdTick, posX - Config.NORMAL_BULLET_WIDTH/(2*Config.TILE_SIZE), posY - Config.NORMAL_BULLET_HEIGHT/(2*Config.TILE_SIZE), deltaX, deltaY, enemyTarget);
	}

	@Override
	public boolean upgrade() {
		if (getLevel() == 2) return false;
		else
		{
			setLevel(getLevel() + 1);
			setRange(getRange() + 1);
			setSpeed((getSpeed()/(2)));
			setGID(GID[getLevel()]);
			return true;
		}
	}

	@Override
	public long getFirepower() {
		return Config.NORMAL_BULLET_STRENGTH;
	}

	@Override
	public long getPrice() {
		return Config.NORMAL_TOWER_PRICE;
	}

	@Override
	public long getSellPrice() {
		return SELL_PRICE[getLevel()];
	}

	@Override
	public long getUpgradePrice() {
		return UPGRADE_PRICE[getLevel()];
	}
}
