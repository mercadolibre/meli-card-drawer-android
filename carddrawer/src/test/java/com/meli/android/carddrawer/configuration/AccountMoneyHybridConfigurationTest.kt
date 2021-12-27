package com.meli.android.carddrawer.configuration

import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AccountMoneyHybridConfigurationTest {

    private lateinit var accountMoneyHybridConfiguration: AccountMoneyHybridConfiguration

    @Before
    fun setUp() {
        accountMoneyHybridConfiguration = AccountMoneyHybridConfiguration()
    }

    @Test
    fun `when getting bank image res then it should return 0`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image rest then it should return drawable card_drawer_hybrid_logo`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardLogoImageRes, R.drawable.card_drawer_hybrid_logo)
    }

    @Test
    fun `when getting security code location then it should return SecurityCodeLocation NONE`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.securityCodeLocation, SecurityCodeLocation.NONE)
    }

    @Test
    fun `when getting card font color then it should return 0`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then it should return 0`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then it should return 0`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.securityCodePattern, 0)
    }

    @Test
    fun `when getting card number pattern then it should return 0`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardNumberPattern.size.toLong(), 0)
    }

    @Test
    fun `when getting name placeholder then it should return empty string`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.namePlaceHolder, "")
    }

    @Test
    fun `when getting expiration placeholder then it should return empty string`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.expirationPlaceHolder, "")
    }

    @Test
    fun `when getting fontType then it should return FontType NONE`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.fontType, FontType.NONE)
    }

    @Test
    fun `when getting animationType then it should return CardAnimation NONE`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.animationType, CardAnimationType.NONE)
    }

    @Test
    fun `when getting card gradient colors then it should return listOf colors`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.cardGradientColors, listOf("#00000000"))
    }

    @Test
    fun `when getting style then it should return CardDrawerStyle ACCOUNT_MONEY_DEFAULT`() {
        Assert.assertEquals(accountMoneyHybridConfiguration.style, CardDrawerStyle.ACCOUNT_MONEY_HYBRID)
    }

}
