package com.meli.android.carddrawer.configuration

import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccountMoneyHybridConfigurationTest {

    private lateinit var accountMoneyHybridConfiguration: AccountMoneyHybridConfiguration

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        accountMoneyHybridConfiguration = AccountMoneyHybridConfiguration()
    }

    @Test
    fun `should return BankImageRes`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.bankImageRes, 0)
    }

    @Test
    fun `should return CardLogoImageRes`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardLogoImageRes, R.drawable.card_drawer_hybrid_logo)
    }

    @Test
    fun `should return SecurityCodeLocation`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.securityCodeLocation, SecurityCodeLocation.NONE)
    }

    @Test
    fun `should return CardFontColor`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardFontColor, 0)
    }

    @Test
    fun `should return CardBackgroundColor`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `should return SecurityCodePattern`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.securityCodePattern, 0)
    }

    @Test
    fun `should return CardNumberPattern`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardNumberPattern.size.toLong(), 0)
    }

    @Test
    fun `should return NamePlaceHolder`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.namePlaceHolder, "")
    }

    @Test
    fun `should return ExpirationPlaceHolder`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.expirationPlaceHolder, "")
    }

    @Test
    fun `should return FontType`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.fontType, FontType.NONE)
    }

    @Test
    fun `should return AnimationType`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.animationType, CardAnimationType.NONE)
    }

    @Test
    fun `should return CardGradientColors`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardGradientColors, listOf("#00000000"))
    }

    @Test
    fun `should return Style`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.style, CardDrawerStyle.ACCOUNT_MONEY_HYBRID)
    }

}
