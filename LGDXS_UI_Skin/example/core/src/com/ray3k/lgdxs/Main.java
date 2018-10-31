package com.ray3k.lgdxs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("lgdxs-ui.json"));
        
        ProgressBarStyle progressBarStyle = skin.get("default-horizontal", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("progress-bar-horizontal");
        tiledDrawable = tiledDrawable.tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0);
        progressBarStyle.knobBefore = tiledDrawable;
        
        progressBarStyle = skin.get("default-vertical", ProgressBarStyle.class);
        tiledDrawable = skin.getTiledDrawable("progress-bar-vertical");
        tiledDrawable = tiledDrawable.tint(skin.getColor("color"));
        tiledDrawable.setMinHeight(0);
        progressBarStyle.knobBefore = tiledDrawable;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table top = new Table();
        root.add(top).colspan(2).growX().padTop(10.0f).padLeft(10.0f).padRight(10.0f);
        
        Button button = new Button(skin, "endcap-left1");
        top.add(button).width(100.0f);
        
        button = new Button(skin, "color5");
        top.add(button).padLeft(10.0f).width(75.0f);
        
        button = new Button(skin);
        top.add(button).padLeft(10.0f).width(25.0f);
        
        button = new Button(skin);
        top.add(button).padLeft(10.0f).width(75.0f);
        
        button = new Button(skin);
        top.add(button).padLeft(10.0f).growX();
        
        Label label = new Label("LGDXS UI", skin, "title-c2");
        top.add(label).padLeft(15.0f).padRight(15.0f);
        
        button = new Button(skin, "endcap-right1");
        top.add(button);
        
        root.row();
        Stack stack = new Stack();
        root.add(stack).padTop(25.0f).grow().padRight(30.0f).padLeft(10.0f).padBottom(75.0f);
        
        Table left = new Table();
        stack.add(left);
        
        Table table = new Table();
        left.add(table).growX();
        
        button = new Button(skin, "endcap-small-left1");
        table.add(button).top();
        
        button = new Button(skin, "small4");
        table.add(button).padLeft(5.0f).padRight(5.0f).top().width(15.0f);
        
        button = new Button(skin, "corner-bs-tr1");
        table.add(button).growX();
        
        final ProgressBar progressBar1 = new ProgressBar(0, 100, 1, false, skin);
        final ProgressBar progressBar2 = new ProgressBar(0, 100, 1, true, skin);
        
        left.row();
        TextButton textButton = new TextButton("PWR INCREASE", skin, "big3");
        textButton.getLabel().setAlignment(Align.bottomRight);
        left.add(textButton).right().padTop(5.0f).height(75.0f);
        
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                float value = progressBar1.getValue() + 10;
                value = MathUtils.clamp(value, 0, 100);
                progressBar1.setValue(value);
                progressBar2.setValue(value);
            }
        });
        
        left.row();
        textButton = new TextButton("DAMPENER", skin, "big1");
        textButton.getLabel().setAlignment(Align.bottomRight);
        left.add(textButton).right().padTop(5.0f).height(175.0f);
        
        left.row();
        button = new Button(skin, "big1");
        left.add(button).right().padTop(5.0f).height(25.0f);
        
        left.row();
        textButton = new TextButton("PWR DECREASE", skin, "big5");
        textButton.getLabel().setAlignment(Align.bottomRight);
        left.add(textButton).right().padTop(5.0f).growY();
        
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                float value = progressBar1.getValue() - 10;
                value = MathUtils.clamp(value, 0, 100);
                progressBar1.setValue(value);
                progressBar2.setValue(value);
            }
        });
        
        left.row();
        table = new Table();
        left.add(table).growX();
        
        button = new Button(skin, "endcap-small-left1");
        table.add(button).bottom();
        
        label = new Label("TACTICAL DIAGNOSTIC", skin, "subtitle-c2");
        table.add(label).bottom().padLeft(5.0f).padRight(5.0f);
        
        button = new Button(skin, "corner-bs-br1");
        table.add(button).growX().padTop(5.0f);
        
        Table overlay = new Table();
        stack.add(overlay);
        
        table = new Table();
        overlay.add(table).padRight(110.0f);
        
        progressBar1.setValue(50);
        table.add(progressBar1).size(141, 78);
        
        table.row();
        progressBar2.setValue(50);
        table.add(progressBar2).padTop(50.0f).size(77, 140);
        
        stack = new Stack();
        root.add(stack).grow().padLeft(5.0f).padTop(75.0f).padRight(10.0f).padBottom(10.0f);
        
        Table right = new Table();
        stack.add(right);
        
        table = new Table();
        right.add(table).growX();
        
        button = new Button(skin, "corner-bm-tl1");
        table.add(button).growX();
        
        label = new Label("UPLOAD", skin, "title-c2");
        table.add(label).padLeft(15.0f).padRight(15.0f).top();
        
        button = new Button(skin, "endcap-right1");
        table.add(button).top();
        
        right.row();
        button = new Button(skin, "big1");
        right.add(button).left().padTop(5.0f).height(25.0f);
        
        right.row();
        textButton = new TextButton("TORPEDO", skin, "big4");
        textButton.getLabel().setAlignment(Align.bottomRight);
        right.add(textButton).left().padTop(5.0f).growY();
        
        right.row();
        textButton = new TextButton("PHASER", skin, "big1");
        textButton.getLabel().setAlignment(Align.bottomRight);
        right.add(textButton).left().padTop(5.0f).growY();
        
        right.row();
        textButton = new TextButton("DISRUPTOR", skin, "big2");
        textButton.getLabel().setAlignment(Align.bottomRight);
        right.add(textButton).left().padTop(5.0f).growY();
        
        right.row();
        table = new Table();
        right.add(table).growX().padTop(5.0f);
        
        textButton = new TextButton("Teleporter", skin, "corner-bs-bl1");
        textButton.getLabel().setAlignment(Align.topRight);
        table.add(textButton);
        
        button = new Button(skin, "small1");
        table.add(button).padLeft(5.0f).growX().bottom();
        
        button = new Button(skin, "small2");
        table.add(button).padLeft(5.0f).width(25.0f).bottom();
        
        button = new Button(skin, "endcap-small-right1");
        table.add(button).padLeft(5.0f).bottom();
        
        overlay = new Table();
        stack.add(overlay);
        
        table = new Table();
        table.defaults().pad(5.0f);
        overlay.add(table).padLeft(110.0f).padTop(50.0f + 25.0f).right().expandX();
        
        textButton = new TextButton("PLAY", skin, "oval2");
        textButton.getLabel().setAlignment(Align.right);
        table.add(textButton);
        
        textButton = new TextButton("WARP", skin, "oval4");
        textButton.getLabel().setAlignment(Align.right);
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("IMPULSE", skin, "oval3");
        textButton.getLabel().setAlignment(Align.right);
        table.add(textButton);
        
        textButton = new TextButton("ENGAGE", skin, "oval5");
        textButton.getLabel().setAlignment(Align.right);
        table.add(textButton);
        
        overlay.row();
        Image image = new Image(skin, "logo");
        image.setScaling(Scaling.fillX);
        overlay.add(image).padLeft(110.0f + 15.0f).padRight(15.0f).expandY().padBottom(27.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            dispose();
            create();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
