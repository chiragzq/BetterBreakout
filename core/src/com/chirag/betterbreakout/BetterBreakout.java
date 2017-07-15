package com.chirag.betterbreakout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BetterBreakout extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Game game;
	static final int GAME_WIDTH = 1920;
	static final int GAME_HEIGHT = 1080;

	@Override
	public void create () {
		game = new Game(new Texture("brick.png"));
		batch = new SpriteBatch();
		img = new Texture("brick.png");
	}

	@Override
	public void render () {
		game.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		game.draw(batch);

		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
