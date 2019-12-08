package mrmathami.thegame.entity.tile.tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.bullet.AbstractBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.AbstractTile;
import mrmathami.thegame.entity.tile.effect.TowerDestroyEffect;

import javax.annotation.Nonnull;
import java.util.Collection;

public abstract class AbstractTower<E extends AbstractBullet> extends AbstractTile implements UpdatableEntity, RotatableEntity, DestroyableEntity, DestroyListener {
	private double range;
	private long speed;
	private double angle;
	private double defaultAngle;
	protected int GID;
	private Class<E> bulletType;
	private long tickDown;
	private int level;
	private boolean sold;

	protected AbstractTower(long createdTick, long posX, long posY, double range, long speed, double angle, int GID, Class<E> bulletType) {
		super(createdTick, posX, posY, 1L, 1L, GID);
		this.range = range;
		this.speed = speed;
		this.angle = angle;
		this.defaultAngle = angle;
		this.tickDown = 0;
		this.GID = GID;
		this.bulletType = bulletType;
		this.level = 0;
		this.sold = false;
	}

	@Override
	public void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		final Collection<LivingEntity> overlappedEntities = GameEntities.getAffectedEntities(field.getEntities(), bulletType,this.getPosX() - range, this.getPosY() - range, (range * 2 + 1), (range * 2 + 1));
		for (LivingEntity livingEntity :
				overlappedEntities) {
			this.angle = this.defaultAngle + Math.atan2((livingEntity.getPosY() + Config.OFFSET/Config.TILE_SIZE + livingEntity.getHeight()/2 - this.getPosY() - this.getWidth()/2), (livingEntity.getPosX() + Config.OFFSET/Config.TILE_SIZE + livingEntity.getWidth()/2 - this.getPosX() - this.getWidth()/2))*180/Math.PI;
			break;
		}
		if (tickDown <= 0) {
			for (LivingEntity livingEntity :
					overlappedEntities) {
				// Remember the freaking OFFSET
				this.angle = this.defaultAngle + Math.atan2((livingEntity.getPosY() + Config.OFFSET/Config.TILE_SIZE + livingEntity.getHeight()/2 - this.getPosY() - this.getWidth()/2), (livingEntity.getPosX() + Config.OFFSET/Config.TILE_SIZE + livingEntity.getWidth()/2 - this.getPosX() - this.getWidth()/2))*180/Math.PI;
				// Using polar coordinate system, dont forget to add width/2 and height/2 to posX and posY with specific bullet
				if (livingEntity instanceof AbstractEnemy) {
					field.doSpawn(doSpawn(getCreatedTick(),
							(getPosX() - Config.OFFSET / (Config.TILE_SIZE) + this.getWidth() / 2 + this.getWidth() / 2 * Math.cos(Math.atan2((livingEntity.getPosY() + Config.OFFSET / Config.TILE_SIZE + livingEntity.getHeight() / 2 - this.getPosY() - this.getWidth() / 2), (livingEntity.getPosX() + Config.OFFSET / Config.TILE_SIZE + livingEntity.getWidth() / 2 - this.getPosX() - this.getWidth() / 2)))),
							(getPosY() - Config.OFFSET / (Config.TILE_SIZE) + this.getHeight() / 2) + this.getWidth() / 2 * Math.sin(Math.atan2((livingEntity.getPosY() + Config.OFFSET / Config.TILE_SIZE + livingEntity.getHeight() / 2 - this.getPosY() - this.getWidth() / 2), (livingEntity.getPosX() + Config.OFFSET / Config.TILE_SIZE + livingEntity.getWidth() / 2 - this.getPosX() - this.getWidth() / 2))),
							livingEntity.getPosX() + livingEntity.getWidth() / 2 - this.getPosX() - this.getWidth() / 2,
							livingEntity.getPosY() + livingEntity.getHeight() / 2 - this.getPosY() - this.getHeight() / 2,
							(AbstractEnemy) livingEntity));
					break;
				}
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

	public int getLevel() {
		return level;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public void doDestroy () {
		this.sold = true;
	}

	@Override
	public void onDestroy(@Nonnull GameField field) {
		field.addSFX(new TowerDestroyEffect(0, getPosX(), getPosY()));
	}

	@Override
	public boolean isDestroyed() {
		return sold;
	}

	public abstract boolean doUpgrade();

	/**
	 * used by context
	 */
	public abstract long getFirepower();
	public abstract long getPrice();
	public abstract long getSellPrice();
	public abstract long getUpgradePrice();
}
