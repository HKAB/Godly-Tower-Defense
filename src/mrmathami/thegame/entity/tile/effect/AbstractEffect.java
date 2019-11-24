package mrmathami.thegame.entity.tile.effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;

public abstract class AbstractEffect extends AbstractEntity implements UpdatableEntity, DestroyableEntity {
    private long tickDown;
    private long timeToLive;
    private int GID;
    private double blur;

    protected AbstractEffect(long createdTick, double posX, double posY, double width, double height,long timeToLive, int GID) {
        super(createdTick, posX, posY, width, height);
        this.timeToLive = timeToLive;
        this.tickDown = timeToLive;
        this.GID = GID;
        this.blur = 1.0;
    }

    @Override
    public final void onUpdate(@Nonnull GameField field) {
        this.tickDown -= 1;
        this.blur = this.tickDown*1.0/this.timeToLive;
    }


    @Override
    public final void doDestroy() {
        this.tickDown = 0;
    }

    @Override
    public final boolean isDestroyed() {
        return tickDown <= 0;
    }


    public int getGID() {
        return GID;
    }

    public double getBlur() {
        return blur;
    }

}
