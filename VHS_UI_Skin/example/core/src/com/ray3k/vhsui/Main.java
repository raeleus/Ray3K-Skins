package com.ray3k.vhsui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("vhs-ui.json"));
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Label label = new Label("PLAY", skin, "play");
        root.add(label).expandX().left().padLeft(25.0f).padTop(25.0f);
        
        root.row();
        Table table = new Table();
        table.defaults().left();
        root.add(table);
        
        label = new Label("- M E N U -", skin);
        table.add(label).padTop(50.0f).center();
        
        table.row();
        TextButton textButton = new TextButton("TIMER PROG. SET", skin);
        table.add(textButton).padTop(35.0f);
        
        table.row();
        table.defaults().padTop(10.0f); 
        textButton = new TextButton("CLOCK SET", skin);
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("LANGUAGE SELECT", skin);
        table.add(textButton);
        
        table.row();
        CheckBox checkBox = new CheckBox("SAP: OFF", skin);
        table.add(checkBox).padTop(35.0f);
        
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (((CheckBox) actor).isChecked()) {
                    ((CheckBox) actor).setText("SAP: ON");
                } else {
                    ((CheckBox) actor).setText("SAP: OFF");
                }
            }
        });
        
        table.row();
        checkBox = new CheckBox("HI-FI AUDIO: OFF", skin);
        table.add(checkBox);
        
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (((CheckBox) actor).isChecked()) {
                    ((CheckBox) actor).setText("HI-FI AUDIO: ON");
                } else {
                    ((CheckBox) actor).setText("HI-FI AUDIO: OFF");
                }
            }
        });
        
        root.row();
        table = new Table();
        root.add(table).expandY().top().padTop(50.0f);
        
        label = new Label("TRACKING", skin);
        table.add(label);
        
        Slider slider = new Slider(0.0f, 10.0f, 1.0f, false, skin);
        slider.setValue(5.0f);
        table.add(slider).width(300.0f).padLeft(10.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            stage.dispose();
            skin.dispose();
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
        skin.dispose();
    }
}
