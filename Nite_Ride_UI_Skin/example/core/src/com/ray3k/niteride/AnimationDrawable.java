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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class AnimationDrawable extends BaseDrawable {
    private String regionName;
    private Skin skin;
    private float frameDuration;
    
    private Animation<TextureRegion> animation;
    private float stateTime;

    public AnimationDrawable() {
        super();
        stateTime = 0.0f;
    }

    public AnimationDrawable(Drawable drawable) {
        super(drawable);
        stateTime = 0.0f;
    }
    
    private void initializeAnimation() {
        if (animation == null) {
            animation = new Animation<TextureRegion>(frameDuration, skin.getRegions(regionName), PlayMode.LOOP);
            setMinWidth(animation.getKeyFrame(0.0f).getRegionWidth());
            setMinHeight(animation.getKeyFrame(0.0f).getRegionHeight());
        }
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        stateTime += Gdx.graphics.getDeltaTime();
        initializeAnimation();
        
        batch.draw(animation.getKeyFrame(stateTime), x, y, width, height);
    }

    @Override
    public float getMinHeight() {
        initializeAnimation();
        return super.getMinHeight();
    }

    @Override
    public float getMinWidth() {
        initializeAnimation();
        return super.getMinWidth();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
        animation = null;
        initializeAnimation();
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }
}
