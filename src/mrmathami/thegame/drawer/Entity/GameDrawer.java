package mrmathami.thegame.drawer.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.GameUI;
import mrmathami.thegame.drawer.Entity.Bullet.*;
import mrmathami.thegame.drawer.Entity.Enemy.*;
import mrmathami.thegame.drawer.Entity.Tile.*;
import mrmathami.thegame.drawer.Entity.Tile.CutInEffect.BossCutInEffectDrawer;
import mrmathami.thegame.drawer.Entity.Tile.Effect.*;
import mrmathami.thegame.drawer.Entity.Tile.Spawner.SpawnerDrawer;
import mrmathami.thegame.drawer.Entity.Tile.Tower.*;
import mrmathami.thegame.drawer.UI.InGame.*;
import mrmathami.thegame.drawer.UI.UIEntityDrawer;
import mrmathami.thegame.entity.bullet.StopSignBullet;
import mrmathami.thegame.entity.enemy.*;
import mrmathami.thegame.entity.tile.cutineffect.BossCutInEffect;
import mrmathami.thegame.entity.tile.effect.TowerDestroyEffect;
import mrmathami.thegame.entity.tile.effect.UpgradeEffect;
import mrmathami.thegame.entity.tile.tower.RobotPoliceTower;
import mrmathami.thegame.towerpicker.AbstractTowerPicker;
import mrmathami.thegame.ui.ingame.button.*;
import mrmathami.thegame.entity.tile.*;
import mrmathami.thegame.entity.tile.effect.*;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.UIEntity;
import mrmathami.thegame.entity.bullet.*;
import mrmathami.thegame.entity.tile.spawner.*;
import mrmathami.thegame.entity.tile.tower.*;
import mrmathami.thegame.ui.ingame.context.*;

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
			NormalTower.class,
			RocketLauncherTower.class,
			CardboardBoxTower.class,
//			SniperTower.class,
			MachineGunTower.class,
			RobotPoliceTower.class,
			NormalBullet.class,
			MachineGunBullet.class,
			RocketBullet.class,
			NyanCatBullet.class,
			StopSignBullet.class,
//			SniperBullet.class,
			NormalEnemy.class,
//			SmallerEnemy.class,
//			TankerEnemy.class,
//			BossEnemy.class,
			NormalAircraftSpawner.class,
			BigAircraftSpawner.class,
			TankerSpawner.class,
			NormalAircraftSpawner.class,
			NormalAircraft.class,
			BigAircraft.class,
			Tanker.class,
			GrabEnemy.class,
			JohnCenaBossEnemy.class,
			BinLadenBossEnemy.class,
			KimJongUnBossEnemy.class,
			SonGokuBossEnemy.class,
			ElonMuskBossEnemy.class,
			MedicBossEnemy.class,
//			SmallerSpawner.class,
//			TankerSpawner.class,
//			BossSpawner.class,
			Target.class,
			ExplosionEffect.class,
			UpgradeEffect.class,
			TowerDestroyEffect.class,
			AlertEffect.class,
			BossCutInEffect.class,
			GameButtonDrawer.class
	);
	/**
	 * TODO:
	 * This is a map between Entity type and its drawer.
	 * Remember to add your entity drawer here.
	 */
	@Nonnull private static final Map<Class<? extends GameEntity>, EntityDrawer> ENTITY_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(Road.class, new RoadDrawer()),
			Map.entry(Mountain.class, new MountainDrawer()),
			Map.entry(Rock.class, new RockDrawer()),
			Map.entry(Bush.class, new BushDrawer()),
			Map.entry(ExplosionEffect.class, new ExplosionEffectDrawer()),
			Map.entry(NormalTower.class, new NormalTowerDrawer()),
			Map.entry(RocketLauncherTower.class, new RocketLauncherTowerDrawer()),
			Map.entry(CardboardBoxTower.class, new CardboardBoxTowerDrawer()),
			Map.entry(RobotPoliceTower.class, new RobotPoliceTowerDrawer()),
//			Map.entry(SniperTower.class, new SniperTowerDrawer()),
			Map.entry(MachineGunTower.class, new MachineGunTowerDrawer()),
			Map.entry(NormalBullet.class, new NormalBulletDrawer()),
			Map.entry(MachineGunBullet.class, new MachineGunBulletDrawer()),
			Map.entry(RocketBullet.class, new RocketBulletDrawer()),
			Map.entry(NyanCatBullet.class, new NyanCatBulletDrawer()),
			Map.entry(StopSignBullet.class, new StopSignBulletDrawer()),
//			Map.entry(SniperBullet.class, new SniperBulletDrawer()),
			Map.entry(NormalEnemy.class, new NormalEnemyDrawer()),
			Map.entry(NormalAircraft.class, new NormalAircraftDrawer()),
			Map.entry(BigAircraft.class, new BigAircraftDrawer()),
			Map.entry(Tanker.class, new TankerDrawer()),
			Map.entry(GrabEnemy.class, new GrabEnemyDrawer()),
			Map.entry(JohnCenaBossEnemy.class, new BossEnemyDrawer()),
			Map.entry(BinLadenBossEnemy.class, new BossEnemyDrawer()),
			Map.entry(KimJongUnBossEnemy.class, new BossEnemyDrawer()),
			Map.entry(SonGokuBossEnemy.class, new BossEnemyDrawer()),
			Map.entry(ElonMuskBossEnemy.class, new BossEnemyDrawer()),
			Map.entry(MedicBossEnemy.class, new BossEnemyDrawer()),
//			Map.entry(SmallerEnemy.class, new SmallerEnemyDrawer()),
//			Map.entry(TankerEnemy.class, new TankerEnemyDrawer()),
//			Map.entry(BossEnemy.class, new BossEnemyDrawer()),
			Map.entry(NormalAircraftSpawner.class, new SpawnerDrawer()),
			Map.entry(BigAircraftSpawner.class, new SpawnerDrawer()),
			Map.entry(TankerSpawner.class, new SpawnerDrawer()),
//			Map.entry(SmallerSpawner.class, new SpawnerDrawer()),
//			Map.entry(TankerSpawner.class, new SpawnerDrawer()),
//			Map.entry(BossSpawner.class, new SpawnerDrawer()),
			Map.entry(UpgradeEffect.class, new UpgradeEffectDrawer()),
			Map.entry(TowerDestroyEffect.class, new TowerDestroyEffectDrawer()),
			Map.entry(AlertEffect.class, new AlertEffectDrawer()),
			Map.entry(BossCutInEffect.class, new BossCutInEffectDrawer()),
			Map.entry(Target.class, new TargetDrawer())
	));

	@Nonnull private static final Map<Class<? extends UIEntity>, UIEntityDrawer> UI_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(BackButton.class, new GameButtonDrawer()),
			Map.entry(PauseButton.class, new GameButtonDrawer()),
			Map.entry(UpgradeButton.class, new GameButtonDrawer()),
			Map.entry(SellButton.class, new GameButtonDrawer()),
			Map.entry(TowerButton.class, new GameButtonDrawer())
	));

	@Nonnull private static final Map<Class<? extends UIEntity>, UIEntityDrawer> UI_CONTEXT_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(NormalUIContext.class, new NormalUIContextDrawer()),
			Map.entry(MPNormalUIContext.class, new MPNormalUIContextDrawer()),
			Map.entry(ButtonUIContext.class, new ButtonUIContextDrawer()),
			Map.entry(TowerUIContext.class, new TowerUIContextDrawer()),
			Map.entry(MessageUIContext.class, new MessageUIContextDrawer())
	));

	@Nonnull private final GraphicsContext graphicsContext;
	@Nonnull private GameField gameField;
	@Nullable private GameField opponentGameField;
	@Nonnull private GameUI gameUI;
	private AbstractTowerPicker towerPicker;
	private ContextArea contextArea;
	private static Image sheetImage;
	private static Image buttonImage;
	private static Image rankImage;
	private static Image contextIconImage;

	public static Image getEmoteImage() {
		return emoteImage;
	}

	private static Image emoteImage;

	private transient double fieldStartPosX = Float.NaN;
	private transient double fieldStartPosY = Float.NaN;
	private transient double fieldZoom = Float.NaN;

	public GameDrawer(@Nonnull GraphicsContext graphicsContext, @Nonnull GameField gameField,
					  @Nullable GameField opponentGameField, @Nonnull GameUI gameUI, AbstractTowerPicker towerPicker,
					  ContextArea contextArea, String sheetImage, String buttonImage) throws FileNotFoundException {
		this.graphicsContext = graphicsContext;
		this.gameField = gameField;
		this.towerPicker = towerPicker;
		this.contextArea = contextArea;
		this.sheetImage = new Image(getClass().getResourceAsStream(sheetImage));
		this.buttonImage = new Image(getClass().getResourceAsStream(buttonImage));
		this.rankImage = new Image(getClass().getResourceAsStream("/stage/default_gold.png"));
		this.contextIconImage = new Image(getClass().getResourceAsStream("/stage/ui/contextIcon.png"));
		this.emoteImage = new Image(getClass().getResourceAsStream("/stage/popup/notif.png"));
		this.gameUI = gameUI;
		this.opponentGameField = opponentGameField;
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

	public void setUIContext(ContextArea contextArea) {
		this.contextArea = contextArea;
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

		// For game UI
		final Collection<UIEntity> UIEntities = gameUI.getEntities();
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

		// If we're having an opponent
		if (this.opponentGameField != null) {
			final List<GameEntity> opponentEntities = new ArrayList<>(GameEntities.getOverlappedEntities(opponentGameField.getEntities(),
					fieldStartPosX, fieldStartPosY, Config.SCREEN_WIDTH / fieldZoom, Config.SCREEN_HEIGHT / fieldZoom));
			for (final GameEntity entity : opponentEntities) {
				final EntityDrawer drawer = getEntityDrawer(entity);
//				if (entity instanceof MachineGunTower) {
//					System.out.println("Opponent MachineGunTower " + entity.getPosX() + "" + entity.getPosY());
//				}
				if (drawer != null) {
					drawer.draw(opponentGameField.getTickCount(), graphicsContext, entity,
							(entity.getPosX() - fieldStartPosX) * fieldZoom,
							(entity.getPosY() - fieldStartPosY) * fieldZoom,
							entity.getWidth() * fieldZoom,
							entity.getHeight() * fieldZoom,
							fieldZoom
					);
				}
			}
		}

		//For tower picker
		if (towerPicker != null) {
			final TowerPickerDrawer drawer = new TowerPickerDrawer();
			drawer.draw(gameField.getTickCount(), graphicsContext, towerPicker,
					(towerPicker.getPosX() - fieldStartPosX) * fieldZoom,
					(towerPicker.getPosY() - fieldStartPosY) * fieldZoom,
					fieldZoom, fieldZoom, fieldZoom
			);
		}

		//For UI Context
		List<AbstractUIContext> UIContexts = contextArea.getUIContextsList();
		for (AbstractUIContext UIContext: UIContexts) {
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
