package com.chirag.betterbreakout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.chirag.betterbreakout.powerup.Particle;

public class BetterBreakout extends ApplicationAdapter {
	public static int deviceType; //0 = desktop, 1 = mobile
	public final static float scaleMult = 1f; //how much zoomed in it is
	private SpriteBatch batch;
	private Game game;
	private BitmapFont bitmapFont;
	private BitmapFont smallFont;
	public static final int GAME_WIDTH = 1680;
	public static final int GAME_PADDING = 240;
	public static final int GAME_HEIGHT = 1080;

	public BetterBreakout(int device) {
		deviceType = device;
		ControlsUtil.setDeviceType(device);
	}

	@Override
	public void create() {
		Particle.loadAllTextures();
		game = new Game(new Texture("brick.png"), new Texture("power.png"));
		batch = new SpriteBatch();
		Matrix4 transform = new Matrix4();
		transform.scale(1/scaleMult, 1/scaleMult,1/scaleMult);
		batch.setTransformMatrix(transform);
		bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"));
		smallFont = new BitmapFont(Gdx.files.internal("smallfont.fnt"));
	}

	@Override
	public void render() {
		game.update();
		TimeUtil.update();
		ControlsUtil.update();

		Gdx.gl.glClearColor(0.08f, 0.08f, 0.05f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		game.draw(bitmapFont, smallFont, batch);

		batch.end();

	}
	
	@Override
	public void dispose() {
		batch.dispose();
		bitmapFont.dispose();
	}
}
