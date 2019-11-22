package mrmathami.thegame.entity.tile.effect;

import mrmathami.thegame.Config;

public class UpgradeEffect extends AbstractEffect {

    public UpgradeEffect(long createdTick, double posX, double posY) {
        super(createdTick, posX, posY, 1, 1, Config.UPGRADE_TTL, Config.UPGRADE_GID);
    }


}
