/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
  	final MyGdxGame game;

	Texture corales_1;
        Texture corales_2;
	Texture tortuga;
        Texture backgruondImage;
	Sound dropSound;
	Music backgroudMusic;
	OrthographicCamera camera;
	Rectangle tortuga_rectangle;
	Array<Rectangle> corales_1_Array;
        Array<Rectangle> corales_2_Array;

	long lastCoralPair_time;
	int dropsGathered;
        
        
        float yVelocity = 0;
        final float GRAVITY = -18f;

	public GameScreen(final MyGdxGame gam) {
            	this.game = gam;
                
		// load the images for the droplet and the bucket, 64x64 pixels each
                backgruondImage = new Texture(Gdx.files.internal("fondo_marino.jpg"));
		corales_1 = new Texture(Gdx.files.internal("tuberiash.png"));
		corales_2 = new Texture(Gdx.files.internal("tuberiash2.png"));
		tortuga = new Texture(Gdx.files.internal("tortuga.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		backgroudMusic = Gdx.audio.newMusic(Gdx.files.internal("musica_fondo.mp3"));
		backgroudMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the bucket
		tortuga_rectangle = new Rectangle();
		tortuga_rectangle.y = 480 / 2 - 64 / 2;
		tortuga_rectangle.x = 25;
						// the bottom screen edge
		tortuga_rectangle.width = 44;
		tortuga_rectangle.height = 44;

		// create the raindrops array and spawn the first raindrop
		corales_1_Array = new Array<Rectangle>();
                corales_2_Array = new Array<Rectangle>();

		spawnCoralPair();

	}



	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the turtle and
		// all corals
		game.batch.begin();
                game.batch.draw(backgruondImage,0,0);
		game.font.draw(game.batch, "Score: " + dropsGathered, 0, 480);
		game.batch.draw(tortuga, tortuga_rectangle.x, tortuga_rectangle.y);
		for (Rectangle raindrop : corales_1_Array) {
			game.batch.draw(corales_2, raindrop.x, raindrop.y);
                        //game.batch.draw(dropImage, raindrop.x, raindrop.y);

		}
                
                for (Rectangle raindrop : corales_2_Array) {
			//game.batch.draw(dropImage2, raindrop.x, raindrop.y);
                        game.batch.draw(corales_1, raindrop.x, raindrop.y);

		}
                
                turtleAnimation(yVelocity);

                
		game.batch.end();

		// process user input
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			tortuga_rectangle.y = touchPos.y - 64 / 2;
                        yVelocity = 0;
		}
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
			tortuga_rectangle.y += 200 * Gdx.graphics.getDeltaTime();
                        yVelocity = 420;
                }

                
                yVelocity = yVelocity + GRAVITY;
                float y = tortuga_rectangle.getY();
                
                float yChange = yVelocity * delta;
                tortuga_rectangle.setPosition(0, y + yChange);

		// make sure the bucket stays within the screen bounds
		if (tortuga_rectangle.y < 0)
			tortuga_rectangle.y = 0;
		if (tortuga_rectangle.y > 480 - 54)
			tortuga_rectangle.y = 480 - 54;

		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - lastCoralPair_time > 1700000000)
			spawnCoralPair();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = corales_1_Array.iterator();
                Iterator<Rectangle> iter2 = corales_2_Array.iterator();

		while (iter.hasNext()) {
			Rectangle coral_1_rectangle = iter.next();
			coral_1_rectangle.x -= 250 * Gdx.graphics.getDeltaTime();
			if (coral_1_rectangle.x + 100 < 0){
                            iter.remove();
                            dropsGathered++;
                        }
                        
                        if (coral_1_rectangle.x == 120){
                        }
                        
			if (coral_1_rectangle.overlaps(tortuga_rectangle)) {
                                game.setScreen(new LoseScreen(game, dropsGathered));
                                dispose();
                        }
                        
		}
                
                
                
                while (iter2.hasNext()) {
			Rectangle coral_2_rectangle = iter2.next();
			coral_2_rectangle.x -= 250 * Gdx.graphics.getDeltaTime();
			if (coral_2_rectangle.x + 100 < 0){
                            iter2.remove();
                        }
                        
			if (coral_2_rectangle.overlaps(tortuga_rectangle)) {
                                game.setScreen(new LoseScreen(game, dropsGathered));
                                dispose();
			}
                        
		}
                
	}
        
    private void spawnCoralPair() {
            Rectangle coral_1_rectangle = new Rectangle();
            coral_1_rectangle.x = 900;
            coral_1_rectangle.y = MathUtils.random(180, 480);
            coral_1_rectangle.width = 100;
            coral_1_rectangle.height = 440;
            corales_1_Array.add(coral_1_rectangle);


            Rectangle coral_2_rectangle = new Rectangle();
            coral_2_rectangle.x = 900;
            coral_2_rectangle.y = coral_1_rectangle.y - MathUtils.random(620, 650);
            coral_2_rectangle.width = 100;
            coral_2_rectangle.height = 480;
            corales_2_Array.add(coral_2_rectangle);

            lastCoralPair_time = TimeUtils.nanoTime();
    }        
    
    private void turtleAnimation(float yVelocity) {
        if(yVelocity > 20){

            if(yVelocity > 330){
                tortuga = new Texture(Gdx.files.internal("tortuga1.5.png"));
            }else if (yVelocity > 50){
                tortuga = new Texture(Gdx.files.internal("tortuga1.png"));
            }else{
                tortuga = new Texture(Gdx.files.internal("tortuga1.5.png"));
            }

        }else if(yVelocity < -20){
            if(yVelocity < -100){
                tortuga = new Texture(Gdx.files.internal("tortuga2.png"));
            }else{
                tortuga = new Texture(Gdx.files.internal("tortuga2.5.png"));
            }
        }else{
            tortuga = new Texture(Gdx.files.internal("tortuga.png"));
        }
    }

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		backgroudMusic.play();
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
		corales_1.dispose();
		tortuga.dispose();
		dropSound.dispose();
		backgroudMusic.dispose();
	}



}
