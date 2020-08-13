package com.meli.android.carddrawer.configuration;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration;

public class LightFontConfiguration implements CardFontConfiguration {

    private int fontColor;
    @NonNull
    private final ShadowConfiguration shadowFontConfiguration;

    LightFontConfiguration(@NonNull final Context context) {
        this(context, new ShadowConfiguration() {});
    }

    LightFontConfiguration(@NonNull final Context context, @NonNull final ShadowConfiguration shadowConfiguration) {
        fontColor = ContextCompat.getColor(context, R.color.card_drawer_light_font_empty_color);
        shadowFontConfiguration = shadowConfiguration;
    }

    @Override
    public int getColor() {
        return fontColor;
    }


    @Override
    public void setShadow(@NonNull final TextPaint textPaint) {
        shadowFontConfiguration.drawShadow(textPaint);
    }
}