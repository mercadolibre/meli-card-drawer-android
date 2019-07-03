package com.meli.android.carddrawer.configuration;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines all the available positions in a visual representation.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({FieldPosition.POSITION_FRONT, FieldPosition.POSITION_BACK})
public @interface FieldPosition {
    int POSITION_FRONT = 1;
    int POSITION_BACK = 2;
}
