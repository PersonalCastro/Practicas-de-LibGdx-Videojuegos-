package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public static Texture fondo_pantallaInicial;
    
    public static Texture title_Tpack;
    public static Animation titleAnimation;
    final static float TitleWidth = 1222;
    final static float TitleHeight = 240;
    public static Texture playButton;
    public static Texture nivel1;
    public static Texture nivel2;


    public static Texture barra;
    public static Texture pelota;
    public static Texture pelota_enemyHit;
    public static Texture pelota_enemyHit1;
    public static Texture pelota_enemyHit2;
    public static Texture pelota_slime;
    public static Texture pelota_godMode;


    public static Texture brick;
    public static Texture brickStrong;

    public static Texture b_abilitie;
    public static Texture g_abilitie;
    public static Texture gm_abilitie;

    public static Texture enemy3;
    public static Texture enemy3_sleep;
    public static Texture enemy2;
    public static Texture enemy2_sleep;
    public static Texture enemy1;
    public static Texture enemy1_sleep;
    public static Texture enemy0;
    public static Texture enemy0_sleep;

    public static Texture lifeguard;

    public static Texture heart;
    public static Texture heartLost;

    public static Texture score;
    public static Texture star;
    public static Texture star_lost;
    public static Texture button_restart;
    public static Texture button_home;
    public static Texture button_next_lvl;
    public static Texture button_next_lvl_lock;

    public static Music musica_pantallaInicial;
    public static Music musica_ingame;
    public static Sound hit_ball;


    public static void load() {

        fondo_pantallaInicial = loadTexture("Sin_nombre.png");
        
        title_Tpack = loadTexture("try.png");
        TextureRegion[][] grid = TextureRegion.split(title_Tpack, (int) TitleWidth, (int) TitleHeight);
        titleAnimation = new Animation(0.08f, grid[0][0], grid[1][0], grid[2][0], grid[3][0], grid[4][0], grid[5][0], grid[6][0], grid[7][0], grid[8][0], grid[9][0], grid[10][0], grid[11][0], grid[12][0], grid[13][0], grid[14][0], grid[15][0], grid[16][0]);
        titleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        playButton =loadTexture("jugar_button.png");
        nivel1 = loadTexture("nivel1.png");
        nivel2 = loadTexture("nivel2.png");

        barra = loadTexture("barra.png");
        pelota = loadTexture("pelota.png");
        pelota_enemyHit = loadTexture("pelota_enemyhit.png");
        pelota_enemyHit1 = loadTexture("pelota_enemyhit_1.png");
        pelota_enemyHit2 = loadTexture("pelota_enemyhit_2.png");
        pelota_slime = loadTexture("pelota_slime.png");
        pelota_godMode = loadTexture("pelota_godMode.png");

        brick = loadTexture("brick_1.png");
        brickStrong = loadTexture("brick_2.png");
        b_abilitie = loadTexture("b_abilitie.png");
        g_abilitie = loadTexture("g_abilitie.png");
        gm_abilitie = loadTexture("gm_abilitie.png");

        enemy3 = loadTexture("enemy_3.png");
        enemy3_sleep = loadTexture("enemy_3_sleep.png");
        enemy2 = loadTexture("enemy_2.png");
        enemy2_sleep = loadTexture("enemy_2_sleep.png");
        enemy1 = loadTexture("enemy_1.png");
        enemy1_sleep = loadTexture("enemy_1_sleep.png");
        enemy0 = loadTexture("enemy_0.png");
        enemy0_sleep = loadTexture("enemy_0_sleep.png");

        lifeguard = loadTexture("lifeguard.png");

        heart = loadTexture("corazon.png");
        heartLost = loadTexture("corazon_vacio.png");

        score = loadTexture("score.png");
        star = loadTexture("star.png");
        star_lost = loadTexture("star_lost.png");
        button_restart = loadTexture("restart_lvl_button.png");
        button_home = loadTexture("home_lvl_button.png");
        button_next_lvl = loadTexture("next_lvl_button.png");
        button_next_lvl_lock = loadTexture("next_lvl_button_blocked.png");



        musica_ingame = Gdx.audio.newMusic(Gdx.files.internal("Musica_v_inicio.mp3"));
        musica_pantallaInicial = Gdx.audio.newMusic(Gdx.files.internal("ascensor.mp3"));
        musica_pantallaInicial.setLooping(true);
        musica_pantallaInicial.setVolume(0.9f);
        musica_ingame.setLooping(true);
        musica_ingame.setVolume(0.9f);

        hit_ball = Gdx.audio.newSound(Gdx.files.internal("hit_ball.mp3"));


    }

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void playSound (Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }
}
