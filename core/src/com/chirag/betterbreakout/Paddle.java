package com.chirag.betterbreakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;


class Paddle implements Rectangular{
    public enum State {
        NORMAL, LARGE, SMALL
    }

    private static Map<State, Integer> WIDTHS;
    private static final int HEIGHT = 30;

    static {
        WIDTHS = new HashMap<State, Integer>();
        WIDTHS.put(State.NORMAL, 100);
        WIDTHS.put(State.SMALL, 50);
        WIDTHS.put(State.LARGE, 800);
    }

    private float mX;
    private long mPowerUpEndTime;
    private State mState;
    private Sprite mSprite;
    private float mY;
    private float mWidth;

    Paddle(Texture paddleTexture, float x, float y) {
        mX = x;
        mY = y;
        mWidth = WIDTHS.get(State.NORMAL);

        mState = State.NORMAL;

        mSprite = new Sprite(paddleTexture);
        mSprite.setBounds(0, y, WIDTHS.get(State.NORMAL), 20);
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    @Override
    public float getWidth() {
        return mWidth;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    Sprite getSprite() {
        mSprite.setCenter(mX, mY);
        return mSprite;
    }

    void update() {
        mX = Gdx.input.getX();
        if(mState != State.NORMAL) {
            if(mPowerUpEndTime < System.currentTimeMillis()) {
                mState = State.NORMAL;
                setState(State.NORMAL, 0);
                mSprite.setCenter(mX, mY);
            }
        }
    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.setSize(mWidth, HEIGHT);
        mSprite.draw(batch);
    }

    /**
     * Changes with width of the paddle for a duration
     * @param state THE STATE
     * @param duration the duration (in millis) that it should stay
     */
    void setState(State state, int duration) {
        mState = state;

        mWidth = WIDTHS.get(state);
        mPowerUpEndTime = System.currentTimeMillis() + duration;
        mX = Gdx.input.getX();
        mSprite.setBounds(mX, mY, mWidth, HEIGHT);

    }
}
