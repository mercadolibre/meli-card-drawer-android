package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.R;

public class DarkFontConfiguration implements CardFontConfiguration {

    private final Context context;
    private static final float CENTER_COLOR_POSITION = 0.3f;
    private static final float START_COLOR_POSITION = 0;
    private static final float END_COLOR_POSITION = 1;

    public DarkFontConfiguration(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int getColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_dark_font_empty_color);
    }

    @Override
    public void setGradient(@NonNull TextPaint textPaint, int width, int height) {
        int fontGradientColorBottom = ContextCompat.getColor(context, R.color.card_drawer_dark_font_color_bottom);
        int fontGradientColorTop = ContextCompat.getColor(context, R.color.card_drawer_dark_font_color_top);
        int fontGradientColorCenter = ContextCompat.getColor(context, R.color.card_drawer_dark_font_color_center);

        LinearGradient gradient = new LinearGradient(width / 2, 0, width / 2, height,
                new int[]{fontGradientColorTop, fontGradientColorCenter, fontGradientColorBottom},
                new float[]{START_COLOR_POSITION, CENTER_COLOR_POSITION, END_COLOR_POSITION},  // start, center and end position
                Shader.TileMode.CLAMP); // start, center and end position

        textPaint.setShader(gradient);
    }

    @Override
    public void setShadow(@NonNull TextPaint textPaint) {
        // Do nothing for this configuration
    }
}