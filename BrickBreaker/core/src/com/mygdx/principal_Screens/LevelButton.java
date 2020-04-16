package com.mygdx.principal_Screens;

import com.mygdx.PJs.GameObject;

public class LevelButton extends GameObject {

    public static final float LEVELBUTTON_WIDTH = 150;
    public static final float LEVELBUTTON_HEIGHT = 150;

    public static final float LEVELBUTTON_WIDTH_BIG = 600;
    public static final float LEVELBUTTON_HEIGHT_BIG = 200;

    public LevelButton(float x, float y) {
        super(x, y, LEVELBUTTON_WIDTH, LEVELBUTTON_HEIGHT);

        bounds.x = position.x;
        bounds.y = position.y;
    }

}
