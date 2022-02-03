package com.meli.android.carddrawer.app.model

import android.graphics.Color
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.model.CardAnimationType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import com.meli.android.carddrawer.app.R as R2

class HybridCreditConfigurationTest {

    private lateinit var hybridCreditConfiguration: HybridCreditConfiguration

    @Before
    fun setUp() {
        hybridCreditConfiguration = HybridCreditConfiguration()
    }

    @Test
    fun `when getting bank image res then return card_drawer_hybrid_logo`() {
        Assert.assertEquals(hybridCreditConfiguration.bankImageRes, R.drawable.card_drawer_hybrid_logo)
    }

    @Test
    fun `when getting card logo image res then return drawable card_drawer_mla_pm_visa_white`() {
        Assert.assertEquals(
            hybridCreditConfiguration.cardLogoImageRes,
            R2.drawable.card_drawer_mla_pm_visa_white
        )
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation NONE`() {
        Assert.assertEquals(
            hybridCreditConfiguration.securityCodeLocation,
            SecurityCodeLocation.NONE
        )
    }

    @Test
    fun `when getting card font color then return WHITE`() {
        Assert.assertEquals(hybridCreditConfiguration.cardFontColor, Color.WHITE)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(hybridCreditConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 0`() {
        Assert.assertEquals(hybridCreditConfiguration.securityCodePattern, 0)
    }

    @Test
    fun `when getting card number pattern size then return 4`() {
        Assert.assertEquals(hybridCreditConfiguration.cardNumberPattern.size.toLong(), 4)
    }

    @Test
    fun `when getting name placeholder then return 'NAME AND SURNAME'`() {
        Assert.assertEquals(hybridCreditConfiguration.namePlaceHolder, "NAME AND SURNAME")
    }

    @Test
    fun `when getting expiration placeholder then return MM-AA`() {
        Assert.assertEquals(hybridCreditConfiguration.expirationPlaceHolder, "MM/AA")
    }

    @Test
    fun `when getting fontType then return FontType LIGHT_TYPE`() {
        Assert.assertEquals(hybridCreditConfiguration.fontType, FontType.LIGHT_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimation LEFT_TOP`() {
        Assert.assertEquals(hybridCreditConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }
}