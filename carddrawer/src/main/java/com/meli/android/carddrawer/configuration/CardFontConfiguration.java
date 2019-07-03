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
     * @param textPaint
     * @param width of the textview that contains the font
     * @param height of the textview that contains the font
     */
    void setGradient(@NonNull TextPaint textPaint, int width, int height);

    /**
     * Sets the shadow for configuration
     * @param textPaint
     */
    void setShadow(@NonNull TextPaint textPaint);
}
