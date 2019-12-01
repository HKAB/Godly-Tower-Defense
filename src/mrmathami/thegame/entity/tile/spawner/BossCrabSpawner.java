package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.BossCrab;

import javax.annotation.Nonnull;
import java.util.Random;


public final class BossCrabSpawner extends AbstractSpawner<BossCrab> {
    public BossCrabSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.BOSS_CRAB_SIZE, BossCrab.class, Config.BOSS_CRAB_GID, spawnInterval, initialDelay + new Random().nextInt(100), numOfSpawn);
    }

    @Nonnull
    @Override
    protected final BossCrab doSpawn(long createdTick, double posX, double posY) {
        return new BossCrab(createdTick, posX, posY);
    }
}
