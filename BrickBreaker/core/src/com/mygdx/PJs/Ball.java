package com.mygdx.PJs;

import com.mygdx.game.Assets;

public class Ball extends DynamicGameObject{


    public static final float BALL_MOVE_VELOCITY = 750;
    public static final float BALL_WIDTH = 25;
    public static final float BALL_HEIGHT = 25;

    public static final int SERVE_state = 0;
    public static final int PLAY_state = 1;
    private int state = 0;
    public int ballAsset = 0;

    public boolean imparable = false;
    public float timeImparable = 0;

    //public boolean soundOn = false;
    //public float timeSoundOn = 0;

    private boolean boosted = false;

    float stateTime;

    public Ball (float x, float y) {
        super(x, y, BALL_WIDTH, BALL_HEIGHT);
        stateTime = 0;
        velocity.x = 0;
        velocity.y = BALL_MOVE_VELOCITY;
        state = SERVE_state;

    }

    public void update (float deltaTime) {

        bounds.x = position.x;
        bounds.y = position.y;

        //timeSoundOn += deltaTime;

        if(state == SERVE_state){
            imparable = false;
            position.add(velocity.x * deltaTime, 0);
            if (position.x < 0 + (Bar.BAR_WIDTH/2)){
                position.x = 0 + (Bar.BAR_WIDTH/2);
            }
            if (position.x > 1080 - (Bar.BAR_WIDTH/2)){
                position.x = 1080 - (Bar.BAR_WIDTH/2);
            }

        }else if(state == PLAY_state){
            position.add(velocity.x * deltaTime, velocity.y * deltaTime);

            if (position.x < 0){
                playHitSound();
                position.x = 1;
                velocity.x = velocity.x * -1;
            }
            if (position.x > 1050){
                playHitSound();
                position.x = 1049;
                velocity.x = velocity.x * -1;
            }
            if (position.y > 1890){
                playHitSound();
                position.y = 1889;
                velocity.y = velocity.y * -1;
            }
        }

        stateTime += deltaTime;
    }


    public void barColision(float boundsX_bar, float boundsX_ball){
        playHitSound();

        float barPosition_bound =  (boundsX_ball - boundsX_bar) - (Bar.BAR_WIDTH/2) ;
        float anglePosition = (barPosition_bound * 60) / 150;


        velocity.x = BALL_MOVE_VELOCITY * (float) Math.sin(Math.toRadians( anglePosition ));
        velocity.y = BALL_MOVE_VELOCITY * (float) Math.cos(Math.toRadians( anglePosition ));

        boosted = false;

    }

    public void brickColision(float brickX, float brickY, float ballX, float ballY, float velocityX, float velocityY){
        playHitSound();

        if(brickY > ballY && velocityY > 0){
            velocity.y = velocity.y * -1;
        }else if (brickX > ballX && velocityX > 0){
            velocity.x = velocity.x * -1;
        }else if(ballX + BALL_WIDTH > brickX + Brick.BRICK_WIDTH && velocityX < 0){
            velocity.x = velocity.x * -1;
        }else if(ballY + BALL_HEIGHT > brickY + Brick.BRICK_HEIGHT && velocityY < 0){
            velocity.y = velocity.y * -1;
        }

        boosted = false;
    }

    public void enemyColision(float brickX, float brickY, float ballX, float ballY, float velocityX, float velocityY){
        playHitSound();

        if(brickY > ballY && velocityY > 0){
            velocity.y = velocity.y * -1;
        }else if (brickX > ballX && velocityX > 0){
            velocity.x = velocity.x * -1;
        }else if(ballX + BALL_WIDTH > brickX + PongEnemy.PONGENEMY_WIDTH && velocityX < 0){
            velocity.x = velocity.x * -1;
        }else if(ballY + BALL_HEIGHT > brickY + PongEnemy.PONGENEMY_HEIGHT && velocityY < 0){
            velocity.y = velocity.y * -1;
        }
        velocity.x *= 1.5;
        velocity.y *= 1.5;
        boosted = true;

    }

    public void lifeguardColision(){
        playHitSound();
        velocity.y *= -1;

        if(boosted){
            velocity.x /= 1.5;
            velocity.y /= 1.5;
        }
        boosted = false;
    }


    public void changeState(int state) {
        this.state = state;
    }

    public int getState(){
        return this.state;
    }

    public void playHitSound(){

        Assets.hit_ball.play();
    }
}