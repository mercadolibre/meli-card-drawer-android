package com.meli.android.carddrawer.configuration.shadow;

import androidx.annotation.NonNull;
import android.text.TextPaint;

public interface ShadowConfiguration {
    default void drawShadow(@NonNull final TextPaint textPaint) {}
}
