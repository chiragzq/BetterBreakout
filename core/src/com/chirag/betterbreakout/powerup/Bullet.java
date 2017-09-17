package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.chirag.betterbreakout.BetterBreakout;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.MovingGameElement;
import com.chirag.betterbreakout.Rectangular;

public class Bullet extends MovingGameElement implements DeleteableGameElement, Rectangular {

    public Bullet(Texture texture, float x, float y) {
        super(texture, x, y, 10, 10, 0, 0);
        launch(x);
    }

    public Sprite getSprite() {
        return mSprite;
    }

    @Override
    public void update() {
        mX += mXVel;
        mY += mYVel;

        mSprite.setBounds(mX, mY, mWidth, mHeight);
        wallCollision();
    }

    void setDirection(float dir, float vel) {
        mXVel = (float) Math.cos(dir * Math.PI / 180f) * vel;
        mYVel = (float) Math.sin(dir * Math.PI / 180f) * vel;
        mSprite.setOriginCenter();
        mSprite.setRotation(dir);
    }

    void launch(float x) {
        setDirection((int) (Math.random() * 160 - 1) + 10, 10);
        mX = x;
    }
    private void wallCollision() {
        if (mX + mWidth > BetterBreakout.GAME_WIDTH) {
            mX = BetterBreakout.GAME_WIDTH - mWidth;
            mXVel *= -1;
        }
        if (mX - mWidth < 0) {
            mX = mWidth;
            mXVel *= -1;
        }
        if (mY - mHeight < 0) {
            mY = mHeight;
            mYVel *= -1;
            mIsDead = true;
        }
        if (mY + mHeight > BetterBreakout.GAME_HEIGHT) {
            mY = BetterBreakout.GAME_HEIGHT - mHeight;
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
