package mrmathami.thegame.entity.bullet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;

public abstract class AbstractBullet extends AbstractEntity implements UpdatableEntity, EffectEntity, DestroyableEntity, RotatableEntity {
	private double deltaX;
	private double deltaY;
	private final long strength;
	private long tickDown;
	private int GID;
	private double speed;
	private double angle;
	private AbstractEnemy enemyTarget;

	protected AbstractBullet(long createdTick, double posX, double posY, double deltaX, double deltaY, double width, double height, double speed, long strength, long timeToLive, int GID, AbstractEnemy enemyTarget) {
		super(createdTick, posX, posY, width, height);
		final double normalize = speed / Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		this.deltaX = deltaX * normalize;
		this.deltaY = deltaY * normalize;
		this.strength = strength;
		this.tickDown = timeToLive;
		this.GID = GID;
		this.enemyTarget = enemyTarget;
		this.speed = speed;
		setAngle(Double.MAX_VALUE);
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		if (!enemyTarget.isDestroyed()) {
			deltaX = enemyTarget.getPosX() + enemyTarget.getWidth()/2 - getPosX();
			deltaY = enemyTarget.getPosY() + enemyTarget.getHeight()/2 - getPosY();
			double normalize = speed / Math.sqrt(deltaX * deltaX + deltaY * deltaY);
			deltaX = deltaX * normalize;
			deltaY = deltaY * normalize;
//			if ((getPosX() + deltaX)*Config.TILE_SIZE + Config.OFFSET + getWidth()*Config.TILE_SIZE > Config.TILE_SIZE*Config.TILE_HORIZONTAL)
//			{
//				doDestroy();
//				return;
//			}
			setPosX(getPosX() + deltaX);
			setPosY(getPosY() + deltaY);
			setAngle(90 + Math.atan2(deltaY, deltaX) * 180 / Math.PI);
		}
		else
		{
//			if ((getPosX() + deltaX)*Config.TILE_SIZE + Config.OFFSET + getWidth()*Config.TILE_SIZE > Config.TILE_SIZE*Config.TILE_HORIZONTAL) {
//				doDestroy();
//				return;
//			}
			setPosX(getPosX() + deltaX);
			setPosY(getPosY() + deltaY);
		}
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
		return tickDown <= 0;
	}

	@Override
	public void rotate(GraphicsContext graphicsContext, Image image, double screenPosX, double screenPosY, double angle) {
		graphicsContext.save();
		Rotate r = new Rotate(angle, screenPosX + image.getWidth()/2, screenPosY + image.getHeight()/2);
		graphicsContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		graphicsContext.drawImage(image, screenPosX, screenPosY);
		graphicsContext.restore();
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

	public AbstractEnemy getEnemyTarget() {
		return enemyTarget;
	}
}
