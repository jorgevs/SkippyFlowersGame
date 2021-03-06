package com.jvs.gdx.skippy.config;

public class GameConfig {

    public static final float WIDTH = 600f;  // pixels
    public static final float HEIGHT = 800f; // pixels

    public static final float WORLD_WIDTH = 30f;   // world units
    public static final float WORLD_HEIGHT = 40f;  // world units

    public static final float HUD_WIDTH = 600f;   // world units
    public static final float HUD_HEIGHT = 800f;  // world units

    public static final float SKIPPY_SIZE = 3f;  // world units
    public static final float SKIPPY_HALF_SIZE = SKIPPY_SIZE / 2;  // world units

    public static final float FLY_ACC = 0.3f;   // world units
    public static final float DIVE_ACC = 0.5f;  // world units

    public static final float GAP_BETWEEN_FLOWERS = 9f;  // world units

    public static final float GROUND_HEIGHT = 5f;  // world units

    private GameConfig() {
    }

}
