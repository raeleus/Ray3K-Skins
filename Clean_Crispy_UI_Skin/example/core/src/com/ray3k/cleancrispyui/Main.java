package com.ray3k.cleancrispyui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private WindowWorker windowWorker;
    private float dragStartX, dragStartY;
    private boolean fullscreen;
    private static final int MIN_WIDTH = 100;
    private static final int MIN_HEIGHT = 100;
    
    @Override
    public void create() {
        fullscreen = false;
        skin = new Skin(Gdx.files.internal("clean-crispy-ui.json"));
        
        ProgressBarStyle progressBarStyle = skin.get("tiled", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("progressbar-tiled").tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0.0f);
        progressBarStyle.knobBefore = tiledDrawable;
        
        progressBarStyle = skin.get("tiled-big", ProgressBarStyle.class);
        tiledDrawable = skin.getTiledDrawable("progressbar-knob-big").tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0.0f);
        progressBarStyle.knobBefore = tiledDrawable;
        
        SliderStyle sliderStyle = skin.get("tick", SliderStyle.class);
        tiledDrawable = skin.getTiledDrawable("slider-tick").tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0.0f);
        sliderStyle.background = tiledDrawable;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Window window = new Window("Clean Crispy UI", skin, "main");
        window.getTitleLabel().setAlignment(Align.center);
        window.setFillParent(true);
        stage.addActor(window);
        
        window.getTitleTable().defaults().pad(5.0f);
        Button button = new Button(skin, "minimize");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                windowWorker.iconify();
            }
        });
        window.getTitleTable().add(button);
        
        button = new Button(skin, "maximize");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                toggleFullscreen();
            }
        });
        window.getTitleTable().add(button);
        
        button = new Button(skin, "close");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        window.getTitleTable().add(button);
        
        window.getTitleTable().setTouchable(Touchable.enabled);
        window.getTitleTable().addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                windowWorker.reposition(windowWorker.getPositionX() + (int) (x - dragStartX),windowWorker.getPositionY() -  (int) (y - dragStartY));
            }

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                dragStartX = x;
                dragStartY = y;
            }
        });
        
        Table top = new Table();
        Table bottom = new Table();
        SplitPane splitPane = new SplitPane(top, bottom, true, skin);
        window.add(splitPane).grow();
        
        TextButton textButton = new TextButton("Text Button", skin);
        top.add(textButton);
        
        textButton = new TextButton("Toggle", skin, "toggle");
        top.add(textButton);
        
        top.row();
        ImageTextButton checkBox = new ImageTextButton("Check Box", skin, "checkbox");
        top.add(checkBox);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton radio = new ImageTextButton("Radio Button", skin, "radio");
        buttonGroup.add(radio);
        top.add(radio);
        
        top.row();
        checkBox = new ImageTextButton("Check Box", skin, "checkbox");
        top.add(checkBox);
        
        radio = new ImageTextButton("Radio Button", skin, "radio");
        buttonGroup.add(radio);
        top.add(radio);
        
        top.row();
        checkBox = new ImageTextButton("Check Box", skin, "checkbox");
        top.add(checkBox);
        
        radio = new ImageTextButton("Radio Button", skin, "radio");
        buttonGroup.add(radio);
        top.add(radio);
        
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        final Slider slider = new Slider(0, 100, 1, false, skin);
        final ProgressBar progressBarTiled = new ProgressBar(0, 100, 1, false, skin, "tiled");
        final Slider sliderTick = new Slider(0, 100, 1, false, skin, "tick");
        final ProgressBar progressBarTiledBig = new ProgressBar(0, 100, 1, false, skin, "tiled-big");
        
        top.row();
        top.add(progressBar);
        
        top.add(slider);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
                progressBarTiled.setValue(slider.getValue());
                sliderTick.setValue(slider.getValue());
                progressBarTiledBig.setValue(slider.getValue());
            }
        });
        
        top.row();
        top.add(progressBarTiled);
        
        top.add(sliderTick);
        sliderTick.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(sliderTick.getValue());
                slider.setValue(sliderTick.getValue());
                progressBarTiled.setValue(sliderTick.getValue());
                progressBarTiledBig.setValue(sliderTick.getValue());
            }
        });
        
        top.row();
        top.add(progressBarTiledBig);
        
        bottom.add(new Label("Name: ", skin)).right();
        
        TextField textField = new TextField("", skin);
        bottom.add(textField).colspan(2);
        
        bottom.row();
        bottom.add(new Label("Password: ", skin)).right();
        
        textField = new TextField("", skin);
        textField.setPasswordMode(true);
        textField.setPasswordCharacter('•');
        bottom.add(textField).colspan(2);
        
        bottom.row();
        Touchpad touchpad = new Touchpad(0.0f, skin);
        bottom.add(touchpad);
        
        textButton = new TextButton("A", skin, "arcade");
        bottom.add(textButton).pad(10.0f);
        
        textButton = new TextButton("B", skin, "arcade");
        bottom.add(textButton).pad(10.0f);
        
        bottom.row();
        TooltipManager.getInstance().subsequentTime = 0;
        TooltipManager.getInstance().initialTime = 0;
        TooltipManager.getInstance().resetTime = 0;
        TooltipManager.getInstance().hideAll();
        Label label = new Label("Hover over me", skin);
        label.addListener(new TextTooltip("ray3k.wordpress.com", skin));
        bottom.add(label).pad(10.0f);
        
        bottom.row();
        Tree tree = new Tree(skin);
        Node parent = new Node(new Label("top", skin));
        tree.add(parent);
        
        Node child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        parent.expandAll();        
        
        parent = new Node(new Label("top", skin));
        tree.add(parent);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        
        child = new Node(new Label("child", skin));
        parent.add(child);
        tree.expandAll();
        
        ScrollPane scrollPane = new ScrollPane(tree, skin);
        scrollPane.setFadeScrollBars(false);
        bottom.add(scrollPane).colspan(3).width(300.0f);
        
        window.row();
        Table statusBar = new Table();
        window.add(statusBar).growX().height(15.0f);
        
        Image resize = new Image(skin, "resize");
        resize.setTouchable(Touchable.enabled);
        statusBar.add(resize).expandX().right().padRight(2.0f);
        resize.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                resizeWindow(x, y);
            }

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                dragStartX = x;
                dragStartY = y;
            }
        });
        
        Window popWindow = new Window("Are you sure?", skin);
        popWindow.setSize(150.0f, 100.0f);
        popWindow.getTitleLabel().setAlignment(Align.center);
        popWindow.align(Align.bottomRight);
        popWindow.setPosition(Gdx.graphics.getWidth(), 75.0f);
        window.addActor(popWindow);
    }

    private void resizeWindow(float x, float y) {
        if (!fullscreen) {
            int oldWidth = Gdx.graphics.getWidth();
            int newWidth = oldWidth + (int) (x - dragStartX);
            if (newWidth < MIN_WIDTH) {
                newWidth = MIN_WIDTH;
            }
            int oldHeight = Gdx.graphics.getHeight();
            int newHeight = oldHeight - (int) (y - dragStartY);
            if (newHeight < MIN_HEIGHT) {
                newHeight = MIN_HEIGHT;
            }
            Gdx.graphics.setWindowedMode(newWidth, newHeight);

            //compensate for new width because image is aligned to the right
            dragStartX = x + oldWidth - Gdx.graphics.getWidth();
            //compensate for new height because image is aligned to the bottom
            dragStartY = y - oldHeight + Gdx.graphics.getHeight();
        }
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public WindowWorker getWindowWorker() {
        return windowWorker;
    }

    public void setWindowWorker(WindowWorker windowWorker) {
        this.windowWorker = windowWorker;
    }
    
    public void toggleFullscreen() {
        if (fullscreen) {
            Gdx.graphics.setWindowedMode(600, 700);
            stage.getViewport().update(600, 700, true);
            fullscreen = false;
        } else {
            DisplayMode displayMode = null;
            for (DisplayMode mode : Gdx.graphics.getDisplayModes()) {
                if (displayMode == null) {
                    displayMode = mode;
                } else if (displayMode.width < mode.width) {
                    displayMode = mode;
                }
            }
            Gdx.graphics.setFullscreenMode(displayMode);
            fullscreen = true;
        }
    }
}
