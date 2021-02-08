package com.meli.android.carddrawer.model

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER_VERTICAL
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

private const val VIEW_ID = 12731

open class CardDrawerSwitchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RadioGroup(context, attrs) {

    private lateinit var switchModel: SwitchModel
    private var onSwitchListener: OnSwitchListener? = null
    private lateinit var switchSelection: String

    init {
        if (id == View.NO_ID) {
            id = VIEW_ID
        }
        isSaveEnabled = true
        orientation = LinearLayout.HORIZONTAL
        gravity = CENTER_VERTICAL
    }

    fun setSwitchModel(switchModel: SwitchModel) {
        this.switchModel = switchModel
        setUpConfiguration()
    }

    fun setSwitchListener(onSwitchListener: OnSwitchListener) {
        this.onSwitchListener = onSwitchListener
    }

    fun getSwitchSelection() = switchSelection

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cardSizeMultiplier = measuredWidth / resources.getDimension(R.dimen.card_drawer_card_width)
        val switchOptionWidthMultiplier = if (oldw > 0) ((w * 100 ) / oldw) / 100f else cardSizeMultiplier
        val switchOptionHeightMultiplier = if (oldh > 0) ((h * 100 ) / oldh) / 100f else cardSizeMultiplier

        for(i in 0 until  childCount) {
            val child = getChildAt(i) as TextView
            setTextPixelSize(child, cardSizeMultiplier)
            setLayoutParamSize(child, switchOptionWidthMultiplier, switchOptionHeightMultiplier)
        }
    }

    private fun setUpConfiguration() {
        switchSelection = switchModel.default
        setUpOptions(switchModel.options)
    }

    private fun setUpOptions(options: List<SwitchOption>) {
        options.forEach { option ->
            RadioButton(context).also { button ->
                button.id = View.generateViewId()
                button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                button.text = option.name
                button.setOnClickListener {
                    switchSelection = option.id
                    onSwitchListener?.onChange(switchSelection)
                }
            }.also(::addView).takeIf { option.id == switchSelection }?.also(::checkDefault)
        }
    }

    private fun setLayoutParamSize(view: TextView, widthMultiplier: Float, heightMultiplier: Float) {
        view.layoutParams = view.layoutParams.also {
            it.width = (view.measuredWidth * widthMultiplier).roundToInt()
            it.height = (view.measuredHeight * heightMultiplier).roundToInt()
        }
    }
    private fun setTextPixelSize(view: TextView, multiplier: Float) {
        val newTextSize = resources.getDimension(R.dimen.card_drawer_font_size) * multiplier
        view.post { view.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize) }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return CardDrawerSwitchSavedState(switchModel, switchSelection, super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState = state
        if (state is CardDrawerSwitchSavedState) {
            switchModel = state.switchModel
            switchSelection = state.switchSelection
            superState = state.superState
        }
        super.onRestoreInstanceState(superState)
    }

    open fun checkDefault(childView: View) = check(childView.id)

    @Parcelize
    internal data class CardDrawerSwitchSavedState(
        val switchModel: SwitchModel,
        val switchSelection: String,
        val superState: Parcelable?) : Parcelable

    interface OnSwitchListener {
        fun onChange(id: String)
    }
}