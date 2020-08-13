package com.meli.android.carddrawer.configuration;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specifies the possible locations for the security code in the visual card
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({SecurityCodeLocation.NONE, SecurityCodeLocation.FRONT, SecurityCodeLocation.BACK})
public @interface SecurityCodeLocation {
    String FRONT = "front";
    String BACK = "back";
    String NONE = "none";
}
