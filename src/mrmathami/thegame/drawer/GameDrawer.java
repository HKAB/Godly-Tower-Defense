package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.GameUI;
import mrmathami.thegame.bar.button.*;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.TowerPlacing;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.bullet.RocketBullet;
import mrmathami.thegame.entity.enemy.BigAircraft;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.NormalEnemy;
import mrmathami.thegame.entity.enemy.Tanker;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Basement;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.Target;
import mrmathami.thegame.entity.tile.spawner.BigAircraftSpawner;
import mrmathami.thegame.entity.tile.spawner.NormalAircraftSpawner;
import mrmathami.thegame.entity.tile.spawner.NormalSpawner;
import mrmathami.thegame.entity.tile.spawner.TankerSpawner;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public final class GameDrawer {
	/**
	 * TODO: This is a list contains Entity type that can be drawn on screen.
	 * Remember to add your own entity class here if it can be drawn.
	 */
	@Nonnull private static final List<Class<?>> ENTITY_DRAWING_ORDER = List.of(
			Basement.class,
			Road.class,
			Mountain.class,
			NormalTower.class,
			RocketLauncherTower.class,
//			SniperTower.class,
			MachineGunTower.class,
			NormalBullet.class,
			MachineGunBullet.class,
			RocketBullet.class,
//			SniperBullet.class,
			NormalEnemy.class,
//			SmallerEnemy.class,
//			TankerEnemy.class,
//			BossEnemy.class,
			NormalSpawner.class,
			NormalAircraft.class,
			BigAircraft.class,
			Tanker.class,
//			SmallerSpawner.class,
//			TankerSpawner.class,
//			BossSpawner.class,
			Target.class,
			ButtonDrawer.class
	);
	/**
	 * TODO:
	 * This is a map between Entity type and its drawer.
	 * Remember to add your entity drawer here.
	 */
	@Nonnull private static final Map<Class<? extends GameEntity>, EntityDrawer> ENTITY_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(Basement.class, new BasementDrawer()),
			Map.entry(Road.class, new RoadDrawer()),
			Map.entry(Mountain.class, new MountainDrawer()),
			Map.entry(NormalTower.class, new NormalTowerDrawer()),
			Map.entry(RocketLauncherTower.class, new RocketLauncherTowerDrawer()),
//			Map.entry(SniperTower.class, new SniperTowerDrawer()),
			Map.entry(MachineGunTower.class, new MachineGunTowerDrawer()),
			Map.entry(NormalBullet.class, new NormalBulletDrawer()),
			Map.entry(MachineGunBullet.class, new MachineGunBulletDrawer()),
			Map.entry(RocketBullet.class, new RocketBulletDrawer()),
//			Map.entry(SniperBullet.class, new SniperBulletDrawer()),
			Map.entry(NormalEnemy.class, new NormalEnemyDrawer()),
			Map.entry(NormalAircraft.class, new NormalAircraftDrawer()),
			Map.entry(BigAircraft.class, new BigAircraftDrawer()),
			Map.entry(Tanker.class, new TankerDrawer()),
//			Map.entry(SmallerEnemy.class, new SmallerEnemyDrawer()),
//			Map.entry(TankerEnemy.class, new TankerEnemyDrawer()),
//			Map.entry(BossEnemy.class, new BossEnemyDrawer()),
			Map.entry(NormalSpawner.class, new SpawnerDrawer()),
			Map.entry(NormalAircraftSpawner.class, new SpawnerDrawer()),
			Map.entry(BigAircraftSpawner.class, new SpawnerDrawer()),
			Map.entry(TankerSpawner.class, new SpawnerDrawer()),
//			Map.entry(SmallerSpawner.class, new SpawnerDrawer()),
//			Map.entry(TankerSpawner.class, new SpawnerDrawer()),
//			Map.entry(BossSpawner.class, new SpawnerDrawer()),
			Map.entry(Target.class, new TargetDrawer())
	));

	@Nonnull private static final Map<Class<? extends UIEntity>, UIDrawer> UI_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(UnclickableButton.class, new ButtonDrawer()),
			Map.entry(BackButton.class, new ButtonDrawer()),
			Map.entry(PauseButton.class, new ButtonDrawer()),
			Map.entry(SellButton.class, new ButtonDrawer()),
			Map.entry(TowerButton.class, new ButtonDrawer()),
			Map.entry(UpgradeButton.class, new ButtonDrawer())
	));

	@Nonnull private final GraphicsContext graphicsContext;
	@Nonnull private GameField gameField;
	@Nonnull private GameUI gameUI;
	private TowerPlacing towerPlacing;
	private static Image sheetImage;
	private static Image buttonImage;
	private transient double fieldStartPosX = Float.NaN;
	private transient double fieldStartPosY = Float.NaN;
	private transient double fieldZoom = Float.NaN;

	public GameDrawer(@Nonnull GraphicsContext graphicsContext, @Nonnull GameField gameField, @Nonnull GameUI gameUI, TowerPlacing towerPlacing, String sheetImage, String buttonImage) throws FileNotFoundException {
		this.graphicsContext = graphicsContext;
		this.gameField = gameField;
		this.towerPlacing = towerPlacing;
		this.sheetImage = new Image(getClass().getResourceAsStream(sheetImage));
		this.buttonImage = new Image(getClass().getResourceAsStream(buttonImage));
		this.gameUI = gameUI;
	}

	/**
	 * Do not touch me.
	 * This is a drawing order comparator, use to sort the entity list.
	 *
	 * @param entityA entity A
	 * @param entityB entity B
	 * @return order
	 */
	private static int entityDrawingOrderComparator(@Nonnull GameEntity entityA, @Nonnull GameEntity entityB) {
		final int compareOrder = Integer.compare(
				ENTITY_DRAWING_ORDER.indexOf(entityA.getClass()),
				ENTITY_DRAWING_ORDER.indexOf(entityB.getClass())
		);
		if (compareOrder != 0) return compareOrder;
		final int comparePosX = Double.compare(entityA.getPosX(), entityB.getPosX());
		if (comparePosX != 0) return comparePosX;
		final int comparePosY = Double.compare(entityA.getPosY(), entityB.getPosY());
		if (comparePosY != 0) return comparePosY;
		final int compareWidth = Double.compare(entityA.getWidth(), entityB.getWidth());
		if (compareWidth != 0) return compareWidth;
		return Double.compare(entityA.getHeight(), entityB.getHeight());
	}

	/**
	 * @param entity entity
	 * @return the drawer fot that entity, or null if that entity is not drawable.
	 */
	@Nullable
	private static EntityDrawer getEntityDrawer(@Nonnull GameEntity entity) {
		return ENTITY_DRAWER_MAP.get(entity.getClass());
	}

	@Nullable
	private static UIDrawer getUIDrawer(@Nonnull UIEntity entity) {
		return UI_DRAWER_MAP.get(entity.getClass());
	}

	public final double getFieldStartPosX() {
		return fieldStartPosX;
	}

	public final double getFieldStartPosY() {
		return fieldStartPosY;
	}

	public final double getFieldZoom() {
		return fieldZoom;
	}

	public final void setGameField(@Nonnull GameField gameField) {
		this.gameField = gameField;
	}

	public void setGameUI(@Nonnull GameUI gameUI) {
		this.gameUI = gameUI;
	}

	public void setTowerPlacing(TowerPlacing towerPlacing) {
	    this.towerPlacing = towerPlacing;
    }

	/**
	 * Set the field view region, in other words, set the region of the field that will be drawn on the screen.
	 *
	 * @param fieldStartPosX pos x
	 * @param fieldStartPosY pos y
	 * @param fieldZoom      zoom
	 */
	public final void setFieldViewRegion(double fieldStartPosX, double fieldStartPosY, double fieldZoom) {
		this.fieldStartPosX = fieldStartPosX;
		this.fieldStartPosY = fieldStartPosY;
		this.fieldZoom = fieldZoom;
	}

	/**
	 * Do render. Should not touch.
	 */
	public final void render() throws FileNotFoundException {
		final GameField gameField = this.gameField;
		final GameUI gameUI = this.gameUI;
		final TowerPlacing towerPlacing = this.towerPlacing;
		final double fieldStartPosX = this.fieldStartPosX;
		final double fieldStartPosY = this.fieldStartPosY;
		final double fieldZoom = this.fieldZoom;

		final List<GameEntity> entities = new ArrayList<>(GameEntities.getOverlappedEntities(gameField.getEntities(),
				fieldStartPosX, fieldStartPosY, Config.SCREEN_WIDTH / fieldZoom, Config.SCREEN_HEIGHT / fieldZoom));
		final Collection<UIEntity> buttons = gameUI.getEntities();
		entities.sort(GameDrawer::entityDrawingOrderComparator);

		graphicsContext.setFill(Color.rgb(46, 46, 46));
		graphicsContext.fillRect(0.0, 0.0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

		GameEntity lastEntity = null;

		for (final GameEntity entity : entities) {
			if (lastEntity != null && entityDrawingOrderComparator(entity, lastEntity) == 0) continue;
			lastEntity = entity;

			final EntityDrawer drawer = getEntityDrawer(entity);
			if (drawer != null) {
				drawer.draw(gameField.getTickCount(), graphicsContext, entity,
						(entity.getPosX() - fieldStartPosX) * fieldZoom,
						(entity.getPosY() - fieldStartPosY) * fieldZoom,
						entity.getWidth() * fieldZoom,
						entity.getHeight() * fieldZoom,
						fieldZoom
				);
			}
		}
		for (UIEntity button : buttons) {
			final UIDrawer drawer = getUIDrawer(button);
			if (drawer != null) {
				drawer.draw(gameField.getTickCount(), graphicsContext, button,
						(button.getPosX() - fieldStartPosX) * fieldZoom,
						(button.getPosY() - fieldStartPosY) * fieldZoom,
						button.getWidth() * fieldZoom,
						button.getHeight() * fieldZoom,
						fieldZoom
				);
			}
		}
		if (towerPlacing != null) {
            //System.out.println(towerPlacing.getPlacingState());
			final TowerPlacingDrawer drawer = new TowerPlacingDrawer();
			drawer.draw(gameField.getTickCount(), graphicsContext, towerPlacing,
					(towerPlacing.getTower().getPosX() - fieldStartPosX) * fieldZoom,
					(towerPlacing.getTower().getPosY() - fieldStartPosY) * fieldZoom,
					towerPlacing.getTower().getWidth() * fieldZoom,
					towerPlacing.getTower().getHeight() * fieldZoom,
					fieldZoom);
		}
	}

	public final double screenToFieldPosX(double screenPosX) {
		return screenPosX * fieldZoom + fieldStartPosX;
	}

	public final double screenToFieldPosY(double screenPosY) {
		return screenPosY * fieldZoom + fieldStartPosY;
	}

	public final double fieldToScreenPosX(double fieldPosX) {
		return (fieldPosX - fieldStartPosX) / fieldZoom;
	}

	public final double fieldToScreenPosY(double fieldPosY) {
		return (fieldPosY - fieldStartPosY) / fieldZoom;
	}

	public static Image getSheetImage() {
		return sheetImage;
	}

	public static Image getButtonImage () {
		return buttonImage;
	}
}
