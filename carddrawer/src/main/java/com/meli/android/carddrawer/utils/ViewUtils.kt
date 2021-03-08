package com.meli.android.carddrawer.utils

import android.content.Context
import androidx.annotation.Dimension
import kotlin.math.roundToInt

fun Context.dpToPx(@Dimension(unit = 0) dps: Int): Float {
    return (resources.displayMetrics.density * dps.toFloat())
}