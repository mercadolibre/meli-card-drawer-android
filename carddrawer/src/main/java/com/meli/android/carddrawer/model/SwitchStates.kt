package com.meli.android.carddrawer.model

import android.os.Parcelable
import com.meli.android.carddrawer.model.State
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SwitchStates(
    val checkedState: State,
    val uncheckedState: State,
    val disabledState: State
): Parcelable