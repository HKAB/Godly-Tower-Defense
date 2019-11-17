package mrmathami.thegame;


import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Game Field. Created from GameMap for each new stage. Represent the currently playing game.
 */
public final class GameField {
	@Nonnull private final Set<GameEntity> entities = new LinkedHashSet<>(Config._TILE_MAP_COUNT);
	@Nonnull private final Collection<GameEntity> unmodifiableEntities = Collections.unmodifiableCollection(entities);
	@Nonnull private final List<GameEntity> spawnEntities = new ArrayList<>(Config._TILE_MAP_COUNT);

	/**
	 * Field width
	 */
	private final double width;
	/**
	 * Field height
	 */
	private final double height;
	/**
	 * Field tick count
	 */
	private long tickCount;

	/**
	 * Field money
	 */
	private long money;

	public GameField(@Nonnull GameStage gameStage) {
		this.width = gameStage.getWidth();
		this.height = gameStage.getHeight();
		this.tickCount = 0;
		this.money = 0;
		entities.addAll(gameStage.getEntities());
	}

	public long getMoney() {
		return money;
	}


	public void setMoney(long money) {
		this.money = money;
	}

	public final double getWidth() {
		return width;
	}

	public final double getHeight() {
		return height;
	}

	public final long getTickCount() {
		return tickCount;
	}

	/**
	 * @return entities on the field. Read-only list.
	 */
	@Nonnull
	public final Collection<GameEntity> getEntities() {
		return unmodifiableEntities;
	}

	/**
	 * Add an Entity to spawn list. Entity will be spawned at the end of this tick.
	 *
	 * @param entity Entity to spawn
	 */
	public final void doSpawn(@Nonnull GameEntity entity) {
		spawnEntities.add(entity);
	}

	/**
	 * Do a tick, in other words, update the field after a fixed period of time.
	 * Current update sequence:
	 * 1. Update Entity:
	 * 1.1. UpdatableEntity update itself, including moving.
	 * 1.2. EffectEntity check collision to affect LivingEntity.
	 * 1.3. DestroyableEntity check and react if it is going to be destroyed.
	 * 2. Destroy Entity:
	 * 2.1. Destroy entities that are marked to be destroyed.
	 * 2.2. Destroy entities that are outside the field.
	 * 3. Spawn Entity: Add entities that are marked to be spawned.
	 */
	public final void tick() {
		this.tickCount += 1;

		// 1.1. Update UpdatableEntity
		for (final GameEntity entity : entities) {
			if (entity instanceof UpdatableEntity) ((UpdatableEntity) entity).onUpdate(this);
		}

		// 1.2. Update EffectEntity & LivingEntity
		for (final GameEntity entity : entities) {
			if (entity instanceof EffectEntity) {
				final EffectEntity effectEntity = (EffectEntity) entity;
				final Collection<LivingEntity> livingEntities = GameEntities.getAffectedEntities(entities,
						effectEntity.getClass(), entity.getPosX(), entity.getPosY(), entity.getWidth(), entity.getHeight());
				for (final LivingEntity livingEntity : livingEntities) {
					if (!effectEntity.onEffect(this, livingEntity)) break;
				}
			}
		}

		// 1.3. Update DestroyableEntity
		final List<GameEntity> destroyedEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		final List<GameEntity> effectEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entity instanceof DestroyableEntity && ((DestroyableEntity) entity).isDestroyed()) {
				if (entity instanceof DestroyListener) ((DestroyListener) entity).onDestroy(this);
				destroyedEntities.add(entity);
			}
		}

		// 2.1. Destroy entities
		for (GameEntity destroyEntity :
				destroyedEntities) {
			if (destroyEntity instanceof AbstractEnemy)
				entities.add(new ExplosionEffect(0, destroyEntity.getPosX() + Config.OFFSET/Config.TILE_SIZE, destroyEntity.getPosY() + Config.OFFSET/Config.TILE_SIZE, Config.EXPLOSION_TTL));
		}
		entities.removeAll(destroyedEntities);

		// 2.2. Destroy entities
		entities.removeIf(entity -> !entity.isBeingOverlapped(0.0, 0.0, width, height));

		// 3. Spawn entities
		for (GameEntity entity : spawnEntities) {
			entities.add(entity);
			if (entity instanceof SpawnListener) ((SpawnListener) entity).onSpawn(this);
		}
		spawnEntities.clear();
	}
}
