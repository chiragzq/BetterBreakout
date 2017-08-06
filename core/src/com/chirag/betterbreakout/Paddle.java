package com.chirag.betterbreakout;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle extends Sprite {
    long timer;
    int defaultWidth;
    State currState;

    public Paddle(int width, int height, int y,Texture paddleTexture) {
        super(paddleTexture);
        timer = 0L;
        currState = State.NORMAL;
        defaultWidth = width;
        setBounds(0, y, width, height);
    }

    public void update() {
        setCenterX(Gdx.input.getX());
        if(currState != State.NORMAL) {
            if(timer < System.currentTimeMillis()) {
                currState = State.NORMAL;
                setBounds(Gdx.input.getX(), getY(), defaultWidth, getHeight());
            }
        }
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public enum State {
        NORMAL, LARGE, SMALL
    }

    /**
     * Changes with width of the paddle for a duration
     * @param width how big the paddle should become
     * @param duration the duration (in millis) that it should stay
     */
    public void setSize(int width, int duration) {
        if(width < defaultWidth) {
            currState = State.SMALL;
        } else if(width > defaultWidth) {
            currState = State.LARGE;
        }
        timer = System.currentTimeMillis() + duration;
        setBounds(Gdx.input.getX(), getY(), width, getHeight());
        setCenterX(Gdx.input.getX());
    }
}
