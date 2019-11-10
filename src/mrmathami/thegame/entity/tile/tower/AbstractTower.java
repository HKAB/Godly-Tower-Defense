package mrmathami.thegame.entity.tile.tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.RotatableEntity;
import mrmathami.thegame.entity.UpdatableEntity;
import mrmathami.thegame.entity.bullet.AbstractBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.AbstractTile;

import javax.annotation.Nonnull;
import java.util.Collection;

public abstract class AbstractTower<E extends AbstractBullet, T extends AbstractEnemy> extends AbstractTile implements UpdatableEntity, RotatableEntity {
	private final double range;
	private final long speed;
	private double angle;
	private double defaultAngle;
	protected int GID;
	private Class<T> target;
	private long tickDown;

	protected AbstractTower(long createdTick, long posX, long posY, double range, long speed, double angle, int GID, Class<T> target) {
		super(createdTick, posX, posY, 1L, 1L, GID);
		this.range = range;
		this.speed = speed;
		this.angle = angle;
		this.defaultAngle = angle;
		this.tickDown = 0;
		this.GID = GID;
		this.target = target;
	}

	@Override
	public void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		final Collection<T> overlappedEntities = GameEntities.getFilteredOverlappedEntities(field.getEntities(), target,
				this.getPosX() - range, this.getPosY() - range, (range * 2 + 1), (range * 2 + 1));
		for (T normalEnemy :
				overlappedEntities) {
//			field.doSpawn(doSpawn(getCreatedTick(), getPosX(), getPosY(), normalEnemy.getPosX() - getPosX(), normalEnemy.getPosY() - getPosY()));
//			System.out.println("Enemy spot");
			this.angle = this.defaultAngle + Math.atan2((normalEnemy.getPosY() - this.getPosY()), (normalEnemy.getPosX() - this.getPosX()))*180/Math.PI;
			break;
		}
		if (tickDown <= 0) {
			// TODO: Find a target and spawn a bullet to that direction.
			// Use GameEntities.getFilteredOverlappedEntities to find target in range
			// Remember to set this.tickDown back to this.speed after shooting something.
			// this.tickDown = speed;
			// TODO: Uncomment
			for (T normalEnemy :
					overlappedEntities) {
				field.doSpawn(doSpawn(getCreatedTick(), getPosX(), getPosY(), normalEnemy.getPosX() - getPosX(), normalEnemy.getPosY() - getPosY(), normalEnemy));
				System.out.println("Enemy spot");
				this.angle = this.defaultAngle + Math.atan2((normalEnemy.getPosY() - this.getPosY()), (normalEnemy.getPosX() - this.getPosX()))*180/Math.PI;
				break;
			}
			 this.tickDown = speed;
		}
	}

	/**
	 * Create a new bullet. Each tower spawn different type of bullet.
	 * Override this method and return the type of bullet that your tower shot out.
	 * See NormalTower for example.
	 *
	 * @param createdTick createdTick
	 * @param posX posX
	 * @param posY posY
	 * @param deltaX deltaX
	 * @param deltaY deltaY
	 * @return the bullet entity
	 */
	@Nonnull
	protected abstract E doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY, AbstractEnemy enemyTarget);

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
}
