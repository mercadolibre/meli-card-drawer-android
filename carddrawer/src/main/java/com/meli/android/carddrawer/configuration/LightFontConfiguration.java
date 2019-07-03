package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.R;


public class LightFontConfiguration implements CardFontConfiguration {

    private final Context context;

    public LightFontConfiguration(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int getColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_light_font_empty_color);
    }

    @Override
    public void setGradient(@NonNull TextPaint textPaint, int width, int height) {
        int lightFontGradientColorBottom = ContextCompat.getColor(context, R.color.card_drawer_light_font_color_bottom);
        int lightFontGradientColorTop = ContextCompat.getColor(context, R.color.card_drawer_light_font_color_top);

        LinearGradient gradient = new LinearGradient(width / 2, 0, width / 2, height, lightFontGradientColorBottom,
                lightFontGradientColorTop, Shader.TileMode.CLAMP);

        textPaint.setShader(gradient);
    }

    @Override
    public void setShadow(@NonNull TextPaint textPaint) {
        int shadowRadius = context.getResources().getDimensionPixelSize(R.dimen.card_drawer_shadow_radius);
        int shadowColor = ContextCompat.getColor(context, R.color.card_drawer_number_shadow_color);
        textPaint.setShadowLayer(shadowRadius, 0, 2, shadowColor);
    }
}