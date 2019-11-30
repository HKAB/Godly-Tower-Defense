
package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;

import java.util.Random;

public final class GrabEnemy extends AbstractEnemy {
    public GrabEnemy(long createdTick, double posX, double posY) {
        super(createdTick,
                posX,
                posY,
                Config.GRAB_ENEMY_WIDTH/Config.TILE_SIZE,
                Config.GRAB_ENEMY_HEIGHT/Config.TILE_SIZE,
                Config.GRAB_ENEMY_HEALTH,
                Config.GRAB_ENEMY_ARMOR,
                Config.GRAB_ENEMY_SPEED,
                Config.GRAB_ENEMY_REWARD,
                Config.GRAB_ENEMY_GID);
    }

    public int getGID() {
        return this.GID;
    }

    @Override
    public void doEffect(long value) {
        if (getHealth() != Long.MIN_VALUE && (value < -getArmor() || value > 0)) setHealth(getHealth() + value);
        setSpeed(getSpeed()*(1 + 0.5));
    }
}