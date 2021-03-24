package com.meli.android.carddrawer.model.customview

import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.model.CardDrawerView

data class CustomViewConfiguration(
    @CardDrawerView.Type val cardDrawerType: Int,
    val cardDrawerStyle: CardDrawerStyle?
)