package com.ray3k.pixthulhuui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("pixthulhu-ui.json"));
        
        skin.get("health", ProgressBarStyle.class).knobBefore = skin.getTiledDrawable("progress-bar-health-knob");
        skin.get("mana", ProgressBarStyle.class).knobBefore = skin.getTiledDrawable("progress-bar-mana-knob");
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table table = new Table(skin);
        table.setBackground("window-round");
        root.add(table).grow().pad(25.0f);
        
        Label label = new Label("The Call of\nPixthulhu", skin, "title");
        label.setAlignment(Align.right);
        label.setColor(Color.BLUE);
        table.add(label).left().padLeft(75.0f);
        
        table.row();
        label = new Label("In his house at R'lyeh, dead Pixthulhu waits dreaming.", skin, "subtitle");
        label.setAlignment(Align.center);
        label.setColor(Color.RED);
        label.setWrap(true);
        table.add(label).growX();
        
        table.row();
        label = new Label("That is not dead which can eternal lie, And with strange aeons even death may die...", skin);
        label.setAlignment(Align.center);
        label.setWrap(true);
        table.add(label).growX();
        
        table.row();
        label = new Label("Continue?", skin, "subtitle");
        label.setAlignment(Align.center);
        label.setColor(Color.YELLOW);
        table.add(label).padTop(15.0f).padBottom(15.0f);
        
        table.row();
        Table subTable = new Table();
        TextButton textButton = new TextButton("Yes", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                showGame();
            }
        });
        subTable.add(textButton).expandX();
        subTable.add(new TextButton("No", skin)).expandX();
        table.add(subTable).growX();
        
        table.row();
        table.add(new Slider(0, 100, 1, false, skin)).growX().padTop(25.0f);
        
        table.row();
        subTable = new Table();
        
        subTable.add(new TextField("", skin)).expandX();
        
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Cultist", "Archaeologist", "Sailor", "Professor", "Police Inspector", "Artist");
        subTable.add(selectBox).expandX();
        
        Image image = new Image(skin, "logo");
        image.setScaling(Scaling.fit);
        ScrollPane scrollPane = new ScrollPane(image, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        
        SplitPane splitPane = new SplitPane(subTable, scrollPane, true, skin);
        table.add(splitPane).grow();
    }
    
    private void showGame() {
        Dialog dialog = new Dialog("", skin);
        dialog.setFillParent(true);
        
        Table table = new Table();
        table.add(new Image(skin, "portrait")).padLeft(20.0f);
        ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        progressBar.setValue(75);
        table.add(progressBar).growX().bottom().padLeft(5.0f).padRight(20.0f);
        dialog.getContentTable().add(table).colspan(2).growX();
        
        dialog.getContentTable().row();
        progressBar = new ProgressBar(0, 100, 1, false, skin, "health");
        progressBar.setValue(100);
        dialog.getContentTable().add(progressBar).left().size(500, 92);
        
        dialog.getContentTable().row();
        progressBar = new ProgressBar(0, 100, 1, false, skin, "mana");
        progressBar.setValue(100);
        dialog.getContentTable().add(progressBar).left().size(500, 92);
        
        dialog.getContentTable().row();
        Image image = new Image(skin, "pixthulhu");
        image.setScaling(Scaling.fit);
        dialog.getContentTable().add(image).size(309,405).colspan(2).left().expandY();
        
        dialog.getContentTable().row();
        dialog.getContentTable().add(new Touchpad(0, skin)).expandX().bottom().left().pad(15.0f);
        
        table = new Table();
        table.add(new Button(skin, "arcade")).padRight(25.0f);
        table.add(new Button(skin, "arcade"));
        table.row();
        table.add(new Label("Punch", skin)).padRight(25.0f);
        Label label = new Label("Cosmic\nMagic", skin);
        label.setAlignment(Align.center);
        table.add(label);
        dialog.getContentTable().add(table).bottom().pad(15.0f);
        
        stage.addActor(dialog);
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
        skin.dispose();
        stage.dispose();
    }
}
