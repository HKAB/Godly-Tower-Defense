package mrmathami.thegame.entity.tile.effect;

public class AlertEffect extends AbstractEffect {
    private int contentGID;
    public AlertEffect(long createdTick, double posX, double posY, long timeToLive, int contentGID, int alertGID) {
        super(createdTick, posX, posY, 32/64.0, 38/64.0, timeToLive, alertGID);
        this.contentGID = contentGID;
    }

    public int getContentGID() {
        return contentGID;
    }
}
