package com.mygdx.PJs;

public class PongEnemy extends DynamicGameObject{

    public static final float PONGENEMY_MOVE_VELOCITY = 400;
    public static float PONGENEMY_WIDTH = 180;
    public static final float PONGENEMY_HEIGHT = 40;

    public int lives = 3;
    public boolean perseguir = false;

    float stateTime;

    public PongEnemy (float x, float y) {
        super(x, y, PONGENEMY_WIDTH, PONGENEMY_HEIGHT);
        stateTime = 0;
    }

    public void update (float deltaTime) {

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x;
        bounds.y = position.y;


        if (position.x < 0){
            position.x = 0;
        }
        if (position.x > 1080 - (PONGENEMY_WIDTH)){
            position.x = 1080 - (PONGENEMY_WIDTH);
        }

        stateTime += deltaTime;


    }

}
