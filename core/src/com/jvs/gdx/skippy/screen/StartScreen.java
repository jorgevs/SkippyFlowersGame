package com.jvs.gdx.skippy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jvs.gdx.skippy.SkippyFlowersGame;
import com.jvs.gdx.skippy.assets.AssetDescriptors;
import com.jvs.gdx.skippy.assets.RegionNames;
import com.jvs.gdx.skippy.common.ScoreController;
import com.jvs.gdx.skippy.config.GameConfig;

public class StartScreen implements Screen {

    private final SkippyFlowersGame skippyFlowersGame;
    private final AssetManager assetManager;
    private final SpriteBatch spriteBatch;

    private final ScoreController scoreController;

    private Stage stage;
    private Viewport hudViewport;

    public StartScreen(SkippyFlowersGame skippyFlowersGame) {
        this.skippyFlowersGame = skippyFlowersGame;
        this.assetManager = skippyFlowersGame.getAssetManager();
        this.spriteBatch = skippyFlowersGame.getSpriteBatch();

        this.scoreController = skippyFlowersGame.getScoreController();
    }

    @Override
    public void show() {
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(hudViewport, spriteBatch);

        initUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void initUI() {
        TextureAtlas textureAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        // background
        Image image = new Image(textureAtlas.findRegion(RegionNames.BACKGROUND));
        image.setFillParent(true);

        stage.addActor(image);

        // play button
        TextureRegion playRegion = textureAtlas.findRegion(RegionNames.PLAY);

        final ImageButton play = new ImageButton(new TextureRegionDrawable(playRegion));
        play.setPosition(GameConfig.HUD_WIDTH / 2f, GameConfig.HUD_HEIGHT / 4f, Align.center);

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        stage.addActor(play);

        // high score
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = assetManager.get(AssetDescriptors.SCORE_FONT);
        labelStyle.fontColor = Color.WHITE;

        String scoreString = "BEST: " + scoreController.getHighScoreString();
        Label bestScore = new Label(scoreString, labelStyle);
        bestScore.setPosition(GameConfig.HUD_WIDTH / 2f, 3 * GameConfig.HUD_HEIGHT / 4f, Align.center);

        stage.addActor(bestScore);
    }

    private void play() {
        skippyFlowersGame.setScreen(new GameScreen(skippyFlowersGame));
    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        hudViewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
