/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.principal_Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.PJs.Abilities;
import com.mygdx.PJs.Ball;
import com.mygdx.PJs.Bar;
import com.mygdx.PJs.Brick;
import com.mygdx.PJs.Lifeguard;
import com.mygdx.PJs.PongEnemy;
import com.mygdx.game.Assets;
import com.mygdx.game.BB_Game;
import com.mygdx.game.Settings;

import java.util.ArrayList;

/**
 *
 * @author PersonalCastro
 */
public class Screen_lvl1 extends ScreenAdapter {

    BB_Game game;
    OrthographicCamera guiCam;


    public Bar bar;
    public Ball pelota;
    public ArrayList<Brick> bricks;

    public ArrayList<Abilities> abilities;
    public ArrayList<PongEnemy> enemy;
    public ArrayList<Lifeguard> lifeguard;

    private int bricksBreaked;

    private float time;
    private float lastDelta;


    private float gyroscopicPosotionState;


    public Screen_lvl1(BB_Game game) {
        this.game = game;
        time = 0;
        lastDelta = 0;
        Assets.musica_pantallaInicial.setVolume(0);


        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 1080, 1920);

        this.bar = new Bar(1080/2 - Bar.BAR_WIDTH /2, 75);
        this.pelota = new Ball (1080/2 - Ball.BALL_WIDTH /2, 200);

        this.bricks = new ArrayList();

        this.abilities = new ArrayList();
        this.enemy = new ArrayList();
        this.lifeguard = new ArrayList();

        final int ALTO = 100;
        final int ANCHO = 160;

        for(int i = 0; i < 6; i++){

            for(int j = 0; j < 8; j++){

                this.bricks.add(new Brick ( 90 + (ANCHO * i), 1000 + (ALTO * j)));
                bricksBreaked++;
            }
        }

    }

    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.enableBlending();

        barDraw();
        ballDraw();
        brickDraw();

        abilitiesDraw();
        enemyDraw();
        lifeguardDraw();


        game.batch.end();

        Assets.musica_ingame.play();
    }

    private void barDraw(){
        game.batch.draw(Assets.barra, bar.position.x, bar.position.y);

        if(bar.lives == 3){
            game.batch.draw(Assets.heart, 25,1840 );
            game.batch.draw(Assets.heart, 110, 1840);
            game.batch.draw(Assets.heart, 195, 1840);
        }else if(bar.lives == 2){
            game.batch.draw(Assets.heart, 25,1840 );
            game.batch.draw(Assets.heart, 110, 1840);
            game.batch.draw(Assets.heartLost, 195, 1840);
        }else if (bar.lives == 1){
            game.batch.draw(Assets.heart, 25,1840 );
            game.batch.draw(Assets.heartLost, 110, 1840);
            game.batch.draw(Assets.heartLost, 195, 1840);
        }else{
            game.batch.draw(Assets.heartLost, 25,1840 );
            game.batch.draw(Assets.heartLost, 110, 1840);
            game.batch.draw(Assets.heartLost, 195, 1840);
        }
    }

    private void ballDraw(){
        if(pelota.ballAsset == 0){
            game.batch.draw(Assets.pelota, pelota.position.x, pelota.position.y);
        }else if(pelota.ballAsset == 1){
            game.batch.draw(Assets.pelota_enemyHit2, pelota.position.x + MathUtils.random(-4,4) + Float.valueOf(pelota.velocity.x) * -0.03f, pelota.position.y +40);
            game.batch.draw(Assets.pelota_enemyHit1, pelota.position.x + MathUtils.random(-4,4) + Float.valueOf(pelota.velocity.x) * -0.015f, pelota.position.y +20);
            game.batch.draw(Assets.pelota_enemyHit, pelota.position.x, pelota.position.y);
        }else if(pelota.ballAsset == 2){
            game.batch.draw(Assets.pelota_godMode, pelota.position.x + MathUtils.random(-15,15), pelota.position.y);
            game.batch.draw(Assets.pelota_godMode, pelota.position.x+ MathUtils.random(-15,15), pelota.position.y + MathUtils.random(-15,15));
            game.batch.draw(Assets.pelota_godMode, pelota.position.x, pelota.position.y + MathUtils.random(-15,15));
            game.batch.draw(Assets.pelota_godMode, pelota.position.x, pelota.position.y);

        }

    }

    private void brickDraw(){
        for (Brick brick: this.bricks) {
            game.batch.draw(Assets.brick, brick.position.x, brick.position.y);
        }
    }

    private void abilitiesDraw(){

        for (Abilities abilitie: this.abilities) {

            if(abilitie.tipoAbilidad == 0){
                game.batch.draw(Assets.b_abilitie, abilitie.position.x, abilitie.position.y);
            }else if (abilitie.tipoAbilidad == 1){
                game.batch.draw(Assets.g_abilitie, abilitie.position.x, abilitie.position.y);
            }else if (abilitie.tipoAbilidad == 2){
                game.batch.draw(Assets.gm_abilitie, abilitie.position.x, abilitie.position.y);
            }
        }
    }

    private void enemyDraw(){

        if (enemy.size() != 0){

            if(enemy.get(0).perseguir){
                if(enemy.get(0).lives == 3){
                    game.batch.draw(Assets.enemy3, enemy.get(0).position.x, enemy.get(0).position.y);
                }else if(enemy.get(0).lives == 2){
                    game.batch.draw(Assets.enemy2, enemy.get(0).position.x, enemy.get(0).position.y);
                }else if(enemy.get(0).lives == 1){
                    game.batch.draw(Assets.enemy1, enemy.get(0).position.x, enemy.get(0).position.y);
                }else if(enemy.get(0).lives == 0){
                    game.batch.draw(Assets.enemy0, enemy.get(0).position.x, enemy.get(0).position.y);
                }
            }else{
                if(enemy.get(0).lives == 3){
                    game.batch.draw(Assets.enemy3_sleep, enemy.get(0).position.x, enemy.get(0).position.y);
                }else if(enemy.get(0).lives == 2){
                    game.batch.draw(Assets.enemy2_sleep, enemy.get(0).position.x, enemy.get(0).position.y);
                }else if(enemy.get(0).lives == 1){
                    game.batch.draw(Assets.enemy1_sleep, enemy.get(0).position.x, enemy.get(0).position.y);
                }else if(enemy.get(0).lives == 0){
                    game.batch.draw(Assets.enemy0_sleep, enemy.get(0).position.x, enemy.get(0).position.y);
                }
            }
        }
    }

    private void lifeguardDraw(){
        if (lifeguard.size() != 0) {
            game.batch.draw(Assets.lifeguard, lifeguard.get(0).position.x, lifeguard.get(0).position.y);
        }
    }

    @Override
    public void pause () {
        Settings.save();
        Assets.musica_pantallaInicial.dispose();
    }

    @Override
    public void render (float delta) {
        act(delta);
        draw();
    }

    public void act(float delta) {
        time += delta;

        ballAct(delta);
        brickAct();

        abilityAct(delta);
        enemyAct(delta);
        barAct(delta);
        lifeguardAct();

        gameAct();
    }


    private void ballAct(float delta){

        if(pelota.imparable){
            pelota.timeImparable += delta;
            if (pelota.timeImparable > 10){
                pelota.imparable = false;
                pelota.ballAsset = 0;
            }
        }else{
            pelota.imparable = false;
            pelota.timeImparable = 0;
        }



        if (Gdx.input.justTouched()) {
            if(pelota.getState() == Ball.SERVE_state){
                pelota.changeState(Ball.PLAY_state);
                pelota.velocity.x = 0;
                pelota.velocity.y = Ball.BALL_MOVE_VELOCITY;
            }
        }

        pelota.update(delta);

        if(pelota.position.y <= -25){
            pelota.changeState(Ball.SERVE_state);
            pelota.position.x = bar.position.x + Bar.BAR_WIDTH /2 - Ball.BALL_WIDTH/2;
            pelota.position.y = 200;
            pelota.ballAsset = 0;
            bar.lives --;
        }
    }

    private void brickAct(){

        for (int i = 0; i <  this.bricks.size(); i++){
            bricks.get(i).update();

            if (pelota.bounds.overlaps(bricks.get(i).bounds)) {
                if(!pelota.imparable){
                    pelota.brickColision(bricks.get(i).position.x, bricks.get(i).position.y, pelota.position.x, pelota.position.y, pelota.velocity.x, pelota.velocity.y);
                }

                if(MathUtils.random(0,100) < 25){
                    abilities.add(new Abilities(bricks.get(i).position.x, bricks.get(i).position.y));
                }
                this.bricks.remove(i);
                bricksBreaked--;
            }
        }
    }

    private void abilityAct(float delta){

        for (int i = 0; i <  this.abilities.size(); i++){
            abilities.get(i).update(delta);

            if(abilities.get(i).tipoAbilidad == 0){
                if(bar.position.x > abilities.get(i).position.x - Abilities.ABILITIES_WIDTH/2){
                    abilities.get(i).velocity.x = Abilities.ABILITIES_MOVE_PERSECUTION;
                }else if (bar.position.x + Bar.BAR_WIDTH < abilities.get(i).position.x - Abilities.ABILITIES_WIDTH/2){
                    abilities.get(i).velocity.x = Abilities.ABILITIES_MOVE_PERSECUTION * -1;
                }
            }

            if (bar.bounds.overlaps(abilities.get(i).bounds)) {

                if(abilities.get(i).tipoAbilidad == 0){
                    spawnEnemy();
                }else if(abilities.get(i).tipoAbilidad == 2){
                    pelota.imparable = true;
                    pelota.ballAsset = 2;
                }else if(abilities.get(i).tipoAbilidad == 1){
                    lifeguard.add(new Lifeguard(0,30));
                }
                this.abilities.remove(i);
            }
        }
    }
    private void spawnEnemy() {

        if (enemy.size() == 0){
            enemy.add(new PongEnemy(1080/2 - Bar.BAR_WIDTH /2, 800));
        }
    }

    private void enemyAct(float delta){

        if(enemy.size() != 0){

            if (pelota.bounds.overlaps(enemy.get(0).bounds) && (lastDelta + 0.1 < time) && pelota.velocity.y > 0) {
                lastDelta = time;
                if(!pelota.imparable){
                    pelota.enemyColision(enemy.get(0).position.x, enemy.get(0).position.y, pelota.position.x, pelota.position.y, pelota.velocity.x, pelota.velocity.y);
                    enemy.get(0).lives --;
                    pelota.ballAsset = 1;
                } else{
                    pelota.enemyColision(enemy.get(0).position.x, enemy.get(0).position.y, pelota.position.x, pelota.position.y, pelota.velocity.x, pelota.velocity.y);
                    enemy.get(0).lives --;
                    enemy.get(0).lives --;
                    enemy.get(0).lives --;
                    enemy.get(0).lives --;
                }
            }
            enemy.get(0).velocity.y = 0;
            enemy.get(0).position.y = 800;

            if(pelota.position.y < enemy.get(0).position.y){
                enemy.get(0).perseguir = true;
                if(pelota.position.x + pelota.BALL_WIDTH < enemy.get(0).position.x + PongEnemy.PONGENEMY_WIDTH/2){
                    enemy.get(0).velocity.x = PongEnemy.PONGENEMY_MOVE_VELOCITY * -1;
                }else if(pelota.position.x > enemy.get(0).position.x + PongEnemy.PONGENEMY_WIDTH/2){
                    enemy.get(0).velocity.x = PongEnemy.PONGENEMY_MOVE_VELOCITY ;
                }else{
                    enemy.get(0).velocity.x = 0;
                }

            }else{
                enemy.get(0).perseguir = false;
                enemy.get(0).velocity.x = 0;
            }
            enemy.get(0).update(delta);

            if(enemy.get(0).lives < 0){
                enemy.remove(0);
            }
        }
    }

    private void barAct(float delta){
        gyroscopicXControl(delta);
        if (pelota.bounds.overlaps(bar.bounds)) {
            pelota.barColision(bar.position.x, pelota.position.x + Ball.BALL_WIDTH/2);
            if(!pelota.imparable){
                pelota.ballAsset = 0;
            }
        }
    }

    private void gyroscopicXControl(float delta) {
        this.gyroscopicPosotionState = Gdx.input.getAccelerometerX();
        bar.velocity.x = -gyroscopicPosotionState / 10 * Bar.BAR_MOVE_VELOCITY;
        bar.update(delta);

        if(pelota.getState() == Ball.SERVE_state){
            pelota.velocity.x = bar.velocity.x;
        }
    }

    private void lifeguardAct(){

        if (lifeguard.size() != 0) {
            lifeguard.get(0).update();
            if (pelota.bounds.overlaps(lifeguard.get(0).bounds)) {
                if(!pelota.imparable){
                    pelota.ballAsset = 0;
                }
                pelota.lifeguardColision();
                this.lifeguard.remove(0);
            }

        }
    }

    private void gameAct(){
        if(bar.lives == 0){
            game.setScreen(new FinalScreen(game, "LEVEL 1", bricksBreaked));
            dispose();
        }

        if(bricksBreaked == 0){
            game.setScreen(new FinalScreen(game, "LEVEL 1", bricksBreaked));
            dispose();
        }
    }
}
