package com.ray3k.cleancrispyui.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.ray3k.cleancrispyui.Main;
import com.ray3k.cleancrispyui.WindowWorker;

public class DesktopLauncher implements WindowWorker {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(600, 700);
        config.setDecorated(false);
        Main main = new Main();
        main.setWindowWorker(new DesktopLauncher());
        new Lwjgl3Application(main, config);
    }
    
    @Override
    public void reposition(int x, int y) {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        Lwjgl3Window window = g.getWindow();
        
        window.setPosition(x, y);
    }
    
    @Override
    public int getPositionX() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        Lwjgl3Window window = g.getWindow();
        
        return window.getPositionX();
    }
    
    @Override
    public int getPositionY() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        Lwjgl3Window window = g.getWindow();
        
        return window.getPositionY();
    }

    @Override
    public void iconify() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        Lwjgl3Window window = g.getWindow();
        
        window.iconifyWindow();
    }
}
