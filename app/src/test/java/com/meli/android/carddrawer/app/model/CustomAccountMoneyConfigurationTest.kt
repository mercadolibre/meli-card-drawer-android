package com.meli.android.carddrawer.app.model

import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CustomAccountMoneyConfigurationTest {

    private lateinit var customAccountMoneyConfiguration: CustomAccountMoneyConfiguration

    @Before
    fun setUp() {
        customAccountMoneyConfiguration = CustomAccountMoneyConfiguration()
    }

    @Test
    fun `when getting bank image res then return 0`() {
        Assert.assertEquals(customAccountMoneyConfiguration.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image res then return drawable card_drawer_account_money_logo`() {
        Assert.assertEquals(
            customAccountMoneyConfiguration.cardLogoImageRes,
            R.drawable.card_drawer_account_money_logo
        )
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation NONE`() {
        Assert.assertEquals(
            customAccountMoneyConfiguration.securityCodeLocation,
            SecurityCodeLocation.NONE
        )
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(customAccountMoneyConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(customAccountMoneyConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 0`() {
        Assert.assertEquals(customAccountMoneyConfiguration.securityCodePattern, 0)
    }

    @Test
    fun `when getting card number pattern then return 0`() {
        Assert.assertEquals(customAccountMoneyConfiguration.cardNumberPattern.size.toLong(), 0)
    }

    @Test
    fun `when getting name placeholder then return empty string`() {
        Assert.assertEquals(customAccountMoneyConfiguration.namePlaceHolder, "")
    }

    @Test
    fun `when getting expiration placeholder then return empty string`() {
        Assert.assertEquals(customAccountMoneyConfiguration.expirationPlaceHolder, "")
    }

    @Test
    fun `when getting fontType then return FontType NONE`() {
        Assert.assertEquals(customAccountMoneyConfiguration.fontType, FontType.NONE)
    }

    @Test
    fun `when getting animationType then return CardAnimation LEFT_TOP`() {
        Assert.assertEquals(customAccountMoneyConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting card gradient colors then return listOf colors`() {
        Assert.assertEquals(
            customAccountMoneyConfiguration.cardGradientColors,
            listOf("#ae3ebd", "#c5f8ae")
        )
    }

    @Test
    fun `when getting style then return CardDrawerStyle ACCOUNT_MONEY_DEFAULT`() {
        Assert.assertEquals(
            customAccountMoneyConfiguration.style,
            CardDrawerStyle.ACCOUNT_MONEY_DEFAULT
        )
    }

}