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
     * Player fields
     */
    public static final int OPPONENT_START_X = 12;

    /**
     * Network stuffs.
     */
    public static final String DEFAULT_SERVER_HOST = "localhost";
    public static final int DEFAULT_LISTEN_PORT = 1337;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 2000; // in millisecond.
    public static final int TICK_PER_KEEPALIVE = 30;
}
