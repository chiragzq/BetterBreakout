package com.chirag.betterbreakout;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class Game {
    int waveNum;
    int lives;
    Paddle paddle;
    BrickPattern bricks;
    List<Ball> balls;

    public Game(Texture brickTexture) {
        bricks = new BrickPattern(brickTexture);
        for(int i = 65; i <= 735; i += 45) {
            for(int j = 535;j >= 410;j -= 25) {
                bricks.add(i, j ,40, 20);
            }
        }
        paddle = new Paddle(120,20, 100, brickTexture);
    }

    public void update() {
        bricks.update();
        paddle.update();
    }

    public void draw(SpriteBatch batch) {
        bricks.draw(batch);
        paddle.draw(batch);
    }

    public void reset() {
        waveNum = 0;
        lives = 3;
        paddle = new Paddle(120, 20, 100, new Texture("brick.png"));
    }
}
