package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;

public class PauseScreen {
    private BitmapFont bigTitleFont;
    private GlyphLayout glyphLayout;
    private Button playButton;
    private Button quitButton;

    public PauseScreen(BitmapFont font, BitmapFont bigFont) {
        bigTitleFont = bigFont;
        glyphLayout = new GlyphLayout();
        playButton = new Button(font, "Resume", BetterBreakout.GAME_FULLWIDTH / 2, BetterBreakout.GAME_HEIGHT / 2 - 250);
        quitButton = new Button(font, "Exit", BetterBreakout.GAME_FULLWIDTH / 2 , BetterBreakout.GAME_HEIGHT / 2 - 400);
    }

    public void update() {
        if(playButton.isClicked() || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("click");
            BetterBreakout.setCurrentScreen(BetterBreakout.Screen.GAME);
        }
        if(quitButton.isClicked()) {
            System.out.println("click");
            BetterBreakout.setCurrentScreen(BetterBreakout.Screen.TITLE);
        }
    }

    public void draw(SpriteBatch batch) {
        glyphLayout = new GlyphLayout(bigTitleFont, "Paused");
        bigTitleFont.draw(batch, "Paused", BetterBreakout.GAME_FULLWIDTH / 2 - glyphLayout.width / 2, BetterBreakout.GAME_HEIGHT / 2 - glyphLayout.height/2 + 400);
        playButton.draw(batch);
        quitButton.draw(batch);
    }
}
