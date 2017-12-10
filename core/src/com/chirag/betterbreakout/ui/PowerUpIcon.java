package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.powerup.PowerUp;

public class PowerUpIcon  {
    public static final float ICON_SIDE = 125;
    private Texture timeLeftTexture;
    private long mDuration;
    private long expireTime;
    private Texture mTexture;

    public PowerUpIcon(PowerUp.Power powerUp, int power, int duration) {
        String path = "icon_test";
        if(
                powerUp == PowerUp.Power.SMALLPADDLE ||
                powerUp == PowerUp.Power.LARGEPADDLE ||
                //powerUp == PowerUp.Power.LASER ||
                powerUp == PowerUp.Power.SHOTGUN
                ) {
            path = "icon_" + powerUp.toString() + ".png";
        } else if(powerUp == PowerUp.Power.LASER) {
            path = "icon_LASER2.png";
        } else {
            if(powerUp == PowerUp.Power.PADDLE_EFFICIENCY) {
                switch(power) {
                    case 0:
                        path = "icon_TERRIBLEFUELEFFECIENCY.png";
                        break;
                    case 1:
                        path = "icon_BADFUELEFFECIENCY.png";
                        break;
                    case 2:
                        path = "icon_GOODFUELEFFECIENCY.png";
                        break;
                    case 3:
                        path = "icon_AMAZINGFUELEFFECIENCY.png";
                        break;
                }
            } else {
                if(power == 0) {
                    path = "icon_BADPADDLESPEED.png";
                } else {
                    path = "icon_GOODPADDLESPEED.png";
                }
            }
        }
        mTexture = new Texture(path);
        expireTime = System.currentTimeMillis() + duration;
        mDuration = duration;
        timeLeftTexture = new Texture("solid.png");
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expireTime;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.setColor(211/256f, 164/256f, 0.2f, 1);
        batch.draw(timeLeftTexture, x, y, ICON_SIDE, ICON_SIDE);
        batch.setColor(Color.WHITE);
        if(!isExpired()) {
            batch.setColor(0.2f, 0.2f, 1, 1);
            batch.draw(timeLeftTexture, x, y, ICON_SIDE, (expireTime - System.currentTimeMillis()) / (float)mDuration * ICON_SIDE);
            batch.setColor(Color.WHITE);
        }
        batch.draw(mTexture, x, y, ICON_SIDE, ICON_SIDE);
    }
}
