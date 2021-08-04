package com.meli.android.carddrawer

import android.graphics.Color

internal object ColorUtils {

    @JvmStatic
    fun safeParcelColor(color: String?, defaultColor: Int) = runCatching {
        Color.parseColor(color)
    }.getOrDefault(defaultColor)
}