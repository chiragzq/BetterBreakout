package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


class Brick implements DeleteableGameElement, Rectangular {
    static final int WIDTH = 100;
    static final int HEIGHT = 25;

    private Sprite mSprite;
    private long mTime;
    private boolean mIsDead;
    private float mX;
    private float mY;
    private Color mColor;

    Brick(Texture brickTexture, int x, int y, int width, int height, Color color) {
        mSprite = new Sprite(brickTexture);
        mSprite.setBounds(x, y, width, height);
        mSprite.setColor(color);
        mColor = color;
        mX = x;
        mY = y;
        mTime = Long.MAX_VALUE;
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

    public Sprite getSprite() {
        return mSprite;
    }

    public Color getColor() {
        return mColor;
    }

    //Setters
    public void setDead(boolean isDead) {
        mIsDead = isDead;
    }

    //Main
    void update() {
        if(System.currentTimeMillis() > mTime) mIsDead = true;
    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.draw(batch);
    }

    //Helper
    void gotHit() {
        mTime = System.currentTimeMillis() + 100;
        mSprite.setColor(mSprite.getColor().mul(0.75f));
    }


}
