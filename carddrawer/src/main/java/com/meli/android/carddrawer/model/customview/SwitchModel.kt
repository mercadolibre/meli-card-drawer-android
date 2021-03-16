package com.meli.android.carddrawer.model.customview

import android.graphics.Typeface

data class SwitchModel(
    val description: Text,
    val states: SwitchStates,
    val options: List<SwitchOption>,
    val switchBackgroundColor: String,
    val pillBackgroundColor: String,
    val safeZoneBackgroundColor: String,
    val default: String
) {

    data class Text(
        val textColor: String,
        val typeface: Typeface,
        val text: String
    )

    data class SwitchOption(
        val id: String,
        val name: String
    )

    data class SwitchStates(
        val checkedState: State,
        val uncheckedState: State
    ) {

        data class State(
            val textColor: String,
            val typeface: Typeface
        )
    }
}