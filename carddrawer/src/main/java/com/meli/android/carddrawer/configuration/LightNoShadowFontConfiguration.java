package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextPaint;

public class LightNoShadowFontConfiguration extends LightFontConfiguration {

    LightNoShadowFontConfiguration(@NonNull final Context context) {
        super(context);
    }

    @Override
    public void setShadow(@NonNull final TextPaint textPaint) {
        // do nothing
    }
}
