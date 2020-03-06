package com.meli.android.carddrawer.configuration;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextPaint;

public interface CardFontConfiguration {

    /**
     * @return the color to use for empty text
     */
    @ColorInt
    int getColor();

    /**
     * Sets the gradient for configuration
     * @param textPaint textPaint for the gradient
     * @param width of the textView that contains the font
     * @param height of the textView that contains the font
     */
    default void setGradient(@NonNull TextPaint textPaint, int width, int height) {}

    /**
     * Sets the shadow for configuration
     * @param textPaint textPaint for the shadow
     */
    default void setShadow(@NonNull TextPaint textPaint) {}
}
