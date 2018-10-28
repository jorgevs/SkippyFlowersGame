package com.jvs.gdx.skippy.entity;

import com.badlogic.gdx.math.Circle;
import com.jvs.gdx.skippy.config.GameConfig;

public class Skippy {
    private final Circle collisionCircle;

    private float x;
    private float y;

    private float ySpeed;

    public Skippy() {
        collisionCircle = new Circle(x, y, GameConfig.SKIPPY_HALF_SIZE);
    }

    public void update(float delta) {
        ySpeed -= GameConfig.DIVE_ACC * delta;
        setY(y + ySpeed);
    }

    public void flyUp() {
        ySpeed = GameConfig.FLY_ACC;
        setY(y + ySpeed);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateCollisionCircle();
    }

    private void updateCollisionCircle() {
        this.collisionCircle.setX(x);
        this.collisionCircle.setY(y);
    }

    public Circle getCollisionCircle() {
        return this.collisionCircle;
    }

    public void setY(float y) {
        this.y = y;
        updateCollisionCircle();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
