package mrmathami.thegame.entity.tile.tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.NormalEnemy;

import javax.annotation.Nonnull;

public final class NormalTower extends AbstractTower<NormalBullet, NormalAircraft> {
	public NormalTower(long createdTick, long posX, long posY, double angle) {
		super(createdTick, posX, posY, Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_SPEED, angle, Config.NORMAL_TOWER_LEVEL1_GID, NormalAircraft.class);
	}

	@Nonnull
	@Override
	protected final NormalBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
		return new NormalBullet(createdTick, posX + 0.4, posY + 0.4, deltaX, deltaY, enemyTarget);
	}
}
