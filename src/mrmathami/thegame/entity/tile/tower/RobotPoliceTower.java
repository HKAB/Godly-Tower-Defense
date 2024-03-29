
package mrmathami.thegame.entity.tile.tower;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.bullet.StopSignBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;

public final class RobotPoliceTower extends AbstractTower<StopSignBullet> {
    public int[] GID = new int[] {Config.ROBOT_POLICE_TOWER_LEVEL1_GID, Config.ROBOT_POLICE_TOWER_LEVEL2_GID, Config.ROBOT_POLICE_TOWER_LEVEL3_GID};
    private long[] SELL_PRICE = {Config.ROBOT_POLICE_TOWER_LEVEL1_SELL_PRICE, Config.ROBOT_POLICE_TOWER_LEVEL2_SELL_PRICE, Config.ROBOT_POLICE_TOWER_LEVEL3_SELL_PRICE};
    private long[] UPGRADE_PRICE = {Config.ROBOT_POLICE_TOWER_LEVEL1_UPGRADE_PRICE, Config.ROBOT_POLICE_TOWER_LEVEL2_UPGRADE_PRICE, 0};

    public RobotPoliceTower(long createdTick, long posX, long posY, double angle) {
        super(createdTick, posX, posY, Config.ROBOT_POLICE_TOWER_RANGE, Config.ROBOT_POLICE_TOWER_SPEED, angle, Config.ROBOT_POLICE_TOWER_LEVEL1_GID, StopSignBullet.class);
    }

    @Nonnull
    @Override
    protected final StopSignBullet doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget) {
        GameAudio.getInstance().playSound(new AudioClip(GameAudio.stopSignBulletSound));
        return new StopSignBullet(createdTick, posX - Config.STOP_SIGN_BULLET_WIDTH/(2*Config.TILE_SIZE),
                posY - Config.STOP_SIGN_BULLET_HEIGHT/(2*Config.TILE_SIZE), deltaX, deltaY, enemyTarget);
    }

    @Override
    public boolean doUpgrade() {
        if (getLevel() == 2) return false;
        else
        {
            setLevel(getLevel() + 1);
            setRange(getRange() + 1);
            setSpeed((getSpeed()/(2)));
            setGID(GID[getLevel()]);
            return true;
        }
    }

    @Override
    public long getFirepower() {
        return Config.STOP_SIGN_BULLET_STRENGTH;
    }

    @Override
    public long getPrice() {
        return Config.ROBOT_POLICE_TOWER_PRICE;
    }

    @Override
    public long getSellPrice() {
        return SELL_PRICE[getLevel()];
    }

    @Override
    public long getUpgradePrice() {
        return UPGRADE_PRICE[getLevel()];
    }
}
