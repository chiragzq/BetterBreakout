package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Ball implements DeleteableGameElement{
    private Sprite mSprite;
    private int mRadius;
    private float mX;
    private float mY;
    private float mXVel;
    private float mYVel;
    private boolean mIsDead;

    Ball(int radius, int x, int y, Texture ballTexture) {
        mSprite = new Sprite(ballTexture);
        mRadius = radius;
        mX = x;
        mY = y;

        mIsDead = false;
        mSprite.setSize(radius * 2, radius * 2);
        launch(BetterBreakout.GAME_WIDTH / 2);
    }

    //Getters
    public boolean isDead() {
        return mIsDead;
    }

    float getX() {
        return mX;
    }

    float getY() {
        return mY;
    }

    float getXVel() {
        return mXVel;
    }

    float getYVel() {
        return mYVel;
    }

    float getOldX() {
        return mX - mXVel;
    }

    float getOldY() {
        return mY - mYVel;
    }

    float getDiameter() {
        return mRadius * 2;
    }

    //Setters
    void setX(float x) {
        mX = x;
    }

    void setY(float y) {
        mY = y;
    }

    //Main
    void update() {
        mX += mXVel;
        mY += mYVel;
        wallCollision();

    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.draw(batch);
    }

    //Helpers
    void setDirection(float dir, float vel) {
        mXVel = (float) Math.cos(dir * Math.PI / 180f) * vel;
        mYVel = (float) Math.sin(dir * Math.PI / 180f) * vel;
        mSprite.setOriginCenter();
        mSprite.setRotation(dir);
    }

    void launch(int x) {
        setDirection((int) (Math.random() * 160 - 1) + 10, 10);
        mX = x;
    }

    void stepBack() {
        mX -= mXVel;
        mY -= mYVel;
    }

    void reverseX() {
        mXVel *= -1;
    }

    void reverseY() {
        mYVel *= -1;
    }

    private void wallCollision() {
        if (mX + mRadius > BetterBreakout.GAME_WIDTH) {
            mX = BetterBreakout.GAME_WIDTH - mRadius;
            mXVel *= -1;
        }
        if (mX - mRadius < 0) {
            mX = mRadius;
            mXVel *= -1;
        }
        if (mY - mRadius < 0) {
            mY = mRadius;
            mYVel *= -1;
            mIsDead = true;
        }
        if (mY + mRadius > BetterBreakout.GAME_HEIGHT) {
            mY = BetterBreakout.GAME_HEIGHT - mRadius;
            mYVel *= -1;
        }
        updateAngle();
    }

     void updateAngle() {
        float dir = (float)(Math.atan2(mYVel, mXVel) * 180.0 / Math.PI);
        mSprite.setOriginCenter();
        mSprite.setRotation(dir);
    }


}
