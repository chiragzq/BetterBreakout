package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Rectangular;

public class Bullet implements DeleteableGameElement, Rectangular {
    private final float WIDTH = 10;
    private final float HEIGHT = 10;

    private float mX;
    private float mY;
    private float mXVel;
    private float mYVel;
    private boolean mIsDead;
    private Sprite mSprite;

    public Bullet(Texture texture, int x, int y) {
        mSprite = new Sprite(texture);

        mXVel = 0;
        mYVel = 0;
        mX = x;
        mY = y;
        mIsDead = false;

        mSprite.setBounds(x, y, WIDTH, HEIGHT);
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
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public boolean isDead() {
        return mIsDead;
    }
}
