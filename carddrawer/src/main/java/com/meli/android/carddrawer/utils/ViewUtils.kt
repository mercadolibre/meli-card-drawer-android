package com.meli.android.carddrawer.utils

import android.content.Context
import android.util.DisplayMetrics
import androidx.annotation.Dimension

fun Context.dpToPx(@Dimension(unit = 0) dps: Int): Float {
    return (resources.displayMetrics.density * dps.toFloat())
}

fun Context.getDensity(): Float {
    val densityScale = 1.0f / DisplayMetrics.DENSITY_DEFAULT
    return resources.displayMetrics.density / densityScale
}

fun Context.getDensityName(): DensityName {
    val density = getDensity()

    return when {

        density >= DisplayMetrics.DENSITY_XXXHIGH -> {
            DensityName.XXXHDPI
        }

        density >= DisplayMetrics.DENSITY_XXHIGH -> {
            DensityName.XXHDPI
        }

        density >= DisplayMetrics.DENSITY_XHIGH -> {
            DensityName.XHDPI
        }

        density >= DisplayMetrics.DENSITY_HIGH -> {
            DensityName.HDPI
        }

        density >= DisplayMetrics.DENSITY_MEDIUM -> {
            DensityName.MDPI
        }
        else -> {
            DensityName.LDPI
        }
    }
}