package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.SonGokuBossEnemy;

import javax.annotation.Nonnull;
import java.util.Random;

public class SonGokuBossSpawner extends AbstractSpawner<SonGokuBossEnemy> {
    public SonGokuBossSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.SON_GOKU_BOSS_ENEMY_SIZE, SonGokuBossEnemy.class, Config.SON_GOKU_BOSS_ENEMY_GID, spawnInterval, initialDelay + new Random().nextInt(100), numOfSpawn);
    }

    @Nonnull
    @Override
    protected final SonGokuBossEnemy doSpawn(long createdTick, double posX, double posY) {
        return new SonGokuBossEnemy(createdTick, posX, posY, false);
    }
}