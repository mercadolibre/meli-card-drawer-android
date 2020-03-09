package com.meli.android.carddrawer.configuration.shadow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import com.meli.android.carddrawer.R;

public class ShadowFontConfiguration implements ShadowConfiguration {
    private final int shadowRadius;
    private final int shadowColor;
    private final float shadowHeight;

    public ShadowFontConfiguration(@NonNull final Context context) {
        shadowRadius = context.getResources().getDimensionPixelSize(R.dimen.card_drawer_shadow_radius);
        shadowColor = ContextCompat.getColor(context, R.color.card_drawer_number_shadow_color);
        shadowHeight = (context.getResources().getDisplayMetrics().density * 1) + 0.5f;
    }

    @Override
    public void drawShadow(@NonNull final TextPaint textPaint) {
        textPaint.setShadowLayer(shadowRadius, 0, shadowHeight, shadowColor);
    }
}
