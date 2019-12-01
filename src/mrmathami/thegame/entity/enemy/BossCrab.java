
package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

import javax.annotation.Nonnull;
import java.util.Random;

public final class BossCrab extends AbstractEnemy {
    public BossCrab(long createdTick, double posX, double posY) {
        super(createdTick,
                posX,
                posY,
                Config.BOSS_CRAB_WIDTH/Config.TILE_SIZE,
                Config.BOSS_CRAB_HEIGHT/Config.TILE_SIZE,
                Config.BOSS_CRAB_HEALTH,
                Config.BOSS_CRAB_ARMOR,
                Config.BOSS_CRAB_SPEED,
                Config.BOSS_CRAB_REWARD,
                Config.BOSS_CRAB_GID);
    }

    public int getGID() {
        return this.GID;
    }

    @Override
    public void onDestroy(@Nonnull GameField field) {
        super.onDestroy(field);
        for (AbstractTower towerEntity: GameEntities.getFilteredOverlappedEntities(field.getEntities(), AbstractTower.class, getPosX() - 1, getPosY() - 1, 3, 3))
        {
            towerEntity.doDestroy();
            field.addSFX(new ExplosionEffect(0, towerEntity.getPosX(), towerEntity.getPosY()));
        }
    }
}