package mrmathami.thegame.entity.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.enemy.boss.BossEnemy;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.TurnPoint;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;
import mrmathami.thegame.net.MPGameField;
import mrmathami.thegame.net.MPSocketController;

import javax.annotation.Nonnull;
import java.util.Collection;

public abstract class AbstractEnemy extends AbstractEntity implements UpdatableEntity, RotatableEntity, EffectEntity, LivingEntity, DestroyListener {
	private static final double[][] DELTA_DIRECTION_ARRAY = {
			{0.0, -1.0}, {0.0, 1.0}, {-1.0, 0.0}, {1.0, 0.0},
//			{-SQRT_2, -SQRT_2}, {SQRT_2, SQRT_2}, {SQRT_2, -SQRT_2}, {-SQRT_2, SQRT_2},
	};

	private long health;
	private long armor;
	private double speed;
	private long reward;
	protected int GID;
	private double angle = Double.MIN_VALUE;
	private double t_bezier = 0;
	private double speed_coef = 1;

	protected AbstractEnemy(long createdTick, double posX, double posY, double width, double height, long health, long armor, double speed, long reward, int GID) {
		super(createdTick, posX, posY, width, height);
		this.health = health;
		this.armor = armor;
		this.speed = speed;
		this.reward = reward;
		this.GID = GID;
	}

	private static double evaluateDistance(@Nonnull Collection<GameEntity> overlappableEntities,
			@Nonnull GameEntity sourceEntity, double posX, double posY, double width, double height) {
		double distance = 0.0;
		double sumArea = 0.0;

		for (GameEntity entity : GameEntities.getOverlappedEntities(overlappableEntities, posX, posY, width, height)) {
			if (sourceEntity != entity && GameEntities.isCollidable(sourceEntity.getClass(), entity.getClass()))
			{
				return Double.NaN;
			}
			if (entity instanceof Road) {
				final double entityPosX = entity.getPosX();
				final double entityPosY = entity.getPosY();
				final double area = (Math.min(posX + width, entityPosX + entity.getWidth()) - Math.max(posX, entityPosX))
						* (Math.min(posY + height, entityPosY + entity.getHeight()) - Math.max(posY, entityPosY));
				sumArea += area;
				distance += area * ((Road) entity).getDistance();
			}

		}
		return distance / sumArea;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		final double enemyPosX = getPosX();
		final double enemyPosY = getPosY();
		final double enemyWidth = 1.0;
		final double enemyHeight = 1.0;

		if (this instanceof BossEnemy) ((BossEnemy) this).skillCheck(field);

		final Collection<GameEntity> overlappableEntities = GameEntities.getOverlappedEntities(field.getEntities(),
				getPosX() - speed, getPosY() - speed, speed + 1.0 + speed, speed + 1.0 + speed);
		double minimumDistance = Double.MAX_VALUE;
		double newPosX = enemyPosX;
		double newPosY = enemyPosY;

		double bestX = 0;
		double bestY = 0;

//			for (double realSpeed = speed * 0.125; realSpeed <= speed; realSpeed += realSpeed) {
				for (double[] deltaDirection : DELTA_DIRECTION_ARRAY) {
					final double currentPosX = enemyPosX + deltaDirection[0] * speed;
					final double currentPosY = enemyPosY + deltaDirection[1] * speed;
					final double currentDistance = evaluateDistance(overlappableEntities, this, currentPosX, currentPosY, enemyWidth, enemyHeight);
					if (currentDistance < minimumDistance) {
						minimumDistance = currentDistance;
						newPosX = currentPosX;
						newPosY = currentPosY;
						bestX = deltaDirection[0];
						bestY = deltaDirection[1];
					}
//				}
//			}
		}
		final Collection<TurnPoint> turnPointCollection = GameEntities.getFilteredOverlappedEntities(field.getEntities(), TurnPoint.class, enemyPosX + bestX * speed, enemyPosY + bestY * speed, enemyWidth, enemyHeight);
		/**
		 * Big thank to Benzier
		 */
		if (turnPointCollection.size() == 1)
		{
			t_bezier += 0.7*speed;
			if (t_bezier > 1) t_bezier = 1;
			newPosX = (1 - t_bezier) * (1 - t_bezier) * turnPointCollection.iterator().next().x1 + 2 * (1 - t_bezier) * t_bezier * turnPointCollection.iterator().next().x2 + t_bezier * t_bezier * turnPointCollection.iterator().next().x3;
			newPosY = (1 - t_bezier) * (1 - t_bezier) * turnPointCollection.iterator().next().y1 + 2 * (1 - t_bezier) * t_bezier * turnPointCollection.iterator().next().y2 + t_bezier * t_bezier * turnPointCollection.iterator().next().y3;
		}
		else {
			t_bezier = 0;
		}
		this.angle = Math.atan2((newPosY- enemyPosY), (newPosX - enemyPosX))*180/Math.PI;

		setPosX(newPosX);
		setPosY(newPosY);

	}

	@Override
	public void onDestroy(@Nonnull GameField field) {
		field.setMoney(field.getMoney() + reward);
		if (this instanceof BossEnemy) ((BossEnemy) this).skillCheck(field);
		GameAudio.getInstance().playSound(new AudioClip(GameAudio.explosionSound));
		field.addSFX(new ExplosionEffect(0, getPosX() + Config.OFFSET/Config.TILE_SIZE, getPosY() + Config.OFFSET/Config.TILE_SIZE));
	}

	@Override
	public final boolean onEffect(@Nonnull GameField field, @Nonnull LivingEntity livingEntity) {
		field.harmPlayer(1);
		field.setMoney(field.getMoney() - 1);
		if (field.isMultiplayer() && !(field instanceof MPGameField)) {
			MPSocketController socket = MPSocketController.getCurrentInstance();
			socket.sendState(field.getHealth());
		}
		this.health = Long.MIN_VALUE;
		return false;
	}

	@Override
	public final long getHealth() {
		return health;
	}

	public void setHealth(long health) {
		this.health = health;
	}

	@Override
	public void doEffect(long value) {
		if (health != Long.MIN_VALUE && (value < -armor || value > 0) && (!((this instanceof BossEnemy) && (((BossEnemy)this).isImmortal())))) {
			this.health += value;
		}
	}

	@Override
	public final void doDestroy() {
		this.health = Long.MIN_VALUE;
	}

	@Override
	public final boolean isDestroyed() {
		return health <= -0L;
	}

	@Override
	public void rotate(GraphicsContext graphicsContext, Image image, double screenPosX, double screenPosY, double angle) {
		graphicsContext.save();
		Rotate r = new Rotate(angle, screenPosX + image.getWidth()/2, screenPosY + image.getHeight()/2);
		graphicsContext.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		graphicsContext.drawImage(image, screenPosX, screenPosY);
		graphicsContext.restore();
	}

	public double getAngle() {
		return angle;
	}

	@Override
	public String toString() {
		return "[Enemy@x=" + getPosX() + ",y=" + getPosY() + ",w=" + getWidth() + ",h=" + getHeight() + "]";
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public long getArmor() {
		return armor;
	}

	public void setArmor(long armor) {
		this.armor = armor;
	}
}
