package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;
import com.chirag.betterbreakout.Game;

public class LoseScreen {
    private BitmapFont bigTitleFont;
    private BitmapFont smallFont;
    private GlyphLayout glyphLayout;
    private Button restartButton;
    private Button exitButton;

    public LoseScreen(BitmapFont font, BitmapFont bigFont) {
        smallFont = font;
        bigTitleFont = bigFont;
        glyphLayout = new GlyphLayout();
        restartButton = new Button(font, "Restart", BetterBreakout.GAME_FULLWIDTH / 2, BetterBreakout.GAME_HEIGHT / 2 - 150);
        exitButton = new Button(font, "Exit", BetterBreakout.GAME_FULLWIDTH / 2 , BetterBreakout.GAME_HEIGHT / 2 - 300);
    }

    public void update() {
        if(restartButton.isClicked()) {
            System.out.println("click");
            Game.score = 0;
            BetterBreakout.setCurrentScreen(BetterBreakout.Screen.GAME);
        }
        if(exitButton.isClicked()) {
            Game.score = 0;
            System.out.println("click");
            BetterBreakout.setCurrentScreen(BetterBreakout.Screen.TITLE);
        }
    }

    public void draw(SpriteBatch batch) {
        glyphLayout = new GlyphLayout(bigTitleFont, "Game Over");
        bigTitleFont.draw(batch, "Game Over", BetterBreakout.GAME_FULLWIDTH / 2 - glyphLayout.width / 2, BetterBreakout.GAME_HEIGHT / 2 - glyphLayout.height/2 + 400);
        glyphLayout = new GlyphLayout(smallFont, "Score: " + com.chirag.betterbreakout.Game.score);
        smallFont.draw(batch, "Score: " + com.chirag.betterbreakout.Game.score, BetterBreakout.GAME_FULLWIDTH / 2 - glyphLayout.width / 2, BetterBreakout.GAME_HEIGHT / 2 - glyphLayout.height/2 + 200);
        restartButton.draw(batch);
        exitButton.draw(batch);
    }
}
