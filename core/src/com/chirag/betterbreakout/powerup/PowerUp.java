package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.DeleteableGameElement;

public class PowerUp implements DeleteableGameElement {
    public enum Power {
        ADDBALL, LARGEPADDLE, SMALLPADDLE, LASER, BOMB, SHOTGUN, RANDOM
    }

    private Power mPower;
    private Sprite mSprite;
    private float mX;
    private float mY;
    private boolean mIsDead;

    public PowerUp(Power power, Texture texture, int x, int y, int width, int height) {
        mSprite = new Sprite(texture);
        mSprite.setBounds(x, y, width, height);
        mIsDead = false;
        mX = x;
        mY = y;

        if(power != Power.RANDOM) {
            this.mPower = power;
        } else {
            switch((int) (Math.random() * 5)) {
                case 0:
                    mPower = Power.ADDBALL;
                    break;
                case 1:
                    mPower = Power.LARGEPADDLE;
                    break;
                case 2:
                    mPower = Power.SMALLPADDLE;
                    break;
                case 3:
                    mPower = Power.LASER;
                    break;
                case 4:
                    mPower = Power.SHOTGUN;
            }
        }

    }

    //Getters
    public float getX() {
        return mX;
    }

    public Power getPower() {
        return mPower;
    }

    public Sprite getSprite() {
        mSprite.setCenter(mX, mY);
        return mSprite;
    }

    public boolean isDead() {
        return mIsDead;
    }

    //Setters

    //Main
    public void update() {
        mY -= 2;
        if(mY < 0) {
            mIsDead = true;
        }
    }

    public void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.draw(batch);
    }

    //Helpers
    void hitPaddle() {
        mIsDead = true;
    }
}
