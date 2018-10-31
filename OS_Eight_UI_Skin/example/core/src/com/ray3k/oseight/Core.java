package com.ray3k.oseight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.awt.SplashScreen;

public class Core extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private final static String LONG_TEXT = "Accelerated! Responsive! "
            + "Uncomplicated! Enjoy the full performance of PowerPP with OS "
            + "Eight! With OS Eight, enjoy the wonders of MULTITASKING. You can "
            + "continue to use your computer without waiting for file operations "
            + "to complete! Continuing to be the industry standard for "
            + "aesthetics, marvel at our unprecedented use of the rounded "
            + "rectangle! With complete control of the personality of the "
            + "computer, you can select a wallpaper from our vast array of "
            + "images. This is the Windows 95 killer. Do it your way today!";

    @Override
    public void create() {
        SplashScreen splashScreen = SplashScreen.getSplashScreen();
        if (splashScreen != null) {
            splashScreen.close();
        }
        
        skin = new Skin(Gdx.files.internal("skin/OS Eight.json"));
        skin.get("title-color", LabelStyle.class).font.getData().markupEnabled = true;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setBackground(skin.getTiledDrawable("bg"));
        root.setFillParent(true);
        stage.addActor(root);
        
        Window window = new Window("", skin, "startup");
        window.setSize(600.0f, 500.0f);
        window.setPosition(Gdx.graphics.getWidth() / 2.0f + 50.0f, Gdx.graphics.getHeight() / 2.0f - 30.0f, Align.center);
        stage.addActor(window);
        
        Table table = new Table(skin);
        table.setBackground("white-rect");
        window.add(table).grow().pad(50.0f).padBottom(25.0f);
        
        Image image = new Image(skin, "logo");
        table.add(image);
        
        table.row();
        Label label = new Label("[#55bce0]OS [BLACK]Eight", skin, "title-color");
        table.add(label);
        
        window.row();
        label = new Label("Starting Up...", skin, "medium");
        window.add(label);
        
        window.row();
        final ProgressBar startupBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin, "startup");
        window.add(startupBar).padTop(10.0f).padBottom(25.0f);
        
        window = new Window("", skin);
        window.setSize(500.0f, 500.0f);
        window.setPosition(Gdx.graphics.getWidth() / 2.0f - 50.0f, Gdx.graphics.getHeight() / 2.0f + 50.0f, Align.center);
        stage.addActor(window);
        
        window.getTitleTable().clearChildren();
        window.getTitleTable().defaults().space(5.0f);
        Button button = new Button(skin, "close");
        window.getTitleTable().add(button);
        
        image = new Image(skin, "title-bar-bg");
        image.setScaling(Scaling.stretchX);
        window.getTitleTable().add(image).growX();
        
        label = new Label("OS Eight UI Skin", skin);
        window.getTitleTable().add(label).padLeft(20.0f).padRight(20.0f);
        
        image = new Image(skin, "title-bar-bg");
        image.setScaling(Scaling.stretchX);
        window.getTitleTable().add(image).growX();
        
        button = new Button(skin, "restore");
        window.getTitleTable().add(button);
        
        button = new Button(skin, "minimize");
        window.getTitleTable().add(button);
        
        window.defaults().space(5.0f);
        label = new Label("User Name:", skin);
        window.add(label);
        
        TextField textField = new TextField("", skin);
        window.add(textField);
        
        window.row();
        label = new Label("Password:", skin);
        window.add(label);
        
        textField = new TextField("", skin);
        textField.setPasswordCharacter('•');
        textField.setPasswordMode(true);
        window.add(textField);
        
        window.row();
        final Slider slider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        window.add(slider).colspan(2).padTop(10.0f);
        
        window.row();
        final Slider volumeSlider = new Slider(0, 100.0f, 1.0f, false, skin, "volume-horizontal");
        window.add(volumeSlider).colspan(2);
        
        window.row();
        final ProgressBar progressBar = new ProgressBar(0, 100.0f, 1.0f, false, skin);
        window.add(progressBar).colspan(2);
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                volumeSlider.setValue(slider.getValue());
                progressBar.setValue(slider.getValue());
                startupBar.setValue(slider.getValue());
            }
        });
        
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                slider.setValue(slider.getValue());
                progressBar.setValue(slider.getValue());
                startupBar.setValue(slider.getValue());
            }
        });
        
        window.row();
        ImageTextButton imageTextButton = new ImageTextButton("AirPort", skin, "checkbox");
        window.add(imageTextButton).colspan(2).left().padTop(10.0f);
        
        window.row();
        ButtonGroup buttonGroup = new ButtonGroup();
        imageTextButton = new ImageTextButton("Walled Garden", skin, "radio");
        buttonGroup.add(imageTextButton);
        window.add(imageTextButton).colspan(2).left();
        
        window.row();
        imageTextButton = new ImageTextButton("Closed Ecosystem", skin, "radio");
        buttonGroup.add(imageTextButton);
        window.add(imageTextButton).colspan(2).left();
        
        window.row();
        imageTextButton = new ImageTextButton("Mac OS", skin, "radio");
        buttonGroup.add(imageTextButton);
        window.add(imageTextButton).colspan(2).left();
        
        window.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Never falls far from the tree", "iPod", "Granny Smith", "Keeps the doctor away", "MacBook", "Bad", "Forbidden fruit", "iPhone", "Pie", "Spoils the whole barrel", "Caramel", "Cider", "Golden Delicious", "Watch", "Sauce", "Of my eye", "Turnover", "The Big", "iMac", "How 'bout them?", "iPad", "Gala");
        window.add(selectBox).colspan(2).padTop(10.0f);
        
        window.row();
        label = new Label(LONG_TEXT, skin);
        label.setWrap(true);
        
        ScrollPane scrollPane = new ScrollPane(label, skin, "white-bg");
        scrollPane.setFadeScrollBars(false);
        scrollPane.setVariableSizeKnobs(false);
        window.add(scrollPane).colspan(2).size(200.0f, 100.0f);
        
        window.row();
        table = new Table();
        window.add(table).colspan(2).padTop(10.0f);
        
        table.defaults().space(10.0f).minWidth(75.0f);
        TextButton textButton = new TextButton("OK", skin);
        table.add(textButton);
        
        textButton = new TextButton("Cancel", skin);
        table.add(textButton);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        stage.dispose();
        skin.dispose();
    }
}
