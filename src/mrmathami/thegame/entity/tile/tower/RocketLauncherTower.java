
package mrmathami.thegame.entity.tile.tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.bullet.RocketBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.enemy.BigAircraft;
import mrmathami.thegame.entity.enemy.NormalAircraft;

import javax.annotation.Nonnull;

public final class RocketLauncherTower extends AbstractTower<RocketBullet, BigAircraft> {
    public RocketLauncherTower(long createdTick, long posX, long posY, double angle) {
        super(createdTick, posX, posY, Config.ROCKET_TOWER_RANGE, Config.ROCKET_TOWER_SPEED, angle, Config.ROCKET_TOWER_LEVEL1_GID, BigAircraft.class);
    }

    @Nonnull
    @Override
    protected final RocketBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        return new RocketBullet(createdTick, posX, posY, deltaX, deltaY, enemyTarget);
    }



}
