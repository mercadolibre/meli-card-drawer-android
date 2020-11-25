package com.meli.android.carddrawer.app.model

import android.graphics.Color
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.model.CardAnimationType
import com.meli.android.carddrawer.model.CardUI
import com.meli.android.carddrawer.app.R as R2

internal class HybridCreditConfiguration : CardUI {
    override fun getBankImageRes() = R.drawable.card_drawer_hybrid_logo
    override fun getCardLogoImageRes() = R2.drawable.card_drawer_mla_pm_visa_white
    override fun getSecurityCodeLocation() = SecurityCodeLocation.NONE
    override fun getCardFontColor() = Color.WHITE
    override fun getCardBackgroundColor() = Color.parseColor("#101820")
    override fun getSecurityCodePattern() = 0
    override fun getCardNumberPattern() = intArrayOf(4, 4, 4, 4)
    override fun getNamePlaceHolder() = "NAME AND SURNAME"
    override fun getExpirationPlaceHolder() = "MM/AA"
    override fun getFontType() = FontType.LIGHT_TYPE
    override fun getAnimationType() = CardAnimationType.LEFT_TOP
}
