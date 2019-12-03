package mrmathami.thegame.entity.tile.cutineffect;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.AbstractEntity;
import mrmathami.thegame.entity.DestroyableEntity;
import mrmathami.thegame.entity.UpdatableEntity;

import javax.annotation.Nonnull;

public class BossCutInEffect extends AbstractEntity implements UpdatableEntity, DestroyableEntity {
    private long tickDown;
    private long timeToLive;
    private String imageURI;
    private double blur;

    public BossCutInEffect(long createdTick, String imageURI) {
        super(createdTick, Config.FIELD_START_POS_X, Config.FIELD_START_POS_Y, Config.TILE_HORIZONTAL, Config.TILE_VERTICAL);
        this.timeToLive = Config.BOSS_CUT_IN_TTL;
        this.tickDown = Config.BOSS_CUT_IN_TTL;
        this.imageURI = imageURI;
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

    public String getImageURI() {
        return imageURI;
    }

    public double getBlur() {
        return blur;
    }
}