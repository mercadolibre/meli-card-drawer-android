package com.meli.android.carddrawer.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates the start position for animation
 * Created by rcortazzo on 18/10/2018.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({CardAnimationType.RIGHT_BOTTOM, CardAnimationType.LEFT_TOP, CardAnimationType.NONE})
public @interface CardAnimationType {
    String RIGHT_BOTTOM = "right_bottom";
    String LEFT_TOP = "left_top";
    String NONE = "none";
}
