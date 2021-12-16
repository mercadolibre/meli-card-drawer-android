package com.meli.android.carddrawer.configuration

import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccountMoneyDefaultConfigurationTest {

    private lateinit var accountMoneyDefaultConfiguration: AccountMoneyDefaultConfiguration

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        accountMoneyDefaultConfiguration = AccountMoneyDefaultConfiguration()
    }

    @Test
    fun `should return BankImageRes`() {
        assertEquals(accountMoneyDefaultConfiguration.bankImageRes, 0)
    }

    @Test
    fun `should return CardLogoImageRes`() {
        assertEquals(accountMoneyDefaultConfiguration.cardLogoImageRes, R.drawable.card_drawer_account_money_logo)
    }

    @Test
    fun `should return SecurityCodeLocation`() {
        assertEquals(accountMoneyDefaultConfiguration.securityCodeLocation, SecurityCodeLocation.NONE)
    }

    @Test
    fun `should return CardFontColor`() {
        assertEquals(accountMoneyDefaultConfiguration.cardFontColor, 0)
    }

    @Test
    fun `should return CardBackgroundColor`() {
        assertEquals(accountMoneyDefaultConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `should return SecurityCodePattern`() {
        assertEquals(accountMoneyDefaultConfiguration.securityCodePattern, 0)
    }

    @Test
    fun `should return CardNumberPattern`() {
        assertEquals(accountMoneyDefaultConfiguration.cardNumberPattern.size.toLong(), 0)
    }

    @Test
    fun `should return NamePlaceHolder`() {
        assertEquals(accountMoneyDefaultConfiguration.namePlaceHolder, "")
    }

    @Test
    fun `should return ExpirationPlaceHolder`() {
        assertEquals(accountMoneyDefaultConfiguration.expirationPlaceHolder, "")
    }

    @Test
    fun `should return FontType`() {
        assertEquals(accountMoneyDefaultConfiguration.fontType, FontType.NONE)
    }

    @Test
    fun `should return AnimationType`() {
        assertEquals(accountMoneyDefaultConfiguration.animationType, CardAnimationType.NONE)
    }

    @Test
    fun `should return CardGradientColors`() {
        assertEquals(accountMoneyDefaultConfiguration.cardGradientColors, listOf("#00000000", "#aaffffff"))
    }

    @Test
    fun `should return Style`() {
        assertEquals(accountMoneyDefaultConfiguration.style, CardDrawerStyle.ACCOUNT_MONEY_DEFAULT)
    }

}