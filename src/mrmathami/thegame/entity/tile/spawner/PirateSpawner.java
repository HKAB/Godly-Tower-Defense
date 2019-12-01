package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.PirateEnemy;

import javax.annotation.Nonnull;


public final class PirateSpawner extends AbstractSpawner<PirateEnemy> {
    public PirateSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.PIRATE_ENEMY_SIZE, PirateEnemy.class, Config.PIRATE_ENEMY_GID, spawnInterval, initialDelay, numOfSpawn);
    }

    @Nonnull
    @Override
    protected final PirateEnemy doSpawn(long createdTick, double posX, double posY) {
        return new PirateEnemy(createdTick, posX, posY);
    }
}
