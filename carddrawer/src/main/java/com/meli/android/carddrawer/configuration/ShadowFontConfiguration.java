package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import com.meli.android.carddrawer.R;

public abstract class ShadowFontConfiguration implements CardFontConfiguration {

    private final int shadowRadius;
    private final int shadowColor;
    private final float shadowHeight;

    ShadowFontConfiguration(@NonNull Context context) {
        shadowRadius = context.getResources().getDimensionPixelSize(R.dimen.card_drawer_shadow_radius);
        shadowColor = ContextCompat.getColor(context, R.color.card_drawer_number_shadow_color);
        shadowHeight = (context.getResources().getDisplayMetrics().density * 2) + 0.5f;
    }

    @Override
    public void setShadow(@NonNull final TextPaint textPaint) {
        textPaint.setShadowLayer(shadowRadius, 0, shadowHeight, shadowColor);
    }
}
