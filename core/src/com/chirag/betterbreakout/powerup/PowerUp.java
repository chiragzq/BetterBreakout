package com.chirag.betterbreakout.powerup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.DeleteableGameElement;
import com.chirag.betterbreakout.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PowerUp implements DeleteableGameElement {
    public enum Power {
        ADDBALL, LARGEPADDLE, SMALLPADDLE, LASER, SHOTGUN, RANDOM, PADDLE_EFFICIENCY
    }

    private static final Power[] POWERS = {Power.ADDBALL, Power.LARGEPADDLE, Power.SMALLPADDLE, Power.LASER, Power.SHOTGUN, Power.PADDLE_EFFICIENCY};
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
            List<Power> allowedPowerups = new ArrayList<Power>(Arrays.asList(POWERS));
            int extraBallCount = 0;
            for(Power p : Game.activePowerups) {
                if(p == Power.ADDBALL) {
                    extraBallCount++;
                } else if(p == Power.SMALLPADDLE || p == Power.LARGEPADDLE) {
                    allowedPowerups.remove(Power.LARGEPADDLE);
                    allowedPowerups.remove(Power.SMALLPADDLE);
                } else if(p == Power.SHOTGUN) {
                    allowedPowerups.remove(Power.SHOTGUN);
                } else if(p == Power.PADDLE_EFFICIENCY) {
                    allowedPowerups.remove(Power.PADDLE_EFFICIENCY);
                }
                if(extraBallCount == 3) {
                    allowedPowerups.remove(Power.ADDBALL);
                }
            }
            mPower = allowedPowerups.get((int)(Math.random() * allowedPowerups.size()));
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
