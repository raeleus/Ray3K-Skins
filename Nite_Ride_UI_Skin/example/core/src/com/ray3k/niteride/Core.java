package com.ray3k.niteride;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Core extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private float cityOffsetX;
    private float rainOffsetX;
    private float rainOffsetY;
    private float progressPercent;
    
    @Override
    public void create() {
        skin = new Skin(new TextureAtlas("nite-ride-ui.atlas"));
        skin.add("default", skin, Skin.class);
        skin.load(Gdx.files.internal("nite-ride-ui.json"));
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Stack stack = new Stack();
        root.add(stack).growX().height(220.0f);
        
        Image image = new Image(skin, "city-background");
        image.setScaling(Scaling.stretch);
        stack.add(image);
        
        Table table = new Table();
        stack.add(table);
        image = new Image(skin, "city-scrolling");
        image.setScaling(Scaling.stretch);
        table.add(image).growX().height(138.0f).bottom().expandY();
        
        image = new Image(skin, "rain-scrolling");
        image.setScaling(Scaling.stretch);
        stack.add(image);
        
        root.row();
        table = new Table();
        table.setName("scrollTable");
        ScrollPane scrollPane = new ScrollPane(table, skin);
        root.add(scrollPane).grow();
        stage.setScrollFocus(scrollPane);
        
        Label label = new Label("Running in the City - Nite Ride UI", skin, "header");
        table.add(label).growX();
        
        table.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Calories", "Metabolism", "Cardio", "Core", "Tone", "Aerobic", "Recovery", "Reps", "Interval");
        selectBox.setAlignment(Align.center);
        selectBox.getList().setAlignment(Align.center);
        table.add(selectBox).padTop(20.0f);
        
        table.row();
        table = new Table();
        table.defaults().spaceRight(50.0f).left();
        ((Table)root.findActor("scrollTable")).add(table).growX().padTop(25.0f);
        
        label = new Label("Distance", skin, "small");
        table.add(label);
        
        label = new Label("Avg. Speed", skin, "small");
        table.add(label);
        
        table.row();
        label = new Label("100 KM", skin);
        table.add(label);
        
        label = new Label("10 MPH", skin);
        table.add(label);
        
        table.row();
        ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        progressBar.setName("redProgress");
        progressBar.setValue(43);
        table.add(progressBar);
        
        progressBar = new ProgressBar(0, 100, 1, false, skin, "cyan");
        progressBar.setName("cyanProgress");
        progressBar.setValue(70);
        table.add(progressBar);
        
        table = ((Table)root.findActor("scrollTable"));
        table.row();
        stack = new Stack();
        table.add(stack);
        
        RadialProgressBar radialProgressBar = new RadialProgressBar(0, 360, 1, skin);
        radialProgressBar.setName("radialProgress");
        radialProgressBar.setClockWise(true);
        radialProgressBar.setStartAngle(245.0f);
        radialProgressBar.setValue(240.0f);
        stack.add(radialProgressBar);
        
        table = new Table();
        stack.add(table);
        
        table.defaults().bottom().expandY();
        label = new Label("80", skin, "title");
        table.add(label).padBottom(25.0f);
        
        label = new Label("BPM", skin);
        table.add(label).padBottom(40.0f);
        
        table = ((Table)root.findActor("scrollTable"));
        table.row();
        table.defaults().space(25.0f).padLeft(20.0f).padRight(20.0f);
        ImageTextButton imageTextButton = new ImageTextButton("Running", skin, "run");
        imageTextButton.getLabelCell().expandX().left().padLeft(25.0f);
        table.add(imageTextButton).growX();
        
        table.row();
        imageTextButton = new ImageTextButton("Shadow Boxing", skin, "box");
        imageTextButton.getLabelCell().expandX().left().padLeft(25.0f);
        table.add(imageTextButton).growX().padBottom(15.0f);
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
        
        cityOffsetX -= 50.0f * Gdx.graphics.getDeltaTime();
        cityOffsetX %= 470.0f;
        skin.get("city-scrolling", ScrollingTiledDrawable.class).setOffsetX(cityOffsetX);
        
        rainOffsetX -= 400.0f * Gdx.graphics.getDeltaTime();
        rainOffsetX %= 163;
        rainOffsetY -= 300.0f * Gdx.graphics.getDeltaTime();
        rainOffsetY %= 150;
        skin.get("rain-scrolling", ScrollingTiledDrawable.class).setOffsetX(rainOffsetX);
        skin.get("rain-scrolling", ScrollingTiledDrawable.class).setOffsetY(rainOffsetY);
        
        progressPercent = (float) Gdx.input.getX() / Gdx.graphics.getWidth();
        ((ProgressBar) stage.getRoot().findActor("redProgress")).setValue(progressPercent * 100.0f);
        ((ProgressBar) stage.getRoot().findActor("cyanProgress")).setValue(progressPercent * 100.0f);
        ((RadialProgressBar) stage.getRoot().findActor("radialProgress")).setValue(progressPercent * 300.0f);
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            dispose();
            create();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.remove("default", Skin.class);
        skin.dispose();
    }
}
