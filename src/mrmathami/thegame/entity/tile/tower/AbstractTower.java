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

public abstract class AbstractTower<E extends AbstractBullet> extends AbstractTile implements UpdatableEntity, RotatableEntity {
	private final double range;
	private final long speed;
	private double angle;

	private long tickDown;

	protected AbstractTower(long createdTick, long posX, long posY, double range, long speed, double angle, int GID) {
		super(createdTick, posX, posY, 1L, 1L, GID);
		this.range = range;
		this.speed = speed;
		this.angle = angle;
		this.tickDown = 0;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		if (tickDown <= 0) {
			// TODO: Find a target and spawn a bullet to that direction.
			// Use GameEntities.getFilteredOverlappedEntities to find target in range
			// Remember to set this.tickDown back to this.speed after shooting something.
			// this.tickDown = speed;
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
	protected abstract E doSpawn(long createdTick, double posX, double posY, double deltaX, double deltaY);

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
