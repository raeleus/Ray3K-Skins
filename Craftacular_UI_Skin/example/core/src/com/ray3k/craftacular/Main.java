package com.ray3k.craftacular;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private static final String longText = "Today is a day of days when men "
            + "and turkey alike can stand together and fight the chicken "
            + "menace. For too long the chickens have oppressed us. No "
            + "longer I say! We will show the poultry that they have gone "
            + "too far with their imperialistic and jingoist ideals. To "
            + "arms! To arms!";
    private float value;
    private ProgressBar armorBar, healthBar, hungerBar, xpBar;

    @Override
    public void create() {
        value = 0.0f;
        
        skin = new Skin(Gdx.files.internal("craftacular-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        ProgressBarStyle style = skin.get("xp", ProgressBarStyle.class);
        skin.getTiledDrawable("xp-bg").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("xp-bg");
        skin.getTiledDrawable("xp").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("xp");
        
        style = skin.get("hunger", ProgressBarStyle.class);
        skin.getTiledDrawable("meat").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("meat");
        skin.getTiledDrawable("meat-bg").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("meat-bg");
        
        style = skin.get("health", ProgressBarStyle.class);
        skin.getTiledDrawable("heart-bg").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("heart-bg");
        skin.getTiledDrawable("heart").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("heart");
        
        style = skin.get("armor", ProgressBarStyle.class);
        skin.getTiledDrawable("armor-bg").setMinWidth(0.0f);
        style.background = skin.getTiledDrawable("armor-bg");
        skin.getTiledDrawable("armor").setMinWidth(0.0f);
        style.knobBefore = skin.getTiledDrawable("armor");
        
        Table root = new Table(skin);
        root.setFillParent(true);
        root.setBackground(skin.getTiledDrawable("dirt"));
        stage.addActor(root);
        
        root.add(new Label("CRAFT=CULAR", skin, "title")).colspan(2);
        
        root.defaults().padTop(10.0f);
        
        root.row();
        root.add(new Label("Create New World", skin, "bold")).padTop(30.0f).colspan(2);
        
        root.row();
        root.add(new Label("Enter seed value", skin)).padTop(30.0f).colspan(2);
        
        root.row();
        root.add(new TextField("", skin)).minWidth(400.0f).colspan(2);
        
        root.row();
        root.add(new Label("Seed is optional", skin, "dim")).colspan(2);
        
        root.row();
        final TextButton structureButton = new TextButton("Structures: ON", skin);
        structureButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!structureButton.isChecked()) {
                    structureButton.setText("Structures: OFF");
                } else {
                    structureButton.setText("Structures: ON");
                }
            }
        });
        structureButton.setChecked(true);
        root.add(structureButton).padRight(10.0f);
        
        final TextButton worldButton = new TextButton("World: Default", skin);
        worldButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!worldButton.isChecked()) {
                    worldButton.setText("World: Flat");
                } else {
                    worldButton.setText("World: Default");
                }
            }
        });
        worldButton.setChecked(true);
        root.add(worldButton).padLeft(10.0f);
        
        root.row();
        TextButton textButton = new TextButton("Done", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Table mock = new Table(skin);
                mock.setBackground("craftacular-mockup");
                mock.setFillParent(true);
                stage.addActor(mock);
                
                Window window = new Window("", skin);
                window.add(new Label("Inventory", skin)).left().padLeft(15.0f);
                window.row();
                Table table = new Table();
                for(int y = 0; y < 4; y++) {
                    for (int x = 0; x < 9; x++) {
                        Container container = new Container();
                        container.setBackground(skin.getDrawable("cell"));
                        table.add(container).size(50.0f);
                    }
                    table.row();
                }
                window.add(table).pad(20.0f);
                mock.add(window).colspan(2).expandY().padTop(100.0f);
                
                mock.row();
                armorBar = new ProgressBar(0.0f, 100, .1f, false, skin, "armor");
                armorBar.setValue(50.0f);
                mock.add(armorBar).width(180.0f).height(18.0f).padBottom(10.0f);
                
                mock.row();
                healthBar = new ProgressBar(0.0f, 100, .1f, false, skin, "health");
                healthBar.setValue(50.0f);
                mock.add(healthBar).width(180.0f).height(18.0f);
                
                hungerBar = new ProgressBar(0.0f, 100, .1f, false, skin, "hunger");
                hungerBar.setValue(50.0f);
                mock.add(hungerBar).width(180.0f).height(18.0f).padLeft(15.0f);
                
                mock.row();
                Stack stack = new Stack();
                xpBar = new ProgressBar(0.0f, 100, .1f, false, skin, "xp");
                xpBar.setValue(50.0f);
                stack.add(xpBar);
                Label label = new Label("25", skin, "xp");
                label.setAlignment(Align.center);
                stack.add(label);
                mock.add(stack).width(378.0f).height(9.0f).colspan(2).padTop(20.0f).padBottom(50.0f);
            }
        });
        root.add(textButton).colspan(2).padTop(15.0f);
        
        root.row();
        Stack stack = new Stack();
        stack.add(new Slider(0, 100, 1, false, skin));
        Label label = new Label("Volume", skin);
        label.setAlignment(Align.center);
        label.setTouchable(Touchable.disabled);
        stack.add(label);
        root.add(stack).padTop(15.0f).minWidth(300.0f);
        
        textButton = new TextButton("Disabled", skin);
        textButton.setDisabled(true);
        root.add(textButton);
        
        root.row();
        label = new Label(longText, skin);
        label.setWrap(true);
        ScrollPane scrollPane = new ScrollPane(label, skin);
        scrollPane.setFadeScrollBars(false);
        root.add(scrollPane).colspan(2).grow().padBottom(15.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (armorBar != null) {
            value += 10.0f * Gdx.graphics.getDeltaTime();
            if (value >= 100) {
                value = 0.0f;
            }
            armorBar.setValue(value);
            healthBar.setValue(value);
            hungerBar.setValue(value);
            xpBar.setValue(value);
        }
        
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }
}
