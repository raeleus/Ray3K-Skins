package com.ray3k.skincomposerui.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3WindowListener;
import com.ray3k.skincomposerui.Main;
import com.ray3k.skincomposerui.WindowWorker;

public class DesktopLauncher implements Lwjgl3WindowListener, WindowWorker {
    
    private WindowState windowState;
    private WindowState previousState;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 800);
        config.setWindowSizeLimits(100, 100, -1, -1);
        config.setDecorated(false);
        DesktopLauncher desktopLauncher = new DesktopLauncher();
        Main main = new Main(desktopLauncher);
        config.setWindowListener(desktopLauncher);
        new Lwjgl3Application(main, config);
    }

    public DesktopLauncher() {
        windowState = WindowState.RESTORED;
    }

    @Override
    public void iconified(boolean isIconified) {
        if (isIconified) {
            previousState = windowState;
            windowState = WindowState.ICONIFIED;
        } else {
            if (previousState == WindowState.RESTORED) {
                previousState = windowState;
                windowState = WindowState.RESTORED;
            } else if (previousState == WindowState.MAXIMIZED) {
                previousState = windowState;
                windowState = WindowState.MAXIMIZED;
            }
        }
    }

    @Override
    public void maximized(boolean isMaximized) {
        if (isMaximized) {
            previousState = windowState;
            windowState = WindowState.MAXIMIZED;
        } else {
            previousState = windowState;
            windowState = WindowState.RESTORED;
        }
    }

    @Override
    public void focusLost() {
    }

    @Override
    public void focusGained() {
    }

    @Override
    public boolean closeRequested() {
        return true;
    }

    @Override
    public void filesDropped(String[] files) {
    }

    @Override
    public void refreshRequested() {
    }

    @Override
    public void minimize() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        g.getWindow().iconifyWindow();
    }

    @Override
    public void maximize() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        g.getWindow().maximizeWindow();
    }

    @Override
    public void restore() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        g.getWindow().restoreWindow();
    }

    @Override
    public WindowState getWindowState() {
        return windowState;
    }

    @Override
    public void setPosition(int x, int y) {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        g.getWindow().setPosition(x, y);
    }

    @Override
    public void center() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        int diffWidth = g.getDisplayMode().width - g.getWidth();
        int diffHeight = g.getDisplayMode().height - g.getHeight();
        g.getWindow().setPosition(diffWidth / 2, diffHeight / 2);
    }

    @Override
    public int getPositionX() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        return g.getWindow().getPositionX();
    }
    
    @Override
    public int getPositionY() {
        Lwjgl3Graphics g = (Lwjgl3Graphics) Gdx.graphics;
        return g.getWindow().getPositionY();
    }
}
