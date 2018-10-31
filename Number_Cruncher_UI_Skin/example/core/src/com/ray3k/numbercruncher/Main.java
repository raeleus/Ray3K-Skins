package com.ray3k.numbercruncher;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private float total;
    private String input;
    private Label lcdLabel;
    private boolean resetFlag;
    
    private static enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, EQUALS, NONE
    }
    private Operation operation;
    
    @Override
    public void create() {
        total = 0.0f;
        input = "";
        operation = Operation.NONE;
        resetFlag = false;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("number-cruncher-ui.json"));
        
        skin.getFont("lcd").setFixedWidthGlyphs("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table table = new Table();
        table.setBackground(skin.getDrawable("bg"));
        root.add(table);
        
        Table panel = new Table();
        panel.setBackground(skin.getDrawable("panel"));
        table.add(panel).top().padTop(35.0f).colspan(4);
        
        panel.add(new Label("libGDX", skin, "title")).top().left().expandX().padTop(10.0f).padLeft(10.0f);
        
        panel.add(new Image(skin, "solar-panel")).top().padRight(10.0f).padTop(5.0f);
        
        panel.row();
        Table lcd = new Table();
        lcd.setBackground(skin.getDrawable("lcd"));
        panel.add(lcd).colspan(2).expandY();
        
        lcdLabel = new Label(".", skin, "lcd");
        lcdLabel.setAlignment(Align.right);
        lcd.add(lcdLabel).growX();
        
        panel.row();
        Table panelBlack = new Table();
        panelBlack.setBackground(skin.getDrawable("panel-black"));
        panel.add(panelBlack).colspan(2).padBottom(3.0f);
        
        panelBlack.add(new Label("Number Cruncher UI", skin)).expandX().left().padLeft(10.0f);
        panelBlack.add(new Label("10 DIGIT", skin)).padRight(10.0f);
        
        table.defaults().padTop(10.0f).expandX();
        table.row();
        table.add();
        table.add();
        
        TextButton textButton = new TextButton("C", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                clear();
            }
        });
        table.add(textButton);
        
        textButton = new TextButton("/", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                process(Operation.DIVIDE);
            }
        });
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("7", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("7");
            }
        });
        table.add(textButton);
        
        textButton = new TextButton("8", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("8");
            }
        });
        table.add(textButton);
        
        textButton = new TextButton("9", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("9");
            }
        });
        table.add(textButton);
        
        textButton = new TextButton("X", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                process(Operation.MULTIPLY);
            }
        });
        table.add(textButton);
        
        table.row();
        textButton = new TextButton("4", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("4");
            }
        });
        table.add(textButton);
        
        textButton = new TextButton("5", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("5");
            }
        });
        table.add(textButton);
        
        textButton = new TextButton("6", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("6");
            }
        });
        table.add(textButton);
        
        textButton = new TextButton("-", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                process(Operation.SUBTRACT);
            }
        });
        table.add(textButton);
        
        table.defaults().expandY().top();
        table.row();
        Table bottom = new Table();
        table.add(bottom).colspan(3).growX();
        
        bottom.defaults().expandX();
        textButton = new TextButton("1", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("1");
            }
        });
        bottom.add(textButton);
        
        textButton = new TextButton("2", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("2");
            }
        });
        bottom.add(textButton);
        
        textButton = new TextButton("3", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("3");
            }
        });
        bottom.add(textButton);
        
        bottom.defaults().padTop(10.0f);
        bottom.row();
        textButton = new TextButton("0", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                newDigit("0");
            }
        });
        bottom.add(textButton);
        
        textButton = new TextButton(".", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (input.contains(".")) {
                    input = input.replace(".", "");
                }
                input += ".";
                if (input.length() > 1) {
                    updateLcd(Float.parseFloat(input), true);
                } else {
                    updateLcd(0, true);
                }
            }
        });
        bottom.add(textButton);
        
        textButton = new TextButton("=", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                process(Operation.EQUALS);
            }
        });
        bottom.add(textButton);
        
        textButton = new TextButton("+", skin, "large");
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                process(Operation.ADD);
            }
        });
        table.add(textButton).height(146.0f);
    }
    
    private void process(Operation newOperation) {
        if (!input.equals("")) {
            float value = Float.parseFloat(input);
            if (newOperation != Operation.EQUALS) {
                operation = newOperation;
                total = value;
                input = "";
                lcdLabel.setText(".");
            } else {
                switch (operation) {
                    case ADD:
                        total += value;
                        updateLcd(total);
                        input = "";
                        break;
                    case SUBTRACT:
                        total -= value;
                        updateLcd(total);
                        input = "";
                        break;
                    case MULTIPLY:
                        total *= value;
                        updateLcd(total);
                        input = "";
                        break;
                    case DIVIDE:
                        if (value != 0) {
                            total /= value;
                            updateLcd(total);
                            input = "";
                        } else {
                            lcdLabel.setText("ERROR: DIV BY 0");
                        }
                        break;
                }
            }
        } else {
            if (operation != Operation.NONE) {
                operation = newOperation;
            }
        }
    }
    
    private void newDigit(String digit) {
        if (resetFlag) {
            resetFlag = false;
            input = "";
            operation = Operation.NONE;
            total = 0.0f;
        }
        input += digit;
        updateLcd(Float.parseFloat(input));
    }
    
    private void updateLcd(float screenValue) {
        updateLcd(screenValue, false);
    }
    
    private void updateLcd(float screenValue, boolean dot) {
        if (screenValue == (int) screenValue){
            String output;
            output = Integer.toString((int)screenValue);
            if (dot) {
                output += ".";
            }
            lcdLabel.setText(output);
        } else {
            String text = Float.toString(screenValue);
            text = text.replace("E", " E");
            lcdLabel.setText(text);
        }
    }
    
    private void clear() {
        total = 0;
        operation = Operation.NONE;
        input = "";
        lcdLabel.setText(".");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getDeltaTime());
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
