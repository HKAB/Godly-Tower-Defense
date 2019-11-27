package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.BigAircraft;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.Tanker;

import javax.annotation.Nonnull;


public final class TankerSpawner extends AbstractSpawner<Tanker> {
    public TankerSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.TANKER_ENEMY_SIZE, Tanker.class, Config.TANKER_ENEMY_GID[0], spawnInterval, initialDelay, numOfSpawn);
    }

    @Nonnull
    @Override
    protected final Tanker doSpawn(long createdTick, double posX, double posY) {
        return new Tanker(createdTick, posX, posY);
    }
}
