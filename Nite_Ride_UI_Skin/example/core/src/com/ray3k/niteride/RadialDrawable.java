/*******************************************************************************
 * MIT License
 * 
 * Copyright (c) 2016 Raymond Buckley
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

package com.ray3k.niteride;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class RadialDrawable extends BaseDrawable {
    private TextureRegion textureRegion;
    
    private float angle;
    private float startAngle;
    private static ShapeRenderer shapeRenderer;

    public RadialDrawable() {
        if (shapeRenderer == null) shapeRenderer = new ShapeRenderer();
    }
    
    public RadialDrawable(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        if (shapeRenderer == null) shapeRenderer = new ShapeRenderer();
    }

    public RadialDrawable(RadialDrawable drawable) {
        super(drawable);
        textureRegion = drawable.getTextureRegion();
        if (shapeRenderer == null) shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        if (MathUtils.isEqual(angle, 360.0f)) {
            batch.draw(textureRegion, x, y, width, height);
        } else if (!MathUtils.isZero(angle)) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

            batch.end();
            Gdx.gl.glClearDepthf(1f);
            Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glDepthFunc(GL20.GL_LESS);
            Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
            Gdx.gl.glDepthMask(true);
            Gdx.gl.glColorMask(false, false, false, false);

            //draw mask with shape primitives. Any combination of shapes will work.
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            float diagonalLength = (float) Math.sqrt(Math.pow(width / 2.0f, 2.0f) + Math.pow(height / 2.0f, 2.0f));
            shapeRenderer.arc(x + width / 2.0f, y + height / 2.0f, diagonalLength, startAngle, angle);
            shapeRenderer.end();

            batch.begin();
            Gdx.gl.glColorMask(true, true, true, true);
            Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
            //set to draw only where mask was drawn
            Gdx.gl.glDepthFunc(GL20.GL_EQUAL);

            //draw image(s) that needs to be masked
            batch.draw(textureRegion, x, y, width, height);

            //reset for regular drawing
            batch.end();
            batch.begin();
            Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        }
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }
}
