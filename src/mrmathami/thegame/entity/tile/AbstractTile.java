package mrmathami.thegame.entity.tile;

import mrmathami.thegame.entity.AbstractEntity;

public abstract class AbstractTile extends AbstractEntity {
	private int GID;
	protected AbstractTile(long createdTick, long posX, long posY, long width, long height, int GID) {
		super(createdTick, posX, posY, width, height);
		this.GID = GID;
	}

	public int getGID() {
		return GID;
	}

	public void setGID(int GID) {
		this.GID = GID;
	}
}
