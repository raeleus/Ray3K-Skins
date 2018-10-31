package com.ray3k.pentothepaperui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Core extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private static final String LONG_TEXT = "Dearest Self,\n\nI have noticed that you are not putting in the effort that I normally expect from you. You would tire seflessly (pun intended) to see the job through. Now you commit only to half-measures and incomplete thoughts. I need you to focus and concentrate on completing your tasks. It's as if you are a car stuck in first gear. A horse that doesn't gallop. A shopping cart with a busted wheel. You know what I'm talking about. Don't just sit there and pout! You are better than this! You've created great works renowned world-wide. You are a master of your craft. Get out there and find that glory once again.\n\nOn another note, I need the following:\n1 x Loaf of Bread\n2 x Tomato\n1 x Ham (NOT OFF-BRAND YOU CHEAP BASTARD)\n1 x Pack of Provolone Cheese\n\nThanks and take out the trash.";
    
    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("pen-to-the-paper-ui.json")) {
            //Override json loader to process FreeType fonts from skin JSON
            @Override
            protected Json getJsonLoader(final FileHandle skinFile) {
                Json json = super.getJsonLoader(skinFile);
                final Skin skin = this;

                json.setSerializer(FreeTypeFontGenerator.class, new Json.ReadOnlySerializer<FreeTypeFontGenerator>() {
                    @Override
                    public FreeTypeFontGenerator read(Json json,
                            JsonValue jsonData, Class type) {
                        String path = json.readValue("font", String.class, jsonData);
                        jsonData.remove("font");
                        
                        Hinting hinting = Hinting.valueOf(json.readValue("hinting", String.class, "AutoMedium", jsonData));
                        jsonData.remove("hinting");
                        
                        TextureFilter minFilter = TextureFilter.valueOf(json.readValue("minFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("minFilter");
                        
                        TextureFilter magFilter = TextureFilter.valueOf(json.readValue("magFilter", String.class, "Nearest", jsonData));
                        jsonData.remove("magFilter");
                        
                        FreeTypeFontParameter parameter = json.readValue(FreeTypeFontParameter.class, jsonData);
                        parameter.hinting = hinting;
                        parameter.minFilter = minFilter;
                        parameter.magFilter = magFilter;
                        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(skinFile.parent().child(path));
                        BitmapFont font = generator.generateFont(parameter);
                        skin.add(jsonData.name, font);
                        if (parameter.incremental) {
                            generator.dispose();
                            return null;
                        } else {
                            return generator;
                        }
                    }
                });

                return json;
            }
        };
        
        skin.get("signature", BitmapFont.class).getData().ascent = 15;
        
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        
        Table table = new Table();
        table.setName("paper");
        root.add(table);
        
        Container container = new Container();
        container.setBackground(skin.getDrawable("header"));
        Label label = new Label("Pen to the Paper UI", skin, "title");
        container.setActor(label);
        table.add(container);
        
        table.row();
        table = new Table();
        table.setBackground(skin.getDrawable("line-tiled"));
        ((Table) root.findActor("paper")).add(table).height(18 * 28);
        
        table.defaults().padLeft(67.0f).padRight(15.0f);
        label = new Label(LONG_TEXT, skin);
        label.setAlignment(Align.left);
        label.setWrap(true);
        table.add(label).growX();
        
        table.row();
        label = new Label("You and yours truly,", skin);
        table.add(label).right().padRight(50.0f).padTop(18);
        
        table.row();
        label = new Label("Myself", skin, "signature");
        table.add(label).expandY().top().right().padRight(50.0f);
        
        table = root.findActor("paper");
        table.row();
        Image image = new Image(skin, "footer");
        image.setScaling(Scaling.none);
        table.add(image);
        
        image = new Image(skin, "grade");
        image.setPosition(500, 600);
        image.setColor(1, 1, 1, 0);
        stage.addActor(image);
        image.addAction(Actions.sequence(Actions.delay(2.0f), Actions.fadeIn(.5f)));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(209 / 255.0f, 209 / 255.0f, 209 / 255.0f, 1);
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
