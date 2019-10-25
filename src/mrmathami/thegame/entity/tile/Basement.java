package mrmathami.thegame.entity.tile;

import mrmathami.thegame.entity.AbstractEntity;

public class Outside extends AbstractEntity {
    private int GID;
    public Outside(long createdTick, long posX, long posY, int GID) {
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
