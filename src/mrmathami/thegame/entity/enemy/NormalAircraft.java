
package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;

import java.util.Random;

public final class NormalAircraft extends AbstractEnemy {
    public NormalAircraft(long createdTick, double posX, double posY) {
        super(createdTick, posX, posY,Config.NORMAL_AIRCRAFT_ENEMY_WIDTH/Config.TILE_SIZE, Config.NORMAL_AIRCRAFT_ENEMY_HEIGHT/Config.TILE_SIZE,  Config.NORMAL_AIRCRAFT_ENEMY_HEALTH, Config.NORMAL_AIRCRAFT_ENEMY_ARMOR, Config.NORMAL_AIRCRAFT_ENEMY_SPEED, Config.NORMAL_AIRCRAFT_ENEMY_REWARD, Config.NORMAL_AIRCRAFT_ENEMY_GID[new Random().nextInt(Config.NORMAL_AIRCRAFT_ENEMY_GID.length)]);
    }

    public int getGID() {
        return this.GID;
    }
}