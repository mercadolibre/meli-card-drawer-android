package com.meli.android.carddrawer.configuration;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({FontType.LIGHT_TYPE, FontType.DARK_TYPE, FontType.NONE})
public @interface FontType {
    String DARK_TYPE = "dark";
    String LIGHT_TYPE = "light";
    String NONE = "none";
}
