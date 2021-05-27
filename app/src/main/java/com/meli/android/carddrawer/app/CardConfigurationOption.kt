package com.meli.android.carddrawer.app

import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.model.*

//TODO: Check if we can do a better thing with tags on styled cards, maybe we can put them
// on a custom CardDrawerSource?
class CardConfigurationOption private constructor(private val cardName: String, val cardConfiguration: CardDrawerSource?,
    val cardStyle: CardDrawerStyle?, val styledCardTag : CardDrawerSource.Tag? = null) {

    constructor(cardName: String, cardConfiguration: CardUI): this(cardName, PaymentCard(cardConfiguration), null)
    constructor(cardName: String, cardConfiguration: CardUI, tag : CardDrawerSource.Tag?) :
        this(cardName, PaymentCard(cardConfiguration, tag), null)
    constructor(cardName: String, genericPayment: GenericPaymentMethod): this(cardName, genericPayment, null)
    constructor(cardName: String, cardStyle: CardDrawerStyle): this(cardName, null, cardStyle)
    constructor(cardName: String, cardStyle: CardDrawerStyle, cardTag : CardDrawerSource.Tag? = null):
        this(cardName, null, cardStyle, cardTag)

    override fun toString(): String {
        return cardName
    }
}
