package com.meli.android.carddrawer.utils

import android.content.Context
import android.util.DisplayMetrics

object DensityHelper {
    fun getName(context: Context): String {
        val densityScale = 1.0f / DisplayMetrics.DENSITY_DEFAULT
        val density = context.resources.displayMetrics.density / densityScale

        return when {
            density >= DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            density >= DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            density >= DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            density >= DisplayMetrics.DENSITY_HIGH -> "hdpi"
            density >= DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            else -> "ldpi"
        }
    }
}
