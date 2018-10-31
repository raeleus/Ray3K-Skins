package com.ray3k.comicui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private Label narration, speech;
    private String[] narrations = {"meanwhile, back at the\nLOBBY of DUE DILLIGANCE...", "meanwhile, back at the\nRESTROOM...", "useless NARRATION\nto point out the OBVIOUS..."};
    private String[] lines = {"WHERE WERE THE\nOTHER PUPPIES GOING!?", "i.. am... CONSTIPATED!", "some quippy,\nFOURTH-WALL BREAKING!"};
    private int index;
    private float counter;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("comic-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        index = 0;
        counter = 3.0f;
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table comicPanel = new Table(skin);
        comicPanel.setBackground("window-no-padding");
        
        Stack stack = new Stack();
        Image image = new Image(skin, "Crimson-Roach");
        image.setScaling(Scaling.stretch);
        stack.add(image);
        
        Table table = new Table();
        narration = new Label(narrations[index], skin, "narration");
        narration.setAlignment(Align.center);
        table.add(narration).left().top().padLeft(40.0f).padTop(25.0f).expand();
        table.row();
        Table speechBubble = new Table(skin);
        speechBubble.background("bubble-top-left");
        speech = new Label(lines[index], skin);
        speech.setAlignment(Align.center);
        speechBubble.add(speech);
        table.add(speechBubble).bottom().right().expand();
        stack.add(table);
        
        comicPanel.add(stack);
        root.add(comicPanel).width(448.5f).height(540.15f).left().pad(5.0f).padLeft(40.0f);
        
        table = new Table();
        table.add(new TextButton("PLAY", skin));
        
        table.row();
        table.add(new TextButton("QUIT", skin)).padTop(10.0f);
        
        table.row();
        Label label = new Label("NAME YOUR HERO:", skin);
        table.add(label).padTop(15.0f);
        
        table.row();
        TextField textField = new TextField("", skin);
        table.add(textField).padTop(5.0f);
        
        table.row();
        ProgressBar progressBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin);
        progressBar.setValue(50.0f);
        table.add(progressBar).padTop(15.0f);
        
        table.row();
        SelectBox selectBox = new SelectBox(skin, "big");
        selectBox.setItems("green bumble-bee", "crimson roach", "banana master", "ultronical", "silver blade", "ricochet", "the lightning lad");
        table.add(selectBox).padTop(15.0f);
        
        table.row();
        Tree tree = new Tree(skin);
        Node parentNode = new Node(new Label("Utility Belt", skin, "big"));
        Node node = new Node(new Label("Hafnium Nade", skin, "big"));
        Node subNode = new Node(new Label("Cost $50", skin, "big"));
        node.add(subNode);
        parentNode.add(node);
        node = new Node(new Label("Booger Puddle", skin, "big"));
        subNode = new Node(new Label("Cost $25", skin, "big"));
        node.add(subNode);
        parentNode.add(node);
        node = new Node(new Label("Iron Guantlets", skin, "big"));
        subNode = new Node(new Label("Cost $10", skin, "big"));
        node.add(subNode);
        parentNode.add(node);
        node = new Node(new Label("Ego Booster", skin, "big"));
        subNode = new Node(new Label("Cost $15", skin, "big"));
        node.add(subNode);
        parentNode.add(node);
        node = new Node(new Label("Protein Shield", skin, "big"));
        subNode = new Node(new Label("Cost $100", skin, "big"));
        node.add(subNode);
        parentNode.add(node);
        tree.add(parentNode);
        parentNode.setExpanded(true);
        table.add(tree).padTop(15.0f).expandX().left();
        root.add(table).growX().expandY().top();
        
        root.row();
        Slider slider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        slider.setValue(50.0f);
        root.add(slider).pad(5.0f).growX().colspan(2);
        
        root.row();
        Table bottom = new Table();
        table = new Table();
        table.add(new Label("Comic UI Example", skin, "title"));
        table.row();
        table.add(new Label("Crimson Roach\nAdventures #3", skin, "big"));
        table.row();
        table.add(new Label("a story by\n     RAYMOND \"RAELEUS\" BUCKLEY", skin)).padTop(7.0f).right().padRight(10.0f);
        bottom.add(table).pad(10.0f).left();
        
        comicPanel = new Table(skin);
        comicPanel.setBackground("window");
        comicPanel.defaults().left();
        CheckBox checkBox = new CheckBox("Invulnerability", skin);
        checkBox.setChecked(true);
        comicPanel.add(checkBox);
        
        comicPanel.row();
        checkBox = new CheckBox("Super Speed", skin);
        comicPanel.add(checkBox);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        comicPanel.row();
        checkBox = new CheckBox("League of Badasses", skin, "radio");
        buttonGroup.add(checkBox);
        comicPanel.add(checkBox).padTop(10.0f);
        
        comicPanel.row();
        checkBox = new CheckBox("American Federation", skin, "radio");
        buttonGroup.add(checkBox);
        comicPanel.add(checkBox);
        
        comicPanel.row();
        checkBox = new CheckBox("The Revengeancers", skin, "radio");
        buttonGroup.add(checkBox);
        comicPanel.add(checkBox);
        
        bottom.add(comicPanel).grow().expandX().right().padRight(25.0f).padLeft(0.0f);
        root.add(bottom).grow().padBottom(5.0f).colspan(2);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        counter -= Gdx.graphics.getRawDeltaTime();
        if (counter < 0) {
            index++;
            if (index >= lines.length) {
                index = 0;
            }
            narration.setText(narrations[index]);
            speech.setText(lines[index]);
            
            counter = 3.0f;
        }
        
        stage.act(Gdx.graphics.getRawDeltaTime());
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
