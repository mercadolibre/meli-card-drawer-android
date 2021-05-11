package com.meli.android.carddrawer.app

import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.model.CardDrawerSource
import com.meli.android.carddrawer.model.CardUI
import com.meli.android.carddrawer.model.GenericPaymentMethod
import com.meli.android.carddrawer.model.PaymentCard

class CardConfigurationOption private constructor(private val cardName: String, val cardConfiguration: CardDrawerSource?,
    val cardStyle: CardDrawerStyle?) {

    constructor(cardName: String, cardConfiguration: CardUI): this(cardName, PaymentCard(cardConfiguration), null)
    constructor(cardName: String, genericPayment: GenericPaymentMethod): this(cardName, genericPayment, null)
    constructor(cardName: String, cardStyle: CardDrawerStyle): this(cardName, null, cardStyle)

    override fun toString(): String {
        return cardName
    }
}
