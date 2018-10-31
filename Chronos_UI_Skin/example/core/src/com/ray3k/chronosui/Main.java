package com.ray3k.chronosui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.Calendar;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    private ClockWidget clock1, clock2, clock3;
    private Calendar calendar;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("chronos-ui.json"));
        
        calendar = Calendar.getInstance();
        
        Table root = new Table(skin);
        root.setBackground("bg");
        root.setFillParent(true);
        stage.addActor(root);
        
        root.defaults().space(20.0f);
        clock1 = new ClockWidget(skin);
        root.add(clock1);
        
        root.row();
        clock2 = new ClockWidget(skin, "classic");
        root.add(clock2);
        
        root.row();
        clock3 = new ClockWidget(skin, "stylish");
        root.add(clock3);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        calendar.setTimeInMillis(TimeUtils.millis());
        
        clock1.setHourPercent((calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) / 60.0f) / 12.0f);
        clock1.setMinutePercent((calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) / 60.0f) / 60.0f);
        clock1.setSecondPercent(calendar.get(Calendar.SECOND) / 60.0f);
        
        clock2.setHourPercent(clock1.getHourPercent());
        clock2.setMinutePercent(clock1.getMinutePercent());
        clock2.setSecondPercent(clock1.getSecondPercent());
        
        clock3.setHourPercent(clock1.getHourPercent());
        clock3.setMinutePercent(clock1.getMinutePercent());
        clock3.setSecondPercent(clock1.getSecondPercent());
        
        stage.act(Gdx.graphics.getRawDeltaTime());
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
