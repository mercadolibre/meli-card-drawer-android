package com.meli.android.carddrawer

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.ColorUtils.safeParcelColor
import com.meli.android.carddrawer.model.GenericPaymentMethod

internal object ViewHelper {

    @JvmStatic
    fun getGradientDrawable(context: Context,
        gradientColors: List<String>?): GradientDrawable {
        val gradientDrawable = ContextCompat.getDrawable(context, R.drawable.card_drawer_gradient) as GradientDrawable
        return if (gradientColors.isNullOrEmpty()) {
            gradientDrawable
        } else {
            val gradientCopy = gradientDrawable.mutate().constantState!!.newDrawable() as GradientDrawable
            val colorsAmount = gradientColors.size.coerceAtMost(3)
            if (colorsAmount == 1) {
                gradientCopy.setColor(safeParcelColor(gradientColors[0], Color.BLACK))
            } else {
                val parsedColors = IntArray(colorsAmount)
                for (i in 0 until colorsAmount) {
                    parsedColors[i] = safeParcelColor(gradientColors[i], Color.BLACK)
                }
                gradientCopy.colors = parsedColors
            }
            gradientCopy
        }
    }

    @JvmStatic
    fun runWhenViewIsAttachedToWindow(view: View, runnable: Runnable) {
        if (view.isAttachedToWindow) {
            runnable.run()
        } else {
            view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    runnable.run()
                    v.removeOnAttachStateChangeListener(this)
                }

                override fun onViewDetachedFromWindow(v: View) {}
            })
        }
    }

    @JvmStatic
    fun applyBackground(
        context: Context,
        appCompatImageView: AppCompatImageView,
        genericPaymentMethod: GenericPaymentMethod
    ) {
        val gradientColors = genericPaymentMethod.gradientColor
        if (gradientColors != null) {
            val gradientDrawable = getGradientDrawable(context, gradientColors)
            appCompatImageView.setImageDrawable(gradientDrawable)
        } else {
            val backgroundColor = genericPaymentMethod.backgroundColor
            appCompatImageView.background.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP)
        }
    }
}