package com.meli.android.carddrawer

import android.graphics.Color

internal object ColorUtils {

    @JvmStatic
    fun safeParcelColor(color: String?) = runCatching { Color.parseColor(color) }.getOrDefault(Color.BLACK)
}