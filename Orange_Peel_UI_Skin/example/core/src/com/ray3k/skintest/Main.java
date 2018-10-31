package com.ray3k.skintest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private static Skin skin;
    static final Vector2 temp = new Vector2();
    final Array<TextButton> menuButtons = new Array<TextButton>();
    private int spinnerValue;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table rootTable = new Table(skin);
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        
        Label label = new Label("Orange Peel UI", skin, "title");
        label.setAlignment(Align.top);
        rootTable.add(label).colspan(2).growX();
        
        rootTable.row();
        Texture texture = new Texture("data/orange-logo.png");
        TextureRegion region = new TextureRegion(texture);
        Drawable drawable = new TextureRegionDrawable(region);
        Window window = new Window("Oranges:\nWho Are They and\nWhat Do They Really Want?", skin, "large");
        Table table = new Table();
        table.setBackground(drawable);
        window.getTitleTable().clear();
        window.getTitleTable().add(table).minSize(100).padLeft(5.0f);
        window.layout();
        window.getTitleLabel().setAlignment(Align.center);
        window.getTitleTable().add(window.getTitleLabel()).padLeft(10.0f).grow();
        
        label = new Label("Sign in to find out!", skin, "error");
        label.setAlignment(Align.center);
        window.add(label).expandX().colspan(2);
        window.row();
        label = new Label("User:", skin);
        label.setAlignment(Align.right);
        window.add(label).right();
        TextField textField = new TextField("", skin, "login");
        textField.setMessageText("User or Email");
        window.add(textField).left().padLeft(5.0f);
        window.row();
        label = new Label("Password:", skin);
        label.setAlignment(Align.right);
        window.add(label).right();
        textField = new TextField("", skin, "password");
        textField.setMessageText("AES Secured");
        textField.setPasswordMode(true);
        textField.setPasswordCharacter('•');
        window.add(textField).left().padLeft(5.0f).padTop(5.0f);
        window.row();
        table = new Table();
        table.add(new TextButton("OK", skin));
        table.add(new TextButton("Cancel", skin)).padLeft(5.0f);
        window.add(table).colspan(2).center().padTop(10.0f);
        rootTable.add(window).minWidth(350.0f).center().padLeft(10.0f).padTop(10.0f);
        
        final TextButton windowButton = new TextButton("Create Windows", skin);
        rootTable.add(windowButton).left().expandX();
        
        rootTable.row();
        table = new Table();
        ImageButtonStyle style = new ImageButtonStyle(skin.get("menu-left", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-help");
        style.imageDown = skin.getDrawable("image-help-down");
        ImageButton imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-info");
        style.imageDown = skin.getDrawable("image-info-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-left");
        style.imageDown = skin.getDrawable("image-left-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-right");
        style.imageDown = skin.getDrawable("image-right-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-plus");
        style.imageDown = skin.getDrawable("image-plus-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-minus");
        style.imageDown = skin.getDrawable("image-minus-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-delete");
        style.imageDown = skin.getDrawable("image-delete-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-duplicate");
        style.imageDown = skin.getDrawable("image-duplicate-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-settings");
        style.imageDown = skin.getDrawable("image-settings-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-center", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-sound-off");
        style.imageChecked = skin.getDrawable("image-sound");
        style.imageDown = skin.getDrawable("image-sound-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        
        style = new ImageButtonStyle(skin.get("menu-right", ImageButtonStyle.class));
        style.imageUp = skin.getDrawable("image-music-off");
        style.imageChecked = skin.getDrawable("image-music");
        style.imageDown = skin.getDrawable("image-music-down");
        imageButton = new ImageButton(style);
        table.add(imageButton);
        rootTable.add(table).padTop(10.0f).colspan(2).growX();
        
        rootTable.row();
        table = new Table();
        
        table.defaults().padTop(10.0f);
        
        Button spinnerMinus = new Button(skin, "spinner-minus");
        table.add(spinnerMinus);
        final TextField spinner = new TextField("", skin, "spinner");
        spinner.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                 return c >= 48 && c <= 57 || c == 45;
            }
        });
        table.add(spinner).width(35.0f);
        spinner.setText("0");
        spinner.setAlignment(Align.center);
        Button spinnerPlus = new Button(skin, "spinner-plus");
        table.add(spinnerPlus).padRight(15.0f);
        
        table.defaults().minWidth(50.0f);

        String[] menuNames = {"File", "Edit", "View", "Window"};
        String[] menuItemNames = {"Save", "Undo", "Preferences", "About"};
        
        for (String menuName : menuNames) {
            TextButton textButton = new TextButton(menuName, skin, "menu");
            menuButtons.add(textButton);
            Table menuItemTable = new Table();
            menuItemTable.defaults().growX();
            for (String menuItemName : menuItemNames) {
                menuItemTable.add(new TextButton(menuItemName, skin, "menu-item"));
                menuItemTable.row();
            }
            final MenuList menuList = new MenuList(textButton, menuItemTable);
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    if (((TextButton) actor).isChecked()) {
                        menuList.show(stage);
                    } else {
                        menuList.hide();
                    }
                }
            });
            table.add(textButton);
        }
        
        table.add().width(15.0f).minWidth(15.0f);
        
        for (String menuName : menuNames) {
            TextButton textButton = new TextButton(menuName, skin, "menu-maroon");
            menuButtons.add(textButton);
            Table menuItemTable = new Table();
            menuItemTable.defaults().growX();
            for (String menuItemName : menuItemNames) {
                menuItemTable.add(new TextButton(menuItemName, skin, "menu-item-maroon"));
                menuItemTable.row();
            }
            final MenuList menuList = new MenuList(textButton, menuItemTable);
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    if (((TextButton) actor).isChecked()) {
                        menuList.show(stage);
                    } else {
                        menuList.hide();
                    }
                }
            });
            table.add(textButton);
        }
        
        TextButton dialogButton = new TextButton("Dialog", skin);
        table.add(dialogButton).padLeft(15.0f);
        
        rootTable.add(table).colspan(2).center();
        
        rootTable.row();
        Table panelTable = new Table();
        panelTable.defaults().growX().pad(5.0f).padTop(10.0f).fillY();
        table = new Table(skin);
        table.background("panel-orange");
        Table subTable = new Table();
        subTable.padLeft(20.0f).padRight(20.0f);
        subTable.add(new CheckBox(" CheckBox item", skin));
        subTable.row();
        subTable.add(new CheckBox(" CheckBox item", skin, "switch")).padTop(3.0f);
        subTable.row();
        subTable.add(new CheckBox(" CheckBox item", skin, "switch-text")).padTop(3.0f).padBottom(3.0f);
        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 1; i <= 10; i++) {
            subTable.row();
            CheckBox checkBox = new CheckBox(" Radio Button Item " + Integer.toString(i), skin, "radio");
            buttonGroup.add(checkBox);
            subTable.add(checkBox).left();
        }
        ScrollPane scrollPane = new ScrollPane(subTable, skin, "no-bg");
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        table.add(scrollPane).grow();
        
        panelTable.add(table);
        
        
        table = new Table(skin);
        table.background("panel-peach");
        subTable = new Table();
        final ProgressBar progressBar = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin);
        progressBar.setValue(50.0f);
        subTable.add(progressBar);
        subTable.row();
        final Slider slider = new Slider(0.0f, 100.0f, 1.0f, false, skin);
        slider.setValue(50.0f);
        subTable.add(slider).padTop(10.0f);
        subTable.row();
        label = new Label("split pane\nsplit pane\nsplit pane\nsplit pane\nsplit pane", skin);
        Label label1 = new Label("Created By Raeleus\nVisit Ray3K.com\nfor more stuff!", skin);
        SplitPane splitPane = new SplitPane(label, label1, false, skin);
        subTable.add(splitPane);
        subTable.row();
        subTable.add(new Touchpad(0.0f, skin));
        scrollPane = new ScrollPane(subTable, skin, "android-no-bg");
        scrollPane.setFlickScroll(false);
        scrollPane.setFadeScrollBars(false);
        table.add(scrollPane).grow();
        panelTable.add(table);
        
        table = new Table(skin);
        table.background("panel-maroon");
        table.defaults().padTop(10.0f);
        
        Tree tree = new Tree(skin);
        Node parentNode = new Node(new Label("Selection 1", skin));
        tree.add(parentNode);
        for (int i = 2; i <= 4; i++) {
            Node node = new Node(new Label("Selection " + i, skin));
            parentNode.add(node);
            parentNode = node;
            if (i == 2) {
                node.expandTo();
            }
        }
        table.add(tree);
        
        table.row();
        tree = new Tree(skin, "folder");
        parentNode = new Node(new Label("Selection 1", skin));
        tree.add(parentNode);
        for (int i = 2; i <= 4; i++) {
            Node node = new Node(new Label("Selection " + i, skin));
            parentNode.add(node);
            parentNode = node;
            if (i == 2) {
                node.expandTo();
            }
        }
        table.add(tree);
        
        table.row();
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems(new Object[] {"Select Box Item 1", "Select Box Item 2", "Select Box Item 3", "Select Box Item 4", "Select Box Item 5"});
        selectBox.setMaxListCount(3);
        table.add(selectBox);
        
        table.row();
        table.add().grow();
        panelTable.add(table);
        rootTable.add(panelTable).maxHeight(275.0f).colspan(2).grow();
        
        rootTable.row();
        rootTable.add().grow();
        
        final ChangeListener closeButtonListener = new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                actor.getParent().getParent().remove();
            }
        };
        
        //create windows at window button position
        windowButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Vector2 coords = new Vector2(windowButton.getX(), windowButton.getY());
                
                Window window = new Window("Window", skin);
                Button button = new Button(skin, "close");
                button.addListener(closeButtonListener);
                window.getTitleTable().add(button);
                window.add(new Label("Orange Window\nOrange Window\nOrange Window\nOrange Window\nOrange Window\n", skin));
                window.setWidth(275.0f);
                stage.addActor(window);
                window.setPosition(coords.x - 50.0f, coords.y + 20, Align.left);
                
                window = new Window("Window", skin, "maroon");
                button = new Button(skin, "close-alt");
                button.addListener(closeButtonListener);
                window.getTitleTable().add(button);
                window.add(new Label("Maroon Window\nMaroon Window\nMaroon Window\nMaroon Window\nMaroon Window\n", skin));
                window.setWidth(275.0f);
                stage.addActor(window);
                window.setPosition(coords.x - 30.0f, coords.y - 10.0f, Align.left);
                
                window = new Window("Window", skin, "peach");
                button = new Button(skin, "close");
                button.addListener(closeButtonListener);
                window.getTitleTable().add(button);
                window.add(new Label("Peach Window\nPeach Window\nPeach Window\nPeach Window\nPeach Window\n", skin));
                window.setWidth(275.0f);
                stage.addActor(window);
                window.setPosition(coords.x - 10.0f, coords.y - 40.0f, Align.left);
            }
        });
        
        //deselect menu buttons if escape is pressed or if stage is clicked anywhere else
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE) {
                    for (TextButton button : menuButtons) {
                        button.setChecked(false);
                    }
                }
                return false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (TextButton textButton : menuButtons) {
                    if (!textButton.isAscendantOf(event.getTarget())) {
                        textButton.setChecked(false);
                    }
                }
                return false;
            }
        });
        
        //create dialog on dialog button press
        dialogButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Dialog dialog = new Dialog("Orange Peel UI", skin, "dialog") {
                    protected void result(Object object) {
                        System.out.println("Chosen: " + object);
                    }
                };
                dialog.text("Are you sure?").button("Yes", true).button("No", false)
                        .key(Keys.ENTER, true).key(Keys.ESCAPE, false).show(stage);
                dialog.setWidth(250.0f);
                dialog.setX((stage.getWidth() - dialog.getWidth()) / 2.0f);
            }
        });
        
        spinnerValue = 0;
        spinnerMinus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                spinnerValue--;
                spinner.setText(Integer.toString(spinnerValue));
            }
        });
        
        spinnerPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                spinnerValue++;
                spinner.setText(Integer.toString(spinnerValue));
            }
        });
        
        spinner.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                String value = spinner.getText();
                if (value.matches(".*\\d.*")) {
                    spinnerValue = Integer.parseInt(value);
                } else {
                    spinnerValue = 0;
                }
            }
        });
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1);
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
    
    /**
     * Custom widget for menu bar lists
     * @param <T> 
     */
    static class MenuList<T> extends Table {
        private final TextButton widget;
        private final Vector2 screenPosition = new Vector2();
        private InputListener hideListener;
        final Table table;
        
        public MenuList(TextButton widget, Table table) {
            this.widget = widget;
            
            this.table = table;
            table.setTouchable(Touchable.disabled);
            table.validate();
            add(table);
            validate();
            
            table.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    hide();
                }
            });
            
            hideListener = new InputListener() {
                @Override
                public boolean keyDown(InputEvent event, int keycode) {
                    if (keycode == Keys.ESCAPE) hide();
                    return false;
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Actor target = event.getTarget();
                    if (isAscendantOf(target)) return false;
                    hide();
                    return false;
                }
                
            };
        }
        
        public void show(Stage stage) {
            if (table.isTouchable()) return;
            
            stage.removeCaptureListener(hideListener);
            stage.addCaptureListener(hideListener);
            stage.addActor(this);
            
            widget.localToStageCoordinates(screenPosition.set(0, 0));
            
            setX(screenPosition.x);
            setY(screenPosition.y - table.getHeight());
            align(Align.left);
            
            setHeight(table.getHeight());
            validate();
            
            stage.setScrollFocus(this);
            
            table.setTouchable(Touchable.enabled);
            clearActions();
            getColor().a = 0;
            addAction(fadeIn(0.3f, Interpolation.fade));
        }
        
        public void hide() {
            if (!table.isTouchable() || ! hasParent()) return;
            table.setTouchable(Touchable.disabled);
            
            Stage stage = getStage();
            if (stage != null) {
                stage.removeCaptureListener(hideListener);
            }
            
            clearActions();
            getColor().a = 1;
            addAction(sequence(fadeOut(0.15f, Interpolation.fade),com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor()));
        }

        public void draw(Batch batch, float parentAlpha) {
            widget.localToStageCoordinates(temp.set(0, 0));
            if (!temp.equals(screenPosition)) {
                widget.setChecked(false);
                hide();
            }
            super.draw(batch, parentAlpha);
        }

        public void act(float delta) {
            super.act(delta);
            toFront();
        }
    }
}
