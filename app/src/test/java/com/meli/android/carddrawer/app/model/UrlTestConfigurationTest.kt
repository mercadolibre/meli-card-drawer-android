package com.meli.android.carddrawer.app.model

import com.meli.android.carddrawer.app.BaseTest
import com.meli.android.carddrawer.app.R
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert
import org.junit.Test

class UrlTestConfigurationTest: BaseTest() {

    private lateinit var urlTestConfiguration: UrlTestConfiguration

    override fun setUp() {
        super.setUp()
        urlTestConfiguration = UrlTestConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern then return array with size 4`() {
        Assert.assertEquals(urlTestConfiguration.cardNumberPattern.size, 4)
    }

    @Test
    fun `when getting card number pattern then return array with values 4, 4, 4, 4`() {
        Assert.assertEquals(urlTestConfiguration.cardNumberPattern[0], 4)
        Assert.assertEquals(urlTestConfiguration.cardNumberPattern[1], 4)
        Assert.assertEquals(urlTestConfiguration.cardNumberPattern[2], 4)
        Assert.assertEquals(urlTestConfiguration.cardNumberPattern[3], 4)
    }

    @Test
    fun `when getting name placeholder then return card_drawer_card_hint_name`() {
        Assert.assertEquals(urlTestConfiguration.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return card_drawer_card_hint_date`() {
        Assert.assertEquals(urlTestConfiguration.expirationPlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_date))
    }

    @Test
    fun `when getting fontType then return FontType LIGHT_TYPE`() {
        Assert.assertEquals(urlTestConfiguration.fontType, FontType.LIGHT_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType LEFT_TOP`() {
        Assert.assertEquals(urlTestConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting bank image res then return 0`() {
        Assert.assertEquals(urlTestConfiguration.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image res then return 0`() {
        Assert.assertEquals(urlTestConfiguration.cardLogoImageRes, 0)
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation BACK`() {
        Assert.assertEquals(urlTestConfiguration.securityCodeLocation, SecurityCodeLocation.BACK)
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(urlTestConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(urlTestConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 3`() {
        Assert.assertEquals(urlTestConfiguration.securityCodePattern, 3)
    }

    @Test
    fun `when getting card logo image url then return link 'imgur'`() {
        Assert.assertEquals(urlTestConfiguration.cardLogoImageUrl, "https://i.imgur.com/GaTELY9.png")
    }

    @Test
    fun `when getting bank image url then return link 'imgur'`() {
        Assert.assertEquals(urlTestConfiguration.bankImageUrl, "https://i.imgur.com/df3Bc7K.png")
    }

}