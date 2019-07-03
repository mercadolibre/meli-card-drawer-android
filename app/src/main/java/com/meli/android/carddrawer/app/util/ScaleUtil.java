package com.meli.android.carddrawer.app.util;

import android.content.Context;
import android.util.DisplayMetrics;

public final class ScaleUtil {

    private static final int HEIGHT_LOW = 800;

    private ScaleUtil() {
    }

    public static boolean isLowRes(final Context context) {
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final boolean dpiLowRes = metrics.densityDpi < DisplayMetrics.DENSITY_HIGH;
        final boolean heightLowRes = metrics.heightPixels < HEIGHT_LOW;
        return dpiLowRes || heightLowRes;
    }
}
