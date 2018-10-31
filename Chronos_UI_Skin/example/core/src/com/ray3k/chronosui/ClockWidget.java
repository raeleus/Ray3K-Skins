/*******************************************************************************
 * MIT License
 * 
 * Copyright (c) 2017 Raymond Buckley
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.ray3k.chronosui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class ClockWidget extends Table {
    private Image bg;
    private Image minuteHand;
    private Image secondHand;
    private Image hourHand;
    private float hourPercent;
    private float minutePercent;
    private float secondPercent;
    private ClockWidgetStyle clockWidgetStyle;
    private LabelStyle labelStyle;
    
    public ClockWidget(Skin skin) {
        this(skin, "default");
    }
    
    public ClockWidget(Skin skin, String styleName) {
        this(skin.get(styleName, ClockWidgetStyle.class));
    }
    
    public ClockWidget(ClockWidgetStyle clockWidgetStyle) {
        this.clockWidgetStyle = clockWidgetStyle;
        
        labelStyle = new LabelStyle(clockWidgetStyle.font, clockWidgetStyle.fontColor);
        
        populate();
    }
    
    private void populate() {
        clearChildren();
        
        float maxWidth = clockWidgetStyle.bg.getMinWidth();
        maxWidth = Math.max(maxWidth, clockWidgetStyle.hourHand.getMinWidth());
        maxWidth = Math.max(maxWidth, clockWidgetStyle.minuteHand.getMinWidth());
        maxWidth = Math.max(maxWidth, clockWidgetStyle.secondHand.getMinWidth());
        
        float maxHeight = clockWidgetStyle.bg.getMinHeight();
        maxHeight = Math.max(maxHeight, clockWidgetStyle.hourHand.getMinHeight());
        maxHeight = Math.max(maxHeight, clockWidgetStyle.minuteHand.getMinHeight());
        maxHeight = Math.max(maxHeight, clockWidgetStyle.secondHand.getMinHeight());
        
        if (clockWidgetStyle.name != null && clockWidgetStyle.titleAbove) {
            Label label = new Label(clockWidgetStyle.name, labelStyle);
            add(label).pad(3.0f);
            row();
        }
        
        Stack stack = new Stack();
        add(stack);
        
        bg = new Image(clockWidgetStyle.bg);
        bg.validate();
        stack.add(bg);
        
        minuteHand = new Image(clockWidgetStyle.minuteHand);
        minuteHand.setScaling(Scaling.none);
        minuteHand.setOrigin(maxWidth / 2.0f, maxHeight / 2.0f);
        stack.add(minuteHand);
        
        hourHand = new Image(clockWidgetStyle.hourHand);
        hourHand.setScaling(Scaling.none);
        hourHand.setOrigin(maxWidth / 2.0f, maxHeight / 2.0f);
        stack.add(hourHand);
        
        secondHand = new Image(clockWidgetStyle.secondHand);
        secondHand.setScaling(Scaling.none);
        secondHand.setOrigin(maxWidth / 2.0f, maxHeight / 2.0f);
        stack.add(secondHand);
        
        if (clockWidgetStyle.name != null && !clockWidgetStyle.titleAbove) {
            row();
            Label label = new Label(clockWidgetStyle.name, labelStyle);
            add(label).pad(3.0f);
        }
    }

    public float getHourPercent() {
        return hourPercent;
    }

    public void setHourPercent(float hourPercent) {
        this.hourPercent = hourPercent;
        updateRotation();
    }

    public float getMinutePercent() {
        return minutePercent;
    }

    public void setMinutePercent(float minutePercent) {
        this.minutePercent = minutePercent;
        updateRotation();
    }

    public float getSecondPercent() {
        return secondPercent;
    }

    public void setSecondPercent(float secondPercent) {
        this.secondPercent = secondPercent;
        updateRotation();
    }
    
    public void updateRotation() {
        minuteHand.setRotation((360 - minutePercent * 360) % 360);
        secondHand.setRotation((360 - secondPercent * 360) % 360);
        hourHand.setRotation((360 - hourPercent * 360) % 360);
    }
    
    public static class ClockWidgetStyle {
        public Drawable bg;
        public Drawable minuteHand;
        public Drawable secondHand;
        public Drawable hourHand;
        public BitmapFont font;
        public Color fontColor;
        public boolean titleAbove;
        public String name;
        
        public ClockWidgetStyle() {
            
        }
        
        public ClockWidgetStyle(ClockWidgetStyle style) {
            bg = style.bg;
            minuteHand = style.minuteHand;
            secondHand = style.secondHand;
            hourHand = style.hourHand;
            font = style.font;
            fontColor = style.fontColor;
            titleAbove = style.titleAbove;
            name = style.name;
        }
    }
}
