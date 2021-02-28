package com.meli.android.carddrawer.model

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.NumberFormatter

internal class SafeZoneLowResConfiguration: SafeZoneConfiguration() {

    override fun setUpConstraintConfiguration(constraintSet: ConstraintSet) {
        setUpConstraintCardNumber(constraintSet)
        setUpConstraintCardCode(constraintSet)
    }

    override fun setUpConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        constraintLayout.findViewById<View>(R.id.cho_card_name).visibility = View.GONE
    }

    override fun resetConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        constraintLayout.findViewById<View>(R.id.cho_card_name).visibility = View.VISIBLE
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

    private fun setUpConstraintCardCode(constraintSet: ConstraintSet) {
        constraintSet.connect(
            R.id.cho_card_code_front,
            ConstraintSet.START,
            R.id.card_header_front_guideline_left,
            ConstraintSet.END
        )
        constraintSet.clear(
            R.id.cho_card_code_front,
            ConstraintSet.END
        )
    }

    override fun getFormattedNumber(input: String?, vararg pattern: Int): String {
        return NumberFormatter.formatShort(input, *pattern)
    }
}