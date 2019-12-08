package mrmathami.thegame;

import mrmathami.thegame.entity.EffectEntity;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.LivingEntity;
import mrmathami.thegame.entity.bullet.*;
import mrmathami.thegame.entity.enemy.*;
import mrmathami.thegame.entity.enemy.boss.*;
import mrmathami.thegame.entity.tile.Bush;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Target;
import mrmathami.thegame.entity.tile.tower.*;
import mrmathami.utilities.Pair;
import mrmathami.utilities.UnorderedPair;

import javax.annotation.Nonnull;
import java.util.*;

public final class GameEntities {
	/**
	 * Remember, if an entity can collide with itself, you should put that here too.
	 */
	private static final Set<UnorderedPair<Class<? extends GameEntity>, Class<? extends GameEntity>>> COLLISION_PAIR_SET
			= new HashSet<>(Set.of(
			UnorderedPair.immutableOf(Mountain.class, NormalAircraft.class),
			UnorderedPair.immutableOf(Mountain.class, BigAircraft.class),
			UnorderedPair.immutableOf(Mountain.class, Tanker.class),
			UnorderedPair.immutableOf(Bush.class, NormalTower.class),
			UnorderedPair.immutableOf(Bush.class, RocketLauncherTower.class),
			UnorderedPair.immutableOf(Bush.class, MachineGunTower.class),
			UnorderedPair.immutableOf(Bush.class, RobotPoliceTower.class),
			UnorderedPair.immutableOf(NormalAircraft.class, NormalAircraft.class),
			UnorderedPair.immutableOf(BigAircraft.class, BigAircraft.class),
			UnorderedPair.immutableOf(Tanker.class, Tanker.class)
	));

	private static final Set<Pair<Class<? extends EffectEntity>, Class<? extends LivingEntity>>> EFFECT_LIVING_SET
			= new HashSet<>(Set.of(
			Pair.immutableOf(MachineGunBullet.class, NormalAircraft.class),
			Pair.immutableOf(NormalBullet.class, NormalAircraft.class),
			Pair.immutableOf(RocketBullet.class, BigAircraft.class),
			Pair.immutableOf(StopSignBullet.class, GrabEnemy.class),
			Pair.immutableOf(MachineGunBullet.class, Tanker.class),
			Pair.immutableOf(NormalBullet.class, JohnCenaBossEnemy.class),
			Pair.immutableOf(MachineGunBullet.class, JohnCenaBossEnemy.class),
			Pair.immutableOf(RocketBullet.class, JohnCenaBossEnemy.class),
			Pair.immutableOf(NormalBullet.class, BinLadenBossEnemy.class),
			Pair.immutableOf(MachineGunBullet.class, BinLadenBossEnemy.class),
			Pair.immutableOf(RocketBullet.class, BinLadenBossEnemy.class),
			Pair.immutableOf(NormalBullet.class, KimJongUnBossEnemy.class),
			Pair.immutableOf(MachineGunBullet.class, KimJongUnBossEnemy.class),
			Pair.immutableOf(RocketBullet.class, KimJongUnBossEnemy.class),
			Pair.immutableOf(NormalBullet.class, SonGokuBossEnemy.class),
			Pair.immutableOf(MachineGunBullet.class, SonGokuBossEnemy.class),
			Pair.immutableOf(RocketBullet.class, SonGokuBossEnemy.class),
			Pair.immutableOf(NormalBullet.class, ElonMuskBossEnemy.class),
			Pair.immutableOf(MachineGunBullet.class, ElonMuskBossEnemy.class),
			Pair.immutableOf(RocketBullet.class, ElonMuskBossEnemy.class),
			Pair.immutableOf(NormalBullet.class, MedicBossEnemy.class),
			Pair.immutableOf(MachineGunBullet.class, MedicBossEnemy.class),
			Pair.immutableOf(RocketBullet.class, MedicBossEnemy.class),
			Pair.immutableOf(NormalAircraft.class, Target.class),
			Pair.immutableOf(BigAircraft.class, Target.class),
			Pair.immutableOf(Tanker.class, Target.class),
			Pair.immutableOf(GrabEnemy.class, Target.class),
			Pair.immutableOf(JohnCenaBossEnemy.class, Target.class),
			Pair.immutableOf(BinLadenBossEnemy.class, Target.class),
			Pair.immutableOf(KimJongUnBossEnemy.class, Target.class),
			Pair.immutableOf(SonGokuBossEnemy.class, Target.class),
			Pair.immutableOf(ElonMuskBossEnemy.class, Target.class),
			Pair.immutableOf(MedicBossEnemy.class, Target.class)
	));

	private GameEntities() {
	}

	/**
	 * An useful method to find entities that are containing this specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities input entities
	 * @param posX     rectangle pos x
	 * @param posY     rectangle pos y
	 * @param width    rectangle width
	 * @param height   rectangle height
	 * @return containing entities
	 */
	public static Collection<GameEntity> getContainingEntities(Collection<GameEntity> entities,
			double posX, double posY, double width, double height) {
		final List<GameEntity> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entity.isContaining(posX, posY, width, height)) outputEntities.add(entity);
		}
		return outputEntities;
	}

	/**
	 * An useful method to find entities that are being contained in a specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities input entities
	 * @param posX     rectangle pos x
	 * @param posY     rectangle pos y
	 * @param width    rectangle width
	 * @param height   rectangle height
	 * @return contained entities
	 */
	public static Collection<GameEntity> getContainedEntities(Collection<GameEntity> entities,
			double posX, double posY, double width, double height) {
		final List<GameEntity> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entity.isBeingContained(posX, posY, width, height)) outputEntities.add(entity);
		}
		return outputEntities;
	}

	/**
	 * An useful method to find entities that are being overlapped in a specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities input entities
	 * @param posX     rectangle pos x
	 * @param posY     rectangle pos y
	 * @param width    rectangle width
	 * @param height   rectangle height
	 * @return overlapped entities
	 */
	public static Collection<GameEntity> getOverlappedEntities(Collection<GameEntity> entities,
			double posX, double posY, double width, double height) {
		final List<GameEntity> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entity.isBeingOverlapped(posX, posY, width, height)) outputEntities.add(entity);
		}
		return outputEntities;
	}

	/**
	 * An useful method to find entities that are being overlapped in a specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities    input entities
	 * @param entityClass filter entity class
	 * @param posX        rectangle pos x
	 * @param posY        rectangle pos y
	 * @param width       rectangle width
	 * @param height      rectangle height
	 * @return overlapped entities
	 */
	public static <E extends GameEntity> Collection<E> getFilteredContainingEntities(Collection<GameEntity> entities,
			Class<E> entityClass, double posX, double posY, double width, double height) {
		final List<E> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entityClass.isInstance(entity) && entity.isContaining(posX, posY, width, height)) {
				outputEntities.add(entityClass.cast(entity));
			}
		}
		return outputEntities;
	}

	/**
	 * An useful method to find entities that are being overlapped in a specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities    input entities
	 * @param entityClass filter entity class
	 * @param posX        rectangle pos x
	 * @param posY        rectangle pos y
	 * @param width       rectangle width
	 * @param height      rectangle height
	 * @return overlapped entities
	 */
	public static <E extends GameEntity> Collection<E> getFilteredContainedEntities(Collection<GameEntity> entities,
			Class<E> entityClass, double posX, double posY, double width, double height) {
		final List<E> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entityClass.isInstance(entity) && entity.isBeingContained(posX, posY, width, height)) {
				outputEntities.add(entityClass.cast(entity));
			}
		}
		return outputEntities;
	}

	/**
	 * An useful method to find entities that are being overlapped in a specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities    input entities
	 * @param entityClass filter entity class
	 * @param posX        rectangle pos x
	 * @param posY        rectangle pos y
	 * @param width       rectangle width
	 * @param height      rectangle height
	 * @return overlapped entities
	 */
	public static <E extends GameEntity> Collection<E> getFilteredOverlappedEntities(Collection<GameEntity> entities,
			Class<E> entityClass, double posX, double posY, double width, double height) {
		final List<E> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entityClass.isInstance(entity) && entity.isBeingOverlapped(posX, posY, width, height)) {
				outputEntities.add(entityClass.cast(entity));
			}
		}
		return outputEntities;
	}

	/**
	 * Check whether two entity class can collide. If you have an entity, use entity.getClass() to get its class.
	 *
	 * @param entityClassA entity class
	 * @param entityClassB entity class
	 * @return if two entity can collide
	 */
	public static boolean isCollidable(@Nonnull Class<? extends GameEntity> entityClassA, @Nonnull Class<? extends GameEntity> entityClassB) {
		return COLLISION_PAIR_SET.contains(UnorderedPair.immutableOf(entityClassA, entityClassB));
	}

	/**
	 * An useful method to find entities that are being collided with a specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities input entities
	 * @param posX     rectangle pos x
	 * @param posY     rectangle pos y
	 * @param width    rectangle width
	 * @param height   rectangle height
	 * @return collided entities
	 */
	public static Collection<GameEntity> getCollidedEntities(Collection<GameEntity> entities,
			Class<? extends GameEntity> entityClass, double posX, double posY, double width, double height) {
		final List<GameEntity> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entity.isBeingOverlapped(posX, posY, width, height) && isCollidable(entityClass, entity.getClass())) {
				outputEntities.add(entity);
			}
		}
		return outputEntities;
	}

	/**
	 * Check whether effect entity can affect living entity. If you have an entity,
	 * use entity.getClass() to get its class.
	 *
	 * @param effectClass effect entity class
	 * @param livingClass living entity class
	 * @return if effect entity can affect living entity
	 */
	public static boolean isAffectable(@Nonnull Class<? extends EffectEntity> effectClass, @Nonnull Class<? extends LivingEntity> livingClass) {
		return EFFECT_LIVING_SET.contains(Pair.immutableOf(effectClass, livingClass));
	}

	/**
	 * An useful method to find entities that are being affected by a specific rectangle region.
	 * Do not touch, just use me.
	 *
	 * @param entities input entities
	 * @param posX     rectangle pos x
	 * @param posY     rectangle pos y
	 * @param width    rectangle width
	 * @param height   rectangle height
	 * @return affected entities
	 */
	public static Collection<LivingEntity> getAffectedEntities(Collection<GameEntity> entities,
			Class<? extends EffectEntity> entityClass, double posX, double posY, double width, double height) {
		final List<LivingEntity> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final GameEntity entity : entities) {
			if (entity instanceof LivingEntity && entity.isBeingOverlapped(posX, posY, width, height)) {
				final LivingEntity livingEntity = (LivingEntity) entity;
				if (isAffectable(entityClass, livingEntity.getClass())) {
					if (!((livingEntity instanceof BossEnemy) && (((BossEnemy) livingEntity).isInvisible()))) {
						outputEntities.add(livingEntity);
					}
				}
			}
		}
		return outputEntities;
	}

	public static <E extends GameEntity, V extends E> Collection<V> entitiesFilter(Collection<E> entities, Class<V> entityClass) {
		final List<V> outputEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
		for (final E entity : entities)
			if (entityClass.isInstance(entity)) outputEntities.add(entityClass.cast(entity));
		return outputEntities;
	}
}
