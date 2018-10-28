package com.jvs.gdx.skippy.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Flower {

    private static final float COLLISION_RECT_WIDTH = 0.5f;     // world units
    private static final float COLLISION_RECT_HALF_WIDTH = COLLISION_RECT_WIDTH / 2f;  // world units
    private static final float COLLISION_RECT_HEIGHT = 20f;     // world units
    private static final float COLLISION_CIRCLE_RADIUS = 1.5f;     // world units

    private static final float MAX_SPEED = 8f;     // world units

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;  // world units

    private final Circle collisionCircle;
    private final Rectangle collisionRectangle;

    private float x;
    private float y;

    public Flower() {
        collisionRectangle = new Rectangle(x, y, COLLISION_RECT_WIDTH, COLLISION_RECT_HEIGHT);
        collisionCircle = new Circle(
                x + COLLISION_RECT_HALF_WIDTH,
                y + COLLISION_RECT_HEIGHT,
                COLLISION_CIRCLE_RADIUS
        );
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
    }

    private void updateCollisionCircle() {
        collisionCircle.setX(x + COLLISION_RECT_HALF_WIDTH);
        collisionCircle.setY(y + COLLISION_RECT_HEIGHT);
    }

    private void updateCollisionRectangle() {
        collisionRectangle.setX(x);
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public float getX() {
        return x;
    }
}
