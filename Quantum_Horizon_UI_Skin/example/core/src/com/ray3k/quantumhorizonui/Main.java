package com.ray3k.quantumhorizonui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
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
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("quantum-horizon-ui.json"));
        
        ProgressBarStyle style = skin.get("signal1", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("signal-bar-1").tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0);
        style.knobBefore = tiledDrawable;
        
        style = skin.get("signal2", ProgressBarStyle.class);
        tiledDrawable = skin.getTiledDrawable("signal-bar-2").tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0);
        style.knobBefore = tiledDrawable;
        
        style = skin.get("signal3", ProgressBarStyle.class);
        tiledDrawable = skin.getTiledDrawable("signal-bar-3").tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0);
        style.knobBefore = tiledDrawable;
        
        style = skin.get("battery", ProgressBarStyle.class);
        tiledDrawable = skin.getTiledDrawable("battery").tint(skin.getColor("color"));
        tiledDrawable.setMinWidth(0);
        style.knobBefore = tiledDrawable;
        tiledDrawable = skin.getTiledDrawable("battery").tint(skin.getColor("pressed"));
        tiledDrawable.setMinWidth(0);
        style.background = tiledDrawable;
        
        Table root = new Table();
        root.setFillParent(true);
        TiledDrawable t = skin.getTiledDrawable("pattern-bg");
        t = t.tint(skin.getColor("background"));
        root.setBackground(t);
        stage.addActor(root);
        
        Window window = new Window("", skin);
        window.setSize(750.0f, 750.0f);
        window.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Align.center);
        stage.addActor(window);
        
        Table top = new Table();
        Table bottom = new Table();
        SplitPane splitPane = new SplitPane(top, bottom, true, skin);
        splitPane.setSplitAmount(.65f);
        window.add(splitPane).grow();
        
        top.add(new Label("Quantum Horizon", skin, "title")).colspan(2);
        
        top.row();
        top.add(new Label("RAY3K.WORDPRESS.COM", skin)).colspan(2);
        
        top.row();
        Table table = new Table();
        table.defaults().growX();
        top.add(table).padTop(10.0f);
        
        table.add(new TextButton("Play", skin));
        
        table.row();
        table.add(new TextButton("Options", skin));
        
        table.row();
        table.add(new TextButton("Quit", skin));
        
        table = new Table();
        table.defaults().padTop(10.0f);
        top.add(table).growX();
        
        final Slider slider = new Slider(0, 100, 1, false, skin);
        table.add(slider).growX().colspan(2);
        
        table.row();
        final ProgressBar signal1 = new ProgressBar(0, 188.0f, 19.0f, false, skin, "signal1");
        table.add(signal1).size(188.0f, 48.0f);
        
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        table.add(progressBar);
        
        table.row();
        final ProgressBar signal3 = new ProgressBar(0, 188.0f, 19.0f, false, skin, "signal3");
        table.add(signal3).size(188.0f, 48.0f);
        
        final ProgressBar battery = new ProgressBar(0, 100, 1, false, skin, "battery");
        table.add(battery).width(13.0f * 5.0f);
        
        table.row();
        table.add();
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                signal1.setValue(slider.getValue() / 100.0f * 188.0f);
                progressBar.setValue(slider.getValue());
                signal3.setValue(slider.getValue() / 100.0f * 188.0f);
                battery.setValue(slider.getValue());
            }
        });
        
        table.add(new Touchpad(0, skin));
        
        top.row();
        table = new Table();
        table.defaults().left().padTop(5.0f);
        top.add(table);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton checkBox = new ImageTextButton("Verbose", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox);
        
        table.row();
        checkBox = new ImageTextButton("Laconic", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox);
        
        table.row();
        checkBox = new ImageTextButton("Mute", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox);
        
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Lackadaisical", "Stagnant", "Brisk", "Blistering");
        top.add(selectBox);
        
        table = new Table();
        bottom.add(table).padTop(20.0f).growY().growX();
        
        Label label = new Label("Hover", skin);
        TextTooltip textToolTip = new TextTooltip("HP: 100\nPower: 20\nTurns: 5", skin);
        TooltipManager tooltipManager = TooltipManager.getInstance();
        tooltipManager.initialTime = .5f;
        tooltipManager.hideAll();
        label.addListener(textToolTip);
        table.add(label).left();
        
        table.row();
        table.add(new Label("Name", skin)).padTop(80.0f);
        
        table.row();
        table.add(new TextField("", skin)).padTop(5.0f);
        
        table.row();
        table.add(new Label("Password", skin)).padTop(10.0f);
        
        table.row();
        TextField textField = new TextField("", skin);
        textField.setPasswordCharacter('•');
        textField.setPasswordMode(true);
        table.add(textField).padTop(5.0f).expandY().top();
        
        table = new Table();
        bottom.add(table).growY();
        
        Tree tree = new Tree(skin);
        Node parent = new Node(new Label("Core", skin));
        tree.add(parent);
        
        Node child = new Node(new Label("Palladium Plating", skin));
        parent.add(child);
        
        child = new Node(new Label("Thorium Reactor", skin));
        parent.add(child);
        
        child = new Node(new Label("Gimbal Hardpoints", skin));
        parent.add(child);
        
        child = new Node(new Label("Personnel Carrier", skin));
        parent.add(child);
        
        child = new Node(new Label("Self Destruct", skin));
        parent.add(child);
        
        child = new Node(new Label("AI Mother Brain", skin));
        parent.add(child);
        
        parent = new Node(new Label("Augmentations", skin));
        tree.add(parent);
        
        child = new Node(new Label("Combat Thrusters", skin));
        parent.add(child);
        
        child = new Node(new Label("Seeker Nukes", skin));
        parent.add(child);
        
        child = new Node(new Label("AP Gatling Gun", skin));
        parent.add(child);
        
        child = new Node(new Label("Manipulation Arm", skin));
        parent.add(child);
        
        child = new Node(new Label("Counter-Measures", skin));
        parent.add(child);
        
        tree.expandAll();
        table.row();
        ScrollPane scrollPane = new ScrollPane(tree, skin);
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);
        table.add(scrollPane).padTop(50.0f);
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
        skin.dispose();
    }
}
