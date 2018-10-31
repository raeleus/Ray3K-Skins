package com.ray3k.freezingui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private final static String longText = "Snow Piercer is an interesting film "
            + "for a number of reasons. It's a movie shot for English speaking "
            + "audiences based on a French comic. Starring an international "
            + "cast and a South Korean director, it met nearly universal "
            + "acclaim upon release. Most of us in the US didn't get to see it "
            + "in theaters because of distributor shennanigans, however I "
            + "picked it up right away when it hit shelves.";
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("freezing-ui.json"));
        
        ProgressBarStyle progressBarStyle = skin.get("fancy", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("progress-bar");
        tiledDrawable.setMinWidth(0.0f);
        progressBarStyle.background = tiledDrawable;
        
        tiledDrawable = skin.getTiledDrawable("progress-bar-knob");
        tiledDrawable.setMinWidth(0.0f);
        progressBarStyle.knobBefore = tiledDrawable;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.add(new Label("Freezing UI", skin, "title"));
        
        root.row();
        root.defaults().padTop(10.0f);
        TextButton textButton = new TextButton("Start", skin);
        root.add(textButton);
        
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                final Window window = new Window("Window", skin);
                
                Label label = new Label("Brrrr... It's cold in here!", skin, "dark");
                window.add(label).padTop(5.0f);
                
                window.row();
                TextButton textButton = new TextButton("OK", skin);
                window.add(textButton).expandY().bottom().padBottom(5.0f);
                
                textButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event,
                            Actor actor) {
                        window.remove();
                    }
                });
                
                stage.addActor(window);
                window.setSize(250.0f, 150.0f);
                window.setPosition(stage.getWidth() / 2.0f, stage.getHeight() / 2.0f, Align.center);
            }
        });
        
        root.row();
        textButton = new TextButton("Options", skin);
        root.add(textButton);
        
        root.row();
        textButton = new TextButton("Quit", skin);
        root.add(textButton);
        
        root.row();
        final ProgressBar progressBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin, "fancy");
        progressBar.setValue(50.0f);
        root.add(progressBar).width(335.0f);
        
        root.row();
        final Slider slider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        slider.setValue(50.0f);
        root.add(slider);
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        
        root.row();
        Table table = new Table();
        root.add(table);
        
        Touchpad touchPad = new Touchpad(0.0f, skin);
        table.add(touchPad);
        
        Table tableRight = new Table();
        table.add(tableRight).padLeft(10.0f);
        
        CheckBox checkBox = new CheckBox("Mute", skin);
        tableRight.add(checkBox).colspan(2).left();
        
        tableRight.row();
        tableRight.defaults().padTop(10.0f);
        Label label = new Label("Name: ", skin);
        tableRight.add(label);
        
        TextField textField = new TextField("", skin);
        tableRight.add(textField).padLeft(10.0f);
        
        tableRight.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Chilly", "Freezing", "Cold");
        tableRight.add(selectBox).expandY().top().colspan(2);
        
        label = new Label(longText, skin);
        label.setWrap(true);
        
        Container container = new Container(label);
        container.width(500.0f);
        
        tableRight.row();
        ScrollPane scrollPane = new ScrollPane(container, skin);
        scrollPane.setFadeScrollBars(false);
        tableRight.add(scrollPane).colspan(2).height(150.0f).width(200.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.2f, .2f, .2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            skin.dispose();
            stage.dispose();
            create();
        }
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        
        stage.getViewport().update(width, height, true);
    }
}
