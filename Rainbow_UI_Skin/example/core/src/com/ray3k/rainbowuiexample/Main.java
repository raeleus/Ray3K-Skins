package com.ray3k.rainbowuiexample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private static final String LONG_TEXT = "Reports from beyond the clouds "
            + "today reveal the presence of a new hero in town: Buddy the "
            + "Unicorn. Are his interests aligned with our own? Only time will "
            + "tell. One thing we do know, however, is that many a fuzzy "
            + "creature can thank Buddy for his contributions to science and "
            + "literature. He is well known among the social elite as the next "
            + "great conveyor of logic and passion. A true paragon of our moral "
            + "base. Some day we will look back on to this day as the day our "
            + "civilization was born. Will you remember where you were when the "
            + "great rainbow landed in the city square? He is the future and "
            + "you heard it here first, folks.";

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("rainbow-ui.json"));
        
        ProgressBarStyle style = skin.get("heart", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("progress-bar-heart");
        tiledDrawable.setMinWidth(0.0f);
        style.knobBefore = tiledDrawable;
        
        style = skin.get("star", ProgressBarStyle.class);
        tiledDrawable = skin.getTiledDrawable("progress-bar-star");
        tiledDrawable.setMinWidth(0.0f);
        style.knobBefore = tiledDrawable;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table table = new Table(skin);
        table.setBackground("bg");
        table.setFillParent(true);
        stage.addActor(table);
        
        Label label = new Label("Rainbow UI", skin, "title");
        root.add(label).colspan(3);
        
        root.row();
        table = new Table();
        table.defaults().pad(5.0f);
        root.add(table).padTop(50.0f);
        
        table.row();
        TextButton textButton = new TextButton("Start", skin);
        table.add(textButton).growX();
        
        table.row();
        textButton = new TextButton("Options", skin);
        table.add(textButton).growX();
        
        table.row();
        textButton = new TextButton("Quit", skin);
        table.add(textButton).growX();
        
        table = new Table();
        root.add(table);
        
        final ProgressBar heartBar = new ProgressBar(0, 100, 1, false, skin, "heart");
        heartBar.setValue(50.0f);
        table.add(heartBar).width(85.0f);
        
        table.row();
        final ProgressBar starBar = new ProgressBar(0, 100, 1, false, skin, "star");
        starBar.setValue(50.0f);
        table.add(starBar).width(80.0f).padTop(10.0f);
        
        table.row();
        Touchpad touchpad = new Touchpad(0, skin);
        table.add(touchpad).padTop(10.0f);
        
        table = new Table();
        root.add(table).growX().padLeft(10.0f);
        
        
        label = new Label(LONG_TEXT, skin);
        label.setWrap(true);
        
        ScrollPane scrollPane = new ScrollPane(label, skin);
        scrollPane.setFadeScrollBars(false);
        table.add(scrollPane).growX().height(200.0f);
        
        table.row();
        SelectBox<String> selectBox = new SelectBox<String>(skin);
        selectBox.setItems("Pearl", "Ruby", "Diamond", "Emerald", "Sapphire", "Amethyst", "Amber", "Topaz");
        table.add(selectBox);
        
        root.row();
        final Slider slider = new Slider(0, 100, 1, false, skin);
        slider.setValue(50.0f);
        root.add(slider).growX().colspan(3);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                heartBar.setValue(slider.getValue());
                starBar.setValue(slider.getValue());
            }
        });
        
        root.row();
        Table bottom = new Table();
        root.add(bottom).colspan(3).growX();
        
        table = new Table();
        bottom.add(table).expandX();
        
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton imageTextButton = new ImageTextButton("Fairy", skin, "star");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).expandX().left();
        
        table.row();
        imageTextButton = new ImageTextButton("Butterfly", skin, "star");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).expandX().left();
        
        table.row();
        imageTextButton = new ImageTextButton("Elf", skin, "star");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).expandX().left();
        
        table = new Table();
        bottom.add(table).expandX();
        
        buttonGroup = new ButtonGroup();
        imageTextButton = new ImageTextButton("Horsey", skin, "heart");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).expandX().left();
        
        table.row();
        imageTextButton = new ImageTextButton("Unicorn", skin, "heart");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).expandX().left();
        
        table.row();
        imageTextButton = new ImageTextButton("Pegasus", skin, "heart");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).expandX().left();
        
        Window window = new Window("Are you sure?", skin);
        stage.addActor(window);
        window.setSize(200.0f, 250.0f);
        
        window.getTitleTable().add(new Button(skin, "close"));
        window.getTitleLabel().setAlignment(Align.center);
        
        label = new Label("User", skin);
        window.add(label).colspan(2);
        
        window.row();
        TextField textField = new TextField("", skin);
        window.add(textField).colspan(2);
        
        window.row();
        label = new Label("Pass", skin);
        window.add(label).colspan(2);
        
        window.row();
        textField = new TextField("", skin);
        textField.setPasswordCharacter('•');
        textField.setPasswordMode(true);
        window.add(textField).colspan(2);
        
        window.row();
        textButton = new TextButton("Yes", skin, "small");
        window.add(textButton).expandX().padTop(10.0f);
        
        textButton = new TextButton("No", skin, "small");
        window.add(textButton).expandX().padTop(10.0f);
    }

    
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(213.0f / 255.0f, 204.0f / 255.0f, 255.0f / 255.0f, 1);
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
