package com.mygdx.principal_Screens;

import com.mygdx.PJs.GameObject;

public class MainScreenButton extends GameObject {
    public static final float LEVELBUTTON_WIDTH_BIG = 600;
    public static final float LEVELBUTTON_HEIGHT_BIG = 200;

    public MainScreenButton(float x, float y) {
        super(x, y, LEVELBUTTON_WIDTH_BIG, LEVELBUTTON_HEIGHT_BIG);

        bounds.x = position.x;
        bounds.y = position.y;
    }
}
