package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Rectangular;

public class Rocket implements Rectangular, DeleteableGameElement {
    private float mHeight;
    private float mWidth;
    private float mX;
    private float mY;
    private boolean mIsDead;
    private Sprite mSprite;

    Rocket(Texture texture, float x, float y, float width, float height) {
        mSprite = new Sprite(texture);
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;

        mSprite.setBounds(x, y, width, height);
    }

    public float getHeight() {
        return mHeight;
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

    public void detonate() {
        mIsDead = true;
    }
}
