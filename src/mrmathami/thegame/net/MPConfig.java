package mrmathami.thegame.net;

import mrmathami.thegame.Config;

public class MPConfig {
    /**
     * size of the tile, in pixel.
     * 1.0 field unit == tile_size pixel on the screen.
     * change it base on your texture size.
     */
    public static final long TILE_SIZE = Config.TILE_SIZE;

    /**
     * Num of tiles the screen can display if fieldZoom is TILE_SIZE,
     * in other words, the texture will be display as it without scaling.
     */
    public static final long TILE_HORIZONTAL = 8;

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
     * Player fields
     */
    public static final int OPPONENT_START_X = 12;
    public static final double OPPONENT_START_PX_X = TILE_SIZE * OPPONENT_START_X;
    public static final double FIELD_WIDTH_PX = TILE_HORIZONTAL * TILE_SIZE;
    public static final double FIELD_HEIGHT_PX = TILE_VERTICAL * TILE_SIZE;

    /**
     * Network stuffs.
     */
    public static final String DEFAULT_SERVER_HOST = "localhost";
    public static final int DEFAULT_LISTEN_PORT = 1337;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 2000; // in millisecond.
    public static final int TICK_PER_KEEPALIVE = 30;

    /**
     * UI Context size and position
     */
    public static final double UI_CONTEXT_POS_X = 8;
    public static final double UI_CONTEXT_POS_Y = 3.5;
    public static final double UI_CONTEXT_WIDTH = 4;
    public static final double UI_CONTEXT_HEIGHT = 4;
}
