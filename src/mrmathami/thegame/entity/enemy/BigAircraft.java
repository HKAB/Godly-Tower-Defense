package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;

import java.util.Random;

public final class BigAircraft extends AbstractEnemy {
    public BigAircraft(long createdTick, double posX, double posY) {
        super(createdTick, posX, posY,Config.BIG_AIRCRAFT_ENEMY_WIDTH/Config.TILE_SIZE,
                Config.BIG_AIRCRAFT_ENEMY_HEIGHT/Config.TILE_SIZE,
                Config.BIG_AIRCRAFT_ENEMY_HEALTH,
                Config.BIG_AIRCRAFT_ENEMY_ARMOR,
                Config.BIG_AIRCRAFT_ENEMY_SPEED,
                Config.BIG_AIRCRAFT_ENEMY_REWARD,
                Config.BIG_AIRCRAFT_ENEMY_GID[new Random().nextInt(Config.BIG_AIRCRAFT_ENEMY_GID.length)]);
    }

    public int getGID() {
        return this.GID;
    }
}