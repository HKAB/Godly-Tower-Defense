package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;

public final class RocketBullet extends AbstractBullet {
    public RocketBullet(long createdTick, double posX, double posY, double deltaX, double deltaY) {
        super(createdTick, posX, posY, deltaX, deltaY, Config.ROCKET_BULLET_SPEED, Config.ROCKET_BULLET_STRENGTH, Config.ROCKET_BULLET_TTL, Config.ROCKET_BULLET_GID);
    }
}