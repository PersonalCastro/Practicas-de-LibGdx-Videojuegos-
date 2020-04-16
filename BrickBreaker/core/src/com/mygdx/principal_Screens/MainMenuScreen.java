package com.mygdx.principal_Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.PJs.Ball;
import com.mygdx.PJs.Bar;
import com.mygdx.game.Assets;
import com.mygdx.game.BB_Game;
import com.mygdx.game.Settings;

import java.util.ArrayList;


public class MainMenuScreen extends ScreenAdapter {

    BB_Game game;
    float time = 0;

    OrthographicCamera guiCam;

    public Bar bar;
    private float gyroscopicPosotionState;
    public Ball ball;

    private ArrayList<LevelButton> botones;
    private ArrayList<MainScreenButton> botonesGrandes;

    private boolean primeraPantalla = true;
    private Vector3 touchPoint;


    public MainMenuScreen (BB_Game game) {
        this.game = game;
        this.bar = new Bar(1080/2 - Bar.BAR_WIDTH /2, 75);
        this.ball = new Ball(1080/2 - Ball.BALL_WIDTH/2, 200);

        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 1080, 1920);
        touchPoint = new Vector3();

        this.botonesGrandes = new ArrayList();
        this.botonesGrandes.add(new MainScreenButton(1080/2 -  LevelButton.LEVELBUTTON_WIDTH_BIG/2, 800));

        this.botones = new ArrayList();
        this.botones.add(new LevelButton(1080/2 - (1080/3)/2 - LevelButton.LEVELBUTTON_WIDTH/2, 825));
        this.botones.add(new LevelButton(1080/2 + (1080/3)/2 - LevelButton.LEVELBUTTON_WIDTH/2, 825));

    }


    public void update (float delta) {
        barAct(delta);
        ballAct(delta);

        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if(primeraPantalla){
                if (this.botonesGrandes.get(0).bounds.contains(touchPoint.x, touchPoint.y)){
                    primeraPantalla = false;
                    this.botonesGrandes.clear();
                }
            }
		}

        if(!primeraPantalla){

            if (this.botones.get(0).bounds.overlaps(ball.bounds)){
                game.setScreen(new Screen_lvl1(game));
                Assets.musica_pantallaInicial.stop();
                dispose();
            }else if(this.botones.get(1).bounds.overlaps(ball.bounds)){
                game.setScreen(new Screen_lvl2(game));
                Assets.musica_pantallaInicial.stop();
                dispose();
            }
        }
    }

    private void barAct(float delta){
        gyroscopicXControl(delta);
        if (ball.bounds.overlaps(bar.bounds)) {
            ball.barColision(bar.position.x, ball.position.x + Ball.BALL_WIDTH/2);
            if(!ball.imparable){
                ball.ballAsset = 0;
            }
        }
    }

    private void gyroscopicXControl(float delta) {
        this.gyroscopicPosotionState = Gdx.input.getAccelerometerX();
        bar.velocity.x = -gyroscopicPosotionState / 10 * Bar.BAR_MOVE_VELOCITY;
        bar.update(delta);

        if(ball.getState() == Ball.SERVE_state){
            ball.velocity.x = bar.velocity.x;
        }
    }

    private void ballAct(float delta){
        if (Gdx.input.justTouched() && !primeraPantalla) {
            if(ball.getState() == Ball.SERVE_state){
                ball.changeState(Ball.PLAY_state);
                ball.velocity.x = 0;
                ball.velocity.y = Ball.BALL_MOVE_VELOCITY;
            }
        }

        ball.update(delta);

        if(ball.position.y <= -25){
            ball.changeState(Ball.SERVE_state);
            ball.position.x = bar.position.x + Bar.BAR_WIDTH /2 - Ball.BALL_WIDTH/2;
            ball.position.y = 200;
            ball.ballAsset = 0;
        }
    }


    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        TextureRegion titulo;
        titulo = (TextureRegion) Assets.titleAnimation.getKeyFrame(time);

        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        game.batch.disableBlending();/**/

        game.batch.begin();
        game.batch.draw(Assets.fondo_pantallaInicial, 0, 0);
        
        game.batch.enableBlending();

        if(primeraPantalla){
            game.batch.draw(Assets.playButton, botonesGrandes.get(0).position.x, botonesGrandes.get(0).position.y);

        }else{
            game.batch.draw(Assets.barra, bar.position.x, bar.position.y);
            game.batch.draw(Assets.pelota, ball.position.x, ball.position.y);
            game.batch.draw(Assets.nivel1, botones.get(0).position.x, botones.get(0).position.y);
            game.batch.draw(Assets.nivel2, botones.get(1).position.x, botones.get(1).position.y);
        }

        game.batch.draw(titulo, 1080/2 - 1222/2, 1400);


        game.batch.end();
        Assets.musica_pantallaInicial.play();
    }
    
    public void act(float delta) {
        time = time + delta;
    }

    @Override
    public void render (float delta) {
        // should work also with Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
        this.update(delta);
        act(delta);
        draw();


    }

    @Override
    public void pause () {
        Settings.save();
        Assets.musica_pantallaInicial.dispose();
    }



}
