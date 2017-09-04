package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Rectangular;

public class Laser implements Rectangular, DeleteableGameElement {
    private static final int HEIGHT = BetterBreakout.GAME_HEIGHT;

    private float mX;
    private float mY;
    private float mWidth;
    private boolean mIsDead;
    private boolean mIsActive;
    private Sprite mSprite;
    private long activationTimer;

    public Laser(Texture texture, float x) {
        mSprite = new Sprite(texture);
        mX = x;
        mY = BetterBreakout.GAME_HEIGHT /  2;
        mWidth = 80;

        activationTimer = 0L;

        mIsActive = false;
        mIsDead = false;

        mSprite.setBounds(x, mY, mWidth, HEIGHT);
    }

    public void update() {
        if(System.currentTimeMillis() < activationTimer) {
            mWidth = (activationTimer - System.currentTimeMillis()) / 3;
        } else {
            mIsDead = true;
            mWidth = 1;
        }
    }

    public void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.setSize(mWidth, HEIGHT);
        mSprite.draw(batch);
    }

    public void setActive(boolean isActive) {
        mIsActive = isActive;
    }

    public float getHeight() {
        return HEIGHT;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public boolean isDead() {
        return mIsDead;
    }

    public boolean isActive() {
        return mIsActive;
    }

    public void activate() {
        activationTimer = System.currentTimeMillis() + 240;
        mIsActive = true;
    }
}
