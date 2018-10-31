package com.ray3k.starsoldiersample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private ProgressBar progressBar;
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("star-soldier-ui.json"));
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Label label = new Label("Star Soldier\nUI", skin, "title");
        label.setAlignment(Align.center);
        root.add(label).colspan(2).padTop(50.0f);
        
        root.row();
        Table table = new Table();
        TextButton textButton = new TextButton("CAMPAIGN", skin);
        table.add(textButton).fill();
        table.row();
        textButton = new TextButton("MATCHMAKING", skin);
        table.add(textButton).fill();
        table.row();
        textButton = new TextButton("OPTIONS", skin);
        table.add(textButton).fill();
        root.add(table).left();
        
        table = new Table();
        table.defaults().colspan(2);
        table.add(new Label("Server", skin));
        
        table.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("WEST", "EAST", "CENTRAL");
        table.add(selectBox);
        
        table.row();
        table.defaults().colspan(1);
        table.add(new Label("User:", skin)).padRight(15.0f);
        TextField textField = new TextField("", skin);
        table.add(textField).padTop(10.0f);
        
        table.row();
        table.add(new Label("Pass:", skin)).padRight(15.0f);
        textField = new TextField("", skin);
        textField.setPasswordCharacter('•');
        textField.setPasswordMode(true);
        table.add(textField).padTop(10.0f);
        root.add(table).right().padRight(20.0f);
        
        root.row();
        root.add(new Label("Volume", skin)).colspan(2).padTop(15.0f).padRight(340.0f);
        root.row();
        Slider slider = new Slider(0, 100, 1, false, skin);
        root.add(slider).colspan(2).growX().padLeft(20.0f).padRight(340.0f);
        
        root.row();
        root.add(new Label("Downloading Update...", skin)).colspan(2).padTop(30.0f).padRight(340.0f);
        root.row();
        progressBar = new ProgressBar(0, 100, 1, false, skin);
        root.add(progressBar).colspan(2).growX().padTop(10.0f).padLeft(20.0f).padRight(340.0f);
        
        root.row();
        root.add().expandY();
        
        Window window = new Window("Window", skin);
        window.setSize(300.0f, 200.0f);
        window.setPosition(50.0f, 5.0f);
        window.getTitleLabel().setAlignment(Align.center);
        
        table = new Table();
        
        Tree tree = new Tree(skin);
        Node node = new Node(new Label("Star", skin));
        tree.add(node);
        
        Node subNode = new Node(new Label("Solder", skin));
        node.add(subNode);
        node = subNode;
        
        subNode = new Node(new Label("UI", skin));
        node.add(subNode);
        node = subNode;
        
        subNode = new Node(new Label("It doesn't matter when it's Arcturian, baby.", skin));
        node.add(subNode);
        node = subNode;
        
        subNode = new Node(new Label("It's game over, man!", skin));
        node.add(subNode);
        node = subNode;
        
        subNode = new Node(new Label("It's game over!", skin));
        node.add(subNode);
        node = subNode;
        tree.expandAll();
        table.add(tree);
        
        table.row();
        ButtonGroup buttonGroup = new ButtonGroup();
        CheckBox checkBox = new CheckBox("Radio Button", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox).padTop(5.0f).left();
        
        table.row();
        checkBox = new CheckBox("Radio Button", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox).left();
        
        table.row();
        checkBox = new CheckBox("Check Box", skin);
        table.add(checkBox).left();
        
        table.row();
        checkBox = new CheckBox("Check Box", skin, "radio");
        table.add(checkBox).left();
        
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        window.add(scrollPane).grow();
        stage.addActor(window);
        
        window = new Window("", skin, "special");
        window.setSize(313, 309);
        Image image = new Image(skin, "soldier");
        image.setScaling(Scaling.fit);
        window.add(image).width(180).bottom();
        window.setPosition(800, 50, Align.bottomRight);
        stage.addActor(window);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        float delta = Gdx.graphics.getDeltaTime();
        
        float value = progressBar.getValue();
        value += 50.0f * delta;
        value %= 100.0f;
        progressBar.setValue(value);
        
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
