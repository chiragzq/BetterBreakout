package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;

public class PauseButton {
    private boolean canClick = true;
    private float mX;
    private float mY;
    private float mSize;
    private Texture mTexture = new Texture("pause.png");

    //x and y are center
    public PauseButton(float x, float y, float size) {
        mX = x;
        mY = y;
        mSize = size;
    }

    public boolean isClicked() {
        if(!canClick) {
            if(BetterBreakout.deviceType == 0) {
                if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    canClick = true;
                }
            } else {
                if(!Gdx.input.isTouched(0)) {
                    canClick = true;
                }
            }
            return false;
        }

        int x = (int)(Gdx.input.getX() * BetterBreakout.scaleX);
        int y = (int)(BetterBreakout.GAME_HEIGHT - Gdx.input.getY() * BetterBreakout.scaleY);
        if(BetterBreakout.deviceType == 0) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                if(mX - mSize / 2 < x && x < mX + mSize / 2 && mY - mSize / 2 < y && y < mY + mSize / 2) {
                    canClick = false;
                    return true;
                }
            }
        } else {
            for(int i = 0; i < 9;i ++) {
                if(Gdx.input.isTouched(i)) {
                    x = Gdx.input.getX(i);
                    y = BetterBreakout.GAME_HEIGHT - Gdx.input.getY(i);
                    if(mX - mSize / 2 < x && x < mX + mSize / 2 && mY - mSize / 2 < y && y < mY + mSize / 2) {
                        canClick = false;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(mTexture, mX - mSize / 2, mY - mSize / 2, mSize, mSize);
    }
}
