package com.ray3k.metalui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private String longText = "Java: Write once, debug everywhere!\n\n"
            + "Why does C get all the chicks and Java doesn't?\n"
            + "\tBecause C doesn't treat them like objects.\n\n"
            + "Why do Java programmers wear glasses?\n"
            + "\tBecause they don't C#\n\n"
            + "How do Java programmers get wealthy?\n"
            + "\tInheritence.";
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("metal-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table topTable = new Table();
        
        topTable.defaults().pad(10.0f).left();
        topTable.add(new Label("Metal UI", skin));
        
        topTable.row();
        TextButton textButton = new TextButton("Press Me", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("", skin);
                dialog.add(new Label(longText, skin));
                dialog.getTitleTable().clear();
                Image image = new Image(skin, "window-icon");
                dialog.getTitleTable().add(image).padLeft(5.0f);
                dialog.getTitleTable().add(new Label("Dialog Alert!", skin)).padLeft(10.0f).padRight(10.0f);
                image = new Image(skin.getTiledDrawable("window-pattern"));
                dialog.getTitleTable().add(image).growX().padBottom(2.0f);
                Button button = new Button(skin, "close");
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        dialog.hide();
                    }
                });
                dialog.getTitleTable().add(button).padLeft(10.0f).padRight(5.0f);
                dialog.show(stage);
            }
        });
        topTable.add(textButton);
        
        topTable.add(new TextField("Swing is so bloated. Thankfully, I found LibGDX.", skin));
        
        topTable.row();
        topTable.add(new TextButton("Toggle Me", skin, "toggle"));
        
        final ProgressBar progressBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin);
        progressBar.setValue(50.0f);
        topTable.add(progressBar);
        
        topTable.row();
        topTable.add(new ImageTextButton("Check Box", skin, "checkbox"));
        
        final Slider slider = new Slider(0, 100.0f, 1.0f, false, skin);
        slider.setValue(50.0f);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        topTable.add(slider);
        
        topTable.row();
        topTable.add(new ImageTextButton("Radio Button", skin, "radio"));
        
        Table spinnerTable = new Table();
        final TextField spinnerField = new TextField("0", skin);
        spinnerTable.add(spinnerField).width(25.0f);
        Table table2 = new Table();
        Button buttonUp = new Button(skin, "spinner-up");
        buttonUp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                spinnerField.setText(Integer.toString(Integer.parseInt(spinnerField.getText()) + 1));
            }
        });
        table2.add(buttonUp);
        
        table2.row();
        Button buttonDown = new Button(skin, "spinner-down");
        buttonDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                spinnerField.setText(Integer.toString(Integer.parseInt(spinnerField.getText()) - 1));
            }
        });
        table2.add(buttonDown);
        spinnerTable.add(table2);
        topTable.add(spinnerTable);
        
        topTable.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Select Box", "Boring", "Pedestrian", "Uninspiring", "Lackluster");
        topTable.add(selectBox).expandY().top();
        
        Table table = new Table();
        Tree tree = new Tree(skin);
        Node parent = new Node(new Label("javax.swing.*", skin));
        Node child = new Node(new Label("JLabel", skin, "page"));
        parent.add(child);
        child = new Node(new Label("JFrame", skin, "page"));
        parent.add(child);
        child = new Node(new Label("JDialog", skin, "page"));
        parent.add(child);
        tree.add(parent);
        parent = new Node(new Label("java.util.*", skin));
        child = new Node(new Label("Vector", skin, "page"));
        parent.add(child);
        child = new Node(new Label("Scanner", skin, "page"));
        parent.add(child);
        child = new Node(new Label("Timer", skin, "page"));
        parent.add(child);
        tree.add(parent);
        parent = new Node(new Label("java.io.*", skin));
        child = new Node(new Label("File", skin, "page"));
        parent.add(child);
        child = new Node(new Label("FileWriter", skin, "page"));
        parent.add(child);
        child = new Node(new Label("InputStream", skin, "page"));
        parent.add(child);
        tree.add(parent);
        tree.expandAll();
        table.add(tree);
        
        Label label = new Label(longText, skin, "red");
        table.add(label).padLeft(15.0f);
        
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        
        SplitPane splitPane = new SplitPane(topTable, scrollPane, true, skin);
        splitPane.setSplitAmount(.65f);
        root.add(splitPane).grow();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(239.0f / 255.0f, 239.0f / 255.0f, 239.0f / 255.0f, 1);
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
