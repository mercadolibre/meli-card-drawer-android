package com.meli.android.carddrawer.model.customview

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.View
import androidx.appcompat.widget.DrawableUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import kotlin.math.roundToInt

internal object CustomSwitchHelper {

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

    @JvmStatic
    fun getOpticalBounds(drawable: Drawable): Rect {
        var currentDrawable = drawable
        var sInsetsClazz: Class<*>? = null
        try {
            sInsetsClazz = Class.forName("android.graphics.Insets")
        } catch (e: ClassNotFoundException) {
            // Oh well...
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val insets = currentDrawable.opticalInsets
            val result = Rect()
            result.left = insets.left
            result.right = insets.right
            result.top = insets.top
            result.bottom = insets.bottom
            return result
        }
        if (sInsetsClazz != null) {
            try {
                // If the Drawable is wrapped, we need to manually unwrap it and process
                // the wrapped drawable.
                currentDrawable = DrawableCompat.unwrap(currentDrawable)
                val getOpticalInsetsMethod = drawable.javaClass
                    .getMethod("getOpticalInsets")
                val insets = getOpticalInsetsMethod.invoke(currentDrawable)
                if (insets != null) {
                    // If the drawable has some optical insets, let's copy them into a Rect
                    val result = Rect()
                    for (field in sInsetsClazz.fields) {
                        when (field.name) {
                            "left" -> result.left = field.getInt(insets)
                            "top" -> result.top = field.getInt(insets)
                            "right" -> result.right = field.getInt(insets)
                            else -> result.bottom = field.getInt(insets)
                        }
                    }
                    return result
                }
            } catch (e: Exception) {
                Log.e("CARD_DRAWER", "Couldn't obtain the optical insets. Ignoring.")
            }
        }

        // If we reach here, either we're running on a device pre-v18, the Drawable didn't have
        // any optical insets, or a reflection issue, so we'll just return an empty rect
        return DrawableUtils.INSETS_NONE
    }

    @JvmStatic
    fun isLayoutRtl(view: View?): Boolean {
        return ViewCompat.getLayoutDirection(view!!) == ViewCompat.LAYOUT_DIRECTION_RTL
    }
}