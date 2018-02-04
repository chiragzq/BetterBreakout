package com.chirag.betterbreakout;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.chirag.betterbreakout.powerup.Particle;
import com.chirag.betterbreakout.ui.LoseScreen;
import com.chirag.betterbreakout.ui.PauseScreen;
import com.chirag.betterbreakout.ui.TitleScreen;
import com.chirag.betterbreakout.ui.Tutorial;

public class BetterBreakout extends ApplicationAdapter {
	public enum Screen {
		TITLE, LOSE, PAUSE, GAME, TUTORIAL
	}

	private static Screen currentScreen;

	public static int deviceType; //0 = desktop, 1 = mobile
	public final static float appSizeScale = 1f; //how much zoomed in it is
	public static float scaleX;
	public static float scaleY;
	private SpriteBatch batch;
	private BitmapFont smallFont;
	private BitmapFont normalFont;
	private BitmapFont titleFont;
	private BitmapFont bigTitleFont;
	private Game game;
	private PauseScreen pauseScreen;
	private LoseScreen loseScreen;
	private TitleScreen titleScreen;
	private Tutorial tutorial;
	public static final int GAME_WIDTH = 1680;
	public static final int GAME_PADDING = 240;
	public static final int GAME_HEIGHT = 1080;
	public static final int GAME_FULLWIDTH = 1920;

	public BetterBreakout(int device) {
		currentScreen = Screen.TITLE;
		deviceType = device;
		ControlsUtil.setDeviceType(device);
	}

	@Override
	public void create() {
		Particle.loadAllTextures();
		initializeFonts();
		scaleX = 1920f / Gdx.app.getGraphics().getWidth();
		scaleY = 1080f / Gdx.app.getGraphics().getHeight();
		System.out.println(Gdx.app.getGraphics().getWidth() + " " + Gdx.app.getGraphics().getHeight());
		System.out.println(scaleX + " " + scaleY);
		loseScreen = new LoseScreen(titleFont, bigTitleFont);
		pauseScreen = new PauseScreen(titleFont, bigTitleFont);
		titleScreen = new TitleScreen(titleFont, bigTitleFont);
		tutorial = new Tutorial();
		game = new Game(new Texture("brick.png"), new Texture("power.png"));

		Matrix4 transform = new Matrix4();
		transform.scale(1 / scaleX, 1 / scaleY, 1 / appSizeScale);
		batch = new SpriteBatch();
		batch.setTransformMatrix(transform);
	}

	@Override
	public void render() {
		TimeUtil.active = currentScreen == Screen.GAME;
		TimeUtil.update();
		ControlsUtil.update();

		switch(currentScreen) {
			case GAME:
				game.update();
				break;
			case LOSE:
				loseScreen.update();
				break;
			case PAUSE:
				pauseScreen.update();
				break;
			case TITLE:
				titleScreen.update();
				break;
			case TUTORIAL:
				tutorial.update();
				if(tutorial.isDone()) {
					currentScreen = Screen.TITLE;
					tutorial.reset();
				}
				break;
		}

		Gdx.gl.glClearColor(0.08f, 0.08f, 0.05f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		switch(currentScreen) {
			case GAME:
				game.draw(smallFont, batch);
				break;
			case LOSE:
				loseScreen.draw(batch);
				break;
			case PAUSE:
				pauseScreen.draw(batch);
				break;
			case TITLE:
				titleScreen.draw(batch);
				break;
			case TUTORIAL:
				tutorial.draw(batch);
				break;
		}

		batch.end();

	}

	@Override
	public void pause() {
		if(deviceType == 1 && currentScreen == Screen.GAME)
			pause2();
	}

	static void pause2() {
		currentScreen = Screen.PAUSE;
	}

	@Override
	public void dispose() {
		batch.dispose();
		normalFont.dispose();
		titleFont.dispose();
		bigTitleFont.dispose();
		smallFont.dispose();
	}

	private void initializeFonts() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Bangers (1).ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.size = 80;
		titleFont = generator.generateFont(parameter);
		parameter.size = 120;
		bigTitleFont = generator.generateFont(parameter);
		parameter.size = 60;
		normalFont = generator.generateFont(parameter);
		parameter.size = 40;
		smallFont = generator.generateFont(parameter);
	}

	public static void setCurrentScreen(Screen screen) {
		currentScreen = screen;
	}
}