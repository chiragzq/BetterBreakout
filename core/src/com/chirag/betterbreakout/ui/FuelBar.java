package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;

public class FuelBar {
    private float mHeight;
    private float mWidth;
    private float mX;
    private float mY;
    private float mFuel;
    private float mMaxFuel;
    private Sprite mHolderSprite;
    private Sprite mBarSprite;

    public FuelBar(Texture texture, float maxFuel) {
        mWidth = 40;
        mX = 0;
        mY = 0;
        mHeight = BetterBreakout.GAME_HEIGHT - mY;
        mMaxFuel = maxFuel;
        mFuel = mMaxFuel / 2;

        mHolderSprite = new Sprite(texture);
        mHolderSprite.setBounds(mX, mY, mWidth, mHeight);
        mBarSprite = new Sprite(texture);
        mBarSprite.setColor(Color.BLUE);
    }

    public void setMaxFuel(float amt) {
        mMaxFuel = amt;
    }

    public void setFuel(float amt) {
        mFuel = Math.min(amt, mMaxFuel);
    }

    public void draw(SpriteBatch batch) {
        mHolderSprite.draw(batch);
        mBarSprite.setBounds(mX, mY, mWidth, mFuel / mMaxFuel * mHeight);
        mBarSprite.draw(batch);
    }
}
