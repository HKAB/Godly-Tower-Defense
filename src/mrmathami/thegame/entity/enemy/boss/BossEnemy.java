package mrmathami.thegame.entity.enemy.boss;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;

public abstract class BossEnemy extends AbstractEnemy {
    private boolean invisible;
    private boolean immortal;
    private double bossWidth;
    private double bossHeight;
    private double maxHealth;

    public BossEnemy (long createdTick, double posX, double posY, double width, double height, long health, long armor, double speed, long reward, int GID) {
        super(createdTick, posX, posY, width / Config.TILE_SIZE, height / Config.TILE_SIZE, health, armor, speed, reward, GID);
        this.bossHeight = height;
        this.bossWidth = width;
        this.maxHealth = health;
        this.invisible = false;
        this.immortal = false;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public double getBossWidth() {
        return bossWidth;
    }

    public double getBossHeight() {
        return bossHeight;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public abstract void skillCheck(@Nonnull GameField field);
}