package com.meli.android.carddrawer.configuration

import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AccountMoneyDefaultConfigurationTest {

    private lateinit var accountMoneyDefaultConfiguration: AccountMoneyDefaultConfiguration

    @Before
    fun setUp() {
        accountMoneyDefaultConfiguration = AccountMoneyDefaultConfiguration()
    }

    @Test
    fun `when getting bank image res then it should return 0`() {
        assertEquals(accountMoneyDefaultConfiguration.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image rest then it should return drawable card_drawer_account_money_logo`() {
        assertEquals(accountMoneyDefaultConfiguration.cardLogoImageRes, R.drawable.card_drawer_account_money_logo)
    }

    @Test
    fun `when getting security code location then it should return SecurityCodeLocation NONE`() {
        assertEquals(accountMoneyDefaultConfiguration.securityCodeLocation, SecurityCodeLocation.NONE)
    }

    @Test
    fun `when getting card font color then it should return 0`() {
        assertEquals(accountMoneyDefaultConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then should it return 0`() {
        assertEquals(accountMoneyDefaultConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then should it return 0`() {
        assertEquals(accountMoneyDefaultConfiguration.securityCodePattern, 0)
    }

    @Test
    fun `when getting card number pattern then it should return 0`() {
        assertEquals(accountMoneyDefaultConfiguration.cardNumberPattern.size.toLong(), 0)
    }

    @Test
    fun `when getting name placeholder then it should return empty string`() {
        assertEquals(accountMoneyDefaultConfiguration.namePlaceHolder, "")
    }

    @Test
    fun `when getting expiration placeholder then it should return empty string`() {
        assertEquals(accountMoneyDefaultConfiguration.expirationPlaceHolder, "")
    }

    @Test
    fun `when getting fontType then it should return FontType NONE`() {
        assertEquals(accountMoneyDefaultConfiguration.fontType, FontType.NONE)
    }

    @Test
    fun `when getting animationType then it should return CardAnimation NONE`() {
        assertEquals(accountMoneyDefaultConfiguration.animationType, CardAnimationType.NONE)
    }

    @Test
    fun `when getting card gradient colors then it should return listOf colors`() {
        assertEquals(accountMoneyDefaultConfiguration.cardGradientColors, listOf("#00000000", "#aaffffff"))
    }

    @Test
    fun `when getting style then it should return CardDrawerStyle ACCOUNT_MONEY_DEFAULT`() {
        assertEquals(accountMoneyDefaultConfiguration.style, CardDrawerStyle.ACCOUNT_MONEY_DEFAULT)
    }

}