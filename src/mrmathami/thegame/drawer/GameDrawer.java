package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.GameUI;
import mrmathami.thegame.towerpicker.AbstractTowerPicker;
import mrmathami.thegame.ui.ingame.button.*;
import mrmathami.thegame.entity.tile.*;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.bullet.RocketBullet;
import mrmathami.thegame.entity.enemy.BigAircraft;
import mrmathami.thegame.entity.enemy.NormalAircraft;
import mrmathami.thegame.entity.enemy.NormalEnemy;
import mrmathami.thegame.entity.enemy.Tanker;
import mrmathami.thegame.entity.tile.spawner.BigAircraftSpawner;
import mrmathami.thegame.entity.tile.spawner.NormalAircraftSpawner;
import mrmathami.thegame.entity.tile.spawner.NormalSpawner;
import mrmathami.thegame.entity.tile.spawner.TankerSpawner;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;
import mrmathami.thegame.ui.ingame.context.AbstractUIContext;
import mrmathami.thegame.ui.ingame.context.ButtonUIContext;
import mrmathami.thegame.ui.ingame.context.NormalUIContext;
import mrmathami.thegame.ui.ingame.context.TowerUIContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
			Rock.class,
			Bush.class,
			ExplosionEffect.class,
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
			NormalAircraftSpawner.class,
			BigAircraftSpawner.class,
			TankerSpawner.class,
			NormalAircraftSpawner.class,
			NormalAircraft.class,
			BigAircraft.class,
			Tanker.class,
//			SmallerSpawner.class,
//			TankerSpawner.class,
//			BossSpawner.class,
			Target.class,
			GameButtonDrawer.class
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
			Map.entry(Rock.class, new RockDrawer()),
			Map.entry(Bush.class, new BushDrawer()),
			Map.entry(ExplosionEffect.class, new ExplosionEffectDrawer()),
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

	@Nonnull private static final Map<Class<? extends UIEntity>, UIEntityDrawer> UI_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(NavigationButton.class, new GameButtonDrawer()),
			Map.entry(TowerButton.class, new GameButtonDrawer()),
			Map.entry(ContextButton.class, new GameButtonDrawer())
	));

	@Nonnull private static final Map<Class<? extends UIEntity>, UIEntityDrawer> UI_CONTEXT_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(NormalUIContext.class, new NormalUIContextDrawer()),
			Map.entry(ButtonUIContext.class, new ButtonUIContextDrawer()),
			Map.entry(TowerUIContext.class, new TowerUIContextDrawer())
	));

	@Nonnull private final GraphicsContext graphicsContext;
	@Nonnull private GameField gameField;
	@Nonnull private GameUI gameUI;
	private AbstractTowerPicker towerPicker;
	private AbstractUIContext UIContext;
	private static Image sheetImage;
	private static Image buttonImage;
	private static Image rankImage;
	private static Image contextIconImage;

	private transient double fieldStartPosX = Float.NaN;
	private transient double fieldStartPosY = Float.NaN;
	private transient double fieldZoom = Float.NaN;

	public GameDrawer(@Nonnull GraphicsContext graphicsContext, @Nonnull GameField gameField, @Nonnull GameUI gameUI, AbstractTowerPicker towerPicker, AbstractUIContext UIContext, String sheetImage, String buttonImage) throws FileNotFoundException {
		this.graphicsContext = graphicsContext;
		this.gameField = gameField;
		this.towerPicker = towerPicker;
		this.UIContext = UIContext;
		this.sheetImage = new Image(getClass().getResourceAsStream(sheetImage));
		this.buttonImage = new Image(getClass().getResourceAsStream(buttonImage));
		this.rankImage = new Image(getClass().getResourceAsStream("/stage/default_gold.png"));
		this.contextIconImage = new Image(getClass().getResourceAsStream("/ui/contextIcon.png"));
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
	private static UIEntityDrawer getUIDrawer(@Nonnull UIEntity entity) {
		return UI_DRAWER_MAP.get(entity.getClass());
	}

	private static UIEntityDrawer getUIContextDrawer (@Nonnull AbstractUIContext UIContext) {
		return UI_CONTEXT_DRAWER_MAP.get(UIContext.getClass());
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

	public void setTowerPicker(AbstractTowerPicker towerPicker) {
	    this.towerPicker = towerPicker;
    }

	public void setUIContext(AbstractUIContext UIContext) {
		this.UIContext = UIContext;
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
		final AbstractTowerPicker towerPicker = this.towerPicker;
		final double fieldStartPosX = this.fieldStartPosX;
		final double fieldStartPosY = this.fieldStartPosY;
		final double fieldZoom = this.fieldZoom;

		final List<GameEntity> entities = new ArrayList<>(GameEntities.getOverlappedEntities(gameField.getEntities(),
				fieldStartPosX, fieldStartPosY, Config.SCREEN_WIDTH / fieldZoom, Config.SCREEN_HEIGHT / fieldZoom));
		final Collection<UIEntity> UIEntities = gameUI.getEntities();
		entities.sort(GameDrawer::entityDrawingOrderComparator);

		graphicsContext.setFill(Color.rgb(152, 118, 90));
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
		for (UIEntity entity : UIEntities) {
			final UIEntityDrawer drawer = getUIDrawer(entity);
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
		if (towerPicker != null) {
			final TowerPickerDrawer drawer = new TowerPickerDrawer();
			drawer.draw(gameField.getTickCount(), graphicsContext, towerPicker,
					(towerPicker.getPosX() - fieldStartPosX) * fieldZoom,
					(towerPicker.getPosY() - fieldStartPosY) * fieldZoom,
					fieldZoom, fieldZoom, fieldZoom
			);
		}
		if (UIContext != null) {
			final UIEntityDrawer drawer = getUIContextDrawer(UIContext);
			if (drawer != null) {
				drawer.draw(gameField.getTickCount(), graphicsContext, UIContext,
						(UIContext.getPosX() - fieldStartPosX) * fieldZoom,
						(UIContext.getPosY() - fieldStartPosY) * fieldZoom,
						UIContext.getWidth() * fieldZoom,
						UIContext.getHeight() * fieldZoom,
						fieldZoom
				);
			}
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

	public static Image getRankImage() {
		return rankImage;
	}

	public static Image getContextIconImage() {
		return contextIconImage;
	}
}
