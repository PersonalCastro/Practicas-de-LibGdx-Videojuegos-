package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {

    public static boolean soundEnabled = true;
    public final static String file = ".superjumper";


    public static void load() {

        try {
            FileHandle filehandle = Gdx.files.external(file);
            String[] strings = filehandle.readString().split("\n");

            soundEnabled = Boolean.parseBoolean(strings[0]);


        } catch (Throwable e) {
            // :( It's ok we have defaults
        }

    }

    public static void save() {
    }
}
