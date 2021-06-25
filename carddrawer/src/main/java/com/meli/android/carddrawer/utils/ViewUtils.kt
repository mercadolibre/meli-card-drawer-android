package com.meli.android.carddrawer.utils

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView

const val TAG: String = "ViewUtils"

internal object ViewUtils {

    fun setTextColor(textView: TextView, color: String?) {
        if (TextUtil.isNotEmpty(color)) {
            try {
                textView.setTextColor(Color.parseColor(color))
            } catch (e: Exception) {
                logParseColorError(color)
            }
        }
    }

    fun setBackgroundColor(view: View, color: String?) {
        if (TextUtil.isNotEmpty(color)) {
            try {
                view.setBackgroundColor(Color.parseColor(color))
            } catch (e: Exception) {
                logParseColorError(color)
            }
        }
    }

    private fun logParseColorError(color: String?) {
        Log.d(TAG, "Cannot parse color $color")
    }

}