package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.BigAircraft;

import javax.annotation.Nonnull;


public final class BigAircraftSpawner extends AbstractSpawner<BigAircraft> {
    public BigAircraftSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.BIG_AIRCRAFT_ENEMY_SIZE, BigAircraft.class, Config.BIG_AIRCRAFT_ENEMY_GID[0], spawnInterval, initialDelay, numOfSpawn);
    }

    @Nonnull
    @Override
    protected final BigAircraft doSpawn(long createdTick, double posX, double posY) {
        return new BigAircraft(createdTick, posX, posY);
    }
}
