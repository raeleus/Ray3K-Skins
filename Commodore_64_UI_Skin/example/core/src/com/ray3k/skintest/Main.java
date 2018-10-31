package com.ray3k.skintest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table rootTable = new Table(skin);
        rootTable.background("window");
        rootTable.setFillParent(true);
        rootTable.defaults().align(Align.topLeft).colspan(2);
        stage.addActor(rootTable);
        
        Label label = new Label("**** COMMODORE 64 BASIC V2 ****\n64K  RAM  SYSTEM  38911  BASIC  BYTES  FREE", skin);
        label.setAlignment(Align.center);
        rootTable.add(label).align(Align.top);
        rootTable.row();
        rootTable.add(new Label("\n\nREADY.\nLOAD \"COMMODORE 64 UI\"\n\nPRESS PLAY ON TAPE\nLOADING...\nREADY.", skin));
        
        rootTable.row();
        TextField textField = new TextField("RUN", skin, "nobg");
        rootTable.add(textField);
        
        rootTable.row();
        Window window = new Window("", skin, "dialog");
        window.getTitleTable().reset();
        label = new Label("WINDOW", skin, "title");
        label.setAlignment(Align.bottom);
        window.getTitleTable().add(label).expand();
        
        window.add(new Label("Press a button to continue...", skin, "optional")).colspan(4);
        window.row();
        window.defaults().padTop(10.0f);
        TextButton dialogButton = new TextButton("DIALOG", skin);
        window.add(dialogButton);
        window.add(new TextButton("TOGGLE", skin, "toggle"));
        window.add(new Button(skin, "music"));
        window.add(new Button(skin, "sound"));
        window.row();
        Table table = new Table();
        table.add(new CheckBox(" Check Box", skin)).padBottom(5.0f).left().expandX();
        SelectBox selectBox = new SelectBox<String>(skin);
        selectBox.setItems(new Object[] {"Select Box", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7"});
        selectBox.setMaxListCount(3);
        table.add(selectBox).left().expandX().padLeft(15.0f);
        window.add(table).growX().colspan(4);
        window.row();
        window.defaults().colspan(4).left();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 1; i <= 3; i++) {
            CheckBox checkBox = new CheckBox(" Selection " + Integer.toString(i), skin, "radio");
            buttonGroup.add(checkBox);
            window.add(checkBox);
            window.row();
        }
        rootTable.add(window).align(Align.right);
        
        rootTable.row();
        window = new Window("", skin);
        window.add(new Label("**** WINDOW ****", skin)).colspan(2);
        window.row();
        window.add(new Label("LOGIN", skin, "error")).colspan(2).padTop(10.0f);
        window.row();
        window.defaults().padTop(5.0f);
        window.add(new Label("USER:", skin, "error")).align(Align.right);
        window.add(new TextField("", skin));
        window.row();
        window.add(new Label("PASS:", skin, "error")).align(Align.right);
        window.add(new TextField("", skin));
        window.row();
        window.add(new Label("Progress", skin, "optional")).colspan(2).padTop(10.0f);
        window.row();
        final ProgressBar progressBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin);
        progressBar.setValue(75.0f);
        window.add(progressBar).colspan(2).growX();
        window.row();
        window.add(new Label("Slider", skin, "optional")).colspan(2).padTop(10.0f);
        window.row();
        final Slider slider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        window.add(slider).colspan(2).growX().padBottom(10.0f);
        rootTable.add(window).colspan(1);
        
        table = new Table(skin);
        table.setBackground("dialog");
        Tree tree = new Tree(skin);
        Node parentNode = new Node(new Label("Selection 1", skin));
        tree.add(parentNode);
        for (int i = 2; i <= 15; i++) {
            Node node = new Node(new Label("Selection " + i, skin));
            parentNode.add(node);
            parentNode = node;
        }
        tree.expandAll();
        tree.setPadding(2);
        ScrollPane scrollPane = new ScrollPane(tree, skin);
        scrollPane.setFadeScrollBars(false);
        Table touchPadTable = new Table();
        touchPadTable.add(new Touchpad(0.0f, skin));
        SplitPane splitPane = new SplitPane(scrollPane, touchPadTable, false, skin);
        table.add(splitPane).align(Align.topLeft).growX();
        rootTable.add(table).colspan(1).right().growX().padLeft(15.0f);
        
        rootTable.row();
        rootTable.add().expand();
        
        dialogButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Dialog dialog = new Dialog("Commodore 64 UI", skin, "dialog-modal") {
                    protected void result(Object object) {
                        System.out.println("Chosen: " + object);
                    }
                };
                dialog.getTitleTable().reset();
                Label label = new Label("WINDOW", skin, "title");
                label.setAlignment(Align.bottom);
                dialog.getTitleTable().add(label).expand();
                dialog.text("Are you sure?").button("Yes", true).button("No", false)
                        .key(Keys.ENTER, true).key(Keys.ESCAPE, false).show(stage);
            }
        });
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
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
