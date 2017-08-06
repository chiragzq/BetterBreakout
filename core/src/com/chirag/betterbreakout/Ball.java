package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball extends Sprite {
    int radius;
    float x;
    float y;
    float xVel;
    float yVel;
    boolean isDead;

    public Ball(int radius, int x, int y, Texture ballTexture) {
        super(ballTexture);
        this.radius = radius;
        this.x = x;
        this.y = y;
        yVel = 0;
        xVel = 0;
        isDead = false;

        setCenter(x,y);
        setSize(radius*2, radius*2);
        launch(BetterBreakout.GAME_WIDTH/2);
    }

    public void update() {
        x += xVel;
        y += yVel;
        wallCollision();
        setCenter(x, y);
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void wallCollision() {
        if(x+radius > BetterBreakout.GAME_WIDTH) {
            x = BetterBreakout.GAME_WIDTH - radius;
            xVel *= -1;
        }
        if(x-radius < 0) {
            x = radius;
            xVel *= -1;
        }
        if(y-radius < 0) {
            y = radius;
            yVel *= -1;
            isDead = true;
        }
        if(y+radius > BetterBreakout.GAME_HEIGHT) {
            y = BetterBreakout.GAME_HEIGHT - radius;
            yVel *= -1;
        }
    }

    public void setDirection(float dir, float vel) {
        xVel = (float)Math.cos(dir * Math.PI / 180f ) * vel;
        yVel = (float)Math.sin(dir * Math.PI / 180f) * vel;
        setOriginCenter();
        setRotation(dir);
    }

    public void launch(int x) {
        setDirection((int)(Math.random() * 160 - 1) + 10, 10);
        setCenterX(x);
    }
}
