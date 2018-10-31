/*******************************************************************************
 * Derived from ProgressBar class by mzechner and Nathan Sweet. Added
 * RadialDrawable functionality and removed all use of Drawable knobs.
 * 
 * Copyright 2016 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.ray3k.niteride;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pools;

/**
 * 
 * @author Raymond "Raeleus" Buckley
 */
public class RadialProgressBar extends Widget implements Disableable {

    private RadialProgressBarStyle style;
    private float min, max, stepSize;
    private float value, animateFromValue;
    float position;
    private float animateDuration, animateTime;
    private Interpolation animateInterpolation = Interpolation.linear;
    boolean disabled;
    private Interpolation visualInterpolation = Interpolation.linear;
    private boolean cw;
    private float startAngle;

    public RadialProgressBar(float min, float max, float stepSize, Skin skin) {
        this(min, max, stepSize, skin.get("default", RadialProgressBarStyle.class));
    }

    public RadialProgressBar(float min, float max, float stepSize, Skin skin, String styleName) {
        this(min, max, stepSize, skin.get(styleName, RadialProgressBarStyle.class));
    }

    /**
     * Creates a new progress bar. It's width is determined by the given
     * prefWidth parameter, its height is determined by the maximum of the
     * height of either the progress bar {@link NinePatch} or progress bar
     * handle {@link TextureRegion}. The min and max values determine the range
     * the values of this progress bar can take on, the stepSize parameter
     * specifies the distance between individual values.
     * <p>
     * E.g. min could be 4, max could be 10 and stepSize could be 0.2, giving
     * you a total of 30 values, 4.0 4.2, 4.4 and so on.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @param stepSize the step size between values
     * @param style the {@link ProgressBarStyle}
     */
    public RadialProgressBar(float min, float max, float stepSize, RadialProgressBarStyle style) {
        startAngle = 90.0f;
        cw = true;

        if (min > max) {
            throw new IllegalArgumentException("max must be > min. min,max: " + min + ", " + max);
        }
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize must be > 0: " + stepSize);
        }
        setStyle(style);
        this.min = min;
        this.max = max;
        this.stepSize = stepSize;
        this.value = min;
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void setStyle(RadialProgressBarStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        invalidateHierarchy();
    }

    /**
     * Returns the progress bar's style. Modifying the returned style may not
     * have an effect until {@link #setStyle(ProgressBarStyle)} is called.
     *
     * @return
     */
    public RadialProgressBarStyle getStyle() {
        return style;
    }

    public float getValue() {
        return value;
    }

    /**
     * If {@link #setAnimateDuration(float) animating} the progress bar value,
     * this returns the value current displayed.
     *
     * @return
     */
    public float getVisualValue() {
        if (animateTime > 0) {
            return animateInterpolation.apply(animateFromValue, value, 1 - animateTime / animateDuration);
        }
        return value;
    }

    public float getPercent() {
        return (value - min) / (max - min);
    }

    public float getVisualPercent() {
        return visualInterpolation.apply((getVisualValue() - min) / (max - min));
    }

    protected Drawable getKnobDrawable() {
        return (disabled && style.disabledKnob != null) ? style.disabledKnob : style.knob;
    }

    /**
     * Returns progress bar visual position within the range.
     *
     * @return
     */
    protected float getKnobPosition() {
        return this.position;
    }

    /**
     * Sets the progress bar position, rounded to the nearest step size and
     * clamped to the minimum and maximum values. {@link #clamp(float)} can be
     * overridden to allow values outside of the progress bar's min/max range.
     *
     * @param value
     * @return false if the value was not changed because the progress bar
     * already had the value or it was canceled by a listener.
     */
    public boolean setValue(float value) {
        value = clamp(Math.round(value / stepSize) * stepSize);
        float oldValue = this.value;
        if (value == oldValue) {
            return false;
        }
        float oldVisualValue = getVisualValue();
        this.value = value;
        ChangeListener.ChangeEvent changeEvent = Pools.obtain(ChangeListener.ChangeEvent.class);
        boolean cancelled = fire(changeEvent);
        if (cancelled) {
            this.value = oldValue;
        } else if (animateDuration > 0) {
            animateFromValue = oldVisualValue;
            animateTime = animateDuration;
        }
        Pools.free(changeEvent);
        return !cancelled;
    }

    /**
     * Clamps the value to the progress bar's min/max range. This can be
     * overridden to allow a range different from the progress bar knob's range.
     *
     * @param value
     * @return
     */
    protected float clamp(float value) {
        return MathUtils.clamp(value, min, max);
    }

    /**
     * Sets the range of this progress bar. The progress bar's current value is
     * clamped to the range.
     *
     * @param min
     * @param max
     */
    public void setRange(float min, float max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        this.min = min;
        this.max = max;
        if (value < min) {
            setValue(min);
        } else if (value > max) {
            setValue(max);
        }
    }

    public void setStepSize(float stepSize) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("steps must be > 0: " + stepSize);
        }
        this.stepSize = stepSize;
    }

    @Override
    public float getPrefWidth() {
        final Drawable knob = getKnobDrawable();
        final Drawable bg = (disabled && style.disabledBackground != null) ? style.disabledBackground : style.background;
        return Math.max(knob == null ? 0 : knob.getMinWidth(), bg.getMinWidth());
    }

    @Override
    public float getPrefHeight() {
        final Drawable knob = getKnobDrawable();
        final Drawable bg = (disabled && style.disabledBackground != null) ? style.disabledBackground : style.background;
        return Math.max(knob == null ? 0 : knob.getMinHeight(), bg == null ? 0 : bg.getMinHeight());
    }

    public float getMinValue() {
        return this.min;
    }

    public float getMaxValue() {
        return this.max;
    }

    public float getStepSize() {
        return this.stepSize;
    }

    /**
     * If > 0, changes to the progress bar value via {@link #setValue(float)}
     * will happen over this duration in seconds.
     *
     * @param duration
     */
    public void setAnimateDuration(float duration) {
        this.animateDuration = duration;
    }

    /**
     * Sets the interpolation to use for {@link #setAnimateDuration(float)}.
     *
     * @param animateInterpolation
     */
    public void setAnimateInterpolation(Interpolation animateInterpolation) {
        if (animateInterpolation == null) {
            throw new IllegalArgumentException("animateInterpolation cannot be null.");
        }
        this.animateInterpolation = animateInterpolation;
    }

    /**
     * Sets the interpolation to use for display.
     *
     * @param interpolation
     */
    public void setVisualInterpolation(Interpolation interpolation) {
        this.visualInterpolation = interpolation;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean isDisabled() {
        return disabled;
    }

    public boolean isClockWise() {
        return cw;
    }

    public void setClockWise(boolean clockWise) {
        this.cw = clockWise;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        startAngle %= 360.0f;
        this.startAngle = startAngle;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (animateTime > 0) {
            animateTime -= delta;
            Stage stage = getStage();
            if (stage != null && stage.getActionsRequestRendering()) {
                Gdx.graphics.requestRendering();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        final Drawable bg = (disabled && style.disabledBackground != null) ? style.disabledBackground : style.background;
        final RadialDrawable knob = (disabled && style.disabledKnob != null) ? style.disabledKnob : style.knob;

        float percent = getVisualPercent();

        if (bg != null) {
            bg.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
        if (knob != null) {
            if (!MathUtils.isZero(percent)) {
                if (!cw) {
                    knob.setStartAngle(startAngle);
                    knob.setAngle(percent * 360.0f);
                    knob.draw(batch, getX(), getY(), getWidth(), getHeight());
                } else {
                    knob.setStartAngle(360.0f - percent * 360.0f + startAngle);
                    knob.setAngle(percent * 360.0f);
                    knob.draw(batch, getX(), getY(), getWidth(), getHeight());
                }
            }
        }
    }

    public static class RadialProgressBarStyle {

        /**
         * Optional
         */
        public Drawable background;
        /**
         * Optional
         */
        public Drawable disabledBackground;
        /**
         * Optional
         */
        public RadialDrawable knob, disabledKnob;

        public RadialProgressBarStyle() {
        }

        public RadialProgressBarStyle(Drawable background, RadialDrawable knob) {
            this.background = background;
            this.knob = knob;
        }

        public RadialProgressBarStyle(RadialProgressBarStyle style) {
            this.background = style.background;
            this.disabledBackground = style.disabledBackground;
            this.knob = style.knob;
            this.disabledKnob = style.disabledKnob;
        }
    }
}
