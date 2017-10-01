package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;

public class MovingGameElement extends BaseGameElement {
    protected float mXVel;
    protected float mYVel;

    public MovingGameElement(Texture texture, float x, float y, float width, float height, float xVel, float yVel) {
        super(texture, x, y, width, height);
        mXVel = xVel;
        mYVel = yVel;
    }

    public float getXVel() {
        return mXVel;
    }

    public float getYVel() {
        return mYVel;
    }

    public float getOldX() {
        return mX - mXVel;
    }

    public float getOldY() {
        return mY - mYVel;
    }

    public void stepBack() {
        mX -= mXVel;
        mY -= mYVel;
    }

    public void reverseX() {
        mXVel *= -1;
    }

    public void reverseY() {
        mYVel *= -1;
    }

    public void setDead(boolean isDead) {
        mIsDead = isDead;
    }

    public void update() {
        mX += mXVel;
        mY += mYVel;

        if(mX < 0 || mX > BetterBreakout.GAME_WIDTH || mY < 0 || mY > BetterBreakout.GAME_HEIGHT) {
            mIsDead = true;
        }
    }
}
