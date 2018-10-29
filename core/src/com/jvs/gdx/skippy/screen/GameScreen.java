package com.jvs.gdx.skippy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jvs.gdx.skippy.SkippyFlowersGame;
import com.jvs.gdx.skippy.assets.AssetDescriptors;
import com.jvs.gdx.skippy.common.ScoreController;
import com.jvs.gdx.skippy.config.GameConfig;
import com.jvs.gdx.skippy.entity.Flower;
import com.jvs.gdx.skippy.entity.Skippy;


public class GameScreen implements Screen {

    private final SkippyFlowersGame skippyFlowersGame;
    private final ScoreController scoreController;
    private final AssetManager assetManager;
    private final SpriteBatch spriteBatch;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private Viewport hudViewport;

    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();

    private Skippy skippy;
    private Array<Flower> flowers = new Array<Flower>();

    private float skippyStartX;
    private float skippyStartY;

    public GameScreen(SkippyFlowersGame skippyFlowersGame) {
        this.skippyFlowersGame = skippyFlowersGame;
        this.scoreController = skippyFlowersGame.getScoreController();
        this.assetManager = skippyFlowersGame.getAssetManager();
        this.spriteBatch = skippyFlowersGame.getSpriteBatch();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        //camera.zoom = 1.30f;
        //camera.update();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);

        font = assetManager.get(AssetDescriptors.SCORE_FONT);

        skippyStartX = GameConfig.WORLD_WIDTH / 4f;
        skippyStartY = GameConfig.WORLD_HEIGHT / 2f;

        skippy = new Skippy();
        skippy.setPosition(skippyStartX, skippyStartY);

        createNewFlower();
    }

    @Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update game world
        update(delta);

        // drawing
        hudViewport.apply();
        renderHud();

        viewport.apply();
        renderDebug();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        shapeRenderer.dispose();
    }

    private void renderDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        shapeRenderer.end();
    }

    private void drawDebug() {
        // skippy
        Circle skippyCollisionCircle = skippy.getCollisionCircle();
        shapeRenderer.circle(skippyCollisionCircle.x, skippyCollisionCircle.y, skippyCollisionCircle.radius, 30);

        // flowers
        for (Flower flower : flowers) {
            // bottom
            Circle bottomCollisionCircle = flower.getBottomCollisionCircle();
            shapeRenderer.circle(bottomCollisionCircle.x, bottomCollisionCircle.y, bottomCollisionCircle.radius, 30);
            Rectangle bottomCollisionRectangle = flower.getBottomCollisionRectangle();
            shapeRenderer.rect(bottomCollisionRectangle.x, bottomCollisionRectangle.y, bottomCollisionRectangle.width, bottomCollisionRectangle.height);

            // top
            Circle topCollisionCircle = flower.getTopCollisionCircle();
            shapeRenderer.circle(topCollisionCircle.x, topCollisionCircle.y, topCollisionCircle.radius, 30);
            Rectangle topCollisionRectangle = flower.getTopCollisionRectangle();
            shapeRenderer.rect(topCollisionRectangle.x, topCollisionRectangle.y, topCollisionRectangle.width, topCollisionRectangle.height);

            // sensor
            Rectangle sensorRectangle = flower.getSensorRectangle();
            shapeRenderer.rect(sensorRectangle.x, sensorRectangle.y, sensorRectangle.width, sensorRectangle.height);
        }
    }

    private void update(float delta) {
        skippy.update(delta);

        if (Gdx.input.justTouched()) {
            skippy.flyUp();
        }

        blockSkippyFromLeavingWorld();

        for (Flower flower : flowers) {
            flower.update(delta);
        }

        spawnFlower();
        removePassedFlowers();

        checkCollision();

        updateScore();
    }

    private void spawnFlower() {
        if (flowers.size == 0) {
            createNewFlower();
        } else {
            Flower lastFlower = flowers.peek();
            if (lastFlower.getX() < GameConfig.WORLD_WIDTH - GameConfig.GAP_BETWEEN_FLOWERS) {
                createNewFlower();
            }
        }
    }

    private void blockSkippyFromLeavingWorld() {
        float newY = MathUtils.clamp(skippy.getY(), 0 + GameConfig.SKIPPY_HALF_SIZE, GameConfig.WORLD_HEIGHT - +GameConfig.SKIPPY_HALF_SIZE);
        skippy.setY(newY);
    }

    private void createNewFlower() {
        Flower flower = new Flower();
        flower.setPosition(GameConfig.WORLD_WIDTH + Flower.WIDTH);
        flowers.add(flower);
    }

    private void removePassedFlowers() {
        if (flowers.size > 0) {
            Flower firstFlower = flowers.first();
            if (firstFlower.getX() < Flower.WIDTH) {
                flowers.removeValue(firstFlower, true);
            }
        }
    }

    private void checkCollision(){
        for (Flower flower : flowers){
            if(flower.isSkippyColliding(skippy)){
                restart();
            }
        }
    }

    private void updateScore(){
        if(flowers.size > 0) {
            Flower flower = flowers.first();
            if(!flower.isScoreCollected() && flower.isSkippyCollidingWithSensor(skippy)){
                flower.collectScore();
                scoreController.incrementScore();
            }
        }
    }

    private void restart(){
        skippy.setPosition(skippyStartX, skippyStartY);
        flowers.clear();
        scoreController.resetScore();
    }

    private void renderHud(){
        spriteBatch.setProjectionMatrix(hudViewport.getCamera().combined);
        spriteBatch.begin();

        drawHud();

        spriteBatch.end();
    }

    private void drawHud(){
        String scoreString = scoreController.getScoreString();
        layout.setText(font, scoreString);

        float scoreX = (GameConfig.HUD_WIDTH - layout.width) / 2f;
        float scoreY = 4 * GameConfig.HUD_HEIGHT / 5 - layout.height / 2f;

        font.draw(spriteBatch, scoreString, scoreX, scoreY);
    }
}
