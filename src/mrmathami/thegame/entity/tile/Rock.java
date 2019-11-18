package mrmathami.thegame.entity.tile;

import mrmathami.thegame.entity.AbstractEntity;

public final class Rock extends AbstractEntity {
    private int GID;
    public Rock(long createdTick, long posX, long posY, int GID) {
        super(createdTick, posX, posY, 1L, 1L);
        this.GID = GID;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }
}
