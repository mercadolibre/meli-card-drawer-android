package com.meli.android.carddrawer.model

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.android.carddrawer.R

internal class CardMediumConfiguration(source: CardUI): CardDefaultResConfiguration(source) {

    override fun setUpConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        setUpVisibility(constraintLayout)
    }

    override fun resetConstraintLayoutConfiguration(constraintLayout: ConstraintLayout) {
        setUpVisibility(constraintLayout)
    }

    private fun setUpVisibility(constraintLayout: ConstraintLayout) {
        constraintLayout.also {
            it.findViewById<View>(R.id.cho_card_name).also(::setVisibility)
            it.findViewById<View>(R.id.cho_card_number).also(::setVisibility)
        }
    }
}