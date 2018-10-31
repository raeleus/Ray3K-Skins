package com.ray3k.tubularui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("tubular-ui.json"));
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table table = new Table(skin);
        table.setBackground("toolbar");
        root.add(table).growX().colspan(2);
        
        table.add(new Button(skin, "menu")).padLeft(10.0f);
        table.add(new Image(skin, "logo small")).padLeft(20.0f).width(62.0f);
        table.add(new TextField("", skin)).prefWidth(400.0f).padLeft(50.0f);
        table.add(new Button(skin, "search")).expandX().left().padRight(10.0f);
        
        root.row();
        table = new Table(skin);
        table.setBackground("toolbar");
        table.defaults().growX();
        
        ButtonGroup buttonGroup = new ButtonGroup();
        TextButton textButton = new TextButton("Home", skin, "sidebar");
        buttonGroup.add(textButton);
        table.add(textButton).padTop(50.0f);
        
        table.row();
        textButton = new TextButton("Trending", skin, "sidebar");
        buttonGroup.add(textButton);
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("Subscriptions", skin, "sidebar");
        buttonGroup.add(textButton);
        table.add(textButton);
        
        table.row();
        table.add().growY().minHeight(900.0f);
                
        ScrollPane scrollPane = new ScrollPane(table, skin, "bg");
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        root.add(scrollPane).width(150.0f).growY();
        
        Table rightTable = new Table();
        root.add(rightTable).grow();
        
        table = new Table(skin);
        table.setBackground("toolbar");
        table.defaults().pad(5.0f).padTop(15.0f);
        rightTable.add(table).growX();
        
        buttonGroup = new ButtonGroup();
        textButton = new TextButton("Home", skin, "topbar");
        buttonGroup.add(textButton);
        table.add(textButton);
        
        textButton = new TextButton("Trending", skin, "topbar");
        buttonGroup.add(textButton);
        table.add(textButton);
        
        textButton = new TextButton("Subscriptions", skin, "topbar");
        buttonGroup.add(textButton);
        table.add(textButton);
        
        rightTable.row();
        final Table contentTable = new Table();
        scrollPane = new ScrollPane(contentTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        rightTable.add(scrollPane).grow();
        
        table = new Table(skin);
        table.setBackground("black");
        contentTable.add(table).grow().padLeft(50.0f).padRight(50.0f).padTop(20.0f);
        
        final Stack stack = new Stack();
        table.add(stack).grow();
        
        Image image = new Image(skin, "super-click-bait");
        image.setScaling(Scaling.fit);
        stack.add(image);
        
        table = new Table(skin);
        table.setBackground("bottom-bar");
        stack.add(table);
        
        Slider slider = new Slider(0, 1000, 1, false, skin);
        table.add(slider).growX().expandY().bottom().colspan(7);
        
        table.row();
        table.defaults().pad(5.0f).padTop(3.0f).padBottom(3.0f);
        table.add(new Button(skin, "left"));
        table.add(new Button(skin, "play"));
        table.add(new Button(skin, "right"));
        table.add(new Button(skin, "speaker"));
        table.add(new Slider(0, 100, 1, false, skin, "volume-horizontal")).width(30.0f);
        table.add(new Button(skin, "settings")).expandX().right();
        table.add(new Button(skin, "full-screen"));
        
        contentTable.row();
        table = new Table(skin);
        table.setBackground("toolbar");
        contentTable.add(table).growX().minHeight(100.0f).pad(50.0f).padTop(10.0f).padBottom(0.0f);
        
        Label label = new Label("This is apparently what streaming video is all about!\n\nDon't forget to like and subscribe because all you amount to is a statistic in our eyes.", skin);
        label.setWrap(true);
        table.add(label).growX().expandY().pad(10.0f).top().colspan(2);
        table.row();
        table.add(new Button(skin, "like")).expandX().right().pad(10.0f).padRight(0.0f);
        table.add(new Button(skin, "dislike")).pad(10.0f).padLeft(5.0f);
        
        contentTable.row();
        
        table = new Table(skin);
        table.setBackground("toolbar");
        table.defaults().growX().padTop(15.0f);
        contentTable.add(table).grow().minHeight(100.0f).pad(50.0f).padTop(10.0f).padBottom(10.0f);
        
        
        Table commentTable = new Table();
        commentTable.add(new Image(skin, "user-blue")).padRight(15.0f);
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Observant Viewer", skin, "name"));
        label = new Label("I swear I've seen this video before...", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable);
        
        table.row();
        commentTable = new Table();
        commentTable.add(new Image(skin, "user-red")).padRight(15.0f);
        verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Internet Celebrity", skin, "name"));
        label = new Label("We reuploaded this video to maximize profit. No other reason, really.", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable).padLeft(40.0f);
        
        table.row();
        commentTable = new Table();
        commentTable.add(new Image(skin, "user-green")).padRight(15.0f);
        verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Complete Tool", skin, "name"));
        label = new Label("First!", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable);
        
        table.row();
        commentTable = new Table();
        commentTable.add(new Image(skin, "user-violet")).padRight(15.0f);
        verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Idiot", skin, "name"));
        label = new Label("WAT IZ DA NAM OF DA SONG?", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable);
        
        table.row();
        commentTable = new Table();
        commentTable.add(new Image(skin, "user-red")).padRight(15.0f);
        verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Internet Celebrity", skin, "name"));
        label = new Label("It's in the goddamned credits! Right there!", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable).padLeft(40.0f);
        
        table.row();
        commentTable = new Table();
        commentTable.add(new Image(skin, "user-yellow")).padRight(15.0f);
        verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Ornery Troll", skin, "name"));
        label = new Label("Your content is crap! I hope you relapse back into a coma!!!", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable);
        
        table.row();
        commentTable = new Table();
        commentTable.add(new Image(skin, "user-red")).padRight(15.0f);
        verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Internet Celebrity", skin, "name"));
        label = new Label("Thanks for your support!", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable).padLeft(40.0f);
        
        table.row();
        commentTable = new Table();
        commentTable.add(new Image(skin, "user-red")).padRight(15.0f);
        verticalGroup = new VerticalGroup();
        verticalGroup.fill();
        commentTable.add(verticalGroup).grow();
        verticalGroup.addActor(new Label("Internet Celebrity", skin, "name"));
        label = new Label("Make sure to support us on Patreon because we want more money! And watch the ads! And buy some sweet, sweet merch. Thanks to all our fans!", skin);
        label.setWrap(true);
        verticalGroup.addActor(label);
        table.add(commentTable);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(239.0f / 255.0f, 234.0f / 255.0f, 222.0f / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
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
