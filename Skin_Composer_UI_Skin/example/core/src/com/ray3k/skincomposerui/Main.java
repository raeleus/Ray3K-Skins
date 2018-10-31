package com.ray3k.skincomposerui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private static final float RESIZE_BORDER = 10;
    
    private Skin skin;
    private Stage stage;
    private final WindowWorker windowWorker;
    private static enum DragState {
        NONE, TOP, BOTTOM, LEFT, RIGHT
    }
    private DragState dragState;
    private boolean draggingCursor;
    
    public Main(WindowWorker windowWorker) {
        this.windowWorker = windowWorker;
    }

    @Override
    public void create() {
        draggingCursor = false;
        
        skin = new Skin(Gdx.files.internal("skin-composer-ui.json"));
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Window root = new Window("Skin Composer UI", skin, "main");
        root.setFillParent(true);
        root.setMovable(false);
        stage.addActor(root);
        
        addListeners(root);
        
        addTitleBarWidgets(root);
        
        addFileMenu(root);
        
        root.row();
        addClassBar(root);
        
        root.row();
        addStyleAndPreviewSplit(root, new ScrollPaneListener(), new IbeamListener());
        
        root.row();
        addStatusBar(root);
        
        constrainWindowSize();
    }

    private void addListeners(final Window root) {
        DragListener dragListener = new DragListener() {
            private int startX, startY;
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                int translateX = (int) x - startX;
                int translateY = startY - (int) y;
                
                windowWorker.setPosition(windowWorker.getPositionX() + translateX, windowWorker.getPositionY() + translateY);
            }

            @Override
            public void dragStart(InputEvent event, float x, float y,
                    int pointer) {
                startX = (int) x;
                startY = (int) y;
            }
        };
        dragListener.setTapSquareSize(0.0f);
        root.getTitleTable().addCaptureListener(dragListener);
        
        root.addCaptureListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if (x < RESIZE_BORDER) {
                    dragState = DragState.LEFT;
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
                } else if (x > root.getWidth() - RESIZE_BORDER) {
                    dragState = DragState.RIGHT;
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
                } else if (y < RESIZE_BORDER) {
                    dragState = DragState.BOTTOM;
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
                } else if (y > root.getHeight() - RESIZE_BORDER) {
                    dragState = DragState.TOP;
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
                } else if (dragState != DragState.NONE) {
                    dragState = DragState.NONE;
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                }
                
                return false;
            }
            
        });
        
        dragListener = new DragListener() {
            private int resizeX, resizeY;
            private int dragX, dragY;
            
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {

            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                if (dragState == DragState.RIGHT) {
                    int translateX = (int) x - resizeX;
                    
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth() + translateX, Gdx.graphics.getHeight());
                    resizeX = (int) x;
                } else if (dragState == DragState.BOTTOM) {
                    int oldHeight = Gdx.graphics.getHeight();
                    int translateY = resizeY - (int) y;
                    
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + translateY);
                    resizeY = (int) y - oldHeight + Gdx.graphics.getHeight();
                } else if (dragState == DragState.LEFT) {
                    int translateX = (int) x - resizeX;
                    
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth() - translateX, Gdx.graphics.getHeight());
                    windowWorker.setPosition(windowWorker.getPositionX() - dragX + (int) x, windowWorker.getPositionY());
                } else if (dragState == DragState.TOP) {
                    int amount = resizeY - (int) y;
                    
                    resizeY = (int) y;
                    
                    
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - amount);
                    windowWorker.setPosition(windowWorker.getPositionX(), windowWorker.getPositionY() + amount);
                }
            }

            @Override
            public void dragStart(InputEvent event, float x, float y,
                    int pointer) {
                resizeX = (int) x;
                resizeY = (int) y;
                dragX = resizeX;
                dragY = resizeY;
            } 
        };
        dragListener.setTapSquareSize(0.0f);
        root.addCaptureListener(dragListener);
    }
    
    private void addTitleBarWidgets(final Window root) {
        root.getTitleTable().getCells().first().padLeft(10.0f);
        
        Button button = new Button(skin, "minimize");
        root.getTitleTable().add(button);
        
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                windowWorker.minimize();
            }
        });
        
        final Button maxButton = new Button(skin, "maximize");
        root.getTitleTable().add(maxButton);
        
        maxButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (windowWorker.getWindowState() == WindowWorker.WindowState.RESTORED) {
                    windowWorker.maximize();
                    maxButton.setStyle(skin.get("restore", ButtonStyle.class));
                } else {
                    windowWorker.restore();
                    maxButton.setStyle(skin.get("maximize", ButtonStyle.class));
                }
            }
        });
        
        button = new Button(skin, "close");
        root.getTitleTable().add(button);
        
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
    
    private void addFileMenu(final Window root) {
        Table table = new Table();
        table.defaults().padRight(2.0f);
        root.add(table).expandX().left().padTop(2.0f);
        
        TextButton textButton = new TextButton("File", skin, "file");
        table.add(textButton).padLeft(2.0f);
        
        textButton = new TextButton("Edit", skin, "file");
        table.add(textButton);
        
        textButton = new TextButton("Project", skin, "file");
        table.add(textButton);
        
        textButton = new TextButton("Help", skin, "file");
        table.add(textButton);
    }
    
    private void addClassBar(final Window root) {
        Table table = new Table();
        table.setBackground(skin.getDrawable("class-bar"));
        root.add(table).expandX().left().padLeft(10.0f).growX();
        
        Label label = new Label("Class:", skin);
        table.add(label).padRight(10.0f);
        
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems("Button", "CheckBox", "ImageButton", "ImageTextButton", "Label", "List", "ProgressBar", "ScrollPane", "SelectBox", "Slider", "SplitPane", "TextButton", "TextField", "TextTolltip", "Touchpad", "Tree", "Window");
        table.add(selectBox).padRight(10.0f).minWidth(150.0f);
        
        Button button = new Button(skin, "new");
        table.add(button).padRight(50.0f);
        
        label = new Label("Style:", skin);
        table.add(label).padRight(10.0f);
        
        selectBox = new SelectBox(skin);
        selectBox.setItems("default");
        table.add(selectBox).padRight(10.0f).minWidth(150.0f);
        
        button = new Button(skin, "new");
        table.add(button);
        
        button = new Button(skin, "duplicate");
        table.add(button);
        
        button = new Button(skin, "delete");
        table.add(button);
        
        button = new Button(skin, "settings");
        table.add(button).expandX().left();
    }
    
    private void addStyleAndPreviewSplit(final Window root, InputListener scrollPaneListener, InputListener iBeamListener) {
        Table left = new Table();
        left.setTouchable(Touchable.enabled);
        
        addStyleProperties(left, scrollPaneListener);
        
        Table right = new Table();
        right.setTouchable(Touchable.enabled);
        
        addPreviewPreviewPropertiesSplit(right, scrollPaneListener, iBeamListener);
        
        SplitPane splitPane = new SplitPane(left, right, false, skin);
        root.add(splitPane).grow();
        
        splitPane.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer,
                    Actor toActor) {
                if (!draggingCursor && event.getListenerActor().equals(event.getTarget())) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer,
                    Actor fromActor) {
                if (!draggingCursor && event.getListenerActor().equals(event.getTarget())) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
                }
            }
            
        });
        
        splitPane.addListener(new DragListener() {
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                draggingCursor = false;
            }

            @Override
            public void dragStart(InputEvent event, float x, float y,
                    int pointer) {
                if (!draggingCursor && event.getListenerActor().equals(event.getTarget())) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
                    draggingCursor = true;
                }
            }
            
        });
        
    }
    
    private void addStyleProperties(final Table left, InputListener scrollPaneListener) {
        Label label = new Label("Style Properties", skin, "title");
        left.add(label);
        
        left.row();
        Table table = new Table();
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.addListener(scrollPaneListener);
        stage.setScrollFocus(scrollPane);
        left.add(scrollPane).grow().padTop(10.0f).padBottom(10.0f);
        
        label = new Label("font", skin, "required");
        table.add(label).colspan(2);
        
        table.row();
        TextField textField = new TextField("title", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        ImageButton imageButton = new ImageButton(skin, "font");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("fontColor", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("red", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "color");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("up", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("down", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button-pressed", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("over", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button-over", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("imageUp", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("image-up", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("imageDown", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("image-down", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("imageOver", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("image-over", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("checked", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button-checked", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("checkedOver", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button-checked-over", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("banana", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button-banana", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("orange", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button-orange", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
        
        table.row();
        label = new Label("lime", skin);
        table.add(label).colspan(2).padTop(10.0f);
        
        table.row();
        textField = new TextField("button-lime", skin);
        textField.setDisabled(true);
        table.add(textField);
        
        imageButton = new ImageButton(skin, "drawable");
        table.add(imageButton).padLeft(5.0f);
    }
    
    private void addPreviewPreviewPropertiesSplit(final Table right, InputListener scrollPaneListener, InputListener iBeamListener) {
        Table top = new Table();
        top.setTouchable(Touchable.enabled);
        
        addPreview(top, scrollPaneListener, iBeamListener);
        
        Table bottom = new Table();
        bottom.setTouchable(Touchable.enabled);
        
        addPreviewProperties(bottom, scrollPaneListener, iBeamListener);
        
        SplitPane splitPane = new SplitPane(top, bottom, true, skin);
        right.add(splitPane).grow();
        
        splitPane.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer,
                    Actor toActor) {
                if (!draggingCursor && event.getListenerActor().equals(event.getTarget())) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer,
                    Actor fromActor) {
                if (!draggingCursor && event.getListenerActor().equals(event.getTarget())) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
                }
            }
        });
        
        splitPane.addListener(new DragListener() {
            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                draggingCursor = false;
            }

            @Override
            public void dragStart(InputEvent event, float x, float y,
                    int pointer) {
                if (!draggingCursor && event.getListenerActor().equals(event.getTarget())) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
                    draggingCursor = true;
                }
            }
            
        });
    }
    
    private void addPreview(Table top, InputListener scrollPaneListener, InputListener iBeamListener) {
        Label label = new Label("Preview", skin, "title");
        top.add(label);
        
        top.row();
        Table table = new Table();
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.addListener(scrollPaneListener);
        top.add(scrollPane).grow().padTop(10.0f).padBottom(10.0f);
        
        TextField textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
    }
    
    private void addPreviewProperties(Table bottom, InputListener scrollPaneListener, InputListener iBeamListener) {
        Label label = new Label("Preview Properties", skin, "title");
        bottom.add(label);
        
        bottom.row();
        Table table = new Table();
        table.defaults().pad(5.0f);
        
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setFlickScroll(false);
        scrollPane.addListener(scrollPaneListener);
        bottom.add(scrollPane).grow().padTop(10.0f).padBottom(10.0f);
        
        label = new Label("Text:", skin);
        table.add(label).right();
        
        TextField textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
        
        table.row();
        label = new Label("Message Text:", skin);
        table.add(label).right();
        
        textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
        
        table.row();
        label = new Label("Hover Text:", skin);
        table.add(label).right();
        
        textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
        
        table.row();
        label = new Label("Banana Text:", skin);
        table.add(label).right();
        
        textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
        
        table.row();
        label = new Label("Apple Text:", skin);
        table.add(label).right();
        
        textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
        
        table.row();
        label = new Label("Orange Text:", skin);
        table.add(label).right();
        
        textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
        
        table.row();
        label = new Label("Lime Text:", skin);
        table.add(label).right();
        
        textField = new TextField("Type Here!", skin);
        textField.addListener(iBeamListener);
        table.add(textField);
    }
    
    private void addStatusBar(Window root) {
        Table table = new Table();
        table.setBackground(skin.getDrawable("status-bar"));
        root.add(table).growX();
        
        Label label = new Label("ver. 1    RAY3K.WORDPRESS.COM    © 2016 Raymond \"Raeleus\" Buckley", skin);
        table.add(label).expandX().right().padRight(25.0f);
    }
    
    private void constrainWindowSize() {
        int width = Math.min(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getWidth());
        int height = Math.min(Gdx.graphics.getDisplayMode().height, Gdx.graphics.getHeight());
        Gdx.graphics.setWindowedMode(width, height);
        windowWorker.center();
    }
    
    private class ScrollPaneListener extends InputListener {
        @Override
        public void enter(InputEvent event, float x, float y, int pointer,
                Actor fromActor) {
            if (event.getTarget() instanceof ScrollPane) {
                ScrollPane scrollPane = (ScrollPane) event.getTarget();
                //if the scroll pane is scrollable
                if (!Float.isNaN(scrollPane.getScrollPercentY())) {
                    stage.setScrollFocus(scrollPane);
                }
            }
        }
    }
    
    private class IbeamListener extends InputListener {

        @Override
        public void exit(InputEvent event, float x, float y, int pointer,
                Actor toActor) {
            if (!draggingCursor && dragState == DragState.NONE) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer,
                Actor fromActor) {
            if (!draggingCursor && dragState == DragState.NONE) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Ibeam);
            }
        }
        
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getRawDeltaTime());
        stage.draw();
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            skin.dispose();
            stage.dispose();
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
