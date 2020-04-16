/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {
        final MyGdxGame game;
	OrthographicCamera camera;
        Texture background;
        Music startMusic;



	public MainMenuScreen(final MyGdxGame gam) {
            game = gam;

            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800, 480);
            background = new Texture(Gdx.files.internal("fondo_principal.jpg"));

            startMusic = Gdx.audio.newMusic(Gdx.files.internal("musica_inicio.mp3"));
            startMusic.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
                game.batch.draw(background,0,0);
		game.font.draw(game.batch, "Welcome to FlapyTurtle!!! ", 800/2 - 80, 480 -100);
		game.font.draw(game.batch, "Tap SPACE to begin!", 800/2 - 70, 480 -200);
		game.batch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
            startMusic.dispose();
	}
}
