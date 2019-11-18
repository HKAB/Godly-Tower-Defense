package mrmathami.thegame.entity.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.entity.AbstractEntity;
import mrmathami.thegame.entity.RotatableEntity;

public final class Bush extends AbstractEntity implements RotatableEntity {
    private int GID;
    public Bush(long createdTick, long posX, long posY, int GID) {
        super(createdTick, posX, posY, 1L, 1L);
        this.GID = GID;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    @Override
    public void rotate(GraphicsContext graphicsContext, Image image, double screenPosX, double screenPosY, double angle) {
        graphicsContext.save();
        Rotate r = new Rotate(angle, screenPosX + image.getWidth()/2, screenPosY + image.getHeight()/2);
        graphicsContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        graphicsContext.drawImage(image, screenPosX, screenPosY);
        graphicsContext.restore();
    }

}
