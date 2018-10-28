package com.jvs.gdx.skippy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jvs.gdx.skippy.config.GameConfig;
import com.jvs.gdx.skippy.entity.Flower;
import com.jvs.gdx.skippy.entity.Skippy;


public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    private Skippy skippy;
    private Array<Flower> flowers = new Array<Flower>();

    public GameScreen() {

    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.zoom = 1.30f;
        camera.update();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        shapeRenderer = new ShapeRenderer();

        skippy = new Skippy();
        skippy.setPosition(GameConfig.WORLD_WIDTH / 4f, GameConfig.WORLD_HEIGHT / 2f);

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
        viewport.apply();
        renderDebug();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
            Circle flowerCollisionCircle = flower.getCollisionCircle();
            shapeRenderer.circle(flowerCollisionCircle.x, flowerCollisionCircle.y, flowerCollisionCircle.radius, 30);
            Rectangle flowerCollisionRectangle = flower.getCollisionRectangle();
            shapeRenderer.rect(flowerCollisionRectangle.x, flowerCollisionRectangle.y, flowerCollisionRectangle.width, flowerCollisionRectangle.height);
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
        flower.setPosition(GameConfig.WORLD_WIDTH);
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
}