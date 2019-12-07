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
	 * Field Position on screen
	 */
	public static final double FIELD_START_POS_X = 0.0;
	public static final double FIELD_START_POS_Y = 0.0;

	/**
	 * UI Context size and position
	 */
	public static final double UI_CONTEXT_POS_X = 16;
	public static final double UI_CONTEXT_POS_X_MP = 8;
	public static final double UI_CONTEXT_POS_Y = 3.5;
	public static final double UI_CONTEXT_WIDTH = 4;
	public static final double UPPER_UI_CONTEXT_HEIGHT = 1.5;
	public static final double LOWER_UI_CONTEXT_HEIGHT = 2;
	public static final double MESSAGE_UI_CONTEXT_HEIGHT = 1;

	/**
	 * Size of the screen.
	 */
	public static final long SCREEN_WIDTH = TILE_SIZE * (TILE_HORIZONTAL + SIDEBAR_HORIZONTAL);
	/**
	 * Size of the screen.
	 */
	public static final long SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;

	public static final double OFFSET = 45.0;

	public static final int MAX_LEVEL_COUNT = 4;


	//region Audio
	public static double INITIAL_MAIN_VOLUME = 0.1;
	//endregion
	//region Popup
	public static final double CREDIT_POPUP_WIDTH = SCREEN_WIDTH - 100;
	public static final double CREDIT_POPUP_HEIGHT = SCREEN_HEIGHT - 100;
	//endregion

	// Player informations
	public static final long PLAYER_INITIAL_HEALTH = 10;
	public static final long PLAYER_INITIAL_MONEY = 10000;

	//Other config related to other entities in the game.

	//region Bullet
	public static final long NORMAL_BULLET_TTL = 20;
	public static final long NORMAL_BULLET_STRENGTH = 10;
	public static final double NORMAL_BULLET_SPEED = 0.3;
	public static final int NORMAL_BULLET_GID = 273;
	public static final double NORMAL_BULLET_WIDTH = 16.0;
	public static final double NORMAL_BULLET_HEIGHT = 16.0;


	public static final long MACHINE_GUN_BULLET_TTL = 20;
	public static final long MACHINE_GUN_BULLET_STRENGTH = 20;
	public static final double MACHINE_GUN_BULLET_SPEED = 0.4;
	public static final int MACHINE_GUN_BULLET_GID = 274;
	public static final double MACHINE_GUN_BULLET_WIDTH = 16.0;
	public static final double MACHINE_GUN_BULLET_HEIGHT = 16.0;

	public static final long STOP_SIGN_BULLET_TTL = 15;
	public static final long STOP_SIGN_BULLET_STRENGTH = 10;
	public static final double STOP_SIGN_BULLET_SPEED = 0.6;
	public static final int STOP_SIGN_BULLET_GID = 276;
	public static final double STOP_SIGN_BULLET_WIDTH = 32.0;
	public static final double STOP_SIGN_BULLET_HEIGHT = 32.0;

	public static final long SNIPER_BULLET_TTL = 60;
	public static final long SNIPER_BULLET_STRENGTH = 120;
	public static final double SNIPER_BULLET_SPEED = 0.5;

	public static final long ROCKET_BULLET_TTL = 60;
	public static final long ROCKET_BULLET_STRENGTH = 60;
	public static final double ROCKET_BULLET_SPEED = 0.3;
	public static final int ROCKET_BULLET_GID = 252;
	public static final double ROCKET_BULLET_WIDTH = 12.0;
	public static final double ROCKET_BULLET_HEIGHT = 36.0;

	public static final long NYAN_CAT_BULLET_TTL = 50;
	public static final long NYAN_CAT_BULLET_STRENGTH = 7554;
	public static final double NYAN_CAT_BULLET_SPEED = 0.2;
	public static final int NYAN_CAT_BULLET_GID = 203;
	public static final double NYAN_CAT_BULLET_WIDTH = 30.0;
	public static final double NYAN_CAT_BULLET_HEIGHT = 39.0;
	public static final double NYAN_CAT_BULLET_TAIL_HEIGHT = 10.0;

	//endregion

	//region Tower
	public static final long NORMAL_TOWER_SPEED = 20;
	public static final double NORMAL_TOWER_RANGE = 2.0;
	public static final int NORMAL_TOWER_LEVEL1_GID = 204;
	public static final int NORMAL_TOWER_LEVEL2_GID = 227;
	public static final int NORMAL_TOWER_LEVEL3_GID = 207;

	public static final long NORMAL_TOWER_PRICE = 100;
	public static final long NORMAL_TOWER_LEVEL1_SELL_PRICE = 75;
	public static final long NORMAL_TOWER_LEVEL1_UPGRADE_PRICE = 150;
	public static final long NORMAL_TOWER_LEVEL2_SELL_PRICE = 150;
	public static final long NORMAL_TOWER_LEVEL2_UPGRADE_PRICE = 250;
	public static final long NORMAL_TOWER_LEVEL3_SELL_PRICE = 250;

	public static final long MACHINE_GUN_TOWER_SPEED = 10;
	public static final double MACHINE_GUN_TOWER_RANGE = 3.0;
	public static final int MACHINE_GUN_TOWER_LEVEL1_GID = 250;
	public static final int MACHINE_GUN_TOWER_LEVEL2_GID = 251;
	public static final int MACHINE_GUN_TOWER_LEVEL3_GID = 253;

	public static final long MACHINE_GUN_TOWER_PRICE = 175;
	public static final long MACHINE_GUN_TOWER_LEVEL1_SELL_PRICE = 125;
	public static final long MACHINE_GUN_TOWER_LEVEL1_UPGRADE_PRICE = 250;
	public static final long MACHINE_GUN_TOWER_LEVEL2_SELL_PRICE = 250;
	public static final long MACHINE_GUN_TOWER_LEVEL2_UPGRADE_PRICE = 400;
	public static final long MACHINE_GUN_TOWER_LEVEL3_SELL_PRICE = 400;

	public static final long ROBOT_POLICE_TOWER_SPEED = 20;
	public static final double ROBOT_POLICE_TOWER_RANGE = 2.0;
	public static final int ROBOT_POLICE_TOWER_LEVEL1_GID = 161;
	public static final int ROBOT_POLICE_TOWER_LEVEL2_GID = 160;
	public static final int ROBOT_POLICE_TOWER_LEVEL3_GID = 159;

	public static final long ROBOT_POLICE_TOWER_PRICE = 175;
	public static final long ROBOT_POLICE_TOWER_LEVEL1_SELL_PRICE = 125;
	public static final long ROBOT_POLICE_TOWER_LEVEL1_UPGRADE_PRICE = 250;
	public static final long ROBOT_POLICE_TOWER_LEVEL2_SELL_PRICE = 250;
	public static final long ROBOT_POLICE_TOWER_LEVEL2_UPGRADE_PRICE = 400;
	public static final long ROBOT_POLICE_TOWER_LEVEL3_SELL_PRICE = 400;

	public static final long ROCKET_TOWER_SPEED = 30;
	public static final double ROCKET_TOWER_RANGE = 4.0;
	public static final int ROCKET_TOWER_LEVEL1_GID = 205;
	public static final int ROCKET_TOWER_LEVEL2_GID = 206;
	public static final int ROCKET_TOWER_LEVEL3_GID = 230;

	public static final long ROCKET_TOWER_PRICE = 135;
	public static final long ROCKET_TOWER_LEVEL1_SELL_PRICE = 100;
	public static final long ROCKET_TOWER_LEVEL1_UPGRADE_PRICE = 200;
	public static final long ROCKET_TOWER_LEVEL2_SELL_PRICE = 200;
	public static final long ROCKET_TOWER_LEVEL2_UPGRADE_PRICE = 300;
	public static final long ROCKET_TOWER_LEVEL3_SELL_PRICE = 300;

	public static final long CARDBOARD_BOX_TOWER_SPEED = 80;
	public static final double CARDBOARD_BOX_TOWER_RANGE = 4.0;
	public static final int CARDBOARD_BOX_TOWER_LEVEL1_GID = 200;
	public static final int CARDBOARD_BOX_TOWER_LEVEL2_GID = 201;
	public static final int CARDBOARD_BOX_TOWER_LEVEL3_GID = 202;

	public static final long CARDBOARD_BOX_TOWER_PRICE = 800;
	public static final long CARDBOARD_BOX_TOWER_LEVEL1_SELL_PRICE = 200;
	public static final long CARDBOARD_BOX_TOWER_LEVEL1_UPGRADE_PRICE = 1500;
	public static final long CARDBOARD_BOX_TOWER_LEVEL2_SELL_PRICE = 1500;
	public static final long CARDBOARD_BOX_TOWER_LEVEL2_UPGRADE_PRICE = 2500;
	public static final long CARDBOARD_BOX_TOWER_LEVEL3_SELL_PRICE = 2500;

	public static final int LOCKED_GID = 291;
	//endregion

	//region Enemy
	public static final double NORMAL_ENEMY_SIZE = 1;
	public static final long NORMAL_ENEMY_HEALTH = 1000;
	public static final long NORMAL_ENEMY_ARMOR = 3;
	public static final double NORMAL_ENEMY_SPEED = 0.2;
	public static final long NORMAL_ENEMY_REWARD = 1;

	public static final double NORMAL_AIRCRAFT_ENEMY_SIZE = 1.0;
	public static final long NORMAL_AIRCRAFT_ENEMY_HEALTH = 20;
	public static final long NORMAL_AIRCRAFT_ENEMY_ARMOR = 0;
	public static final double NORMAL_AIRCRAFT_ENEMY_SPEED = 0.1;
	public static final long NORMAL_AIRCRAFT_ENEMY_REWARD = 1;
	public static final int[] NORMAL_AIRCRAFT_ENEMY_GID = {246, 247, 248, 249};
	public static final double NORMAL_AIRCRAFT_ENEMY_WIDTH = 24.0;
	public static final double NORMAL_AIRCRAFT_ENEMY_HEIGHT = 28.0;
	public static final long NORMAL_AIRCRAFT_ENEMY_DAMAGE = 1;


	public static final double BIG_AIRCRAFT_ENEMY_SIZE = 1;
	public static final long BIG_AIRCRAFT_ENEMY_HEALTH = 120;
	public static final long BIG_AIRCRAFT_ENEMY_ARMOR = 0;
	public static final double BIG_AIRCRAFT_ENEMY_SPEED = 0.15;
	public static final long BIG_AIRCRAFT_ENEMY_REWARD = 1;
	public static final int[] BIG_AIRCRAFT_ENEMY_GID = {271, 272};
	public static final double BIG_AIRCRAFT_ENEMY_WIDTH = 55.0;
	public static final double BIG_AIRCRAFT_ENEMY_HEIGHT = 60.0;
	public static final long BIG_AIRCRAFT_ENEMY_DAMAGE = 2;


	public static final double TANKER_ENEMY_SIZE = 1;
	public static final long TANKER_ENEMY_HEALTH = 300;
	public static final long TANKER_ENEMY_ARMOR = 2;
	public static final double TANKER_ENEMY_SPEED = 0.07;
	public static final long TANKER_ENEMY_REWARD = 35;
	public static final int[] TANKER_ENEMY_GID = {269, 270};
	public static final double TANKER_ENEMY_WIDTH = 54.0;
	public static final double TANKER_ENEMY_HEIGHT = 36.0;
	public static final double TANKER_BARREL_ENEMY_WIDTH = 54.0;
	public static final double TANKER_BARREL_ENEMY_HEIGHT = 20.0;
	public static final long TANKER_ENEMY_DAMAGE = 4;

	public static final double JOHN_CENA_BOSS_ENEMY_SIZE = 1.0;
	public static final long JOHN_CENA_BOSS_ENEMY_HEALTH = 1500;
	public static final long JOHN_CENA_BOSS_ENEMY_ARMOR = 10;
	public static final double JOHN_CENA_BOSS_ENEMY_SPEED = 0.05;
	public static final long JOHN_CENA_BOSS_ENEMY_REWARD = 100;
	public static final double JOHN_CENA_BOSS_ENEMY_WIDTH = 30.0;
	public static final double JOHN_CENA_BOSS_ENEMY_HEIGHT = 64.0;
	public static final int JOHN_CENA_BOSS_ENEMY_GID = 223;
	public static final int JOHN_CENA_BOSS_ENEMY_ON_SKILL_GID = 224;
	public static final long JOHN_CENA_BOSS_ENEMY_SKILL_ACTIVATE_TIME = 100;
	public static final long JOHN_CENA_BOSS_ENEMY_SKILL_DEACTIVATE_TIME = 250;
	public static final String JOHN_CENA_BOSS_ENEMY_CUT_IN_URI = "/stage/bosscutin/johncenacutin.png";

	public static final double BIN_LADEN_BOSS_ENEMY_SIZE = 1.0;
	public static final long BIN_LADEN_BOSS_ENEMY_HEALTH = 1000;
	public static final long BIN_LADEN_BOSS_ENEMY_ARMOR = 5;
	public static final double BIN_LADEN_BOSS_ENEMY_SPEED = 0.07;
	public static final long BIN_LADEN_BOSS_ENEMY_REWARD = 100;
	public static final double BIN_LADEN_BOSS_ENEMY_WIDTH = 40.0;
	public static final double BIN_LADEN_BOSS_ENEMY_HEIGHT = 64.0;
	public static final int BIN_LADEN_BOSS_ENEMY_GID = 155;
	public static final String BIN_LADEN_BOSS_ENEMY_CUT_IN_URI = "/stage/bosscutin/binladencutin.png";

	public static final double KIM_JONG_UN_BOSS_ENEMY_SIZE = 1.0;
	public static final long KIM_JONG_UN_BOSS_ENEMY_HEALTH = 2000;
	public static final long KIM_JONG_UN_BOSS_ENEMY_ARMOR = 10;
	public static final double KIM_JONG_UN_BOSS_ENEMY_SPEED = 0.07;
	public static final long KIM_JONG_UN_BOSS_ENEMY_REWARD = 100;
	public static final double KIM_JONG_UN_BOSS_ENEMY_WIDTH = 48.0;
	public static final double KIM_JONG_UN_BOSS_ENEMY_HEIGHT = 64.0;
	public static final int KIM_JONG_UN_BOSS_ENEMY_GID = 154;
	public static final long KIM_JONG_UN_BOSS_ENEMY_SKILL_ACTIVATE_TIME = 100;
	public static final int KIM_JONG_UN_BOSS_ENEMY_SKILL_RANGE = 3;
	public static final String KIM_JONG_UN_BOSS_ENEMY_CUT_IN_URI = "/stage/bosscutin/kimjonguncutin.png";

	public static final double SON_GOKU_BOSS_ENEMY_SIZE = 1.0;
	public static final long SON_GOKU_BOSS_ENEMY_HEALTH = 1400;
	public static final long SON_GOKU_BOSS_ENEMY_ARMOR = 6;
	public static final double SON_GOKU_BOSS_ENEMY_SPEED = 0.05;
	public static final long SON_GOKU_BOSS_ENEMY_REWARD = 100;
	public static final double SON_GOKU_BOSS_ENEMY_WIDTH = 54.0;
	public static final double SON_GOKU_BOSS_ENEMY_HEIGHT = 64.0;
	public static final int SON_GOKU_BOSS_ENEMY_GID = 156;
	public static final String SON_GOKU_BOSS_ENEMY_CUT_IN_URI = "/stage/bosscutin/songokucutin.png";

	public static final double ELON_MUSK_BOSS_ENEMY_SIZE = 1.0;
	public static final long ELON_MUSK_BOSS_ENEMY_HEALTH = 1500;
	public static final long ELON_MUSK_BOSS_ENEMY_ARMOR = 8;
	public static final double ELON_MUSK_BOSS_ENEMY_SPEED = 0.07;
	public static final long ELON_MUSK_BOSS_ENEMY_REWARD = 100;
	public static final double ELON_MUSK_BOSS_ENEMY_WIDTH = 42.0;
	public static final double ELON_MUSK_BOSS_ENEMY_HEIGHT = 64.0;
	public static final int ELON_MUSK_BOSS_ENEMY_GID = 179;
	public static final double ELON_MUSK_BOSS_ENEMY_SKILL_MULTIPLY = 2.5;
	public static final long ELON_MUSK_BOSS_ENEMY_SKILL_ACTIVATE_TIME = 75;
	public static final long ELON_MUSK_BOSS_ENEMY_SKILL_DEACTIVATE_TIME = 200;
	public static final String ELON_MUSK_BOSS_ENEMY_CUT_IN_URI = "/stage/bosscutin/elonmuskcutin.png";

	public static final double MEDIC_BOSS_ENEMY_SIZE = 1.0;
	public static final long MEDIC_BOSS_ENEMY_HEALTH = 1500;
	public static final long MEDIC_BOSS_ENEMY_ARMOR = 8;
	public static final double MEDIC_BOSS_ENEMY_SPEED = 0.05;
	public static final long MEDIC_BOSS_ENEMY_REWARD = 100;
	public static final double MEDIC_BOSS_ENEMY_WIDTH = 45.0;
	public static final double MEDIC_BOSS_ENEMY_HEIGHT = 64.0;
	public static final int MEDIC_BOSS_ENEMY_GID = 177;
	public static final int MEDIC_BOSS_ENEMY_UBER_GID = 178;
	public static final long MEDIC_BOSS_ENEMY_SKILL_ACTIVATE_TIME = 125;
	public static final long MEDIC_BOSS_ENEMY_SKILL_DEACTIVATE_TIME = 225;
	public static final String MEDIC_BOSS_ENEMY_CUT_IN_URI = "/stage/bosscutin/mediccutin.png";

	public static final double GRAB_ENEMY_SIZE = 1;
	public static final long GRAB_ENEMY_HEALTH = 20;
	public static final long GRAB_ENEMY_ARMOR = 2;
	public static final double GRAB_ENEMY_SPEED = 0.11;
	public static final long GRAB_ENEMY_REWARD = 1;
	public static final int GRAB_ENEMY_GID = 225;
	public static final double GRAB_ENEMY_WIDTH = 40.0;
	public static final double GRAB_ENEMY_HEIGHT = 18.0;
	public static final long GRAB_ENEMY_DAMAGE = 4;
	//endregion


	//region GID
	public static final int NORMAL_TOWER_DEFAULT_GID = 204;
	public static final int NORMAL_TOWER_BULLET_GID = 273;

	public static final int ROCKET_LAUNCHER_DEFAULT_GID = 205;
	public static final int ROCKET_LAUNCHER_BASE_GID = 228;
	public static final int ROCKET_LAUNCHER_ROCKET_GID = 252;

	public static final int ROCKET_LAUNCHER2_DEFAULT_GID = 206;
	public static final int ROCKET_LAUNCHER2_BASE_GID = 229;
	public static final int ROCKET_LAUNCHER2_ROCKET_GID = 252;

	public static final int ROCKET_LAUNCHER3_DEFAULT_GID = 207;
	public static final int ROCKET_LAUNCHER3_BASE_GID = 230;
	public static final int ROCKET_LAUNCHER3_ROCKET_GID = 253;


	public static final int MACHINE_GUN_TOWER_BULLET1_GID = 273;
	public static final int MACHINE_GUN_TOWER_BULLET2_GID = 274;

	public static final int FIRE1_GID = 296;
	public static final int FIRE2_GID = 297;
	public static final int FIRE3_GID = 298;
	public static final int FIRE4_GID = 299;

	//endregion

	public static final double FIRE1_WIDTH = 12.0;
	public static final double FIRE1_HEIGHT = 24.0;

	public static final double NYAN_CAT_RAINBOW_WIDTH = 30.0;
	public static final double NYAN_CAT_RAINBOW_HEIGHT = 42.0;

	//region star rank
	public static final int TOWER_RANK_1_GID = 36;
	public static final int TOWER_RANK_2_GID = 37;
	public static final int TOWER_RANK_3_GID = 38;
	//endregion

	//region effect
	public static final int EXPLOSION_GID = 23;
	public static final int EXPLOSION_TTL = 6;

	public static final int UPGRADE_GID = 22;
	public static final int UPGRADE_TTL = 10;

	public static final int TOWER_DESTROY_GID = 20;
	public static final int TOWER_DESTROY_TTL = 10;

	public static final int BOSS_CUT_IN_TTL = 50;
	//endregion

	//region spawner target
	public static int SPAWNER_GID = 21;
	public static int TARGET_GID = 46;
	//endregion

	private Config() {
	}


}
