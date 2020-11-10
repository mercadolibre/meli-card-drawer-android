package com.meli.android.carddrawer.configuration

import android.graphics.Color
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.CardAnimationType
import com.meli.android.carddrawer.model.CardUI

internal class AccountMoneyHybridConfiguration : CardUI {
    override fun getBankImageRes() = 0
    override fun getCardLogoImageRes() = R.drawable.card_drawer_hybrid_logo
    override fun getSecurityCodeLocation() = SecurityCodeLocation.NONE
    override fun getCardFontColor() = 0
    override fun getCardBackgroundColor() = Color.parseColor("#101820")
    override fun getSecurityCodePattern() = 0
    override fun getCardNumberPattern() = intArrayOf()
    override fun getNamePlaceHolder() = ""
    override fun getExpirationPlaceHolder() = ""
    override fun getFontType() = FontType.NONE
    override fun getAnimationType() = CardAnimationType.NONE
    override fun getCardGradientColors() = listOf("#00000000")
    override fun getStyle() = CardDrawerStyle.ACCOUNT_MONEY_HYBRID
}
