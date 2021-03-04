package com.meli.android.carddrawer.model.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.meli.android.carddrawer.format.TypefaceSetter.set
import com.mercadolibre.android.andesui.font.Font
import com.mercadolibre.android.andesui.font.TypefaceHelper

internal class CardDrawerSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): LinearLayout(context, attrs) {

    private lateinit var switchModel: SwitchModel
    private var onSwitchListener: OnSwitchListener? = null
    private lateinit var switchSelection: String

    private lateinit var switchCompat: SwitchCompat
    private lateinit var description: TextView

    init {
        if (id == View.NO_ID) {
            id = View.generateViewId()
        }
        orientation = HORIZONTAL
    }

    fun setSwitchModel(switchModel: SwitchModel) {
        this.switchModel = switchModel
        setUpConfiguration()
    }

    private fun setUpConfiguration() {
        switchSelection = switchModel.default
        onSwitchListener?.onChange(switchSelection)
        setBackgroundColor(Color.parseColor(switchModel.backgroundColor))
        setUpOptions(switchModel.options)
        setUpDescription(switchModel.description)
    }

    private fun setUpOptions(options: List<SwitchModel.SwitchOption>) {
        val (op1, op2) = (options.first() to options.last())

        switchCompat.textOn = op1.name
        switchCompat.textOff = op2.name

        switchCompat.setOnCheckedChangeListener { _, isChecked ->

            // If isChecked = true it is in the on position
            switchSelection = if (isChecked) { op1.id } else { op2.id }
            onSwitchListener?.onChange(switchSelection)
        }
    }

    private fun setUpDescription(descriptionText: SwitchModel.Text) {
        description.text = descriptionText.text
        description.setTextColor(Color.parseColor(descriptionText.textColor))
        set(description, TypefaceHelper.getFontTypeface(context, Font.valueOf(descriptionText.weight)))
    }

    private fun setUpSwitchStates(states: SwitchModel.SwitchStates) {

    }

    fun setSwitchListener(onSwitchListener: OnSwitchListener) {
        this.onSwitchListener = onSwitchListener
    }

    fun getSwitchSelection() = switchSelection

    interface OnSwitchListener {
        fun onChange(id: String)
    }
}