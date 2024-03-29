package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UpdatableEntity;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.AbstractTile;
import mrmathami.thegame.entity.tile.effect.AlertEffect;

import javax.annotation.Nonnull;
import java.util.Collection;

public abstract class AbstractSpawner<E extends AbstractEnemy> extends AbstractTile implements UpdatableEntity {
	private final double spawningSize;
	@Nonnull private final Class<E> spawningClass;
	private final long spawnInterval;
	private long tickDown;
	private long numOfSpawn;
	private boolean isNotif = false;
	private int enemyRepresentGID;

	protected AbstractSpawner(long createdTick, long posX, long posY, long width, long height, double spawningSize, @Nonnull Class<E> spawningClass, int enemyRepresentGID, long spawnInterval, long initialDelay, long numOfSpawn) {
		super(createdTick, posX, posY, width, height, 0);
		this.spawningSize = spawningSize;
		this.spawningClass = spawningClass;
		this.enemyRepresentGID = enemyRepresentGID;
		this.spawnInterval = spawnInterval;
		this.tickDown = initialDelay;
		this.numOfSpawn = numOfSpawn;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		if (tickDown == 20 && !isNotif)
		{
			field.addSFX(new AlertEffect(0, getPosX(), getPosY(), 15, enemyRepresentGID, 1));
			isNotif = true;
		}
		if (tickDown <= 0 && numOfSpawn > 0) {
			// Check if the spot is valid and then spawn an enemy
			// Remember to set this.tickDown back to this.spawnInterval
			// and decrease this.numOfSpawn once you spawn an enemy.
			// TODO: check if spot is valid
			boolean valid = true;
			final Collection<GameEntity> overlappedEntities = GameEntities.getOverlappedEntities(field.getEntities(), getPosX(), getPosY(), getWidth(), getHeight());
			for (GameEntity entity: overlappedEntities)
			{
				if (GameEntities.isCollidable(spawningClass, entity.getClass()))
				{
					System.out.println("one fucked my way!!");
					this.tickDown = spawnInterval;
					valid = false;
					break;
				}
			}
			if (valid) {
				field.doSpawn(doSpawn(field.getTickCount(), getPosX(), getPosY()));
				this.tickDown = spawnInterval;
				this.numOfSpawn -= 1;
			}
		}
	}

	/**
	 * Create a new enemy. Each spawner spawn different type of enemy.
	 * Override this method and return the type of enemy that your spawner spawn.
	 * See NormalSpawner for example.
	 *
	 * @param createdTick createdTick
	 * @param posX posX
	 * @param posY posY
	 * @return new enemy entity
	 */
	@Nonnull
	protected abstract E doSpawn(long createdTick, double posX, double posY);

	public long getNumOfSpawn() {
		return numOfSpawn;
	}
}
