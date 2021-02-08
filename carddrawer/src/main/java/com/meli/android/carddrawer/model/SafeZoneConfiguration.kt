package com.meli.android.carddrawer.model

import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.android.carddrawer.format.NumberFormatter

internal interface SafeZoneConfiguration {
    fun updateConfiguration(constraintLayout: ConstraintLayout)
    fun resetConfiguration(constraintLayout: ConstraintLayout)
    fun getFormattedNumber(input: String?, vararg pattern: Int) = NumberFormatter.format(input, *pattern)
}