package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.bosses.KimJongUnBossEnemy;

import javax.annotation.Nonnull;
import java.util.Random;

public class KimJongUnBossSpawner extends AbstractSpawner<KimJongUnBossEnemy> {
    public KimJongUnBossSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.KIM_JONG_UN_BOSS_ENEMY_SIZE, KimJongUnBossEnemy.class,
                Config.KIM_JONG_UN_BOSS_ENEMY_GID, spawnInterval,
                initialDelay + new Random().nextInt(100), numOfSpawn);
    }

    @Nonnull
    @Override
    protected final KimJongUnBossEnemy doSpawn(long createdTick, double posX, double posY) {
        return new KimJongUnBossEnemy(createdTick, posX, posY);
    }
}