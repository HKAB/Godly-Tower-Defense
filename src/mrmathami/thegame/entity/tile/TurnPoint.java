package mrmathami.thegame.entity.tile;

public class TurnPoint extends AbstractTile {
    public long x1;
    public long y1;
    public long x2;
    public long y2;
    public long x3;
    public long y3;

    public TurnPoint(long x1, long y1, long x2, long y2, long x3, long y3) {
        super(0, x2, y2, 1, 1, 0);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }
}
