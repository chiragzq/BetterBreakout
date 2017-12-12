package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


class Brick implements DeleteableGameElement, Rectangular {
    static final int WIDTH = 138;
    static final int HEIGHT = 35;

    private Sprite mSprite;
    private boolean mIsDead;
    private boolean mIsHit;
    private float mX;
    private float mY;
    private BrickGenerator mBrickGenerator;
    private Color mColor;

    Brick(Texture brickTexture, int x, int y, Color color, BrickGenerator brickGenerator) {
        mSprite = new Sprite(brickTexture);
        mSprite.setColor(color);
        mSprite.setSize(WIDTH, HEIGHT);
        mColor = color;
        mX = x;
        mY = y;
        mBrickGenerator = brickGenerator;
        mIsHit = false;
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
        return mY + mBrickGenerator.getBottomY();
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

    boolean isHit() {
        return mIsHit;
    }

    //Setters
    void setDead() {
        mIsDead = true;
    }

    //Main
    void update() {
        //something
    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY + mBrickGenerator.getBottomY());
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
        mIsHit = true;
    }


}
