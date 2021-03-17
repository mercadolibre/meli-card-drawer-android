package com.meli.android.carddrawer.model.customview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.utils.DensityName
import com.meli.android.carddrawer.utils.getDensityName
import kotlin.math.roundToInt

internal object CardDrawerSwitchHelper {

    private fun getTrackTextSize(context: Context, multiplier: Float): Float {
        with(context) {
            return when (getDensityName()) {
                DensityName.XXXHDPI -> {
                    if (multiplier <= 1) {
                        resources.getDimension(R.dimen.card_drawer_switch_xxxhdpi_text_regular)
                    } else {
                        resources.getDimension(R.dimen.card_drawer_switch_xxxhdpi_text_responsive)
                    }
                }
                DensityName.XXHDPI -> {
                    if (multiplier <= 1) {
                        resources.getDimension(R.dimen.card_drawer_switch_xxhdpi_text_regular)
                    } else {
                        resources.getDimension(R.dimen.card_drawer_switch_xxhdpi_text_responsive)
                    }
                }
                DensityName.XHDPI -> {
                    if (multiplier <= 1) {
                        resources.getDimension(R.dimen.card_drawer_switch_xhdpi_text_regular)
                    } else {
                        resources.getDimension(R.dimen.card_drawer_switch_xhdpi_text_responsive)
                    }
                }
                DensityName.HDPI -> {
                    if (multiplier <= 1) {
                        resources.getDimension(R.dimen.card_drawer_switch_hdpi_text_regular)
                    } else {
                        resources.getDimension(R.dimen.card_drawer_switch_hdpi_text_responsive)
                    }
                }
                else -> {
                    if (multiplier <= 1) {
                        resources.getDimension(R.dimen.card_drawer_switch_mdpi_text_regular)
                    } else {
                        resources.getDimension(R.dimen.card_drawer_switch_mdpi_text_responsive)
                    }
                }
            }
        }
    }

    fun getThumbTextAppearance(context: Context, multiplier: Float): Int {
        return when (context.getDensityName()) {
            DensityName.XXXHDPI -> {
                if (multiplier <= 1) {
                    R.style.SwitchTextAppearanceXxxhdpiRegular
                } else {
                    R.style.SwitchTextAppearanceXxxhdpiResponsive
                }
            }
            DensityName.XXHDPI -> {
                if (multiplier <= 1) {
                    R.style.SwitchTextAppearanceXxhdpiRegular
                } else {
                    R.style.SwitchTextAppearanceXxhdpiResponsive
                }
            }
            DensityName.XHDPI -> {
                if (multiplier <= 1) {
                    R.style.SwitchTextAppearanceXhdpiRegular
                } else {
                    R.style.SwitchTextAppearanceXhdpiResponsive
                }
            }
            DensityName.HDPI -> {
                if (multiplier <= 1) {
                    R.style.SwitchTextAppearanceHdpiRegular
                } else {
                    R.style.SwitchTextAppearanceHdpiResponsive
                }
            }
            else -> {
                if (multiplier <= 1) {
                    R.style.SwitchTextAppearanceMdpiRegular
                } else {
                    R.style.SwitchTextAppearanceMdpiResponsive
                }
            }
        }
    }

    fun makeTextLayout(textToDraw: String, textPaint: TextPaint): StaticLayout {
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

    fun makeTrackTextPaint(
        context: Context, textColor: String, multiplier: Float): TextPaint {
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.density = context.resources.displayMetrics.density
        textPaint.color = Color.parseColor(textColor)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSkewX = 0f
        textPaint.isFakeBoldText = false
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = getTrackTextSize(context, multiplier)
        return textPaint
    }
}