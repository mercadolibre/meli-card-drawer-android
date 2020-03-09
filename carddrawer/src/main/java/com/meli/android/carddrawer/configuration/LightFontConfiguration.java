package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration;

public class LightFontConfiguration implements CardFontConfiguration {

    private int fontColor;
    private int lightFontGradientColorBottom;
    private int lightFontGradientColorTop;
    @NonNull
    private final ShadowConfiguration shadowFontConfiguration;

    LightFontConfiguration(@NonNull final Context context) {
        this(context, new ShadowConfiguration() {});
    }

    LightFontConfiguration(@NonNull final Context context, @NonNull final ShadowConfiguration shadowConfiguration) {
        fontColor = ContextCompat.getColor(context, R.color.card_drawer_light_font_empty_color);
        lightFontGradientColorBottom = ContextCompat.getColor(context, R.color.card_drawer_light_font_color_bottom);
        lightFontGradientColorTop = ContextCompat.getColor(context, R.color.card_drawer_light_font_color_top);
        shadowFontConfiguration = shadowConfiguration;
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

    @Override
    public void setShadow(@NonNull final TextPaint textPaint) {
        shadowFontConfiguration.drawShadow(textPaint);
    }
}