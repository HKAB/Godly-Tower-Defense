package mrmathami.thegame.entity.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.tile.Road;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.BiFunction;

public abstract class AbstractEnemy extends AbstractEntity implements UpdatableEntity, RotatableEntity, EffectEntity, LivingEntity, DestroyListener {
//	private static final double SQRT_2 = Math.sqrt(2.0) / 2.0;
	private static final double[][] DELTA_DIRECTION_ARRAY = {
			{0.0, -1.0}, {0.0, 1.0}, {-1.0, 0.0}, {1.0, 0.0},
//			{-SQRT_2, -SQRT_2}, {SQRT_2, SQRT_2}, {SQRT_2, -SQRT_2}, {-SQRT_2, SQRT_2},
	};

	private long health;
	private long armor;
	private double speed;
	private long reward;
	protected int GID;
	private double angle = 0;

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

//		System.out.println("Overlap in direction: " + GameEntities.getOverlappedEntities(overlappableEntities, posX, posY, width, height));
		for (GameEntity entity : GameEntities.getOverlappedEntities(overlappableEntities, posX, posY, width, height)) {
			if (sourceEntity != entity && GameEntities.isCollidable(sourceEntity.getClass(), entity.getClass()))
			{
				return Double.NaN;
			}
			if (entity instanceof Road) {
//				System.out.println("Road " + ((Road)entity).getPosX() + "," + ((Road)entity).getPosY());
				final double entityPosX = entity.getPosX();
				final double entityPosY = entity.getPosY();
				final double area = (Math.min(posX + width, entityPosX + entity.getWidth()) - Math.max(posX, entityPosX))
						* (Math.min(posY + height, entityPosY + entity.getHeight()) - Math.max(posY, entityPosY));
				sumArea += area;
//				System.out.println("sumArea: " + sumArea);
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
		System.out.println(GameEntities.getOverlappedEntities(field.getEntities(), getPosX() - speed, getPosY() - speed, speed +1 + speed, speed +1 + speed));
		final Collection<GameEntity> overlappableEntities = GameEntities.getOverlappedEntities(field.getEntities(),
				getPosX() - speed, getPosY() - speed, speed + 1.0 + speed, speed + 1.0 + speed);
		double minimumDistance = Double.MAX_VALUE;
		double newPosX = enemyPosX;
		double newPosY = enemyPosY;
//		System.out.println(newPosX + ":" + newPosY);
		for (double realSpeed = speed * 0.125; realSpeed <= speed; realSpeed += realSpeed) {
			for (double[] deltaDirection : DELTA_DIRECTION_ARRAY) {
				final double currentPosX = enemyPosX + deltaDirection[0] * realSpeed;
				BigDecimal a = BigDecimal.valueOf(enemyPosY + deltaDirection[1] * realSpeed);
				final double currentPosY = enemyPosY + deltaDirection[1] * realSpeed;
//				System.out.println("Evaluate at (" + (currentPosX) + "," + (currentPosY) + ")" + " with width = " + enemyWidth + ", height = " + enemyHeight + ", but bigdecimal Y is : " + a);
				final double currentDistance = evaluateDistance(overlappableEntities, this, currentPosX, currentPosY, enemyWidth, enemyHeight);
//				System.out.println("At direction (" + deltaDirection[0] + "," + deltaDirection[1] +  ") currentDistance is : "  + currentDistance);
				if (currentDistance < minimumDistance) {
					minimumDistance = currentDistance;
					newPosX = currentPosX;
					newPosY = currentPosY;
				}
			}
//			System.out.println("--------");
		}
//		System.out.println(newPosX + ":" + newPosY);
		if (newPosX - enemyPosX == 0 && newPosY - enemyPosY > 0) this.angle = 180;
		else
			this.angle = Math.atan((newPosX - enemyPosX)/(newPosY- enemyPosY))*180/Math.PI;



//		System.out.println(angle);
		setPosX(newPosX);
		setPosY(newPosY);

	}

	@Override
	public final void onDestroy(@Nonnull GameField field) {
		// TODO: reward
	}

	@Override
	public final boolean onEffect(@Nonnull GameField field, @Nonnull LivingEntity livingEntity) {
		// TODO: harm the target
		this.health = Long.MIN_VALUE;
		return false;
	}

	@Override
	public final long getHealth() {
		return health;
	}

	@Override
	public final void doEffect(long value) {
		if (health != Long.MIN_VALUE && (value < -armor || value > 0)) this.health += value;
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
}
