package mrmathami.thegame.entity.tile.tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.Config;
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
			this.angle = this.defaultAngle + Math.atan2((normalEnemy.getPosY() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getHeight()/2 - this.getPosY() - this.getWidth()/2), (normalEnemy.getPosX() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getWidth()/2 - this.getPosX() - this.getWidth()/2))*180/Math.PI;
			break;
		}
		if (tickDown <= 0) {
			for (T normalEnemy :
					overlappedEntities) {
				// Remember the freaking OFFSET
				this.angle = this.defaultAngle + Math.atan2((normalEnemy.getPosY() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getHeight()/2 - this.getPosY() - this.getWidth()/2), (normalEnemy.getPosX() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getWidth()/2 - this.getPosX() - this.getWidth()/2))*180/Math.PI;
				// Using polar coordinate system, dont forget to add width/2 and height/2 to posX and posY with specific bullet
				field.doSpawn(doSpawn(getCreatedTick(),
						(getPosX() - Config.OFFSET/(Config.TILE_SIZE) + this.getWidth()/2 + this.getWidth()/2*Math.cos(Math.atan2((normalEnemy.getPosY() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getHeight()/2 - this.getPosY() - this.getWidth()/2), (normalEnemy.getPosX() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getWidth()/2 - this.getPosX() - this.getWidth()/2)))),
						(getPosY() - Config.OFFSET/(Config.TILE_SIZE) + this.getHeight()/2) + this.getWidth()/2*Math.sin(Math.atan2((normalEnemy.getPosY() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getHeight()/2 - this.getPosY() - this.getWidth()/2), (normalEnemy.getPosX() + Config.OFFSET/Config.TILE_SIZE + normalEnemy.getWidth()/2 - this.getPosX() - this.getWidth()/2))),
						normalEnemy.getPosX() + normalEnemy.getWidth()/2 - this.getPosX() - this.getWidth()/2,
						normalEnemy.getPosY() + normalEnemy.getHeight()/2 - this.getPosY() - this.getHeight()/2,
						normalEnemy));
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
