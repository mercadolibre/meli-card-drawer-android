package com.meli.android.carddrawer.model

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.NumberFormatter.formatShort

internal class CardHighResConfiguration(source: CardUI): CardConfiguration(source) {

    override fun setUpConstraintConfiguration(constraintSet: ConstraintSet) {
        setUpConstraintCardNumber(constraintSet)
        setUpConstraintCardName(constraintSet)
    }

    override fun setUpConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        setUpVisibility(constraintLayout)
    }

    override fun resetConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        setUpVisibility(constraintLayout)
    }

    private fun setUpVisibility(constraintLayout: ConstraintLayout) {
        constraintLayout.also {
            val codeFront = it.findViewById<TextView>(R.id.cho_card_code_front).also (::setVisibility)
            it.findViewById<View>(R.id.cho_card_date).also (::setVisibility)
            it.findViewById<View>(R.id.cho_card_code_front_red_circle).also { view ->
                setVisibilityForRedCircle(view, codeFront)
            }
        }
    }

    override fun canPerformAction(view: View) = when (view.id) {
        R.id.cho_card_date -> defaultConfiguration == null
        else -> super.canPerformAction(view)
    }

    override fun getFormattedNumber(input: String?, vararg pattern: Int): String {
        return defaultConfiguration?.let { formatShort(input, *pattern) } ?: super.getFormattedNumber(input, *pattern)
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
}