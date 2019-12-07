package mrmathami.thegame;


import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;
import mrmathami.thegame.entity.tile.spawner.AbstractSpawner;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Game Field. Created from GameMap for each new stage. Represent the currently playing game.
 */
public class GameField {
	@Nonnull protected final Set<GameEntity> entities = new LinkedHashSet<>(Config._TILE_MAP_COUNT);
	@Nonnull protected final Collection<GameEntity> unmodifiableEntities = Collections.unmodifiableCollection(entities);
	@Nonnull protected final List<GameEntity> spawnEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
	// This is not a list of EffectEntity, it's a list of AbstractEffect entity
	@Nonnull private final List<GameEntity> sfxEntities = new ArrayList<>(Config._TILE_MAP_COUNT);

	/**
	 * Field width.
	 */
	protected final double width;
	/**
	 * Field height.
	 */
	protected final double height;
	/**
	 * Field tick count.
	 */
	protected long tickCount;

	/**
	 * Player money.
	 */
	protected long money;

	/**
	 * Player health.
	 */
	protected long health;

	/**
	 * If the game is multi-player.
	 * We will check for it when an enemy harm the target to decide whether to subtract player health OR send state to opponent.
	 */
	protected boolean isMultiplayer = false;

	public GameField(@Nonnull GameStage gameStage) {
		this.width = gameStage.getWidth();
		this.height = gameStage.getHeight();
		this.tickCount = 0;
		this.money = Config.PLAYER_INITIAL_MONEY;
		this.health = Config.PLAYER_INITIAL_HEALTH;
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

	public final long getHealth() {
		return health;
	}

	public final void setHealth(long health) {
		this.health = health;
	}

	public void harmPlayer(long damage) {
		this.health -= damage;
	}

	public boolean isLoss() {
		return this.health <= 0;
	}

	public final long getTickCount() {
		return tickCount;
	}

	public boolean isMultiplayer() {
		return isMultiplayer;
	}

	public void setMultiplayer(boolean multiplayer) {
		isMultiplayer = multiplayer;
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

	public void addSFX(@Nonnull GameEntity entity) {
		sfxEntities.add(entity);
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
	public void tick() {
		this.tickCount += 1;
		if ((this.tickCount % 10) == 0) this.money++;

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
				sfxEntities.add(new ExplosionEffect(0, destroyEntity.getPosX() + Config.OFFSET/Config.TILE_SIZE, destroyEntity.getPosY() + Config.OFFSET/Config.TILE_SIZE));
		}
		entities.removeAll(destroyedEntities);

		// 2.2. Destroy entities (removed becuz it deleting my entities :<)
//		entities.removeIf(entity -> !entity.isBeingOverlapped(0.0, 0.0, width, height));

		// 3. Spawn entities
		for (GameEntity entity : spawnEntities) {
			entities.add(entity);
			if (entity instanceof SpawnListener) ((SpawnListener) entity).onSpawn(this);
		}
		spawnEntities.clear();

		entities.addAll(sfxEntities);
		sfxEntities.clear();
	}

	public boolean isWon()
	{
		for (GameEntity entity :
				entities) {
			if (entity instanceof AbstractSpawner)
			{
				if (((AbstractSpawner) entity).getNumOfSpawn() > 0)
				{
					return false;
				}
			}
			if (entity instanceof AbstractEnemy)
			{
				return false;
			}
		}
		return getHealth() > 0;
	}
}
