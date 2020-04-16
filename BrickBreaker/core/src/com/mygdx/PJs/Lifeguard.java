package com.mygdx.PJs;

public class Lifeguard extends GameObject{

    public static float  LIFEGUARD_WIDTH = 1080;
    public static final float LIFEGUARD_HEIGHT = 20;

    float stateTime;

    public Lifeguard(float x, float y) {
        super(x, y, LIFEGUARD_WIDTH, LIFEGUARD_HEIGHT);
        stateTime = 0;
    }

    public void update () {
        bounds.x = position.x;
        bounds.y = position.y;

    }

}
