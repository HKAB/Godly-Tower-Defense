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
     * Size of the screen.
     */
    public static final long SCREEN_WIDTH = TILE_SIZE * TILE_HORIZONTAL;
    /**
     * Size of the screen.
     */
    public static final long SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;

    public static final double OPPONENT_START_PX_X = TILE_SIZE * 10;
    public static final int OPPONENT_START_X = 10;

    /**
     * Network stuffs.
     */
    public static final int LISTEN_PORT = 1337;
    public static final String DEFAULT_SERVER_HOST = "localhost";
}
