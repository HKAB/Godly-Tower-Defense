package mrmathami.thegame.entity.tile.effect;

import mrmathami.thegame.Config;

public class ExplosionEffect extends AbstractEffect {

    public ExplosionEffect(long createdTick, double posX, double posY) {
        super(createdTick, posX, posY, 1, 1, Config.EXPLOSION_TTL, Config.EXPLOSION_GID);
    }
}
