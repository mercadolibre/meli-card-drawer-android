package com.meli.android.carddrawer.app.model

import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.model.CardAnimationType
import com.meli.android.carddrawer.model.CardUI

internal class CustomAccountMoneyConfiguration : CardUI {
    override fun getBankImageRes() = 0
    override fun getCardLogoImageRes() = R.drawable.card_drawer_account_money_logo
    override fun getSecurityCodeLocation() = SecurityCodeLocation.NONE
    override fun getCardFontColor() = 0
    override fun getCardBackgroundColor() = 0
    override fun getSecurityCodePattern() = 0
    override fun getCardNumberPattern() = IntArray(0)
    override fun getNamePlaceHolder() = ""
    override fun getExpirationPlaceHolder() = ""
    override fun getFontType() = FontType.NONE
    override fun getAnimationType() = CardAnimationType.LEFT_TOP
    override fun getCardGradientColors() = listOf("#ae3ebd", "#c5f8ae")
    override fun getStyle() = CardDrawerStyle.ACCOUNT_MONEY_LEGACY
}