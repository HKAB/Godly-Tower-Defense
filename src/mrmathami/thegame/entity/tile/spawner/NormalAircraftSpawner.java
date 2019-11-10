package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.NormalAircraft;

import javax.annotation.Nonnull;


public final class NormalAircraftSpawner extends AbstractSpawner<NormalAircraft> {
    public NormalAircraftSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.NORMAL_AIRCRAFT_ENEMY_SIZE, NormalAircraft.class, spawnInterval, initialDelay, numOfSpawn);
    }

    @Nonnull
    @Override
    protected final NormalAircraft doSpawn(long createdTick, double posX, double posY) {
        return new NormalAircraft(createdTick, posX, posY);
    }
}
