package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

public class CardFontConfigurationFactory {
    private CardFontConfigurationFactory() {
    }

    public static CardFontConfiguration getConfiguration(@NonNull @FontType String fontType, @NonNull @ColorInt int color, @NonNull Context context) {
        CardFontConfiguration configuration;
        switch (fontType) {
            case FontType.DARK_TYPE:
                configuration = new DarkFontConfiguration(context);
                break;
            case FontType.LIGHT_TYPE:
                configuration = new LightFontConfiguration(context);
                break;
            default:
                configuration = new DefaultFontConfiguration(color);
                break;
        }

        return configuration;
    }
}
