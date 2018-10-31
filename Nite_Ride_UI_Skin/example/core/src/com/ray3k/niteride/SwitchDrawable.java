/*
 * The MIT License
 *
 * Copyright 2018 Raymond Buckley.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ray3k.niteride;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class SwitchDrawable extends BaseDrawable {
    private Drawable drawable1, drawable2;
    
    private int selectedIndex;
    private boolean initialized;

    public SwitchDrawable() {
        selectedIndex = 0;
        initialized = false;
    }
    
    public SwitchDrawable(Drawable drawable1, Drawable drawable2) {
        this.drawable1 = drawable1;
        this.drawable2 = drawable2;
    }
    
    public void switchSelection() {
        selectedIndex++;
        selectedIndex %= 2;
    }
    
    private void initialize() {
        if (!initialized) {
            initialized = true;
            setMinWidth(drawable1.getMinWidth());
            setMinHeight(drawable1.getMinHeight());
        }
    }
    
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        initialize();
        
        Drawable drawable = drawable1;
        
        if (selectedIndex == 1) {
            drawable = drawable2;
        }
        
        drawable.draw(batch, x, y, width, height);
    }

    public Drawable getDrawable1() {
        return drawable1;
    }

    public void setDrawable1(Drawable drawable1) {
        this.drawable1 = drawable1;
    }

    public Drawable getDrawable2() {
        return drawable2;
    }

    public void setDrawable2(Drawable drawable2) {
        this.drawable2 = drawable2;
    }

    @Override
    public float getMinHeight() {
        initialize();
        return super.getMinHeight();
    }

    @Override
    public float getMinWidth() {
        initialize();
        return super.getMinWidth();
    }
    
}
