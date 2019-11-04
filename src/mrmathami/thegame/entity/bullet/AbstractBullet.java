package mrmathami.thegame.entity.bullet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.*;

import javax.annotation.Nonnull;

public abstract class AbstractBullet extends AbstractEntity implements UpdatableEntity, EffectEntity, DestroyableEntity, RotatableEntity {
	private final double deltaX;
	private final double deltaY;
	private final long strength;
	private long tickDown;
	private int GID;
	private double angle;

	protected AbstractBullet(long createdTick, double posX, double posY, double deltaX, double deltaY, double speed, long strength, long timeToLive, int GID) {
		super(createdTick, posX, posY, 1, 1);
		final double normalize = speed / Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		this.deltaX = deltaX * normalize;
		this.deltaY = deltaY * normalize;
		this.strength = strength;
		this.tickDown = timeToLive;
		this.GID = GID;
		setAngle(90 + Math.atan2((deltaY), (deltaX))*180/Math.PI);
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		setPosX(getPosX() + deltaX);
		setPosY(getPosY() + deltaY);

	}

	@Override
	public final boolean onEffect(@Nonnull GameField field, @Nonnull LivingEntity livingEntity) {
		livingEntity.doEffect(-strength);
		this.tickDown = 0;
		return false;
	}

	@Override
	public final void doDestroy() {
		this.tickDown = 0;
	}

	@Override
	public final boolean isDestroyed() {
		return tickDown <= -0;
	}

	@Override
	public void rotate(GraphicsContext graphicsContext, Image image, double screenPosX, double screenPosY, double angle) {
		graphicsContext.save();
		Rotate r = new Rotate(angle, screenPosX + image.getWidth()/2, screenPosY + image.getHeight()/2);
		graphicsContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		graphicsContext.drawImage(image, screenPosX, screenPosY);
		graphicsContext.restore();
	}

	@Override
	public void rotate(GraphicsContext graphicsContext, Image[] image, double screenPosX, double screenPosY, double angle) {

	}

	public int getGID() {
		return GID;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
}
