package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.powerup.PowerUp;

public class PowerUpIcon  {
    public static final float ICON_SIDE = 75;
    private Sprite timeLeftSprite;
    private long mDuration;
    private long expireTime;
    private Texture mTexture;

    public PowerUpIcon(PowerUp.Power powerUp, int power, int duration) {
        String path = "icon_test";
        if(
                powerUp == PowerUp.Power.SMALLPADDLE ||
                powerUp == PowerUp.Power.LARGEPADDLE ||
                powerUp == PowerUp.Power.LASER ||
                powerUp == PowerUp.Power.SHOTGUN
                ) {
            path = "icon_" + powerUp.toString() + ".png";
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
                }
                path = "icon_GOODPADDLESPEED.png";
            }
        }
        mTexture = new Texture(path);
        expireTime = System.currentTimeMillis() + duration;
        mDuration = duration;
        timeLeftSprite = new Sprite(new Texture("brick.png"));
        timeLeftSprite.setColor(0, 0, 1, 0.5f);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expireTime;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.draw(mTexture, x, y, ICON_SIDE, ICON_SIDE);
        if(!isExpired()) {
            timeLeftSprite.setBounds(x, y, ICON_SIDE, (expireTime - System.currentTimeMillis()) / (float)mDuration * ICON_SIDE);
            timeLeftSprite.draw(batch);
        }
    }
}
