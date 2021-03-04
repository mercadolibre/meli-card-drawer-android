package com.meli.android.carddrawer.model.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat

internal class CardDrawerSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): LinearLayout(context, attrs) {

    private lateinit var switchModel: SwitchModel
    private var onSwitchListener: OnSwitchListener? = null
    private lateinit var switchSelection: String

    private lateinit var switchCompat: SwitchCompat

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
        setUpOptions(switchModel.options)
    }

    private fun setUpOptions(options: List<SwitchModel.SwitchOption>) {
        options.forEach {

        }
    }

    fun setSwitchListener(onSwitchListener: OnSwitchListener) {
        this.onSwitchListener = onSwitchListener
    }

    fun getSwitchSelection() = switchSelection

    interface OnSwitchListener {
        fun onChange(id: String)
    }
}