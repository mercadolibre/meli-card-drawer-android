package com.meli.android.carddrawer.configuration

import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.CardAnimationType
import com.meli.android.carddrawer.model.CardUI

internal class AccountMoneyLegacyConfiguration : CardUI {
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
    override fun getAnimationType() = CardAnimationType.NONE
    override fun getCardGradientColors() = listOf("#009ee2", "#aedef8")
    override fun getStyle() = CardDrawerStyle.ACCOUNT_MONEY_LEGACY
}