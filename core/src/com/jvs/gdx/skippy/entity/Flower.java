package com.jvs.gdx.skippy.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.jvs.gdx.skippy.config.GameConfig;

public class Flower {

    private static final float COLLISION_RECT_WIDTH = 0.5f;     // world units
    private static final float COLLISION_RECT_HALF_WIDTH = COLLISION_RECT_WIDTH / 2f;  // world units
    private static final float COLLISION_RECT_HEIGHT = 25f;     // world units
    private static final float COLLISION_CIRCLE_RADIUS = 1.5f;     // world units
    private static final float SENSOR_RECT_WIDTH = 0.05f; // world units

    private static final float MAX_SPEED = 8f;     // world units
    private static final float HEIGHT_OFFSET = -20f;  // world units
    private static final float FLOWER_GAP = 12f;  // world units

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;  // world units
    public static final float HEIGHT = COLLISION_RECT_HEIGHT;  // world units


    private final Circle bottomCollisionCircle;
    private final Rectangle bottomCollisionRectangle;

    private final Circle topCollisionCircle;
    private final Rectangle topCollisionRectangle;

    private final Rectangle sensorRectangle;

    private float x;
    private float y;

    private boolean scoreCollected;

    public Flower() {
        y = MathUtils.random(HEIGHT_OFFSET);

        // bottom
        bottomCollisionRectangle = new Rectangle(x, y, COLLISION_RECT_WIDTH, COLLISION_RECT_HEIGHT);
        bottomCollisionCircle = new Circle(
                x + COLLISION_RECT_HALF_WIDTH,
                y + COLLISION_RECT_HEIGHT,
                COLLISION_CIRCLE_RADIUS
        );

        // top
        float topY = bottomCollisionCircle.y + FLOWER_GAP;
        topCollisionRectangle = new Rectangle(x, topY, COLLISION_RECT_WIDTH, COLLISION_RECT_HEIGHT);
        topCollisionCircle = new Circle(
                x + COLLISION_RECT_HALF_WIDTH,
                topY,
                COLLISION_CIRCLE_RADIUS
        );

        // sensor
        sensorRectangle = new Rectangle(x + COLLISION_RECT_HALF_WIDTH, 0, SENSOR_RECT_WIDTH, GameConfig.WORLD_HEIGHT);
    }

    public void update(float delta) {
        float xSpeed = MAX_SPEED * delta;
        setX(x - xSpeed);
    }

    public void setPosition(float x) {
        setX(x);
    }

    public void setX(float x) {
        this.x = x;
        updateCollisionCircle();
        updateCollisionRectangle();
        updateSensorRectangle();
    }

    public Circle getBottomCollisionCircle() {
        return bottomCollisionCircle;
    }

    public Rectangle getBottomCollisionRectangle() {
        return bottomCollisionRectangle;
    }

    public Circle getTopCollisionCircle() {
        return topCollisionCircle;
    }

    public Rectangle getTopCollisionRectangle() {
        return topCollisionRectangle;
    }

    public Rectangle getSensorRectangle() {
        return sensorRectangle;
    }

    public float getX() {
        return x;
    }

    public boolean isSkippyColliding(Skippy skippy) {
        return overlapsTopFlower(skippy) || overlapsBottomFlower(skippy);
    }

    public boolean isSkippyCollidingWithSensor(Skippy skippy) {
        return Intersector.overlaps(skippy.getCollisionCircle(), sensorRectangle);
    }

    public void collectScore() {
        scoreCollected = true;
    }

    public boolean isScoreCollected() {
        return scoreCollected;
    }

    private void updateCollisionCircle() {
        float newX = x + COLLISION_RECT_HALF_WIDTH;
        bottomCollisionCircle.setX(newX);
        topCollisionCircle.setX(newX);
    }

    private void updateCollisionRectangle() {
        bottomCollisionRectangle.setX(x);
        topCollisionRectangle.setX(x);
    }

    private void updateSensorRectangle() {
        float newX = x + COLLISION_RECT_HALF_WIDTH;
        sensorRectangle.setX(newX);
    }

    private boolean overlapsTopFlower(Skippy skippy) {
        Circle skippyCollisionCircle = skippy.getCollisionCircle();
        return Intersector.overlaps(skippyCollisionCircle, topCollisionCircle) ||
                Intersector.overlaps(skippyCollisionCircle, topCollisionRectangle);
    }

    private boolean overlapsBottomFlower(Skippy skippy) {
        Circle skippyCollisionCircle = skippy.getCollisionCircle();
        return Intersector.overlaps(skippyCollisionCircle, bottomCollisionCircle) ||
                Intersector.overlaps(skippyCollisionCircle, bottomCollisionRectangle);
    }
}
