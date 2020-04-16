package com.mygdx.principal_Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.PJs.Ball;
import com.mygdx.game.Assets;
import com.mygdx.game.BB_Game;
import com.mygdx.game.Settings;

import java.util.ArrayList;

public class FinalScreen extends ScreenAdapter {

    BB_Game game;
    OrthographicCamera guiCam;

    private String levelName;
    private int bricksBreaked;
    private int totalStars;
    private boolean next_lvl_enabled;

    private ArrayList<LevelButton> buttons;

    private Vector3 touchPoint;



    public FinalScreen (BB_Game game, String levelName, int bricksBreaked) {
        this.game = game;

        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 1080, 1920);

        this.levelName = levelName;
        this.bricksBreaked = bricksBreaked;
        this.buttons = new ArrayList();
        this.touchPoint = new Vector3();


        calculateStars();


        this.buttons.add(new LevelButton(((1080/4) - LevelButton.LEVELBUTTON_WIDTH/2), 400));
        this.buttons.add(new LevelButton(((1080/2) - LevelButton.LEVELBUTTON_WIDTH/2), 400));
        this.buttons.add(new LevelButton(((1080/2) + (1080/4) - LevelButton.LEVELBUTTON_WIDTH/2), 400));

        if(totalStars >= 2 && (levelName == "LEVEL 1")){
            next_lvl_enabled = true;
        }else{
            next_lvl_enabled = false;
        }
        System.out.println(": " + totalStars + ": "+ next_lvl_enabled + ": "+ levelName);

    }

    private void calculateStars() {

        System.out.println(":" + bricksBreaked);

        if(levelName == "LEVEL 1"){
            //48
            if(bricksBreaked == 0){
                this.totalStars = 3;
            }else if(bricksBreaked <= 15){
                this.totalStars = 2;
            }else if(bricksBreaked <= 25){
                this.totalStars = 1;
            }else{
                this.totalStars = 0;
            }

        }else if (levelName == "LEVEL 2"){
            //24
            if(bricksBreaked == 0){
                this.totalStars = 3;
            }else if(bricksBreaked <= 8){
                this.totalStars = 2;
            }else if(bricksBreaked <= 14){
                this.totalStars = 1;
            }else{
                this.totalStars = 0;
            }
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 0.3f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        game.batch.disableBlending();

        game.batch.begin();

        game.batch.enableBlending();

        game.batch.draw(Assets.score, (1080/2) - 800/2, 1350);

        if(totalStars == 3){
            game.batch.draw(Assets.star, (1080/2)/1.5f - 150/2, 900);
            game.batch.draw(Assets.star, (1080/2)/1f - 150/2, 900);
            game.batch.draw(Assets.star, (1080/2)*1.33f - 150/2, 900);
        }else if(totalStars == 2){
            game.batch.draw(Assets.star, (1080/2)/1.5f - 150/2, 900);
            game.batch.draw(Assets.star, (1080/2)/1f - 150/2, 900);
            game.batch.draw(Assets.star_lost, (1080/2)*1.33f - 150/2, 900);
        }else if(totalStars == 1){
            game.batch.draw(Assets.star, (1080/2)/1.5f - 150/2, 900);
            game.batch.draw(Assets.star_lost, (1080/2)/1f - 150/2, 900);
            game.batch.draw(Assets.star_lost, (1080/2)*1.33f - 150/2, 900);
        }else if(totalStars == 0){
            game.batch.draw(Assets.star_lost, (1080/2)/1.5f - 150/2, 900);
            game.batch.draw(Assets.star_lost, (1080/2)/1f - 150/2, 900);
            game.batch.draw(Assets.star_lost, (1080/2)*1.33f - 150/2, 900);
        }

        buttonsDraw();

        game.batch.end();
    }

    private void buttonsDraw() {

        game.batch.draw(Assets.button_restart, this.buttons.get(0).position.x, this.buttons.get(0).position.y);
        game.batch.draw(Assets.button_home, this.buttons.get(1).position.x, this.buttons.get(1).position.y);

        if(next_lvl_enabled){
            game.batch.draw(Assets.button_next_lvl, this.buttons.get(2).position.x, this.buttons.get(1).position.y);
        }else{
            game.batch.draw(Assets.button_next_lvl_lock, this.buttons.get(2).position.x, this.buttons.get(1).position.y);
        }

    }

    @Override
    public void render (float delta) {
        // should work also with Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
        draw();
        act();

    }

    private void act() {
        buttonsAct();

    }

    private void buttonsAct() {

        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (this.buttons.get(0).bounds.contains(touchPoint.x, touchPoint.y)){

                if(levelName == "LEVEL 1"){
                    game.setScreen(new Screen_lvl1(game));
                    Assets.musica_ingame.stop();
                    dispose();
                }else if(levelName == "LEVEL 2"){
                    game.setScreen(new Screen_lvl2(game));
                    Assets.musica_ingame.stop();
                    dispose();
                }
            }else if (this.buttons.get(1).bounds.contains(touchPoint.x, touchPoint.y)){
                game.setScreen(new MainMenuScreen(game));
                Assets.musica_ingame.stop();
                Assets.musica_pantallaInicial.setVolume(0.9f);

                dispose();
            }else if(this.buttons.get(2).bounds.contains(touchPoint.x, touchPoint.y)){
                if(next_lvl_enabled){
                    if(levelName == "LEVEL 1"){
                        game.setScreen(new Screen_lvl2(game));
                        Assets.musica_ingame.stop();
                        dispose();
                    }
                }
            }

        }
    }

    @Override
    public void pause () {
        Settings.save();
        Assets.musica_pantallaInicial.dispose();
    }

}
