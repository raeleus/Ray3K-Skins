package com.ray3k.cloudformui.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ray3k.cloudformui.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
                Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
                config.setWindowedMode(600, 600);
		new Lwjgl3Application(new Main(), config);
	}
}
