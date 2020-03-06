package com.meli.android.carddrawer.configuration;

import android.support.annotation.ColorInt;

public class DefaultFontConfiguration implements CardFontConfiguration {
    private final @ColorInt
    int color;

    DefaultFontConfiguration(@ColorInt int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }
}
