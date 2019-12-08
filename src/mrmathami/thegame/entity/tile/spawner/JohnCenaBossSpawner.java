package mrmathami.thegame.entity.tile.spawner;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.boss.JohnCenaBossEnemy;

import javax.annotation.Nonnull;
import java.util.Random;

public class JohnCenaBossSpawner extends AbstractSpawner<JohnCenaBossEnemy> {
    public JohnCenaBossSpawner(long createdTick, long posX, long posY, long width, long height, long spawnInterval, long initialDelay, long numOfSpawn) {
        super(createdTick, posX, posY, width, height, Config.JOHN_CENA_BOSS_ENEMY_SIZE, JohnCenaBossEnemy.class, Config.JOHN_CENA_BOSS_ENEMY_GID, spawnInterval, initialDelay + new Random().nextInt(100), numOfSpawn);
    }

    @Nonnull
    @Override
    protected final JohnCenaBossEnemy doSpawn(long createdTick, double posX, double posY) {
        return new JohnCenaBossEnemy(createdTick, posX, posY);
    }
}
