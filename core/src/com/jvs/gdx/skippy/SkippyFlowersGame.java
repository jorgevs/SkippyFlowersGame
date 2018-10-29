package com.jvs.gdx.skippy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jvs.gdx.skippy.assets.AssetDescriptors;
import com.jvs.gdx.skippy.common.ScoreController;
import com.jvs.gdx.skippy.screen.GameScreen;

public class SkippyFlowersGame extends Game {

    private ScoreController scoreController;

    private AssetManager assetManager;
    private SpriteBatch spriteBatch;

    @Override
    public void create() {
        scoreController = new ScoreController();
        assetManager = new AssetManager();
        spriteBatch = new SpriteBatch();

        assetManager.load(AssetDescriptors.SCORE_FONT);

        // blocks until all assets are loaded
        assetManager.finishLoading();

        setScreen(new GameScreen(this));
    }

    public ScoreController getScoreController() {
        return scoreController;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        spriteBatch.dispose();
    }
}
