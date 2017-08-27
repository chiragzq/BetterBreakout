package com.chirag.betterbreakout;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class PowerUp implements DeleteableGameElement{
    public enum Power {
        ADDBALL, LARGEPADDLE, SMALLPADDLE, RANDOM
    }

    private Power mPower;
    private Sprite mSprite;
    private float mX;
    private float mY;
    private boolean mIsDead;

    PowerUp(Power power, Texture texture, int x, int y, int width, int height) {
        mSprite = new Sprite(texture);
        mSprite.setBounds(x, y, width, height);
        mIsDead = false;
        mX = x;
        mY = y;

        if(power != Power.RANDOM) {
            this.mPower = power;
        } else {
            switch((int) (Math.random() * 3)) {
                case 0:
                    this.mPower = Power.ADDBALL;
                    break;
                case 1:
                    this.mPower = Power.LARGEPADDLE;
                    break;
                case 2:
                    this.mPower = Power.SMALLPADDLE;
                    break;
            }
        }

    }

    //Getters
    Power getPower() {
        return mPower;
    }

    Sprite getSprite() {
        mSprite.setCenter(mX, mY);
        return mSprite;
    }
    public boolean isDead() {
        return mIsDead;
    }

    //Setters

    //Main
    void update() {
        mY -= 2;
        if(mY < 0) {
            mIsDead = true;
        }
    }

    void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.draw(batch);
    }

    //Helpers
    void hitPaddle() {
        mIsDead = true;
    }
}
