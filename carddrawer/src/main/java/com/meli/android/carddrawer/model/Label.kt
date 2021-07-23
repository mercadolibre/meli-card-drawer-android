package com.meli.android.carddrawer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Label @JvmOverloads constructor(
    val text: String,
    val backgroundColor: String? = null,
    val color: String? = null,
    val weight: String? = null,
    val animate: Boolean = true
) : Parcelable