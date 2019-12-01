package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.BinLadenBossEnemy;

import javax.annotation.Nonnull;
import java.util.Random;

public class BinLadenBossSpawner extends AbstractSpawner<BinLadenBossEnemy> {
    public BinLadenBossSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.BIN_LADEN_BOSS_ENEMY_SIZE, BinLadenBossEnemy.class, Config.BIN_LADEN_BOSS_ENEMY_GID, spawnInterval, initialDelay + new Random().nextInt(100), numOfSpawn);
    }

    @Nonnull
    @Override
    protected final BinLadenBossEnemy doSpawn(long createdTick, double posX, double posY) {
        return new BinLadenBossEnemy(createdTick, posX, posY);
    }
}