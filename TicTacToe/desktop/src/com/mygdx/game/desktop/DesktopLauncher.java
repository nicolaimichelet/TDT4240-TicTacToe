package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ThinkOutsideTheBOX;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ThinkOutsideTheBOX.WIDTH;
		config.height = ThinkOutsideTheBOX.HEIGHT;
		config.title = ThinkOutsideTheBOX.TITLE;
		new LwjglApplication(new ThinkOutsideTheBOX(), config);
	}
}
