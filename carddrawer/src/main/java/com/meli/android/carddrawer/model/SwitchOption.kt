package com.meli.android.carddrawer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SwitchOption(
    val id: String,
    val name: String
): Parcelable