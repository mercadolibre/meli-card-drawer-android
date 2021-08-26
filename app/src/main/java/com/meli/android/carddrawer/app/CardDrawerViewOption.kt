package com.meli.android.carddrawer.app

import com.meli.android.carddrawer.model.CardDrawerView

data class CardDrawerViewOption(val name: String, val view: CardDrawerView) {
    override fun toString() = name
}
