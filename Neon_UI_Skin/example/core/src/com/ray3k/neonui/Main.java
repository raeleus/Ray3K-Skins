package com.ray3k.neonui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("neon-ui.json"));
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table table = new Table();
        table.defaults().growX();
        table.add(new TextButton("Play", skin));
        
        table.row();
        table.add(new TextButton("Options", skin));
        
        table.row();
        table.add(new TextButton("Quit", skin));
        root.add(table);
        
        table = new Table();
        table.add(new Label("Name", skin));
        
        table.row();
        table.add(new TextField("", skin)).growX();
        
        table.row();
        table.add(new Label("Login", skin)).padTop(15.0f);
        
        table.row();
        table.add(new TextField("", skin, "login")).growX();
        
        table.row();
        TextField textField = new TextField("", skin, "password");
        textField.setPasswordMode(true);
        textField.setPasswordCharacter('•');
        table.add(textField).growX();
        root.add(table);
        
        table = new Table();
        table.defaults().expandX().left();
        table.add(new ImageTextButton("Extra Enemies", skin, "checkbox"));
        
        table.row();
        table.add(new ImageTextButton("No Powerups", skin, "checkbox"));
        
        table.row();
        table.add(new ImageTextButton("Shield Regen", skin, "checkbox"));
        
        table.row();
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton radio = new ImageTextButton("Easy", skin, "radio");
        buttonGroup.add(radio);
        table.add(radio).padTop(10.0f);
        
        table.row();
        radio = new ImageTextButton("Medium", skin, "radio");
        buttonGroup.add(radio);
        table.add(radio);
        
        table.row();
        radio = new ImageTextButton("Hard", skin, "radio");
        buttonGroup.add(radio);
        table.add(radio);
        root.add(table);
        
        root.row();
        table = new Table();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Ship A", "Ship B", "Ship C");
        table.add(selectBox);
        
        table.row();
        Tree tree = new Tree(skin);
        Node parent = new Node(new Label("top", skin));
        tree.add(parent);
        Node child = new Node(new Label("mid", skin));
        parent.add(child);
        parent = child;
        child = new Node(new Label("bottom", skin));
        parent.add(child);
        table.add(tree);
        tree.expandAll();
        root.add(table);
        
        table = new Table();
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        table.add(progressBar);
        
        table.row();
        TiledDrawable tiledDrawable = skin.getTiledDrawable("progress-bar-big-knob");
        tiledDrawable = tiledDrawable.tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0);
        skin.get("big", ProgressBar.ProgressBarStyle.class).knobBefore = tiledDrawable;
        final ProgressBar progressBarBig = new ProgressBar(0, 100, 1, false, skin, "big");
        table.add(progressBarBig).size(241, 82);
        
        table.row();
        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
                progressBarBig.setValue(slider.getValue());
            }
        });
        table.add(slider);
        root.add(table).colspan(2);
        
        root.row();
        ScrollPane scrollPane = new ScrollPane(new Touchpad(0, skin), skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        
        Label label = new Label("NEON UI\nray3k.wordpress.com", skin, "over");
        label.setAlignment(Align.center);
        
        SplitPane splitPane = new SplitPane(scrollPane, label, false, skin);
        splitPane.setSplitAmount(.35f);
        root.add(splitPane).colspan(3).maxHeight(125.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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

    }
}
