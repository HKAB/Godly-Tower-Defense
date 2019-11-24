package mrmathami.thegame.entity.tile.effect;

import mrmathami.thegame.Config;

public class TowerDestroyEffect extends AbstractEffect {

    public TowerDestroyEffect(long createdTick, double posX, double posY) {
        super(createdTick, posX, posY, 1, 1, Config.TOWER_DESTROY_TTL, Config.TOWER_DESTROY_GID);
    }


}
