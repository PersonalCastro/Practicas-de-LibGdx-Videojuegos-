package com.mygdx.PJs;

public class Brick extends GameObject{

    public static float BRICK_WIDTH = 100;
    public static final float BRICK_HEIGHT = 50;

    float stateTime;

    public Brick (float x, float y) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        stateTime = 0;
    }

    public void update () {
        bounds.x = position.x;
        bounds.y = position.y;

    }

}
