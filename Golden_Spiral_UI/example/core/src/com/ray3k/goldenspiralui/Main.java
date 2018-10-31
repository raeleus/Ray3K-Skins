package com.ray3k.goldenspiralui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("golden-ui-skin.json"));
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.add(new Label("Golden Spiral UI", skin, "title")).colspan(3).expandX();
        
        root.row();
        Table table = new Table(skin);
        table.setBackground("select-box-bg");
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Select Box ", "Golden ", "Spiral ");
        table.add(selectBox);
        root.add(table).colspan(3);
        
        root.row();
        table = new Table();
        TextButton textButton = new TextButton("Begin Story ", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("", skin);
                dialog.setFillParent(true);
                dialog.text("Continue?", skin.get("title", LabelStyle.class));
                dialog.getContentTable().row();
                dialog.text("Do you want to continue?");
                dialog.getContentTable().row();
                
                ChangeListener changeListener = new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        dialog.hide();
                    }
                };
                Table table = new Table();
                TextButton textButton = new TextButton("OK", skin);
                textButton.addListener(changeListener);
                table.add(textButton);
                
                textButton = new TextButton("Cancel", skin);
                textButton.addListener(changeListener);
                table.add(textButton);
                dialog.getContentTable().add(table);
                dialog.show(stage);
            }
        });
        table.add(textButton).growX();
        
        table.row();
        table.add(new TextButton("Options ", skin)).growX();
        table.row();
        table.add(new TextButton("Quit ", skin)).growX();
        root.add(table).left();
        
        table = new Table();
        table.add(new Label("CLASS", skin));
        
        table.row();
        table.defaults().left();
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton checkBox = new ImageTextButton("Demon Bounty Hounter", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox);
        
        table.row();
        checkBox = new ImageTextButton("Angelic Bureaucrat", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox);
        
        table.row();
        checkBox = new ImageTextButton("Middling Terran", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox);
        
        table.row();
        checkBox = new ImageTextButton("Plausible Deniability", skin, "checkbox");
        table.add(checkBox).padTop(5.0f);
        root.add(table);
        
        table = new Table();
        TextField textField = new TextField("", skin);
        table.add(textField).left();
        
        table.row();
        Tree tree = new Tree(skin);
        Node parent = new Node(new Label("Hat", skin));
        tree.add(parent);
        Node child = new Node(new Label("Sturdy Bowler's Hat of Misguided Adventuring", skin));
        parent.add(child);
        parent = new Node(new Label("Shirt", skin));
        tree.add(parent);
        child = new Node(new Label("Floppy Tunic", skin));
        parent.add(child);
        parent = new Node(new Label("Pants", skin));
        tree.add(parent);
        child = new Node(new Label("Studded Leather Chaps", skin));
        parent.add(child);
        parent = new Node(new Label("Shoes", skin));
        tree.add(parent);
        child = new Node(new Label("Bedazzled Sabatons of Cybernetic Desire", skin));
        parent.add(child);
        tree.expandAll();
        table.add(tree);
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        
        table = new Table();
        table.add(new Touchpad(0.0f, skin)).size(168, 168);
        SplitPane splitPane = new SplitPane(scrollPane, table, true, skin);
        splitPane.setSplitAmount(.49f);
        root.add(splitPane).growY().width(200.0f);
        
        float value = 50.0f;
        
        root.row();
        TiledDrawable tiledDrawable = skin.getTiledDrawable("health-orb-fill");
        tiledDrawable.setMinHeight(0.0f);
        skin.get("health-orb", ProgressBar.ProgressBarStyle.class).knobBefore = tiledDrawable;
        final ProgressBar healthOrb = new ProgressBar(0.0f, 100.0f, 1.0f, true, skin, "health-orb");
        healthOrb.setValue(value);
        root.add(healthOrb).left().size(201, 164).padTop(10.0f);
        
        table = new Table();
        table.defaults().pad(5.0f);
        final ProgressBar healthBar = new ProgressBar(0, 100, 1, false, skin);
        final ProgressBar manaBar = new ProgressBar(0, 100, 1, false, skin, "mana");
        final ProgressBar manaOrb = new ProgressBar(0, 100, 1, true, skin, "mana-orb");
        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.setValue(value);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                healthOrb.setValue(slider.getValue());
                healthBar.setValue(slider.getValue());
                manaBar.setValue(slider.getValue());
                manaOrb.setValue(slider.getValue());
            }
        });
        table.add(slider).growX();
        
        table.row();
        tiledDrawable = skin.getTiledDrawable("progress-bar-life");
        tiledDrawable.setMinWidth(0.0f);
        skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class).knobBefore = tiledDrawable;
        healthBar.setValue(value);
        table.add(healthBar).size(173.0f, 51.0f);
        
        table.row();
        tiledDrawable = skin.getTiledDrawable("progress-bar-mana");
        tiledDrawable.setMinWidth(0.0f);
        skin.get("mana", ProgressBar.ProgressBarStyle.class).knobBefore = tiledDrawable;
        manaBar.setValue(value);
        table.add(manaBar).size(173.0f, 51.0f);
        root.add(table).growX();
        
        tiledDrawable = skin.getTiledDrawable("mana-orb-fill");
        tiledDrawable.setMinHeight(0.0f);
        skin.get("mana-orb", ProgressBar.ProgressBarStyle.class).knobBefore = tiledDrawable;
        manaOrb.setValue(value);
        root.add(manaOrb).right().size(201, 164).padTop(10.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
