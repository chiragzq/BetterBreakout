package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;

public class Tutorial {
    private Texture[] screens;
    private int screen;

    public Tutorial() {
        screens = new Texture[7];
        for(int i = 0;i < 7;i ++) {
            screens[i] = new Texture("tutorial" + (i+1) + ".png");
        }
        screen = 1;
    }

    public void update() {
        if(Gdx.input.justTouched()) {
            screen++;
        }
    }

    public void draw(SpriteBatch batch) {
        if(screen > 0 && screen < 8) {
            batch.draw(screens[screen - 1], 0, 0, BetterBreakout.GAME_FULLWIDTH, BetterBreakout.GAME_HEIGHT);
        }
    }

    public boolean isDone() {
        return screen == 8;
    }

    public void reset() {
        screen = 1;
    }
}
