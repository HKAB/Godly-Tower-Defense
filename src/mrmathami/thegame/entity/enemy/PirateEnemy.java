
package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;

import java.util.Random;

public final class PirateEnemy extends AbstractEnemy {
    public PirateEnemy(long createdTick, double posX, double posY) {
        super(createdTick,
                posX,
                posY,
                Config.PIRATE_ENEMY_WIDTH/Config.TILE_SIZE,
                Config.PIRATE_ENEMY_HEIGHT/Config.TILE_SIZE,
                Config.PIRATE_ENEMY_HEALTH,
                Config.PIRATE_ENEMY_ARMOR,
                Config.PIRATE_ENEMY_SPEED,
                Config.PIRATE_ENEMY_REWARD,
                Config.PIRATE_ENEMY_GID);
    }

    public int getGID() {
        return this.GID;
    }
}