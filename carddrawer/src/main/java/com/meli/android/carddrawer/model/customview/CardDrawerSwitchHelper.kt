package com.meli.android.carddrawer.model.customview

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import kotlin.math.roundToInt

internal object CardDrawerSwitchHelper {

    @JvmStatic
    fun makeTextLayout(textToDraw: CharSequence, textPaint: TextPaint): StaticLayout {
        val textWidth = Layout.getDesiredWidth(textToDraw, textPaint).roundToInt()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout
                .Builder
                .obtain(
                    textToDraw,
                    0,
                    textToDraw.length,
                    textPaint,
                    textWidth).build()
        } else {
            StaticLayout(textToDraw,
                textPaint,
                textWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0f,
                false)
        }
    }
}