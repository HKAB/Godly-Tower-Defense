package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.boss.ElonMuskBossEnemy;

import javax.annotation.Nonnull;
import java.util.Random;

public class ElonMuskBossSpawner extends AbstractSpawner<ElonMuskBossEnemy> {
    public ElonMuskBossSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.ELON_MUSK_BOSS_ENEMY_SIZE, ElonMuskBossEnemy.class, Config.ELON_MUSK_BOSS_ENEMY_GID, spawnInterval, initialDelay + new Random().nextInt(100), numOfSpawn);
    }

    @Nonnull
    @Override
    protected final ElonMuskBossEnemy doSpawn(long createdTick, double posX, double posY) {
        return new ElonMuskBossEnemy(createdTick, posX, posY);
    }
}
