package com.meli.android.carddrawer.model

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.format.NumberFormatter

internal abstract class SafeZoneConfiguration {

    protected var defaultConfiguration: ConstraintSet? = null

    fun updateConfiguration(constraintLayout: ConstraintLayout) {
        defaultConfiguration = ConstraintSet().also { it.clone(constraintLayout) }

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        setUpConstraintLayoutConfiguration(constraintLayout)
        setUpConstraintConfiguration(constraintSet)
        constraintSet.applyTo(constraintLayout)
    }

    fun resetConfiguration(constraintLayout: ConstraintLayout) {
        defaultConfiguration?.applyTo(constraintLayout)
        defaultConfiguration = null
        resetConstraintLayoutConfiguration(constraintLayout)
    }

    open fun getFormattedNumber(input: String?, vararg pattern: Int) = NumberFormatter.format(input, *pattern)

    abstract fun setUpConstraintConfiguration(constraintSet: ConstraintSet)
    abstract fun setUpConstraintLayoutConfiguration(constraintLayout: ConstraintLayout)
    abstract fun resetConstraintLayoutConfiguration(constraintLayout: ConstraintLayout)
}