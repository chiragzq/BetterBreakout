package com.chirag.betterbreakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


class Paddle implements Rectangular{
    public enum State {
        NORMAL, LARGE, SMALL
    }

    private float mX;
    private long mPowerUpEndTime;
    private float mDefaultWidth;
    private State mState;
    private Sprite mSprite;
    private float mY;
    private float mWidth;
    private float mHeight;

    Paddle(Texture paddleTexture, float x, float y, float width, float height) {
        mSprite = new Sprite(paddleTexture);
        mSprite.setBounds(0, y, width, height);

        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;

        mState = State.NORMAL;
        mDefaultWidth = width;
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
        return mHeight;
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
                mSprite.setCenter(mX, mY);
            }
        }
    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.setSize(mWidth, mHeight);
        mSprite.draw(batch);
    }

    /**
     * Changes with width of the paddle for a duration
     * @param width how big the paddle should become
     * @param duration the duration (in millis) that it should stay
     */
    void setSize(int width, int duration) {
        if(width < mDefaultWidth) {
            mState = State.SMALL;
        } else if(width > mDefaultWidth) {
            mState = State.LARGE;
        }

        mWidth = width;
        mPowerUpEndTime = System.currentTimeMillis() + duration;
        mX = Gdx.input.getX();
    }
}
