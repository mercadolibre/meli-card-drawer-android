package com.meli.android.carddrawer.model

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.NumberFormatter

internal class CardLowResConfiguration(source: CardUI): CardConfiguration(source) {

    override fun setUpConstraintConfiguration(constraintSet: ConstraintSet) {
        setUpConstraintCardNumber(constraintSet)
    }

    override fun setUpConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        setUpVisibility(constraintLayout)
    }

    override fun resetConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        setUpVisibility(constraintLayout)
    }

    private fun setUpVisibility(constraintLayout: ConstraintLayout) {
        constraintLayout.also {
            val codeFront = it.findViewById<TextView>(R.id.cho_card_code_front).also(::setVisibility)
            it.findViewById<View>(R.id.cho_card_name).also(::setVisibility)
            it.findViewById<View>(R.id.cho_card_code_front_red_circle).also { view ->
                setVisibilityForRedCircle(view, codeFront)
            }
        }
    }

    private fun setUpConstraintCardNumber(constraintSet: ConstraintSet) {
        constraintSet.connect(
            R.id.cho_card_number,
            ConstraintSet.START,
            R.id.card_header_front_guideline_left,
            ConstraintSet.END
        )
        constraintSet.clear(
            R.id.cho_card_number,
            ConstraintSet.END
        )
    }

    override fun canPerformAction(view: View) = when (view.id) {
        R.id.cho_card_name -> defaultConfiguration == null
        else -> super.canPerformAction(view)
    }

    override fun getFormattedNumber(input: String?, vararg pattern: Int): String {
        return NumberFormatter.formatShort(input, *pattern)
    }
}