package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


class Brick implements DeleteableGameElement, Rectangular {
    static final int WIDTH = 100;
    static final int HEIGHT = 25;

    private Sprite mSprite;
    private boolean mIsDead;
    private float mX;
    private float mY;
    private Color mColor;

    Brick(Texture brickTexture, int x, int y, Color color) {
        mSprite = new Sprite(brickTexture);
        mSprite.setColor(color);
        mSprite.setSize(WIDTH, HEIGHT);
        mColor = color;
        mX = x;
        mY = y;
    }

    //Getters
    @Override
    public boolean isDead() {
        return mIsDead;
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

    Sprite getSprite() {
        return mSprite;
    }

    Color getColor() {
        return mColor;
    }

    //Setters
    void setDead(boolean isDead) {
        mIsDead = isDead;
    }

    //Main
    void update() {
        //something
    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.draw(batch);
    }

    //Helper
    void gotHit() {
        TimeUtil.doLater(new Runnable() {
            @Override
            public void run() {
                mIsDead = true;
            }
        }, 100);
        mSprite.setColor(mSprite.getColor().mul(0.75f));
    }


}
