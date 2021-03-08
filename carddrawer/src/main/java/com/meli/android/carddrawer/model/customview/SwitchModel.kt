package com.meli.android.carddrawer.model.customview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SwitchModel(
    val description: Text,
    val states: SwitchStates,
    val options: List<SwitchOption>,
    val switchBackgroundColor: String,
    val pillBackgroundColor: String,
    val safeZoneBackgroundColor: String,
    val default: String
): Parcelable {

    @Parcelize
    data class Text(
        val textColor: String,
        val weight: String,
        val text: String
    ) :Parcelable

    @Parcelize
    data class SwitchOption(
        val id: String,
        val name: String
    ): Parcelable

    @Parcelize
    data class SwitchStates(
        val checkedState: State,
        val uncheckedState: State
    ): Parcelable {

        @Parcelize
        data class State(
            val textColor: String,
            val backgroundColor: String,
            val weight: String
        ): Parcelable
    }

}