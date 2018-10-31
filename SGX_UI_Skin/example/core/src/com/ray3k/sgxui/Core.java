package com.ray3k.sgxui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.tests.utils.scene2d.TabbedPane;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.musicevents.tool.file.FileChooser;
import com.ray3k.skincomposer.MenuButton;
import com.ray3k.skincomposer.MenuButtonGroup;
import com.ray3k.skincomposer.Spinner;

public class Core extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("sgx-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Texture texture = new Texture(Gdx.files.internal("background.png"));
        Image background = new Image(texture);
        background.setFillParent(true);
        stage.addActor(background);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        MenuButtonGroup menuButtonGroup = new MenuButtonGroup();
        Table menuBar = new Table(skin);
        menuBar.setBackground("file-menu-bar");
        root.add(menuBar).growX();
        
        final MenuButton fileMenuButton = new MenuButton("File", skin);
        fileMenuButton.getLabelCell().padLeft(5.0f).padRight(5.0f);
        menuBar.add(fileMenuButton);
        menuButtonGroup.add(fileMenuButton);
        fileMenuButton.setItems("Save", "Save As...", "Open...", "Exit");
        fileMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                System.out.println(fileMenuButton.getSelectedIndex() + " " + fileMenuButton.getSelectedItem());
            }
        });
        
        final MenuButton editMenuButton = new MenuButton("Edit", skin);
        editMenuButton.getLabelCell().padLeft(5.0f).padRight(5.0f);
        menuBar.add(editMenuButton);
        menuButtonGroup.add(editMenuButton);
        editMenuButton.setItems("Undo", "Redo", "Preferences...");
        editMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                System.out.println(editMenuButton.getSelectedIndex() + " " + editMenuButton.getSelectedItem());
            }
        });
        
        final MenuButton helpMenuButton = new MenuButton("Help", skin);
        helpMenuButton.getLabelCell().padLeft(5.0f).padRight(5.0f);
        menuBar.add(helpMenuButton).expandX().left();
        menuButtonGroup.add(helpMenuButton);
        helpMenuButton.setItems("About");
        helpMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                System.out.println(helpMenuButton.getSelectedIndex() + " " + helpMenuButton.getSelectedItem());
            }
        });
        
        root.row();
        Table table = new Table();
        root.add(table).padTop(5.0f).growX();
        
        table.add().expandX().width(300);
        
        Table subTable = new Table();
        subTable.defaults().space(5.0f);
        table.add(subTable);
        
        ImageButton imageButton = new ImageButton(skin);
        imageButton.setDisabled(true);
        subTable.add(imageButton);
        
        imageButton = new ImageButton(skin);
        imageButton.setDisabled(true);
        subTable.add(imageButton);
        
        imageButton = new ImageButton(skin);
        subTable.add(imageButton);
        
        imageButton = new ImageButton(skin);
        subTable.add(imageButton);
        
        Label label = new Label("SGX UI", skin, "title-white");
        label.setAlignment(Align.right);
        table.add(label).expandX().right().width(275).padRight(25.0f);
        
        root.row();
        table = new Table();
        root.add(table).padTop(5.0f);
        
        TextButton textButton = new TextButton("2", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("3", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("4", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("5", skin, "number");
        textButton.setDisabled(true);
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("6", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("7", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("8", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("9", skin, "number");
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("10", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("11", skin, "number");
        table.add(textButton);
        
        textButton = new TextButton("12", skin, "number");
        table.add(textButton);
        
        root.row();
        TabbedPane tabbedPane = new  TabbedPane(skin, Align.left);
        root.add(tabbedPane).padTop(10.0f);
        
        table = new Table();
        tabbedPane.addTab("Player", table);
        
        label = new Label("What is your name?", skin, "small");
        table.add(label);
        
        TextField textField = new TextField("", skin);
        table.add(textField);
        
        table.row();
        subTable = new Table();
        subTable.defaults().left();
        table.add(subTable).colspan(2);
        
        ImageTextButton checkBox = new ImageTextButton("Mute Audio", skin, "checkbox");
        subTable.add(checkBox).colspan(2);
        
        subTable.row();
        checkBox = new ImageTextButton("Play BGM", skin, "checkbox");
        checkBox.setDisabled(true);
        subTable.add(checkBox).colspan(2);
        
        table = new Table();
        tabbedPane.addTab("Options", table);
        
        root.row();
        table = new Table();
        table.defaults().space(35.0f).minWidth(150.0f);
        root.add(table).padTop(10.0f);
        
        final TextButton playButton = new TextButton("Play", skin);
        table.add(playButton);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                FileChooser fileChooser = new FileChooser("Choose a file...", skin, Gdx.files.local("/"));
                fileChooser.setFileNameEnabled(true);
                fileChooser.show(stage);
            }
        });
        
        textButton = new TextButton("Level Select", skin);
        textButton.setDisabled(true);
        table.add(textButton);
        
        textButton = new TextButton("Quit", skin);
        table.add(textButton);
        
        root.row();
        table = new Table();
        table.defaults().space(35.0f).minWidth(150.0f);
        root.add(table).padTop(10.0f);
        
        textButton = new TextButton("Play", skin, "emphasis");
        table.add(textButton);
        
        textButton = new TextButton("Level Select", skin, "emphasis");
        textButton.setDisabled(true);
        table.add(textButton);
        
        textButton = new TextButton("Quit", skin, "emphasis");
        table.add(textButton);
        
        root.row();
        table = new Table();
        table.defaults().space(35.0f).minWidth(150.0f);
        root.add(table).padTop(10.0f);
        
        textButton = new TextButton("Play", skin, "emphasis-colored");
        table.add(textButton);
        
        textButton = new TextButton("Level Select", skin, "emphasis-colored");
        textButton.setDisabled(true);
        table.add(textButton);
        
        textButton = new TextButton("Quit", skin, "emphasis-colored");
        table.add(textButton);
        
        root.row();
        Spinner spinner = new Spinner(0.0, 1.0, true, Spinner.Orientation.HORIZONTAL, skin);
        root.add(spinner).padTop(5.0f);
        
        root.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Easy Automatic", "Baby Mode", "Normal", "Difficult", "Hell");
        root.add(selectBox).padTop(5.0f).expandY().top();
        
        root.row();
        table = new Table();
        table.defaults().expandX();
        root.add(table).growX().padLeft(10.0f).padRight(10.0f).padBottom(5.0f);
        
        label = new Label("ray3k.wordpress.com", skin);
        table.add(label).left();
        
        label = new Label("ray3k.wordpress.com", skin, "medium");
        table.add(label);
        
        label = new Label("ray3k.wordpress.com", skin, "small");
        table.add(label).right();
        
        Window window = new Window("Settings", skin, "tool");
        stage.addActor(window);
        window.setSize(300.0f, 350.0f);
        window.setPosition(Gdx.graphics.getWidth() - 20.0f, Gdx.graphics.getHeight() / 2.0f, Align.right);
        window.getTitleLabel().setAlignment(Align.center);
        window.getTitleTable().getCells().first().padLeft(20.0f);
        
        Button button = new Button(skin, "close");
        window.getTitleTable().add(button);
        
        label = new Label("Preview", skin, "white");
        window.add(label);
        
        window.row();
        table = new Table();
        table.defaults().left();
        window.add(table).padTop(5.0f);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton radioButton = new ImageTextButton("Wireframe Display", skin, "radio");
        buttonGroup.add(radioButton);
        table.add(radioButton);
        
        table.row();
        radioButton = new ImageTextButton("Live Preview", skin, "radio");
        buttonGroup.add(radioButton);
        table.add(radioButton);
        
        table.row();
        radioButton = new ImageTextButton("Shaded Render", skin, "radio");
        buttonGroup.add(radioButton);
        table.add(radioButton);
        
        table.row();
        radioButton = new ImageTextButton("Interactive Test", skin, "radio");
        radioButton.setDisabled(true);
        buttonGroup.add(radioButton);
        table.add(radioButton);
        
        table.row();
        radioButton = new ImageTextButton("Final Render", skin, "radio");
        radioButton.setDisabled(true);
        buttonGroup.add(radioButton);
        table.add(radioButton);
        
        window.row();
        label = new Label("Volume", skin, "white");
        window.add(label).padTop(15.0f);
        
        window.row();
        Slider slider = new Slider(0, 100, 1, false, skin);
        slider.setValue(50.0f);
        window.add(slider).padTop(5.0f);
        
        window.row();
        label = new Label("Distance Field", skin, "white");
        window.add(label).padTop(15.0f);
        
        window.row();
        slider = new Slider(0, 100, 1, false, skin);
        slider.setValue(50.0f);
        slider.setDisabled(true);
        window.add(slider).padTop(5.0f);
        
        
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            dispose();
            create();
        }
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
