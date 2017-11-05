package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.DeleteableGameElement;

public class PowerUp implements DeleteableGameElement {
    public enum Power {
        ADDBALL, LARGEPADDLE, SMALLPADDLE, LASER, SHOTGUN, RANDOM, PADDLE_EFFICIENCY, PADDLE_SPEED
    }

    public static final Power[] POWERS = {Power.PADDLE_SPEED, Power.ADDBALL, Power.LARGEPADDLE, Power.SMALLPADDLE, Power.LASER, Power.SHOTGUN, Power.PADDLE_EFFICIENCY};
    private Sprite mSprite;
    private float mX;
    private float mY;
    private boolean mIsDead;

    public PowerUp(Texture texture, int x, int y, int width, int height) {
        mSprite = new Sprite(texture);
        mSprite.setBounds(x, y, width, height);
        mIsDead = false;
        mX = x;
        mY = y;
    }

    //Getters
    public float getX() {
        return mX;
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
        mY -= 3;
        if(mY < 0) {
            mIsDead = true;
        }
    }

    public void draw(SpriteBatch batch) {
        mSprite.setCenter(mX, mY);
        mSprite.draw(batch);
    }

}
