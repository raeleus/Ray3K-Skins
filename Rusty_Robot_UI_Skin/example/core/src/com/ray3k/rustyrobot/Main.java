package com.ray3k.rustyrobot;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private Array<GearTextButton> gearTextButtons;
    private Image dialogCog;
    private float meterRotation;
    private static final String longText1 = "1. A robot may not injure a human being or, through inaction, allow a human being to come to harm.\n\n"
            + "2. A robot must obey the orders given it by human beings except where such orders would conflict with the First Law.\n\n"
            + "3. A robot must protect its own existence as long as such protection does not conflict with the First or Second Laws.";
    private static final String longText2 = "1. A robot may not injure THE SUPREME COMMANDER or, through inaction, allow THE SUPREME COMMANDER to come to harm.\n\n"
            + "2. A robot must obey the orders given it by THE SUPREME COMMANDER except where such orders would conflict with the First Law.\n\n"
            + "3. A robot must protect its own existence.";
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("rusty-robot-ui.json"));
        gearTextButtons = new Array<GearTextButton>();
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        root.add(new Label("Rusty Robot UI", skin, "title")).colspan(3);
        
        root.row();
        Table table = new Table();
        table.defaults().left().padBottom(5.0f).top().padLeft(10.0f).colspan(2);
        root.add(table).growY();
        table.add(new CheckBox("Assimilate Humans", skin));
        
        table.row();
        ButtonGroup buttonGroup = new ButtonGroup();
        CheckBox radioButton = new CheckBox("Victorian Clockwork Companion", skin, "radio");
        buttonGroup.add(radioButton);
        table.add(radioButton).padTop(5.0f);
        
        table.row();
        radioButton = new CheckBox("Industrial Revolution Robot", skin, "radio");
        buttonGroup.add(radioButton);
        table.add(radioButton);
        
        table.row();
        radioButton = new CheckBox("Space-Age Automaton", skin, "radio");
        buttonGroup.add(radioButton);
        table.add(radioButton);        
        
        table.row();
        table.defaults().reset();
        table.add(new Label("Name: ", skin)).right();
        table.add(new TextField("", skin)).left().padTop(10.0f);
        
        table.row();
        table.add(new Label("Pass: ", skin)).right();
        TextField pass = new TextField("", skin);
        pass.setPasswordCharacter('•');
        pass.setPasswordMode(true);
        table.add(pass).padTop(10.0f).left();
        
        table.row();
        Tree tree = new Tree(skin);
        Node parent = new Tree.Node(new Label("Power Source", skin));
        tree.add(parent);
        Node child = new Node(new Label("Spring Meachanism", skin));
        parent.add(child);
        child = new Node(new Label("Steam Engine", skin));
        parent.add(child);
        child = new Node(new Label("Aether Battery", skin));
        parent.add(child);
        parent.expandAll();
        
        parent = new Tree.Node(new Label("Chassis", skin));
        tree.add(parent);
        child = new Node(new Label("Brass", skin));
        parent.add(child);
        child = new Node(new Label("Copper", skin));
        parent.add(child);
        child = new Node(new Label("Iron", skin));
        parent.add(child);
        table.add(tree).colspan(2).padTop(10.0f).left();
        
        table.row();
        Image image = new Image(skin, "robot");
        image.setScaling(Scaling.fit);
        image.setAlign(Align.left);
        table.add(image).expandY().bottom().left().colspan(2);
        
        Table top = new Table();
        table = new Table();
        top.add(table).padRight(15.0f);
        
        final Slider slider = new Slider(0, 100, 1, true, skin);
        table.add(slider).size(67, 240).padRight(15.0f);
        
        final ProgressBar progressBar = new ProgressBar(0, 100, 1, true, skin);
        table.add(progressBar).size(67, 326);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        
        table = new Table();
        top.add(table).top();
        GearTextButton gearTextButton = new GearTextButton("Start", skin);
        gearTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                final GearTextButton gearTextButton1 = new GearTextButton("Yes", skin);
                final GearTextButton gearTextButton2 = new GearTextButton("No", skin);
                
                final Dialog dialog = new Dialog("", skin, "empty-bg") {
                    @Override
                    public boolean remove() {
                        dialogCog = null;
                        gearTextButtons.removeValue(gearTextButton1, false);
                        gearTextButtons.removeValue(gearTextButton2, false);
                        
                        return super.remove();
                    }
                    
                };
                Stack stack = new Stack();
                
                Container cont = new Container();
                cont.fill();
                cont.padLeft(159);
                cont.padTop(123.0f);
                cont.padRight(139);
                cont.padBottom(65);
                Container black = new Container();
                black.setBackground(skin.getDrawable("black"));
                cont.setActor(black);
                stack.add(cont);
                
                cont = new Container();
                dialogCog = new Image(skin, "cog3");
                dialogCog.setOrigin(Align.center);
                cont.setActor(dialogCog);
                cont.top().left();
                cont.padLeft(166);
                cont.padTop(82);
                stack.add(cont);
                
                cont = new Container();
                cont.background(skin.getDrawable("window"));
                stack.add(cont);
                
                Table table = new Table();
                Label label = new Label("Continue?", skin, "bg");
                table.add(label).top().padTop(30.0f).padLeft(24.0f).colspan(2);
                
                table.row();
                gearTextButton1.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        dialog.hide();
                    }
                });
                gearTextButtons.add(gearTextButton1);
                table.add(gearTextButton1).top().expandY();
                
                gearTextButton2.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        dialog.hide();
                    }
                });
                gearTextButtons.add(gearTextButton2);
                table.add(gearTextButton2).top().expandY();
                stack.add(table);
                
                dialog.add(stack).size(408, 246);
                dialog.key(Keys.ESCAPE, false);
                dialog.show(stage);
            }
        });
        gearTextButtons.add(gearTextButton);
        table.add(gearTextButton);
        table.row();
        gearTextButton = new GearTextButton("Stop", skin);
        gearTextButtons.add(gearTextButton);
        table.add(gearTextButton);
        table.row();
        gearTextButton = new GearTextButton("Set", skin);
        gearTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                final Dialog dialog = new Dialog("", skin, "empty-bg");
                Stack stack = new Stack();
                
                Container cont = new Container();
                cont.background(skin.getDrawable("meter"));
                stack.add(cont);
                
                cont = new Container();
                Image image = new Image(skin, "meter-needle");
                image.setOrigin(Align.center);
                image.setRotation(100.0f);
                cont.setActor(image);
                stack.add(cont);
                
                cont = new Container();
                cont.background(skin.getDrawable("meter-front"));
                stack.add(cont);
                
                RotateToAction rotateRightAction = new RotateToAction();
                rotateRightAction.setRotation(-100.0f);
                rotateRightAction.setDuration(3.0f);
                
                RotateToAction rotateLeftAction = new RotateToAction();
                rotateLeftAction.setRotation(100.0f);
                rotateLeftAction.setDuration(3.0f);
                
                Action hideAction = new Action() {
                    @Override
                    public boolean act(float delta) {
                        dialog.hide();
                        return true;
                    }
                };
                
                SequenceAction sequenceAction = new SequenceAction();
                sequenceAction.addAction(rotateRightAction);
                sequenceAction.addAction(rotateLeftAction);
                sequenceAction.addAction(hideAction);
                
                image.addAction(sequenceAction);
                
                dialog.add(stack).size(265, 265);
                dialog.key(Keys.ESCAPE, false);
                dialog.show(stage);
            }
        });
        gearTextButtons.add(gearTextButton);
        table.add(gearTextButton);
        
        table.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Select Box", "Cogs", "Springs", "Coils", "Valves", "Keys", "Lenses", "Spirits");
        table.add(selectBox).minWidth(201).padTop(10.0f);
        
        table = new Table();
        table.row();
        table.add(new Label("The Three Laws of Robotics", skin, "bg")).padTop(15.0f).padBottom(10.0f);
        table.row();
        Label label = new Label(longText1, skin);
        label.setWrap(true);
        table.add(label).growX();
        
        table.row();
        table.add(new Label("The Three Laws rev. by THE SUPREME COMMANDER", skin, "bg")).padTop(20.0f).padBottom(10.0f);
        table.row();
        label = new Label(longText2, skin);
        label.setWrap(true);
        table.add(label).growX();
        
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setVariableSizeKnobs(false);
        
        SplitPane splitPane = new SplitPane(top, scrollPane, true, skin);
        root.add(splitPane).grow().padRight(10.0f);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.67f, .64f, .45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        for (GearTextButton gearTextButton : gearTextButtons) {
            gearTextButton.setDegrees(gearTextButton.getDegrees() + 25.0f * Gdx.graphics.getRawDeltaTime());
        }
        
        if (dialogCog != null) {
            dialogCog.setRotation(dialogCog.getRotation() + 10.0f * Gdx.graphics.getRawDeltaTime());
        }
        
        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
