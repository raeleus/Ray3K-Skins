package com.ray3k.arcadeui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("arcade-ui.json"));
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table(skin);
        root.setBackground("bg");
        root.setFillParent(true);
        stage.addActor(root);
        
        Table booth = new Table(skin);
        booth.setBackground("booth");
        root.add(booth).size(594, 783).expand().bottom();
        
        Label label = new Label("Arcade UI", skin, "title");
        booth.add(label);
        
        booth.row();
        label = new Label("INSERT COIN(S)\nTO CONTINUE\n7", skin, "screen");
        label.setAlignment(Align.center);
        booth.add(label).padTop(250.0f);
        
        booth.row();
        Table table = new Table();
        booth.add(table).expandY().bottom();
        Stack stack = new Stack();
        table.add(stack);
        
        Image image = new Image(skin, "button-bg");
        stack.add(image);
        
        Button button = new Button(skin);
        Container container = new Container(button);
        container.center();
        stack.add(container);
        
        label = new Label("1P Start", skin);
        table.add(label).padLeft(10.0f);
        
        booth.row();
        Table bottom = new Table();
        booth.add(bottom).padBottom(15.0f);
        
        stack = new Stack();
        bottom.add(stack);
        
        image = new Image(skin, "joystick-bg");
        stack.add(image);
        
        final Touchpad touchpad = new Touchpad(0, skin, "red");
        stack.add(touchpad);
        
        //change joystick image based on touchpad position
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                int align = 0;
                
                if (touchpad.getKnobPercentX() < -.25f) {
                    align = Align.left;
                } else if (touchpad.getKnobPercentX() > .25f) {
                    align = Align.right;
                }
                
                if (touchpad.getKnobPercentY() < -.25f) {
                    align += Align.bottom;
                } else if (touchpad.getKnobPercentY() > .25f) {
                    align += Align.top;
                }
                
                switch(align) {
                    case Align.left:
                        touchpad.getStyle().background = skin.getDrawable("joystick-l-red");
                        break;
                    case Align.topLeft:
                        touchpad.getStyle().background = skin.getDrawable("joystick-ul-red");
                        break;
                    case Align.top:
                        touchpad.getStyle().background = skin.getDrawable("joystick-u-red");
                        break;
                    case Align.topRight:
                        touchpad.getStyle().background = skin.getDrawable("joystick-ur-red");
                        break;
                    case Align.right:
                        touchpad.getStyle().background = skin.getDrawable("joystick-r-red");
                        break;
                    case Align.bottomRight:
                        touchpad.getStyle().background = skin.getDrawable("joystick-dr-red");
                        break;
                    case Align.bottom:
                        touchpad.getStyle().background = skin.getDrawable("joystick-d-red");
                        break;
                    case Align.bottomLeft:
                        touchpad.getStyle().background = skin.getDrawable("joystick-dl-red");
                        break;
                    default:
                        touchpad.getStyle().background = skin.getDrawable("joystick-red");
                }
            }
        });
        
        table = new Table();
        bottom.add(table).padLeft(10.0f);
        
        stack = new Stack();
        table.add(stack);
        
        image = new Image(skin, "button-bg");
        stack.add(image);
        
        button = new Button(skin, "red");
        container = new Container(button);
        container.center();
        stack.add(container);
        
        stack = new Stack();
        table.add(stack);
        
        image = new Image(skin, "button-bg");
        stack.add(image);
        
        button = new Button(skin, "blue");
        container = new Container(button);
        container.center();
        stack.add(container);
        
        stack = new Stack();
        table.add(stack);
        
        image = new Image(skin, "button-bg");
        stack.add(image);
        
        button = new Button(skin);
        container = new Container(button);
        container.center();
        stack.add(container);
        
        table.row();
        label = new Label("Punch", skin);
        table.add(label);
        
        label = new Label("Kick", skin);
        table.add(label);
        
        label = new Label("Jump", skin);
        table.add(label);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getRawDeltaTime());
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
        skin.dispose();
        stage.dispose();
    }
}
