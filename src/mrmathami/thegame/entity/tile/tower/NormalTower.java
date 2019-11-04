package mrmathami.thegame.entity.tile.tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.NormalEnemy;

import javax.annotation.Nonnull;

public final class NormalTower extends AbstractTower<NormalBullet, NormalEnemy> {
	public NormalTower(long createdTick, long posX, long posY, double angle, int GID) {
		super(createdTick, posX, posY, Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_SPEED, angle, GID, NormalEnemy.class);
	}

	@Nonnull
	@Override
	protected final NormalBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY) {
		return new NormalBullet(createdTick, posX, posY, deltaX, deltaY);
	}
}
