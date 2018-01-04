package com.chirag.betterbreakout.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.BetterBreakout;

public class TitleScreen {
    private BitmapFont bigTitleFont;
    private GlyphLayout glyphLayout;
    private Button playButton;
    private Button quitButton;

    public TitleScreen(BitmapFont font, BitmapFont bigFont) {
        bigTitleFont = bigFont;
        glyphLayout = new GlyphLayout();
        playButton = new Button(font, "Play", BetterBreakout.GAME_FULLWIDTH / 2, BetterBreakout.GAME_HEIGHT / 2);
        quitButton = new Button(font, "Quit", BetterBreakout.GAME_FULLWIDTH / 2 , BetterBreakout.GAME_HEIGHT / 2 - 150);
    }

    public void update() {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println("Click at: " + Gdx.input.getX() + " " + (BetterBreakout.GAME_HEIGHT - Gdx.input.getY()));
            System.out.println("Actual click at: " + (Gdx.input.getX() * BetterBreakout.scaleX) + " " + (BetterBreakout.GAME_HEIGHT - Gdx.input.getY() * BetterBreakout.scaleY));
            System.out.println("Button at: " + playButton.mX + " " + playButton.mY);
        }
        if(playButton.isClicked()) {
            System.out.println("click");
            BetterBreakout.setCurrentScreen(BetterBreakout.Screen.GAME);
        }
        if(quitButton.isClicked()) {
            System.out.println("click");
            Gdx.app.exit();
        }
    }

    public void draw(SpriteBatch batch) {
        glyphLayout = new GlyphLayout(bigTitleFont, "Better Breakout");
        bigTitleFont.draw(batch, "Better Breakout", BetterBreakout.GAME_FULLWIDTH / 2 - glyphLayout.width / 2, BetterBreakout.GAME_HEIGHT / 2 - glyphLayout.height/2 + 400);
        playButton.draw(batch);
        quitButton.draw(batch);
    }
}
