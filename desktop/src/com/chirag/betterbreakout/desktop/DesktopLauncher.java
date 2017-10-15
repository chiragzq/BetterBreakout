package com.chirag.betterbreakout.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chirag.betterbreakout.BetterBreakout;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)(1920 / BetterBreakout.scaleMult);
		config.height = (int)(1080 / BetterBreakout.scaleMult);
		new LwjglApplication(new BetterBreakout(0), config);
	}
}
