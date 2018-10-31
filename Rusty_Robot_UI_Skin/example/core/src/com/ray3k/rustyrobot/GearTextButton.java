package com.ray3k.rustyrobot;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class GearTextButton extends Table {
    private float degrees;
    private Image cog1, cog2;
    
    public GearTextButton(String text, Skin skin) {
        super(skin);
        degrees = 0.0f;
        
        Stack stack = new Stack();
        Container<Actor> cont = new Container<Actor>();
        cog1 = new Image(skin, "cog1");
        cog1.setOrigin(Align.center);
        cont.setActor(cog1);
        cont.top().right().padRight(30.0f).padTop(5.0f);
        stack.add(cont);
        
        cont = new Container<Actor>();
        cog2 = new Image(skin, "cog2");
        cog2.setOrigin(Align.center);
        cont.setActor(cog2);
        cont.bottom().right();
        stack.add(cont);
        
        TextButton textButton = new TextButton(text, skin);
        cont = new Container<Actor>(textButton);
        cont.fill();
        cont.padRight(15.0f);
        stack.add(cont);
        
        add(stack).grow();
    }
    
    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        degrees %= 360.0f;
        this.degrees = degrees;
        cog1.setRotation(degrees * 2.0f);
        cog2.setRotation(-degrees);
    }
}
