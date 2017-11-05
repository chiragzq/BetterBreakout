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

    public PowerUpIcon(PowerUp.Power powerUp, int duration) {
        mTexture = new Texture("icon_" + //powerUp.toString()); //TODO
                "test.png");
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
