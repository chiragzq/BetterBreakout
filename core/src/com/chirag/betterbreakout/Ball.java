package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Ball extends MovingGameElement {
    private float mRadius;

    Ball(float radius, float x, float y, Texture ballTexture) {
        super(ballTexture, x, y, radius * 2, radius * 2, 0, 0);
        mRadius = radius;

        launch(BetterBreakout.GAME_WIDTH / 2);
    }

    //Setters
    void setX(float x) {
        mX = x;
    }

    void setY(float y) {
        mY = y;
    }

    //Main
    public void update() {
        super.update();
        wallCollision();

    }

    @Override
    public void draw(SpriteBatch batch) {
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
