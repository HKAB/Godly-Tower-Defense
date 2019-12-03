package mrmathami.thegame;

import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.*;
import mrmathami.thegame.entity.tile.spawner.*;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;
import mrmathami.thegame.net.MPConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class GameStage {
	private final long width;
	private final long height;
	@Nonnull
	private final List<GameEntity> entities;

	private GameStage(long width, long height, @Nonnull List<GameEntity> entities) {
		this.width = width;
		this.height = height;
		this.entities = List.copyOf(entities);
	}

	@Nullable
	public static GameStage load(@Nonnull String name, boolean isOpponent) {
		try (final InputStream stream = GameStage.class.getResourceAsStream(name)) {
			if (stream == null) throw new IOException("Resource not found! Resource name: " + name);
			final Scanner scanner = new Scanner(stream);
			try {
				final int width = scanner.nextInt();
				final int height = scanner.nextInt();
				final int numOfTiles = scanner.nextInt();
				final List<GameEntity> entities = new ArrayList<>(width * height + numOfTiles);
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						final int value = scanner.nextInt();
						if (value == 26 || value == 4 || value == 48 || value == 49 || value == 47 || value == 3 // map 1 part 1 road id
								|| value == 243 || value == 264 || value == 265 || value == 266 || value == 220 || value == 221// map 1 part 2 road id
								|| value == 169 || value == 190 || value == 191 || value == 260 || value == 261 || value == 238 ||  value == 233 || value == 211 || value == 255 // map 2
								|| value == 58 || value == 59 || value == 36 || value == 13 || value == 56 || value == 57) {
							entities.add(new Road(0, x + (isOpponent ? MPConfig.OPPONENT_START_X : 0), y, value));
						}
						else if (value != 0)
						{
							entities.add(new Mountain(0, x + (isOpponent ? MPConfig.OPPONENT_START_X : 0), y, value));
						}
					}
				}
				// path finding
				final Queue<Road> roadQueue = new LinkedList<>();

				for (int i = 0; i < numOfTiles; i++) {
					final String value = scanner.next();
					if ("Rock".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int gid = scanner.nextInt();
						entities.add(new Rock(0, x, y, gid));
					}
					else if ("Bush".equals(value)) {
						final int x = scanner.nextInt();
						final int y = scanner.nextInt();
						final int gid = scanner.nextInt();
						entities.add(new Bush(0, x, y, gid));
					} else if ("NormalAircraftSpawner".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new NormalAircraftSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("BigAircraftSpawner".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new BigAircraftSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("TankerSpawner".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new TankerSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("GrabSpawner".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new GrabSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("JohnCenaBossSpawner".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new JohnCenaBossSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("BinLadenBossSpawner".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int spawnInterval = scanner.nextInt();
						final int initialDelay = scanner.nextInt();
						final int numOfSpawn = scanner.nextInt();
						entities.add(new BinLadenBossSpawner(0, x, y, w, h, spawnInterval, initialDelay, numOfSpawn));
					} else if ("NormalTower".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int angle = scanner.nextInt();
						entities.add(new NormalTower(0, x, y, angle));
					} else if ("MachineGunTower".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final double angle = scanner.nextInt();
						entities.add(new MachineGunTower(0, x, y, angle));
					} else if ("RocketLauncherTower".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final double angle = scanner.nextInt();
						entities.add(new RocketLauncherTower(0, x, y, angle));
					} else if ("Target".equals(value)) {
						final int x = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final int y = scanner.nextInt();
						final int w = scanner.nextInt();
						final int h = scanner.nextInt();
						final int health = scanner.nextInt();
						entities.add(new Target(0, x, y, w, h, health));

						final Collection<GameEntity> overlappedEntities = GameEntities.getOverlappedEntities(entities, x, y, w, h);
						for (GameEntity entity : overlappedEntities) {
							if (entity instanceof Road) {
								final Road road = (Road) entity;
								roadQueue.add(road);
								road.setDistance(0.0);
							}
						}
					}
					else if ("TurnPoint".equals(value))
					{
						final long x1 = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final long y1 = scanner.nextInt();
						final long x2 = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final long y2 = scanner.nextInt();
						final long x3 = scanner.nextInt() + (isOpponent ? MPConfig.OPPONENT_START_X : 0);
						final long y3 = scanner.nextInt();
						entities.add(new TurnPoint(x1, y1, x2, y2, x3, y3));
					}
					else {
						System.out.println("Unexpected value! Input value: " + value);
						scanner.nextLine();
//						throw new InputMismatchException("Unexpected value! Input value: " + value);
					}
				}

				final Set<Road> roadSet = new HashSet<>(roadQueue);
				while (!roadQueue.isEmpty()) {
					final Road road = roadQueue.poll();
					roadSet.add(road);
					final Collection<Road> overlappedEntities = GameEntities.getFilteredOverlappedEntities(entities, Road.class,
							road.getPosX() - 0.5, road.getPosY() - 0.5, 2, 2);
					for (Road destRoad : overlappedEntities) {
						if (!roadSet.contains(destRoad)) {
							if (!roadQueue.contains(destRoad)) roadQueue.add(destRoad);
							final double deltaX = road.getPosX() - destRoad.getPosX();
							final double deltaY = road.getPosY() - destRoad.getPosY();
							final Collection<Road> destOverlappedRoads = GameEntities.getFilteredOverlappedEntities(entities, Road.class,
									destRoad.getPosX() - 0.5, destRoad.getPosY() - 0.5, 2.0, 2.0);
							final double destDistance = road.getDistance() + Math.sqrt(deltaX * deltaX + deltaY * deltaY)/destOverlappedRoads.size();
							if (Double.isNaN(destRoad.getDistance()) || destRoad.getDistance() > destDistance) {
								destRoad.setDistance(destDistance);
							}
						}
					}
				}
				return new GameStage(width, height, entities);
			} catch (NoSuchElementException e) {
				throw new IOException("Resource invalid! Resource name: " + name, e);
			}
			// width height numOfRemainingTiles
			// (width*height matrix with 1 for Mountain and 0 for Road)
			// <SpawnerName> x y w h spawnInterval initialDelay numOfSpawn
			// <TowerName> x y
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final long getWidth() {
		return width;
	}

	public final long getHeight() {
		return height;
	}

	@Nonnull
	public final List<GameEntity> getEntities() {
		return entities;
	}
}
