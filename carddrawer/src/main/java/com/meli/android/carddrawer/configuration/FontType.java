package com.meli.android.carddrawer.configuration;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({FontType.LIGHT_TYPE, FontType.DARK_TYPE, FontType.NONE})
public @interface FontType {
    String DARK_TYPE = "dark";
    String DARK_NO_SHADOW_TYPE = "dark_no_shadow";
    String LIGHT_TYPE = "light";
    String LIGHT_NO_SHADOW_TYPE = "light_no_shadow";
    String NONE = "none";
}
