package com.ray3k.tracerui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private final static String longText = "The Specialists Mod was a little "
            + "known modification for the original Half-Life game on PC. It "
            + "sported a wide variety of weapons and unique gameplay elements. "
            + "Very much like a stylized cross between Counter-Strike and a Hong "
            + "Kong action flick, you can walk on walls, dive around corners, "
            + "and kung fu your enemies in to submission. There are even powerups "
            + "to slow down time to give you an upperhand against opponents. "
            + "This was a feature well before popular games like F.E.A.R. would "
            + "implement it. It was said that the developers of this game "
            + "basically used it as a resume to get into game design and "
            + "programming fields. I really hope that they're doing great "
            + "things now.";

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("tracer-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Image image = new Image(skin, "bg");
        image.setScaling(Scaling.stretch);
        image.setFillParent(true);
        stage.addActor(image);
        
        Window root = new Window("", skin);
        root.setSize(600, 700);
        root.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Align.center);
        stage.addActor(root);
        
        Table table = new Table();
        root.add(table).growX().colspan(2);
        
        Label label = new Label("Tracer UI", skin, "title");
        table.add(label).expandX().padLeft(15.0f);
        
        ImageButton imageButton = new ImageButton(skin, "close");
        table.add(imageButton);
        
        root.row();
        List list = new List(skin, "menu");
        list.setItems("Beretta 92FS", "M40A1", "M60", "Mark 23", "Desert Eagle", "Steyr AUG", "FN P90", "MP7", "MP5K", "SVD", "PSG1", "RPK");
        root.add(list).size(200.0f, 300.0f).padTop(10.0f).top().padRight(50.0f).padTop(15.0f);
        
        Table top = new Table();
        Table bottom = new Table();
        ScrollPane scrollPane = new ScrollPane(top, skin);
        scrollPane.setFadeScrollBars(false);
        SplitPane splitPane = new SplitPane(scrollPane, bottom, true, skin);
        splitPane.setSplitAmount(.425f);
        root.add(splitPane).growY().growX().padTop(15.0f);
        
        table = new Table();
        top.add(table);
        
        ImageTextButton imageTextButton = new ImageTextButton("Scope", skin, "checkbox");
        table.add(imageTextButton).left();
        
        table.row();
        imageTextButton = new ImageTextButton("Compensator", skin, "checkbox");
        table.add(imageTextButton).left();
        
        table.row();
        ButtonGroup buttonGroup = new ButtonGroup();
        imageTextButton = new ImageTextButton("Suppressor", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).left().padTop(15.0f);
        
        table.row();
        imageTextButton = new ImageTextButton("Muzzle Extender", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).left();
        
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Contract", "Client", "Job", "Target", "Asset", "Patsy", "John", "Mark");
        top.add(selectBox).padLeft(20.0f).width(100.0f).top();
        
        top.row();
        label = new Label(longText, skin);
        label.setWrap(true);
        top.add(label).colspan(2).padTop(20.0f).fillX();
        
        label = new Label("Range", skin, "sub-title");
        bottom.add(label).left().padTop(15.0f);
        
        bottom.row();
        final ProgressBar progressBar = new ProgressBar(0, 100.0f, 1.0f, false, skin);
        progressBar.setValue(50.0f);
        bottom.add(progressBar).padTop(10.0f).growX();
        
        bottom.row();
        label = new Label("Zoom", skin, "sub-title");
        bottom.add(label).left().padTop(20.0f);
        
        bottom.row();
        final Slider slider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        slider.setValue(50.0f);
        bottom.add(slider).padTop(6.0f).growX();
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        
        bottom.row();
        table = new Table();
        bottom.add(table).padTop(10.0f).growX();
        
        TextButton textButton = new TextButton("Execute", skin);
        table.add(textButton).expandX();
        
        Tree tree = new Tree(skin);
        table.add(tree).expandX();
        
        Node parent = new Node(new Label("Alpha", skin));
        tree.add(parent);
        
        Node child = new Node(new Label("Bravo", skin));
        parent.add(child);
        parent = child;
        
        child = new Node(new Label("Charlie", skin));
        parent.add(child);
        parent = child;
        
        child = new Node(new Label("Delta", skin));
        parent.add(child);
        parent = child;
        child.expandTo();
        
        child = new Node(new Label("Echo", skin));
        parent.add(child);
        parent = child;
        
        bottom.row();
        Touchpad touchpad = new Touchpad(0, skin);
        bottom.add(touchpad).padTop(10.0f);
        
        bottom.row();
        TextField textField = new TextField("", skin);
        bottom.add(textField).padTop(20.0f).expandY().top().width(200.0f);
        
        root.row();
        table = new Table();
        table.defaults().padLeft(10.0f).padRight(10.0f);
        root.add(table).colspan(2).growX();
        
        table.add().expandX();
        
        buttonGroup = new ButtonGroup();
        Button button = new Button(skin, "home");
        buttonGroup.add(button);
        table.add(button).padLeft(46.0f);
        
        button = new Button(skin, "settings");
        buttonGroup.add(button);
        table.add(button);
        
        button = new Button(skin, "info");
        buttonGroup.add(button);
        table.add(button);
        
        imageButton = new ImageButton(skin, "new");
        table.add(imageButton).expandX().right();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            dispose();
            create();
        }
        
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
        stage.dispose();
        skin.dispose();
    }
}
