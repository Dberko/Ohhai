package com.berko.ohhai.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.berko.ohhai.Ohhai;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = Ohhai.WIDTH;
        config.height = Ohhai.HEIGHT;
        config.title = Ohhai.TITLE;
		new LwjglApplication(new Ohhai(), config);
	}
}
