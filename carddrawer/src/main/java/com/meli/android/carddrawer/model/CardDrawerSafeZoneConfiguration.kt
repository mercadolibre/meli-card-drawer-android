package com.meli.android.carddrawer.model

import android.view.View.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.NumberFormatter.format
import com.meli.android.carddrawer.format.NumberFormatter.formatShort

internal class CardDrawerSafeZoneConfiguration : SafeZoneConfiguration {

    private var defaultConfiguration: ConstraintSet? = null

    override fun updateConfiguration(constraintLayout: ConstraintLayout) {
        defaultConfiguration = ConstraintSet().also { it.clone(constraintLayout) }

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        setUpExpirationVisibility(constraintLayout)
        setUpConstraintCardNumber(constraintSet)
        setUpConstraintCardName(constraintSet)

        constraintSet.applyTo(constraintLayout)
    }

    override fun resetConfiguration(constraintLayout: ConstraintLayout) {
        defaultConfiguration?.applyTo(constraintLayout)
        defaultConfiguration = null
        setUpExpirationVisibility(constraintLayout)
    }

    private fun setUpExpirationVisibility(constraintLayout: ConstraintLayout) {
        constraintLayout
            .findViewById<GradientTextView>(R.id.cho_card_date).also { textView ->
                defaultConfiguration?.let {
                    textView.visibility = INVISIBLE
                } ?: let {
                        textView.visibility = VISIBLE
                    }
            }
    }

    private fun setUpConstraintCardName(constraintSet: ConstraintSet) {
        constraintSet.connect(
            R.id.cho_card_name,
            ConstraintSet.TOP,
            R.id.card_header_front_guideline_cvv,
            ConstraintSet.BOTTOM, 0)
        constraintSet.connect(
            R.id.cho_card_name,
            ConstraintSet.BOTTOM,
            R.id.card_header_front_guideline_number,
            ConstraintSet.TOP, 0)
        constraintSet.connect(
            R.id.cho_card_name,
            ConstraintSet.END,
            R.id.card_header_front_guideline_center,
            ConstraintSet.START, 0)
        constraintSet.connect(
            R.id.cho_card_name,
            ConstraintSet.START,
            R.id.card_header_front_guideline_left,
            ConstraintSet.END, 0)
    }

    private fun setUpConstraintCardNumber(constraintSet: ConstraintSet) {
        constraintSet.connect(
            R.id.cho_card_number,
            ConstraintSet.END,
            R.id.card_header_front_guideline_right,
            ConstraintSet.START, 0)
        constraintSet.connect(
            R.id.cho_card_number,
            ConstraintSet.START,
            R.id.card_header_front_guideline_center,
            ConstraintSet.END, 0)
        constraintSet.connect(
            R.id.cho_card_number,
            ConstraintSet.TOP,
            R.id.card_header_front_guideline_cvv,
            ConstraintSet.BOTTOM, 0)
        constraintSet.connect(
            R.id.cho_card_number,
            ConstraintSet.BOTTOM,
            R.id.card_header_front_guideline_number,
            ConstraintSet.TOP, 0)
    }

    override fun getFormattedNumber(input: String?, vararg pattern: Int): String {
        return defaultConfiguration?.let { formatShort(input, *pattern) }
            ?: super.getFormattedNumber(input, *pattern)
    }
}