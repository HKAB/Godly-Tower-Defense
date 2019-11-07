
package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;

import java.util.Random;

public final class Tanker extends AbstractEnemy {
    public Tanker(long createdTick, double posX, double posY) {
        super(createdTick, posX, posY,  Config.TANKER_ENEMY_SIZE, 10,  Config.TANKER_ENEMY_HEALTH, Config.TANKER_ENEMY_ARMOR, Config.TANKER_ENEMY_SPEED, Config.TANKER_ENEMY_REWARD, Config.TANKER_ENEMY_GID[new Random().nextInt(Config.TANKER_ENEMY_GID.length)]);
    }

    public int getGID() {
        return this.GID;
    }
}
