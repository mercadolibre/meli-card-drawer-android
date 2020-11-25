package com.meli.android.carddrawer.configuration

import android.content.Context
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.CardAnimationType
import com.meli.android.carddrawer.model.CardUI

open class DefaultCardConfiguration(context: Context) : CardUI {
    private val context = context.applicationContext

    override fun getCardNumberPattern(): IntArray {
        return intArrayOf(DEFAULT_NUMBER, DEFAULT_NUMBER, DEFAULT_NUMBER, DEFAULT_NUMBER)
    }

    override fun getNamePlaceHolder() = context.getString(R.string.card_drawer_card_hint_name).orEmpty()

    override fun getExpirationPlaceHolder() = context.getString(R.string.card_drawer_card_hint_date).orEmpty()

    override fun getFontType() = FontType.DARK_TYPE

    override fun getAnimationType() = CardAnimationType.RIGHT_BOTTOM

    override fun getBankImageRes() = 0

    override fun getCardLogoImageRes() = 0

    override fun getSecurityCodeLocation() = SecurityCodeLocation.BACK

    override fun getCardFontColor() = ContextCompat.getColor(context, R.color.card_drawer_card_default_font_color)

    override fun getCardBackgroundColor() = ContextCompat.getColor(context, R.color.card_drawer_card_default_color)

    override fun getSecurityCodePattern() = 4

    companion object {
        private const val DEFAULT_NUMBER = 4
    }
}
