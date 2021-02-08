package com.meli.android.carddrawer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SwitchModel(
    val states: SwitchStates,
    val options: List<SwitchOption>,
    val backgroundColor: String,
    val default: String
): Parcelable
