package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.MedicBossEnemy;

import javax.annotation.Nonnull;
import java.util.Random;

public class MedicBossSpawner extends AbstractSpawner<MedicBossEnemy> {
    public MedicBossSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.MEDIC_BOSS_ENEMY_SIZE, MedicBossEnemy.class, Config.MEDIC_BOSS_ENEMY_GID, spawnInterval, initialDelay + new Random().nextInt(100), numOfSpawn);
    }

    @Nonnull
    @Override
    protected final MedicBossEnemy doSpawn(long createdTick, double posX, double posY) {
        return new MedicBossEnemy(createdTick, posX, posY);
    }
}