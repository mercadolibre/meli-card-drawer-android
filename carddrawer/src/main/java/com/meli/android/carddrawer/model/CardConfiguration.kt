package com.meli.android.carddrawer.model

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.format.NumberFormatter

internal abstract class CardConfiguration(protected var source: CardUI) {

    private var defaultConfiguration: ConstraintSet? = null

    fun updateSource(source: CardUI) {
        this.source = source
    }

    fun updateConfiguration(constraintLayout: ConstraintLayout) {
        if (defaultConfiguration == null) {
            defaultConfiguration = ConstraintSet().also { it.clone(constraintLayout) }
        }

        val constraintSet = ConstraintSet()
        setUpConstraintLayoutConfiguration(constraintLayout)
        constraintSet.clone(constraintLayout)
        setUpConstraintConfiguration(constraintSet)
        constraintSet.applyTo(constraintLayout)
    }

    fun resetConfiguration(constraintLayout: ConstraintLayout) {
        defaultConfiguration?.applyTo(constraintLayout)
        defaultConfiguration = null
        resetConstraintLayoutConfiguration(constraintLayout)
    }

    protected fun setVisibility(view: View) {
        view.visibility = when {
            hasSafeZone() -> View.INVISIBLE
            canPerformAction(view) -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    protected fun setVisibilityForRedCircle(view: View, codeFront: TextView) {
        view.visibility = when {
            hasSafeZone() -> View.INVISIBLE
            canPerformAction(view) && !cardCodeIsDefaultOrEmpty(codeFront) -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    protected fun isRegularStyle() = source.style == null || source.style == CardDrawerStyle.REGULAR

    protected fun hasSafeZone() = defaultConfiguration != null

    private fun cardCodeIsDefaultOrEmpty(codeFront: TextView) = codeFront.text.isNullOrEmpty()
        || codeFront.text == getFormattedNumber("", source.securityCodePattern)

    fun canShow(view: View) = canPerformAction(view)
    fun canAnimate(view: View, block: (View) -> Unit) {
        if (canPerformAction(view)) {
            block(view)
        }
    }

    open fun getFormattedNumber(input: String?, vararg pattern: Int) = NumberFormatter.format(input, *pattern)

    protected open fun canPerformAction(view: View): Boolean {
        return when (view.id) {
            R.id.cho_card_code_front,
            R.id.cho_card_code_front_red_circle -> {
                source.securityCodeLocation == SecurityCodeLocation.FRONT && !hasSafeZone()
            }
            else -> true
        }
    }

    abstract fun setUpConstraintConfiguration(constraintSet: ConstraintSet)
    abstract fun setUpConstraintLayoutConfiguration(constraintLayout: ConstraintLayout)
    abstract fun resetConstraintLayoutConfiguration(constraintLayout: ConstraintLayout)
}