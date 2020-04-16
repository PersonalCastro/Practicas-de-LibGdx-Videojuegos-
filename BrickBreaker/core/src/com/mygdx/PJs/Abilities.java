package com.mygdx.PJs;

import com.badlogic.gdx.math.MathUtils;

public class Abilities extends DynamicGameObject{

    float stateTime;
    public static final float  ABILITIES_MOVE_VELOCITY = 300;
    public static final float  ABILITIES_MOVE_PERSECUTION = 100;
    public static float ABILITIES_WIDTH = 30;
    public static final float ABILITIES_HEIGHT = 30;

    public int tipoAbilidad = 0;

    public Abilities(float x, float y) {
        super(x, y, ABILITIES_WIDTH, ABILITIES_HEIGHT);
        stateTime = 0;
        tipoAbilidad = MathUtils.random(0,2);
    }

    public void update (float deltaTime) {

        position.add(velocity.x * deltaTime, ABILITIES_MOVE_VELOCITY * deltaTime * -1);
        bounds.x = position.x;
        bounds.y = position.y;

        stateTime += deltaTime;

    }






}
