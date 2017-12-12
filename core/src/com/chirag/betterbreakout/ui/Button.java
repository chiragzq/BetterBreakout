package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;

public class Button {
    private boolean canClick = true;
    public float mX;
    public float mY;
    private float mWidth;
    private float mHeight;
    private BitmapFont mFont;
    private String mText;

    //x and y are center
    public Button(BitmapFont font, String text, float x, float y) {
        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        mFont = font;
        mWidth = (int)(glyphLayout.width * 1.4);
        mHeight = (int)(glyphLayout.height * 1.4);
        mX = x;
        mY = y;
        mText = text;
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
                if(mX - mWidth / 2 < x && x < mX + mWidth / 2 && mY - mHeight / 2 < y && y < mY + mHeight / 2) {
                    canClick = false;
                    return true;
                }
            }
        } else {
            for(int i = 0; i < 9;i ++) {
                if(Gdx.input.isTouched(i)) {
                    x = Gdx.input.getX(i);
                    y = BetterBreakout.GAME_HEIGHT - Gdx.input.getY(i);
                    if(mX - mWidth / 2 < x && x < mX + mWidth / 2 && mY - mHeight / 2 < y && y < mY + mHeight / 2) {
                        canClick = false;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        GlyphLayout glyphLayout = new GlyphLayout(mFont, mText);
        mFont.draw(batch, mText, mX - glyphLayout.width / 2, mY + glyphLayout.height / 2);
    }
}
