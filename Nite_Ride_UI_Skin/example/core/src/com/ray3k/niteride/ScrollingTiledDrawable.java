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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScrollingTiledDrawable extends TextureRegionDrawable {
    private final Color color = new Color(1, 1, 1, 1);
    private float offsetX;
    private float offsetY;

    public ScrollingTiledDrawable() {
        super();
    }

    public ScrollingTiledDrawable(TextureRegion region) {
        super(region);
    }

    public ScrollingTiledDrawable(TextureRegionDrawable drawable) {
        super(drawable);
    }

    public void draw(Batch batch, float x, float y, float width, float height) {
        float batchColor = batch.getPackedColor();
        batch.setColor(batch.getColor().mul(color));

        TextureRegion region = getRegion();
        float regionWidth = region.getRegionWidth(), regionHeight = region.getRegionHeight();
        float offX = this.offsetX % regionWidth;
        if (offX < 0) offX += regionWidth;
        float offY = this.offsetY % regionHeight;
        if (offY < 0) offY += regionHeight;
        int fullX = (int) ((width - offX) / regionWidth), fullY = (int) ((height - offY) / regionHeight);
        float remainingX = width - regionWidth * fullX - offX, remainingY = height - regionHeight * fullY - offY;
        float startX = x, startY = y;
        
        Texture texture = region.getTexture();
        if (offX > 0) {
            // Lower left corner.
            float u = region.getU() + (regionWidth - offX) / texture.getWidth();
            float u2 = u + offX / texture.getWidth();
            float v = region.getV();
            float v2 = region.getV() + offY / texture.getHeight();
            y = startY;
            if (offY > 0) {
                batch.draw(texture, x, y, offX, offY, u, v2, u2, v);
            }
            
            // Left edge.
            u = region.getU() + (regionWidth - offX) / texture.getWidth();
            v2 = region.getV2();
            u2 = u + offX / texture.getWidth();
            v = region.getV();
            for (int ii = 0; ii < fullY; ii++) {
                batch.draw(texture, x, y + offY, offX, regionHeight, u, v2, u2, v);
                y += regionHeight;
            }
            // Upper left corner.
            if (remainingY > 0) {
                u = region.getU2() - offX / texture.getWidth();
                u2 = region.getU2();
                v = region.getV2() - remainingY / texture.getHeight();
                v2 = region.getV2();
                batch.draw(texture, x, y + offY, offX, remainingY, u, v2, u2, v);
            }
        }
        
        if (offY > 0) {
            // Bottom edge.
            float u = region.getU();
            float v = region.getV();
            float v2 = v + offY / texture.getHeight();
            float u2 = region.getU2();
            x = startX;
            y = startY;
            for (int i = 0; i < fullX; i++) {
                batch.draw(texture, x + offX, y, regionWidth, offY, u, v2, u2, v);
                x += regionWidth;
            }
        }
        
        // Body.
        x = startX;
        for (int i = 0; i < fullX; i++) {
            y = startY;
            for (int ii = 0; ii < fullY; ii++) {
                batch.draw(region, x + offX, y + offY, regionWidth, regionHeight);
                y += regionHeight;
            }
            x += regionWidth;
        }
        
        if (remainingX > 0) {
            // Lower right corner.
            float u = region.getU();
            float u2 = region.getU() + remainingX / texture.getWidth();
            float v = region.getV();
            float v2 = region.getV() + offY / texture.getHeight();
            y = startY;
            if (offY > 0) {
                batch.draw(texture, x + offX, y, remainingX, offY, u, v2, u2, v);
            }
            
            // Right edge.
            u = region.getU();
            v2 = region.getV2();
            u2 = u + remainingX / texture.getWidth();
            v = region.getV();
            y = startY;
            for (int ii = 0; ii < fullY; ii++) {
                batch.draw(texture, x + offX, y + offY, remainingX, regionHeight, u, v2, u2, v);
                y += regionHeight;
            }
            // Upper right corner.
            if (remainingY > 0) {
                v = v2 - remainingY / texture.getHeight();
                batch.draw(texture, x + offX, y + offY, remainingX, remainingY, u, v2, u2, v);
            }
        }
        
        if (remainingY > 0) {
            // Top edge.
            float u = region.getU();
            float u2 = region.getU2();
            float v2 = region.getV2();
            float v = v2 - remainingY / texture.getHeight();
            x = startX;
            for (int i = 0; i < fullX; i++) {
                batch.draw(texture, x + offX, y + offY, regionWidth, remainingY, u, v2, u2, v);
                x += regionWidth;
            }
        }

        batch.setColor(batchColor);
    }

    public void draw(Batch batch, float x, float y, float originX, float originY,
            float width, float height, float scaleX,
            float scaleY, float rotation) {
        throw new UnsupportedOperationException();
    }

    public Color getColor() {
        return color;
    }

    public ScrollingTiledDrawable tint(Color tint) {
        ScrollingTiledDrawable drawable = new ScrollingTiledDrawable(this);
        drawable.color.set(tint);
        drawable.setLeftWidth(getLeftWidth());
        drawable.setRightWidth(getRightWidth());
        drawable.setTopHeight(getTopHeight());
        drawable.setBottomHeight(getBottomHeight());
        return drawable;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }
}
