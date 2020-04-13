package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration;

public class DarkFontConfiguration implements CardFontConfiguration {

    private final int fontColor;
    @NonNull
    private final ShadowConfiguration shadowFontConfiguration;

    DarkFontConfiguration(@NonNull final Context context) {
        this(context, new ShadowConfiguration() {});
    }

    DarkFontConfiguration(@NonNull Context context, @NonNull final ShadowConfiguration shadowConfiguration) {
        fontColor = ContextCompat.getColor(context, R.color.card_drawer_dark_font_empty_color);
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