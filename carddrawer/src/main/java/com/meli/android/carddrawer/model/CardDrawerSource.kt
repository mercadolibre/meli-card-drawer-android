package com.meli.android.carddrawer.model

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.android.parcel.Parcelize

sealed class CardDrawerSource {
    abstract val tag: Tag?
    abstract val backgroundColor: Int
    open val disabledBackgroundColor: Int? = null
    open val animationType: String = CardAnimationType.NONE
    open fun setPaymentMethodImage(paymentMethod: ImageView) = Unit

    @Parcelize
    data class Tag(val text: String, val backgroundColor: Int, val textColor: Int, val weight: String) : Parcelable
}

@Parcelize
open class GenericPaymentMethod @JvmOverloads constructor(
    override val backgroundColor: Int,
    val title: Text,
    val imageUrl: String? = null,
    val subtitle: Text? = null,
    override val tag : Tag? = null,
    val gradientColor: List<String>? = null,
    val description: Text? = null
) : CardDrawerSource(), Parcelable {

    @Parcelize
    data class Text @JvmOverloads constructor(val text: String, val color: Int, val weight: String? = null): Parcelable
}

class PaymentCard(val cardUI: CardUI, override val tag: Tag? = null) : CardDrawerSource() {
    constructor(cardUI: CardUI) : this(cardUI, null)

    override val backgroundColor: Int = cardUI.cardBackgroundColor
    override val disabledBackgroundColor: Int? = cardUI.disabledColor
    override val animationType: String = cardUI.animationType
    override fun setPaymentMethodImage(paymentMethod: ImageView) = cardUI.setCardLogoImage(paymentMethod)
}
