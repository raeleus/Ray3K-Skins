package com.ray3k.levelplaneui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private static final String longText = "FTL is my all time favorite game. "
            + "Describing what it ‘s all about is pretty challenging though. "
            + "Primarily, you can describe it as a real-time, strategy, "
            + "rogue-like, text adventure. Thematically, it's a scifi, space "
            + "exploration, ship simulator, RPG. Gameplay takes a cue from Star "
            + "Trek, featuring teleporters, torpedoes, lasers, shields, and "
            + "deck combat. It's quite challenging to play through to the end "
            + "because dying means you have to start a brand new game every "
            + "time. It's well worth a buy on steam with many hours of replay "
            + "value in the multiple ships unlocked and advanced version "
            + "additions";

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("level-plane-ui.json"));
        ProgressBarStyle progressBarStyle = skin.get("default-horizontal", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("progress-bar-knob-h");
        tiledDrawable = tiledDrawable.tint(skin.getColor("color2-s"));
        tiledDrawable.setMinWidth(0.0f);
        progressBarStyle.knobBefore = tiledDrawable;
        
        SliderStyle sliderStyle = skin.get("default-horizontal", SliderStyle.class);
        sliderStyle.knobBefore = tiledDrawable;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table left = new Table();
        root.add(left);
        
        Table slideShow = new Table();
        left.add(slideShow).size(340, 270);
        
        final ButtonGroup<Button> imageButtonGroup = new ButtonGroup<Button>();
        
        ImageButton imageButton = new ImageButton(skin, "side-left-3");
        slideShow.add(imageButton).growY().padRight(3.0f);
        
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                imageButtonGroup.getButtons().get(Math.max(imageButtonGroup.getCheckedIndex() - 1, 0)).setChecked(true);
            }
        });
        
        Table table = new Table(skin);
        table.setBackground("color-3-h");
        slideShow.add(table).grow();
        
        Table top = new Table();
        Table bottom = new Table();
        
        SplitPane splitPane = new SplitPane(top, bottom, true, skin);
        splitPane.setSplitAmount(.75f);
        table.add(splitPane).grow();
        
        table = new Table();
        table.defaults().space(87.5f);
        final ScrollPane imageScrollPane = new ScrollPane(table);
        imageScrollPane.setTouchable(Touchable.disabled);
        top.add(imageScrollPane).padBottom(30.0f);
        
        Image image = new Image(skin, "image1");
        image.setScaling(Scaling.none);
        table.add(image).padLeft(87.5f);
        
        image = new Image(skin, "image2");
        image.setScaling(Scaling.none);
        table.add(image);
        
        image = new Image(skin, "image3");
        image.setScaling(Scaling.none);
        table.add(image);
        
        image = new Image(skin, "image4");
        image.setScaling(Scaling.none);
        table.add(image).padRight(87.5f);
        
        top.row();
        table = new Table();
        top.add(table);
        
        Button button;
        for (int i = 0; i < 4; i++) {
            button = new Button(skin, "selector");
            table.add(button).space(10.0f);
            imageButtonGroup.add(button);
            
            final int value = i;
            
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event,
                        Actor actor) {
                    imageScrollPane.setScrollX(value * (87.5f + 119));
                }
            });
        }
        
        TextButton textButton = new TextButton("CLICK HERE TO SUBSCRIBE!", skin, "dark");
        bottom.add(textButton);
        
        imageButton = new ImageButton(skin, "side-right-3");
        slideShow.add(imageButton).padLeft(3.0f).growY();
        
        imageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                imageButtonGroup.getButtons().get(Math.min(imageButtonGroup.getCheckedIndex() + 1, 3)).setChecked(true);
            }
        });
        
        left.row();
        table = new Table();
        table.defaults().space(1.0f);
        left.add(table).padTop(25.0f);
        
        imageButton = new ImageButton(skin, "left-1");
        table.add(imageButton);
        
        textButton = new TextButton("1", skin, "small-1");
        table.add(textButton);
        
        textButton = new TextButton("2", skin, "small-1");
        table.add(textButton);
        
        imageButton = new ImageButton(skin, "right-1");
        table.add(imageButton);
        
        left.row();
        table = new Table();
        table.defaults().space(50.0f);
        left.add(table).padTop(25.0f);
        
        textButton = new TextButton("Continue", skin, "c3");
        table.add(textButton);
        
        textButton = new TextButton("Quit", skin, "c3");
        table.add(textButton);
        
        left.row();
        table = new Table();
        table.defaults().space(20.0f);
        left.add(table).padTop(25.0f);
        
        button = new Button(skin, "big-2");
        button.add(new Label("HOME", skin, "title-white")).expandY();
        button.row();
        button.add(new Label("CLICK HERE", skin, "subtitle-black")).height(27.0f);
        table.add(button);
        
        button = new Button(skin, "big-1");
        button.add(new Label("STORE", skin, "title-white")).expandY();
        button.row();
        button.add(new Label("CLICK HERE", skin, "subtitle-black")).height(27.0f);
        table.add(button);
        
        button = new Button(skin, "big-3");
        button.add(new Label("EMAIL", skin, "title-white")).expandY();
        button.row();
        button.add(new Label("CLICK HERE", skin, "subtitle-black")).height(27.0f);
        table.add(button);
        
        left.row();
        bottom = new Table();
        left.add(bottom).padTop(20.0f);
        
        Touchpad touchpad = new Touchpad(0, skin);
        bottom.add(touchpad);
        
        table = new Table();
        table.defaults().space(10.0f);
        bottom.add(table).padLeft(20.0f);
        
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        progressBar.setValue(50.0f);
        table.add(progressBar).width(266.0f);
        
        table.row();
        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.setValue(50.0f);
        table.add(slider).width(266.0f);
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        
        table.row();
        Table login = new Table(skin);
        login.setBackground("white");
        login.defaults().space(15.0f);
        table.add(login).growX();
        
        Label label = new Label("GAME CLUB LOGIN", skin, "underline-1");
        login.add(label).padTop(10.0f);
        
        login.row();
        table = new Table();
        login.add(table);
        
        label = new Label("User:", skin, "subtitle-1");
        table.add(label).padRight(20.0f);
        
        TextField textField = new TextField("", skin);
        table.add(textField);
        
        login.row();
        table = new Table();
        login.add(table);
        
        label = new Label("Pass:", skin, "subtitle-1");
        table.add(label).padRight(20.0f);
        
        textField = new TextField("", skin);
        table.add(textField);
        
        login.row();
        CheckBox checkBox = new CheckBox("Stay Logged In", skin);
        login.add(checkBox).left().padLeft(5.0f);
        
        login.row();
        table = new Table();
        table.defaults().space(15.0f);
        login.add(table).padBottom(10.0f);
        
        ButtonGroup buttonGroup = new ButtonGroup<Button>();
        checkBox = new CheckBox("Use Cloud Data", skin, "radio-1");
        buttonGroup.add(checkBox);
        table.add(checkBox).padLeft(5.0f);
        
        checkBox = new CheckBox("Use Local Data", skin, "radio-1");
        buttonGroup.add(checkBox);
        table.add(checkBox).padRight(5.0f);
        
        Table right = new Table();
        root.add(right).padLeft(15.0f).growY();
        
        SelectBox selectBox = new SelectBox(skin, "c2");
        selectBox.setItems("The selection is sublime.","There is a preponderance of evidence.", "The peace was tenuous.", "His performance was exemplary.", "The customer was flippant.", "The animal was feral.", "The questions were irksome.");
        selectBox.setMaxListCount(5);
        right.add(selectBox).left();
        
        right.row();
        selectBox = new SelectBox(skin);
        selectBox.setItems("The selection is sublime.","There is a preponderance of evidence.", "The peace was tenuous.", "His performance was exemplary.", "The customer was flippant.", "The animal was feral.", "The questions were irksome.");
        selectBox.setMaxListCount(5);
        right.add(selectBox).left().padLeft(35.0f).padTop(20.0f);
        
        right.row();
        selectBox = new SelectBox(skin, "c3");
        selectBox.setItems("The selection is sublime.","There is a preponderance of evidence.", "The peace was tenuous.", "His performance was exemplary.", "The customer was flippant.", "The animal was feral.", "The questions were irksome.");
        selectBox.setMaxListCount(5);
        right.add(selectBox).left().padLeft(70.0f).padTop(20.0f);
        
        right.row();
        Tree tree = new Tree(skin, "c2");
        right.add(tree).padTop(100.0f);
        
        Node parent = new Node(new Label("Sumptuous", skin, "subtitle-white"));
        tree.add(parent);
        
        Node sub = new Node(new Label("Opulent", skin, "subtitle-white"));
        parent.add(sub);
        
        Node child = new Node(new Label("item", skin, "subtitle-white"));
        sub.add(child);
        
        sub = new Node(new Label("Splendid", skin, "subtitle-white"));
        parent.add(sub);
        
        child = new Node(new Label("item", skin, "subtitle-white"));
        sub.add(child);
        
        sub = new Node(new Label("Luxurious", skin, "subtitle-white"));
        parent.add(sub);
        
        child = new Node(new Label("item", skin, "subtitle-white"));
        sub.add(child);
        
        sub = new Node(new Label("Lavish", skin, "subtitle-white"));
        parent.add(sub);
        
        child = new Node(new Label("item", skin, "subtitle-white"));
        sub.add(child);
        sub.expandTo();
        
        right.row();
        table = new Table(skin);
        table.setBackground("color-2");
        ScrollPane scrollPane = new ScrollPane(table, skin, "c2");
        scrollPane.setOverscroll(false, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setVariableSizeKnobs(false);
        right.add(scrollPane).growX().height(100.0f).padTop(100.0f);
        
        label = new Label(longText, skin, "subtitle-white");
        label.setWrap(true);
        table.add(label).grow().pad(10.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(5/255.0f, 107/255.0f, 153/255.0f, 1);
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
