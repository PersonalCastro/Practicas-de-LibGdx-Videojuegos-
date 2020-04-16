package com.mygdx.PJs;


public class Bar extends DynamicGameObject{


    public static final float BAR_MOVE_VELOCITY = 3000;
    public static float BAR_WIDTH = 300;
    public static final float BAR_HEIGHT = 50;

    float stateTime;
    public int lives = 3;

    public Bar (float x, float y) {
        super(x, y, BAR_WIDTH, BAR_HEIGHT);
        stateTime = 0;
    }

    public void update (float deltaTime) {

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x;
        bounds.y = position.y;


        if (position.x < 0){
            position.x = 0;
        }
        if (position.x > 1080 - (Bar.BAR_WIDTH)){
            position.x = 1080 - (Bar.BAR_WIDTH);
        }

        stateTime += deltaTime;


    }





}
