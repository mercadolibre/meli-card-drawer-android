package com.meli.android.carddrawer.app

import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.model.CardUI

class CardConfigurationOption private constructor(private val cardName: String, val cardConfiguration: CardUI?,
    val cardStyle: CardDrawerStyle?) {

    constructor(cardName: String, cardConfiguration: CardUI): this(cardName, cardConfiguration, null)
    constructor(cardName: String, cardStyle: CardDrawerStyle): this(cardName, null, cardStyle)

    override fun toString(): String {
        return cardName
    }
}