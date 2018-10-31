package com.ray3k.flatearth;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private final static String longText = "The tale of Flatland tells a story\n"
            + "about a square living in a two dimensional world. He attempts to\n"
            + "convince the world of Lineland, a one dimensional world, that there\n"
            + "is indeed more than just one dimension. He was sadly repudiated.\n"
            + "He is then visited by Sphere who shows Square the three dimensional\n"
            + "world of Spaceland. Having his mind blown by this notion, Square\n"
            + "attempts and fails to convince the people of Flatland that there\n"
            + "are more than two dimensions. An important takeaway is that Sphere,\n"
            + "so sure of his three dimensions, denounced any possibility of a\n"
            + "fourth or fifth dimension. There is more to the world than what\n"
            + "you can see, people.";

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("flat-earth-ui.json"));
        ProgressBarStyle progressBarStyle = skin.get("fancy", ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("slider-fancy-knob").tint(skin.getColor("selection"));
        tiledDrawable.setMinWidth(0);
        progressBarStyle.knobBefore = tiledDrawable;
        
        SliderStyle sliderStyle = skin.get("fancy", SliderStyle.class);
        sliderStyle.knobBefore = tiledDrawable;
        
        Window window = new Window("Flat Earth UI", skin);
        window.setSize(700.0f, 700.0f);
        window.setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f, Align.center);
        window.getTitleTable().getCells().first().padLeft(15.0f);
        stage.addActor(window);
        
        Button button = new Button(skin, "close");
        window.getTitleTable().add(button).padRight(15.0f);
        
        Label label = new Label("Flat Earth UI", skin, "title");
        window.add(label).colspan(2).padBottom(10.0f).expandX();
        
        window.row();
        Table rowTable = new Table();
        window.add(rowTable).growX().colspan(2);
        
        Table table = new Table();
        table.defaults().growX();
        rowTable.add(table).width(200.0f).right();
        
        TextButton textButton = new TextButton("Start", skin);
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("Config", skin);
        table.add(textButton).padTop(10.0f);
        
        table.row();
        textButton = new TextButton("Quit", skin);
        table.add(textButton).padTop(10.0f);
        
        Image image = new Image(skin, "earth");
        image.setScaling(Scaling.none);
        rowTable.add(image).left().padLeft(15.0f);
        
        window.row();
        table = new Table();
        window.add(table).colspan(2).padTop(20.0f);
        
        label = new Label("Volume:", skin);
        table.add(label);
        
        final Slider slider = new Slider(0, 100, 1, false, skin, "fancy");
        slider.setValue(50.0f);
        table.add(slider).width(261);
        
        table.row();
        label = new Label("Progress:", skin);
        table.add(label);
        
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, false, skin, "fancy");
        progressBar.setValue(50.0f);
        table.add(progressBar).width(261);
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        
        window.row();
        table = new Table();
        table.defaults().expandX().left();
        window.add(table).colspan(2).padTop(20.0f);
        
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton imageTextButton = new ImageTextButton("Flat", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
        
        imageTextButton = new ImageTextButton("Horizon", skin, "check");
        imageTextButton.setChecked(true);
        table.add(imageTextButton).padLeft(20.0f);
        
        table.row();
        imageTextButton = new ImageTextButton("Round", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
        
        imageTextButton = new ImageTextButton("Satellites", skin, "check");
        imageTextButton.setChecked(true);
        table.add(imageTextButton).padLeft(20.0f);
        
        table.row();
        imageTextButton = new ImageTextButton("Cuboid", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
        
        imageTextButton = new ImageTextButton("Planes", skin, "check");
        imageTextButton.setChecked(true);
        table.add(imageTextButton).padLeft(20.0f);
        
        window.row();
        rowTable = new Table();
        window.add(rowTable).colspan(2);
        
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Rhetoric", "Hyperbole", "Colorful Prose", "Banality", "Celebrity", "Contrarianism", "Arrogance", "Ignorance", "Denialism", "Solipsism");
        selectBox.setMaxListCount(4);
        rowTable.add(selectBox).padTop(20.0f);
        
        table = new Table();
        table.add(new Label(longText, skin));
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        
        table = new Table();
        Touchpad touchpad = new Touchpad(0, skin);
        table.add(touchpad);
        
        SplitPane splitPane = new SplitPane(table, scrollPane, false, skin);
        splitPane.setSplitAmount(.45f);
        rowTable.add(splitPane).padTop(20.0f).width(250.0f).padLeft(50.0f);
        
        window.row();
        rowTable = new Table();
        window.add(rowTable).colspan(2).growY();
        
        Tree tree = new Tree(skin);
        Node parent = new Tree.Node(new Label("Global", skin));
        tree.add(parent);
        rowTable.add(tree).padTop(20.0f);
        
        Node child = new Tree.Node(new Label("Conspiracy", skin));
        parent.add(child);
        parent = child;
        
        child = new Tree.Node(new Label("Spanning", skin));
        parent.add(child);
        
        child = new Tree.Node(new Label("Thousands of Years", skin));
        parent.add(child);
        
        child = new Tree.Node(new Label("Yet no whistleblowers?", skin));
        parent.add(child);
        tree.expandAll();
        
        table = new Table();
        rowTable.add(table).padTop(20.0f).padLeft(50.0f);
        
        table.add(new Label("User", skin));
        TextField textField = new TextField("", skin);
        table.add(textField).padLeft(10.0f);
        
        table.row();
        table.add(new Label("Pass", skin));
        textField = new TextField("", skin);
        textField.setPasswordCharacter('•');
        textField.setPasswordMode(true);
        table.add(textField).padLeft(10.0f).padTop(5.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(128.0f / 255.0f, 255.0f / 255.0f, 255.0f / 255.0f, 1);
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
