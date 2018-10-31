package com.ray3k.biologicalattackui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private String longText = "The biohazard symbol is a very recognizable "
            + "graphic used throughout the medical field. In the entertainment "
            + "industry, it's synonymous with outbreaks of infection crazed monkeys, "
            + "zombies, and world ending epidemics. One may notice, however, "
            + "that the symbol itself is utterly meaningless. It's not a model "
            + "describing some aspect of virology like how the atom diagram "
            + "illustrates neutrons and electrons. It's just an example of "
            + "effective symbolism proliferating throughout our culture.";

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("biological-attack-ui.json"));
        
        ProgressBarStyle progressBarStyle = skin.get("default-horizontal", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("progress-bar-knob");
        tiledDrawable = tiledDrawable.tint(skin.getColor("background"));
        tiledDrawable.setMinWidth(0);
        progressBarStyle.knobBefore = tiledDrawable;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Window window = new Window("CAUTION", skin);
        window.setSize(500.0f, 300.0f);
        window.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() - 45.0f, Align.top);
        window.getTitleLabel().setAlignment(Align.center);
        window.getTitleLabel().setStyle(skin.get("title-bg", LabelStyle.class));
        window.getTitleTable().getCells().first().fill(false);
        stage.addActor(window);
        
        Table left = new Table();
        
        ImageTextButton imageTextButton = new ImageTextButton("BIOLOGICAL\nHAZARD", skin, "warning");
        left.add(imageTextButton);
        
        left.row();
        imageTextButton = new ImageTextButton("GASMASKS\nMANDATORY", skin, "gasmask");
        left.add(imageTextButton).padTop(25.0f);
        
        Table right = new Table();
        
        Image image = new Image(skin, "icon-biohazard-large-c");
        image.setScaling(Scaling.fit);
        right.add(image);
        
        SplitPane splitPane = new SplitPane(left, right, false, skin);
        window.add(splitPane).grow();
        
        left = new Table();
        root.add(left).grow().padTop(375.0f);
        
        Table table = new Table();
        table.defaults().left().expandX();
        left.add(table);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        CheckBox checkBox = new CheckBox("HARD", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox);
        
        table.row();
        checkBox = new CheckBox("MEDIUM", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox).padTop(10.0f);
        
        table.row();
        checkBox = new CheckBox("EASY", skin, "radio");
        buttonGroup.add(checkBox);
        table.add(checkBox).padTop(10.0f);
        
        left.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems(" NUKE DUKEM", " FALLINGOUT", " BORDERWALLS", " THE FIRST OF US", " THE STROLLING DECEASED", " TOOK4ALIVE");
        left.add(selectBox).expandY().top().padTop(25.0f);
        
        right = new Table();
        root.add(right).grow().padTop(375.0f);
        
        table = new Table();
        table.defaults().left();
        right.add(table);
        
        checkBox = new CheckBox("SFX", skin);
        table.add(checkBox);
        
        table.row();
        checkBox = new CheckBox("BGM", skin);
        table.add(checkBox);
        
        right.row();
        TextField textField = new TextField("Type Here", skin);
        right.add(textField);
        
        table = new Table();
        
        image = new Image(skin, "icon-poison-c");
        table.add(image).minWidth(71.0f).padLeft(10.0f);
        
        image = new Image(skin, "icon-fire-c");
        table.add(image).minWidth(58.0f).padLeft(10.0f);
        
        image = new Image(skin, "icon-electricity-c");
        table.add(image).expandX().left().minWidth(44.0f).padLeft(10.0f);
        
        table.row();
        Label label = new Label(longText, skin);
        label.setWrap(true);
        table.add(label).size(400.0f, 400.0f).colspan(3);
        
        right.row();
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        right.add(scrollPane).size(300.0f, 150.0f).padTop(20.0f);
        
        right.row();
        table = new Table();
        right.add(table);
        
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin);
        progressBar.setValue(50.0f);
        table.add(progressBar).size(212, 33).padTop(20.0f);
        
        table.row();
        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.setValue(50.0f);
        table.add(slider).padTop(20.0f).growX();
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            dispose();
            create();
        }
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
