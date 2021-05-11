package com.meli.android.carddrawer.model

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.android.parcel.Parcelize

sealed class CardDrawerSource {
    abstract val backgroundColor: Int
    open val disabledBackgroundColor: Int?
        get() = null
    open val animationType: String
        get() = CardAnimationType.NONE
    open fun setPaymentMethodImage(paymentMethod: ImageView) = Unit
}

@Parcelize
open class GenericPaymentMethod(
    override val backgroundColor: Int,
    val title: Text,
    val imageUrl: String? = null,
    val subtitle: Text? = null
) : CardDrawerSource(), Parcelable {
    @Parcelize
    data class Text(val text: String, val color: Int): Parcelable
}

class PaymentCard(val cardUI: CardUI) : CardDrawerSource() {
    override val backgroundColor: Int
        get() = cardUI.cardBackgroundColor
    override val disabledBackgroundColor: Int?
        get() = cardUI.disabledColor
    override val animationType: String
        get() = cardUI.animationType
    override fun setPaymentMethodImage(paymentMethod: ImageView) = cardUI.setCardLogoImage(paymentMethod)
}
