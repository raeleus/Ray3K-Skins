package com.ray3k.oseight.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ray3k.oseight.Core;

public class DesktopLauncher {

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 800);
        config.setWindowIcon("icons/icon-16.png", "icons/icon-24.png", "icons/icon-32.png", "icons/icon-64.png", "icons/icon-128.png", "icons/icon-256.png", "icons/icon-512.png");
        new Lwjgl3Application(new Core(), config);
    }
}
