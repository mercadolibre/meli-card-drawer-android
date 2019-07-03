package com.meli.android.carddrawer.configuration;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextPaint;

public class DefaultFontConfiguration implements CardFontConfiguration {
    private final @ColorInt
    int color;

    public DefaultFontConfiguration(@ColorInt int color) {
        this.color = color;
    }


    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setGradient(@NonNull TextPaint textPaint, int width, int height) {
        // Do nothing for this configuration
    }

    @Override
    public void setShadow(@NonNull TextPaint textPaint) {
        // Do nothing for this configuration
    }
}
