package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.R;

public class LightFontConfiguration extends ShadowFontConfiguration {

    private final int fontColor;
    private final int lightFontGradientColorBottom;
    private final int lightFontGradientColorTop;

    LightFontConfiguration(@NonNull Context context) {
        super(context);
        fontColor = ContextCompat.getColor(context, R.color.card_drawer_light_font_empty_color);
        lightFontGradientColorBottom = ContextCompat.getColor(context, R.color.card_drawer_light_font_color_bottom);
        lightFontGradientColorTop = ContextCompat.getColor(context, R.color.card_drawer_light_font_color_top);
    }

    @Override
    public int getColor() {
        return fontColor;
    }

    @Override
    public void setGradient(@NonNull final TextPaint textPaint, final int width, final int height) {
        LinearGradient gradient = new LinearGradient(width / 2, 0, width / 2, height, lightFontGradientColorBottom,
            lightFontGradientColorTop, Shader.TileMode.CLAMP);

        textPaint.setShader(gradient);
    }
}