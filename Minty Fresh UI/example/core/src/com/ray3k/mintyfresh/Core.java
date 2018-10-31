/*
 * The MIT License
 *
 * Copyright 2018 Raymond Buckley.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ray3k.mintyfresh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Core extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("minty-fresh-ui.json"));
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Image image = new Image(skin, "wallpaper");
        image.setScaling(Scaling.fill);
        root.add(image).grow();
        
        final Window window = new Window("Minty Fresh UI", skin);
        window.setSize(600, 600);
        window.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Align.center);
        window.getTitleTable().getCells().first().padTop(10.0f);
        window.getTitleLabel().setAlignment(Align.center);
        stage.addActor(window);
        
        window.getTitleTable().defaults().padTop(10.0f);
        Button button = new Button(skin, "minus");
        window.getTitleTable().add(button);
        
        button = new Button(skin, "plus");
        window.getTitleTable().add(button);
        
        button = new Button(skin, "close");
        window.getTitleTable().add(button);
        
        Table table = new Table();
        table.setName("content-table");
        window.add(table).grow();
        
        table = new Table();
        table.setBackground(skin.getDrawable("side-panel"));
        ((Table)window.findActor("content-table")).add(table).minWidth(150.0f).growY();
        
        Tree tree = new Tree(skin);
        table.add(tree).growX().expandY().top().padTop(5.0f);
        
        Label label = new Label("My Computer", skin, "white-bold");
        Node parent = new Node(label);
        tree.add(parent);
        
        label = new Label("Home", skin, "path");
        Node node = new Node(label);
        parent.add(node);
        
        label = new Label("Desktop", skin, "path");
        node = new Node(label);
        parent.add(node);
        
        label = new Label("Documents", skin, "path");
        node = new Node(label);
        parent.add(node);
        
        label = new Label("Music", skin, "path");
        node = new Node(label);
        parent.add(node);
        
        label = new Label("Pictures", skin, "path");
        node = new Node(label);
        parent.add(node);
        
        label = new Label("Videos", skin, "path");
        node = new Node(label);
        parent.add(node);
        
        label = new Label("Downloads", skin, "path");
        node = new Node(label);
        parent.add(node);
        
        label = new Label("Network", skin, "white-bold");
        parent = new Node(label);
        tree.add(parent);
        
        label = new Label("Shared", skin, "path");
        node = new Node(label);
        parent.add(node);
        
        tree.expandAll();
        parent.collapseAll();
        
        table = new Table();
        table.setName("right");
        ((Table)window.findActor("content-table")).add(table).grow();
        
        table = new Table();
        table.setBackground(skin.getDrawable("panel"));
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        ((Table)window.findActor("right")).add(scrollPane).grow().pad(10.0f);
        stage.setScrollFocus(scrollPane);
        
        image = new Image(skin, "logo");
        image.setScaling(Scaling.none);
        table.add(image).minSize(1000, 1000);
        window.layout();
        scrollPane.setScrollX(310f);
        scrollPane.setScrollY(390f);
        
        ((Table)window.findActor("right")).row();
        table = new Table();
        table.setName("settings");
        table.pad(5.0f);
        table.setBackground(skin.getDrawable("panel"));
        ((Table)window.findActor("right")).add(table).pad(5.0f).expandX();
        
        label = new Label("Settings", skin, "bold");
        table.add(label).colspan(3).left();
        
        table.row();
        table = new Table();
        ((Table) window.findActor("settings")).add(table).padTop(10.0f).top();
        
        table.defaults().left();
        ButtonGroup buttonGroup = new ButtonGroup();
        CheckBox checkBox = new CheckBox("Ubuntu", skin, "radio");
        table.add(checkBox);
        buttonGroup.add(checkBox);
        
        table.row();
        checkBox = new CheckBox("Mint", skin, "radio");
        table.add(checkBox);
        buttonGroup.add(checkBox);
        
        table.row();
        checkBox = new CheckBox("Zorin", skin, "radio");
        table.add(checkBox);
        buttonGroup.add(checkBox);
        
        table = new Table();
        ((Table) window.findActor("settings")).add(table).padTop(10.0f).padLeft(10.0f).top();
        
        table.defaults().left();
        checkBox = new CheckBox("Distro", skin);
        table.add(checkBox);
        
        table.row();
        checkBox = new CheckBox("Shell", skin);
        table.add(checkBox);
        
        table.row();
        checkBox = new CheckBox("Sudo", skin);
        table.add(checkBox);
        
        table.row();
        checkBox = new CheckBox("Kernel", skin);
        table.add(checkBox);
        
        table = new Table();
        ((Table) window.findActor("settings")).add(table).padTop(10.0f).padLeft(10.0f).top();
        
        SelectBox selectBox = new SelectBox(skin);
        table.add(selectBox);
        selectBox.setItems("Mint", "Unblemished", "Like New", "Perfect", "Original", "Undamaged", "Fresh", "Pristine", "Immaculate", "Brand New", "Unused");
        
        ((Table) window.findActor("settings")).row();
        table = new Table();
        ((Table) window.findActor("settings")).add(table).colspan(3);
        
        label = new Label("softer", skin);
        table.add(label);
        
        Slider slider = new Slider(0, 100, 1, false, skin, "round-long");
        slider.setName("slider1");
        slider.setValue(50);
        table.add(slider).width(288.0f).colspan(3);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ((ProgressBar) window.findActor("progress1")).setValue(((Slider) actor).getValue());
                ((ProgressBar) window.findActor("progress2")).setValue(((Slider) actor).getValue());
                ((ProgressBar) window.findActor("progress3")).setValue(((Slider) actor).getValue());
                ((Slider) window.findActor("slider2")).setValue(((Slider) actor).getValue());
                ((Slider) window.findActor("slider3")).setValue(((Slider) actor).getValue());
            }
        });
        
        label = new Label("harder", skin);
        table.add(label);
        
        table.row();
        label = new Label("worse", skin);
        table.add(label);
        
        ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin, "round-long");
        progressBar.setName("progress1");
        progressBar.setValue(50);
        table.add(progressBar).width(272).colspan(3);
        
        label = new Label("better", skin);
        table.add(label);
        
        table.row();
        label = new Label("slower", skin);
        table.add(label);
        
        progressBar = new ProgressBar(0, 100, 1, false, skin);
        progressBar.setName("progress2");
        progressBar.setValue(50);
        table.add(progressBar).growX().padLeft(10.0f).padRight(10.0f).colspan(3);
        
        label = new Label("faster", skin);
        table.add(label);
        
        table.row();
        label = new Label("weaker", skin);
        table.add(label);
        
        progressBar = new ProgressBar(0, 100, 1, false, skin, "round-short");
        progressBar.setName("progress3");
        progressBar.setValue(50);
        table.add(progressBar).width(52);
        
        slider = new Slider(0, 100, 1, false, skin);
        slider.setName("slider2");
        slider.setValue(50);
        table.add(slider);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ((ProgressBar) window.findActor("progress1")).setValue(((Slider) actor).getValue());
                ((ProgressBar) window.findActor("progress2")).setValue(((Slider) actor).getValue());
                ((ProgressBar) window.findActor("progress3")).setValue(((Slider) actor).getValue());
                ((Slider) window.findActor("slider1")).setValue(((Slider) actor).getValue());
                ((Slider) window.findActor("slider3")).setValue(((Slider) actor).getValue());
            }
        });
        
        slider = new Slider(0, 100, 1, false, skin, "round-short");
        slider.setName("slider3");
        slider.setValue(50);
        table.add(slider).width(68.0f);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ((ProgressBar) window.findActor("progress1")).setValue(((Slider) actor).getValue());
                ((ProgressBar) window.findActor("progress2")).setValue(((Slider) actor).getValue());
                ((ProgressBar) window.findActor("progress3")).setValue(((Slider) actor).getValue());
                ((Slider) window.findActor("slider2")).setValue(((Slider) actor).getValue());
                ((Slider) window.findActor("slider1")).setValue(((Slider) actor).getValue());
            }
        });
        
        label = new Label("stronger", skin);
        table.add(label);
        
        ((Table) window.findActor("settings")).row();
        table = new Table();
        ((Table) window.findActor("settings")).add(table).padTop(10.0f).colspan(3);
        
        table.defaults().space(10.0f);
        label = new Label("Super User Login:", skin);
        table.add(label);
        
        TextField textField = new TextField("", skin);
        table.add(textField);
        stage.setKeyboardFocus(textField);
        
        table.row();
        label = new Label("Password:", skin);
        table.add(label).right();
        
        textField = new TextField("", skin);
        textField.setPasswordCharacter('*');
        textField.setPasswordMode(true);
        table.add(textField);
        
        ((Table) window.findActor("settings")).row();
        table = new Table();
        ((Table) window.findActor("settings")).add(table).padTop(10.0f).colspan(3);
        
        table.defaults().space(25.0f).minWidth(100.0f);
        TextButton textButton = new TextButton("OK", skin);
        table.add(textButton);
        
        textButton = new TextButton("Cancel", skin);
        table.add(textButton);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
