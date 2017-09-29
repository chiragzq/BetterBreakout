package com.chirag.betterbreakout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chirag.betterbreakout.powerup.Particle;

public class BetterBreakout extends ApplicationAdapter {
	SpriteBatch batch;
	Game game;
	BitmapFont bitmapFont;
	public static final int GAME_WIDTH = 1920;
	public static final int GAME_HEIGHT = 1080;

	@Override
	public void create () {
		Particle.loadAllTextures();
		game = new Game(new Texture("brick.png"), new Texture("power.png"));
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));
	}

	@Override
	public void render () {
		game.update();
		TimeUtil.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		game.draw(bitmapFont, batch);

		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bitmapFont.dispose();
	}
}
