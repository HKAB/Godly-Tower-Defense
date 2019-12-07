package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

public class NyanCatBullet extends AbstractBullet {
    public NyanCatBullet(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        super(createdTick, posX, posY, deltaX, deltaY, Config.NYAN_CAT_BULLET_WIDTH /Config.TILE_SIZE, Config.NYAN_CAT_BULLET_HEIGHT /Config.TILE_SIZE, Config.NYAN_CAT_BULLET_SPEED, Config.NYAN_CAT_BULLET_STRENGTH, Config.NYAN_CAT_BULLET_TTL, Config.NYAN_CAT_BULLET_GID, enemyTarget);
    }
}