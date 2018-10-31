package com.ray3k.neutralizerui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.BusyBar;
import com.kotcrab.vis.ui.widget.LinkLabel;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisProgressBar;
import com.kotcrab.vis.ui.widget.VisRadioButton;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisSplitPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisTree;
import com.kotcrab.vis.ui.widget.color.ColorPicker;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.spinner.FloatSpinnerModel;
import com.kotcrab.vis.ui.widget.spinner.Spinner;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneListener;

public class Core extends ApplicationAdapter {
    private Stage stage;
    private final static String LONG_TEXT = "Is am are was were be being been has have had may might must do does did shall should can could will would.";

    @Override
    public void create() {
        new Skin(Gdx.files.internal("neutralizer-ui.json"));
        
        VisUI.load(new Skin(Gdx.files.internal("neutralizer-ui.json")));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        VisTable root = new VisTable();
        root.setFillParent(true);
        stage.addActor(root);
        
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menu.addItem(new MenuItem("Save"));
        menu.addItem(new MenuItem("Save As..."));
        menu.addItem(new MenuItem("Quit"));
        menuBar.addMenu(menu);
        menu = new Menu("Edit");
        menu.addItem(new MenuItem("Undo"));
        menu.addItem(new MenuItem("Redo"));
        menu.addItem(new MenuItem("Preferences"));
        menuBar.addMenu(menu);
        menu = new Menu("Help");
        menu.addItem(new MenuItem("About"));
        menu.addItem(new MenuItem("Online Help"));
        menu.addItem(new MenuItem("Submit Bug Report"));
        menuBar.addMenu(menu);
        root.add(menuBar.getTable()).growX().colspan(2);
        
        root.row();
        VisTable left = new VisTable();
        left.setBackground("panel");
        left.defaults().padLeft(5.0f).padRight(5.0f).space(10.0f);
        root.add(left).growY();
        ButtonGroup buttonGroup = new ButtonGroup();
        
        VisImageButton imageButton = new VisImageButton("arrow-1");
        buttonGroup.add(imageButton);
        left.add(imageButton).padTop(10.0f);
        
        left.row();
        imageButton = new VisImageButton("arrow-2");
        buttonGroup.add(imageButton);
        left.add(imageButton);
        
        left.row();
        imageButton = new VisImageButton("rect");
        buttonGroup.add(imageButton);
        left.add(imageButton);
        
        left.row();
        imageButton = new VisImageButton("pencil");
        buttonGroup.add(imageButton);
        left.add(imageButton);
        
        left.row();
        imageButton = new VisImageButton("brush");
        buttonGroup.add(imageButton);
        left.add(imageButton).padBottom(10.0f).top().expandY();
        
        VisTable right = new VisTable(true);
        root.add(right).grow();
        
        VisLabel label = new VisLabel("Neutralizer UI", "title");
        right.add(label).colspan(2).pad(30.0f);
        
        right.row();
        VisTable subTable = new VisTable(true);
        right.add(subTable).growX();
        
        subTable.row();
        VisSelectBox selectBox = new VisSelectBox();
        selectBox.setItems("Slate", "Grate", "Metal Plate", "Crate", "Freight");
        subTable.add(selectBox);
        
        subTable.row();
        VisCheckBox checkBox = new VisCheckBox("Saturate");
        checkBox.setChecked(true);
        subTable.add(checkBox).padTop(85.0f);
        
        subTable.row();
        VisTable table = new VisTable(true);
        subTable.add(table).padTop(10.0f);
        
        buttonGroup = new ButtonGroup();
        VisRadioButton radioButton = new VisRadioButton("Placate");
        buttonGroup.add(radioButton);
        table.add(radioButton).expandX().left();
        
        table.row();
        radioButton = new VisRadioButton("Dictate");
        buttonGroup.add(radioButton);
        table.add(radioButton).expandX().left();
        
        table.row();
        radioButton = new VisRadioButton("Concentrate");
        buttonGroup.add(radioButton);
        table.add(radioButton).expandX().left();
        
        subTable.row();
        table = new VisTable(true);
        table.defaults().growX();
        subTable.add(table);
        
        VisTextButton textButton = new VisTextButton("INITIATE");
        table.add(textButton);
        
        table.row();
        textButton = new VisTextButton("DEMONSTRATE");
        table.add(textButton);
        
        table.row();
        label = new VisLabel("Innovate");
        table.add(label);
        
        table.row();
        LinkLabel linkLabel = new LinkLabel("Alexander the Great", "https://en.wikipedia.org/wiki/Alexander_the_Great");
        table.add(linkLabel);
        
        table.row();
        final VisProgressBar progressBar = new VisProgressBar(0, 100, 1, false);
        progressBar.setValue(50.0f);
        table.add(progressBar);
        
        table.row();
        final VisSlider slider = new VisSlider(0, 100, 1, false);
        slider.setValue(50.0f);
        table.add(slider);
        
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                progressBar.setValue(slider.getValue());
            }
        });
        
        label = new VisLabel(LONG_TEXT);
        label.setWrap(true);
        
        subTable.row();
        table = new VisTable(true);
        subTable.add(table);
        
        VisScrollPane scrollPane = new VisScrollPane(label);
        scrollPane.setFadeScrollBars(false);
        table.add(scrollPane).size(100.0f);
        
        label = new VisLabel(LONG_TEXT);
        label.setWrap(true);
        
        VisLabel label2 = new VisLabel(LONG_TEXT);
        label2.setWrap(true);
        VisSplitPane splitPane = new VisSplitPane(label, label2, true);
        table.add(splitPane).size(100.0f);
        
        subTable = new VisTable(true);
        right.add(subTable).growX();
        
        table = new VisTable(true);
        subTable.add(table);
        
        label = new VisLabel("User:");
        table.add(label).right();
        
        VisTextField textField = new VisTextField();
        textField.setText("Raeleus");
        table.add(textField);
        
        table.row();
        label = new VisLabel("Password:");
        table.add(label).right();
        
        textField = new VisTextField();
        textField.setText("123456789");
        textField.setPasswordCharacter('•');
        textField.setPasswordMode(true);
        table.add(textField);
        
        subTable.row();
        Touchpad touchpad = new Touchpad(0, VisUI.getSkin());
        subTable.add(touchpad);
        
        subTable.row();
        VisTree tree = new VisTree();
        subTable.add(tree);
        
        Node parent = new Node(new VisLabel("Bemoan"));
        tree.add(parent);
        
        Node child = new Node(new VisLabel("Drone"));
        parent.add(child);
        parent = child;
        
        child = new Node(new VisLabel("Loan"));
        parent.add(child);
        child.expandTo();
        parent = child;
        
        child = new Node(new VisLabel("Groan"));
        parent.add(child);
        
        subTable.row();
        Spinner spinner = new Spinner("Zone", new FloatSpinnerModel("0.0", "-100.0", "100.0"));
        subTable.add(spinner);
        
        subTable.row();
        textButton = new VisTextButton("Choose a file...");
        subTable.add(textButton);
        
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                FileChooser fileChooser = new FileChooser(FileChooser.Mode.OPEN);
                fileChooser.getTitleTable().getCells().first().padLeft(7.0f);
                fileChooser.getTitleTable().getCells().get(1).padBottom(3.5f).padRight(1.0f);
                stage.addActor(fileChooser.fadeIn());
            }
        });
        
        subTable.row();
        textButton = new VisTextButton("Pick a color...");
        subTable.add(textButton);
        
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                ColorPicker colorPicker = new ColorPicker();
                colorPicker.getTitleTable().getCells().first().padLeft(7.0f);
                colorPicker.getTitleTable().getCells().get(1).padBottom(3.5f).padRight(1.0f);
                stage.addActor(colorPicker.fadeIn());
            }
        });
        
        subTable.row();
        BusyBar busyBar = new BusyBar();
        subTable.add(busyBar).grow();
        
        right.row();
        right.addSeparator().colspan(2).expandY().top();
        
        right.row();
        TabbedPane tabbedPane = new TabbedPane();
        right.add(tabbedPane.getTable()).growX().colspan(2).expandY().bottom().spaceBottom(0.0f);
        
        right.row();
        final VisTable contentTable = new VisTable();
        right.add(contentTable).growX().height(100).colspan(2).space(0.0f).pad(0.0f);
        
        tabbedPane.addListener(new TabbedPaneListener() {
            @Override
            public void switchedTab(Tab tab) {
                contentTable.clearChildren();
                contentTable.add(tab.getContentTable()).grow();
            }

            @Override
            public void removedTab(Tab tab) {
            }

            @Override
            public void removedAllTabs() {
            }
        });
        
        tabbedPane.add(new sampleTab("Fate"));
        tabbedPane.add(new sampleTab("Date"));
        tabbedPane.add(new sampleTab("Rate"));
    }
    
    public static class sampleTab extends Tab {
        private VisTable table;
        private String name;

        public sampleTab(String name) {
            super(false, false);
            this.name = name;
            table = new VisTable();
            table.setBackground("panel2");
        }

        @Override
        public String getTabTitle() {
            return name;
        }

        @Override
        public Table getContentTable() {
            return table;
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyJustPressed(Keys.F5)) {
            dispose();
            create();
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
        VisUI.dispose();
    }
}
