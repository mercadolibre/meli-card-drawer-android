package com.meli.android.carddrawer.model

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.android.parcel.Parcelize

sealed class CardDrawerSource(open val tag: Tag? = null) {
    abstract val backgroundColor: Int
    open val disabledBackgroundColor: Int?
        get() = null
    open val animationType: String
        get() = CardAnimationType.NONE
    open fun setPaymentMethodImage(paymentMethod: ImageView) = Unit

    @Parcelize
    data class Tag(val text: String, val backgroundColor: Int, val textColor: Int, val weight : String) : Parcelable
}

@Parcelize
open class GenericPaymentMethod(
    override val backgroundColor: Int,
    val title: Text,
    val imageUrl: String? = null,
    val subtitle: Text? = null,
    override val tag : Tag? = null
) : CardDrawerSource(tag), Parcelable {

    @Parcelize
    data class Text(val text: String, val color: Int): Parcelable
}

@Parcelize
class PaymentCard(val cardUI: CardUI, override val tag : Tag? = null) : CardDrawerSource(tag), Parcelable {
    constructor(cardUI: CardUI) : this(cardUI, null)

    override val backgroundColor: Int
        get() = cardUI.cardBackgroundColor
    override val disabledBackgroundColor: Int?
        get() = cardUI.disabledColor
    override val animationType: String
        get() = cardUI.animationType
    override fun setPaymentMethodImage(paymentMethod: ImageView) = cardUI.setCardLogoImage(paymentMethod)
}
