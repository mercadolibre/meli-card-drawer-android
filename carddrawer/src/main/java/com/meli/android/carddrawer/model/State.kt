package com.meli.android.carddrawer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class State(
    val textColor: String,
    val backgroundColor: String,
    val weight: String
): Parcelable