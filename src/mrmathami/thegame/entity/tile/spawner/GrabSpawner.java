package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.GrabEnemy;

import javax.annotation.Nonnull;


public final class GrabSpawner extends AbstractSpawner<GrabEnemy> {
    public GrabSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.GRAB_ENEMY_SIZE, GrabEnemy.class, Config.GRAB_ENEMY_GID, spawnInterval, initialDelay, numOfSpawn);
    }

    @Nonnull
    @Override
    protected final GrabEnemy doSpawn(long createdTick, double posX, double posY) {
        return new GrabEnemy(createdTick, posX, posY);
    }
}
