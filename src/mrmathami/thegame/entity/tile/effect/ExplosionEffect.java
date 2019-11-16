package mrmathami.thegame.entity.tile.effect;

import mrmathami.thegame.Config;

public class ExplosionEffect extends AbstractEffect {

    public ExplosionEffect(long createdTick, double posX, double posY, long timeToLive) {
        super(createdTick, posX, posY, 1, 1, timeToLive, Config.EXPLOSION_GID);
    }
}
