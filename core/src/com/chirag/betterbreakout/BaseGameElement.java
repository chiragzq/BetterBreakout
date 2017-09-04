package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Rectangular;

public class BaseGameElement implements Rectangular, DeleteableGameElement {
    protected float mX;
    protected float mY;
    protected float mWidth;
    protected float mHeight;
    protected boolean mIsDead;
    protected Sprite mSprite;

    public BaseGameElement(Texture texture, float x, float y, float width, float height) {
        mSprite = new Sprite(texture);
        mSprite.setBounds(x, y, width, height);

        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
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

    @Override
    public boolean isDead() {
        return mIsDead;
    }

    public void draw(SpriteBatch batch) {
        mSprite.setBounds(mX, mY, mWidth, mHeight);
        mSprite.draw(batch);
    }
}
