package com.meli.android.carddrawer.utils

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import com.meli.android.carddrawer.internal.CardDrawerTextView
import com.meli.android.carddrawer.internal.Text

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

    fun loadOrHide(visibility: Int, text: Text?, view: CardDrawerTextView): Boolean {
        return if (text == null || TextUtil.isEmpty(text.message)) {
            view.visibility = visibility
            false
        } else {
            view.setText(text)
            view.visibility = View.VISIBLE
            true
        }
    }

    fun loadOrGone(text: Text?, textView: CardDrawerTextView) {
        if (text == null || TextUtil.isEmpty(text.message)) {
            textView.visibility = View.GONE
        } else {
            textView.setText(text)
            textView.visibility = View.VISIBLE
        }
    }

    private fun logParseColorError(color: String?) {
        Log.d(TAG, "Cannot parse color $color")
    }

}