package mrmathami.thegame;

public final class Config {
	/**
	 * Game name. Change it if you want.
	 */
	public static final String GAME_NAME = "The Game";
	/**
	 * Ticks per second
	 */
	public static final long GAME_TPS = 20;
	/**
	 * Nanoseconds per tick
	 */
	public static final long GAME_NSPT = Math.round(1000000000.0 / GAME_TPS);

	/**
	 * Size of the tile, in pixel.
	 * 1.0 field unit == TILE_SIZE pixel on the screen.
	 * Change it base on your texture size.
	 */
	public static final long TILE_SIZE = 64;
	/**
	 * Num of tiles the screen can display if fieldZoom is TILE_SIZE,
	 * in other words, the texture will be display as it without scaling.
	 */
	public static final long TILE_HORIZONTAL = 16;
	/**
	 * Num of tiles the screen can display if fieldZoom is TILE_SIZE,
	 * in other words, the texture will be display as it without scaling.
	 */
	public static final long TILE_VERTICAL = 9;
	/**
	 * An arbitrary number just to make some code run a little faster.
	 * Do not touch.
	 */
	public static final int _TILE_MAP_COUNT = (int) (TILE_HORIZONTAL * TILE_VERTICAL);

	/**
	 * Num of tiles the sidebar uses
	 */
	public static final long SIDEBAR_HORIZONTAL = 4;
	public static final long SIDEBAR_VERTICAL = TILE_VERTICAL;


	/**
	 * Size of the screen.
	 */
	public static final long SCREEN_WIDTH = TILE_SIZE * (TILE_HORIZONTAL + SIDEBAR_HORIZONTAL);
	/**
	 * Size of the screen.
	 */
	public static final long SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;


	//Other config related to other entities in the game.

	//region Bullet
	public static final long NORMAL_BULLET_TTL = 30;
	public static final long NORMAL_BULLET_STRENGTH = 30;
	public static final double NORMAL_BULLET_SPEED = 0.3;

	public static final long MACHINE_GUN_BULLET_TTL = 15;
	public static final long MACHINE_GUN_BULLET_STRENGTH = 20;
	public static final double MACHINE_GUN_BULLET_SPEED = 0.4;
	public static final int MACHINE_GUN_BULLET_GID = 273;

	public static final long SNIPER_BULLET_TTL = 60;
	public static final long SNIPER_BULLET_STRENGTH = 120;
	public static final double SNIPER_BULLET_SPEED = 0.5;

	public static final long ROCKET_BULLET_TTL = 60;
	public static final long ROCKET_BULLET_STRENGTH = 300;
	public static final double ROCKET_BULLET_SPEED = 0.2;
	public static final int ROCKET_BULLET_GID = 252;
	//endregion

	//region Tower
	public static final long NORMAL_TOWER_SPEED = 10;
	public static final double NORMAL_TOWER_RANGE = 2.0;
	public static final int NORMAL_TOWER_LEVEL1_GID = 204;
	public static final int NORMAL_TOWER_LEVEL2_GID = 0;
	public static final int NORMAL_TOWER_LEVEL3_GID = 0;

	public static final long MACHINE_GUN_TOWER_SPEED = 5;
	public static final double MACHINE_GUN_TOWER_RANGE = 4.0;
	public static final int MACHINE_GUN_TOWER_LEVEL1_GID = 250;
	public static final int MACHINE_GUN_TOWER_LEVEL2_GID = 251;
	public static final int MACHINE_GUN_TOWER_LEVEL3_GID = 251;

	public static final long ROCKET_TOWER_SPEED = 20;
	public static final double ROCKET_TOWER_RANGE = 4.0;
	public static final int ROCKET_TOWER_LEVEL1_GID = 205;
	public static final int ROCKET_TOWER_LEVEL2_GID = 206;
	public static final int ROCKET_TOWER_LEVEL3_GID = 206;

	public static final int LOCKED_GID = 291;
	//endregion

	//region Enemy
	public static final double NORMAL_ENEMY_SIZE = 1;
	public static final long NORMAL_ENEMY_HEALTH = 1000;
	public static final long NORMAL_ENEMY_ARMOR = 3;
	public static final double NORMAL_ENEMY_SPEED = 0.2;
	public static final long NORMAL_ENEMY_REWARD = 1;

	public static final double NORMAL_AIRCRAFT_ENEMY_SIZE = 1;
	public static final long NORMAL_AIRCRAFT_ENEMY_HEALTH = 50;
	public static final long NORMAL_AIRCRAFT_ENEMY_ARMOR = 3;
	public static final double NORMAL_AIRCRAFT_ENEMY_SPEED = 0.2;
	public static final long NORMAL_AIRCRAFT_ENEMY_REWARD = 1;
	public static final int[] NORMAL_AIRCRAFT_ENEMY_GID = {246, 247, 248, 249};

	public static final double BIG_AIRCRAFT_ENEMY_SIZE = 1;
	public static final long BIG_AIRCRAFT_ENEMY_HEALTH = 300;
	public static final long BIG_AIRCRAFT_ENEMY_ARMOR = 2;
	public static final double BIG_AIRCRAFT_ENEMY_SPEED = 0.2;
	public static final long BIG_AIRCRAFT_ENEMY_REWARD = 1;
	public static final int[] BIG_AIRCRAFT_ENEMY_GID = {271, 272};

	public static final double TANKER_ENEMY_SIZE = 1;
	public static final long TANKER_ENEMY_HEALTH = 1000;
	public static final long TANKER_ENEMY_ARMOR = 2;
	public static final double TANKER_ENEMY_SPEED = 0.2;
	public static final long TANKER_ENEMY_REWARD = 1;
	public static final int[] TANKER_ENEMY_GID = {269, 270};

	public static final double SMALLER_ENEMY_SIZE = 0.7;
	public static final long SMALLER_ENEMY_HEALTH = 50;
	public static final long SMALLER_ENEMY_ARMOR = 0;
	public static final double SMALLER_ENEMY_SPEED = 0.4;
	public static final long SMALLER_ENEMY_REWARD = 2;

	public static final double BOSS_ENEMY_SIZE = 1.3;
	public static final long BOSS_ENEMY_HEALTH = 500;
	public static final long BOSS_ENEMY_ARMOR = 8;
	public static final double BOSS_ENEMY_SPEED = 0.3;
	public static final long BOSS_ENEMY_REWARD = 10;
	//endregion

	private Config() {
	}


}
