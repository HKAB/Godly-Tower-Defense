package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.GameField;

import javax.annotation.Nonnull;

public abstract class BossEnemy extends AbstractEnemy {
    private boolean invisible;
    private boolean immortal;

    public BossEnemy (long createdTick, double posX, double posY, double width, double height, long health, long armor, double speed, long reward, int GID) {
        super(createdTick, posX, posY, width, height, health, armor, speed, reward, GID);
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

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public abstract void skillCheck(@Nonnull GameField field);
}