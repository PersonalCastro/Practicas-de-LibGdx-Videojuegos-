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

public class LoseScreen implements Screen {
        final MyGdxGame game;
	OrthographicCamera camera;
        String finalScore;
        Music deathMusic;
        Texture background;
        boolean highscore_superado;
        model_points puntos_model;
        
        String score_str;
        
        

	public LoseScreen(final MyGdxGame gam, int score) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

                background = new Texture(Gdx.files.internal("fondo_final.jpg"));
                deathMusic = Gdx.audio.newMusic(Gdx.files.internal("musica_final.mp3"));
		deathMusic.setLooping(false);
                deathMusic.play();

                puntos_model = new model_points();

                highscore_superado = false;
                int puntuacion = puntos_model.getDataFromDb();
                
                if (puntuacion < score){
                    puntos_model.addDataToDb(score);
                    finalScore = "HIGH Score: " + score;
                    highscore_superado = false;
                        
                }else{
                    finalScore = "HIGH Score: " + puntuacion;
                }
                score_str = new String();
                score_str = String.valueOf(score);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

                
		game.batch.begin();
                game.batch.draw(background,0,0);
                
		game.font.draw(game.batch, finalScore, 800 / 2 - 50, 480 / 2 + 80);
                
                if(!highscore_superado){
                    game.font.draw(game.batch, "Your Score: " + score_str, 800 / 2 - 50, 480 / 2 + 120);
                }else{
                    game.font.draw(game.batch, "You Break the High Score: ", 800 / 2 - 80, 480 / 2 + 120);
                }
                
                
		game.font.draw(game.batch, "Tap SPACE to play again", 800 / 2 - 80, 480 / 2 - 52);
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
            deathMusic.dispose();

	}
}
