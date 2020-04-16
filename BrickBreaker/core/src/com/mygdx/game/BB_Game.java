package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.principal_Screens.MainMenuScreen;

public class BB_Game extends Game {
	public SpriteBatch batch;
	public BitmapFont font;


	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(/*Gdx.files.internal("font_juego.fnt")   Te recuerdo que se pueden cambiar las fuentes estas feas*/);
		Settings.load();
		Assets.load();
		setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}

}
