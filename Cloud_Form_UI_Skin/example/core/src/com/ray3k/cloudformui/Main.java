package com.ray3k.cloudformui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.cloudformui.RadialProgressBar.CircularProgressBarStyle;

public class Main extends ApplicationAdapter {
    public static Stage stage;
    private Skin skin;
    private CircularProgressBarStyle circleStyle;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));
        
        circleStyle = new RadialProgressBar.CircularProgressBarStyle();
        circleStyle.background = skin.getDrawable("progress-bar-circle");
        circleStyle.knob = new RadialDrawable(skin.getRegion("progress-bar-circle-knob"));
        
        Table root = new Table(skin);
        root.setFillParent(true);
        stage.addActor(root);
        
        TextButton textButton = new TextButton("Create new Window", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                createWindow();
            }
        });
        root.add(textButton).top().left().pad(25.0f);
        root.add(new Label("Cloud Form UI", skin, "title")).expand().top().pad(25.0f);
        
        createWindow();
    }
    
    private void createWindow() {
        final Window window = new Window("", skin);
        window.getTitleTable().clear();
        window.getTitleTable().padBottom(3.0f);
        window.getTitleTable().add(new Label("Window", skin, "white")).expandX();
        Button closeButton = new Button(skin, "close");
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                window.remove();
            }
        });
        window.getTitleTable().add(closeButton);
        window.setWidth(500.0f);
        window.setHeight(375.0f);
        window.setPosition((int)(Gdx.graphics.getWidth() / 2.0f - window.getWidth() / 2.0f), (int) (Gdx.graphics.getHeight() / 2.0f - window.getHeight() / 2.0f));
        
        Table table = new Table(skin);
        table.setBackground("menu-bg");
        window.add(table).minWidth(100.0f).growY();
        ButtonGroup buttonGroup = new ButtonGroup();
        TextButton textButton = new TextButton("Design", skin, "menu");
        textButton.getLabel().setAlignment(Align.left);
        buttonGroup.add(textButton);
        table.add(textButton).growX();
        
        table.row();
        textButton = new TextButton("Animation", skin, "menu");
        textButton.getLabel().setAlignment(Align.left);
        buttonGroup.add(textButton);
        table.add(textButton).growX();
        
        table.row();
        textButton = new TextButton("Settings", skin, "menu");
        textButton.getLabel().setAlignment(Align.left);
        buttonGroup.add(textButton);
        table.add(textButton).growX();
        
        table.row();
        textButton = new TextButton("Export", skin, "menu");
        textButton.getLabel().setAlignment(Align.left);
        buttonGroup.add(textButton);
        table.add(textButton).growX().expandY().top();
        
        Table contentTable = new Table();
        Table scrollPaneTable = new Table();
        table = new Table();
        table.defaults().left();
        scrollPaneTable.add(table).padLeft(5.0f);
        ScrollPane scrollPane = new ScrollPane(scrollPaneTable, skin);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        contentTable.add(scrollPane).grow();
        window.add(contentTable).grow();
        
        buttonGroup = new ButtonGroup();
        ImageTextButton imageTextButton = new ImageTextButton("Cirrus", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
        
        table.row();
        imageTextButton = new ImageTextButton("Cumulus", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
        
        table.row();
        imageTextButton = new ImageTextButton("Stratus", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
        
        table.row();
        imageTextButton = new ImageTextButton("Contrail", skin, "checkbox");
        table.add(imageTextButton);
        
        table.row();
        imageTextButton = new ImageTextButton("Chemtrail", skin, "checkbox");
        table.add(imageTextButton);
        
        table.row();
        Label label = new Label("Enter your name:", skin);
        table.add(label).padTop(10.0f);
        
        table.row();
        table.add(new TextField("", skin)).minWidth(10.0f).prefWidth(10.0f).growX();
        
        table.row();
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        final RadialProgressBar circleBar = new RadialProgressBar(0, 100, 1, circleStyle);
        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
                circleBar.setValue(slider.getValue());
            }
        });
        table.add(slider).growX().padTop(10.0f);
        
        table.row();
        Tree tree = new Tree(skin);
        Node parent = new Node(new Label("Meteorologist", skin));
        tree.add(parent);
        Node child = new Node(new Label("Forecaster", skin));
        parent.add(child);
        child = new Node(new Label("Weatherman", skin));
        parent.add(child);
        child = new Node(new Label("Tornado Chaser", skin));
        parent.add(child);
        child = new Node(new Label("Weather Researcher", skin));
        parent.add(child);
        child = new Node(new Label("Climatologist", skin));
        parent.add(child);
        child = new Node(new Label("Rain Dancer", skin));
        parent.add(child);
        child = new Node(new Label("Weather Prophet", skin));
        parent.add(child);
        child = new Node(new Label("Cloud Expert", skin));
        parent.add(child);
        parent = new Node(new Label("Cloud", skin));
        tree.add(parent);
        child = new Node(new Label("Mushroom Cloud", skin));
        parent.add(child);
        child = new Node(new Label("Cloud Computing", skin));
        parent.add(child);
        child = new Node(new Label("On cloud nine", skin));
        parent.add(child);
        child = new Node(new Label("Clouding the issue", skin));
        parent.add(child);
        child = new Node(new Label("Cloud Cover", skin));
        parent.add(child);
        child = new Node(new Label("A cloudy drink", skin));
        parent.add(child);
        tree.expandAll();
        table.add(tree).padTop(10.0f).expandY().top();
        
        table = new Table();
        scrollPaneTable.add(table).expand().pad(15.0f).top();
        Container container = new Container();
        container.setActor(new Image(skin, "sky"));
        container.setBackground(skin.getDrawable("textfield"));
        table.add(container).padBottom(10.0f).maxSize(180, 101);
        
        table.row();
        progressBar.setAnimateDuration(.1f);
        table.add(progressBar).growX();
        
        table.row();
        circleBar.setAnimateDuration(.1f);
        table.add(circleBar).padTop(10.0f);
        
        table.row();
        table.add(new Label("What shape is the cloud?", skin)).padTop(10.0f);
        
        table.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Dog", "Cat", "Heart", "Horse", "Banana");
        table.add(selectBox).growX();
        
        contentTable.row();
        table = new Table();
        contentTable.add(table);
        
        textButton = new TextButton("OK", skin);
        table.add(textButton);
        
        textButton = new TextButton("Cancel", skin);
        table.add(textButton).padLeft(30.0f);
        
        stage.addActor(window);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(237.0f / 255.0f, 239.0f / 255.0f, 242.0f / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
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
