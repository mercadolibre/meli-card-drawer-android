package com.meli.android.carddrawer.app.model

import android.widget.ImageView
import com.meli.android.carddrawer.app.BaseTest
import com.meli.android.carddrawer.app.R
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import com.meli.android.carddrawer.model.CardAnimationType
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class VisaCardYellowConfigurationTest: BaseTest() {

    private lateinit var visaCardYellowConfiguration: VisaCardYellowConfiguration

    override fun setUp() {
        super.setUp()
        visaCardYellowConfiguration = VisaCardYellowConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern then return array with size 4`() {
        Assert.assertEquals(visaCardYellowConfiguration.cardNumberPattern.size, 4)
    }

    @Test
    fun `when getting card number pattern then return array with values 4, 4, 4, 4`() {
        Assert.assertEquals(visaCardYellowConfiguration.cardNumberPattern[0], 4)
        Assert.assertEquals(visaCardYellowConfiguration.cardNumberPattern[1], 4)
        Assert.assertEquals(visaCardYellowConfiguration.cardNumberPattern[2], 4)
        Assert.assertEquals(visaCardYellowConfiguration.cardNumberPattern[3], 4)
    }

    @Test
    fun `when getting name placeholder then return card_drawer_card_hint_name`() {
        Assert.assertEquals(visaCardYellowConfiguration.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return card_drawer_card_hint_date`() {
        Assert.assertEquals(visaCardYellowConfiguration.expirationPlaceHolder, contextMock.getString(
            R.string.card_drawer_card_hint_date))
    }

    @Test
    fun `when getting fontType then return FontType NONE`() {
        Assert.assertEquals(visaCardYellowConfiguration.fontType, FontType.NONE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType LEFT_TOP`() {
        Assert.assertEquals(visaCardYellowConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting bank image res then return card_drawer_mla_issuer_test01_white`() {
        Assert.assertEquals(visaCardYellowConfiguration.bankImageRes, R.drawable.card_drawer_mla_issuer_test01_white)
    }

    @Test
    fun `when getting card logo image res then return card_drawer_mla_pm_visa_white`() {
        Assert.assertEquals(visaCardYellowConfiguration.cardLogoImageRes, R.drawable.card_drawer_mla_pm_visa_white)
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation FRONT`() {
        Assert.assertEquals(visaCardYellowConfiguration.securityCodeLocation, SecurityCodeLocation.FRONT)
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(visaCardYellowConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(visaCardYellowConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 3`() {
        Assert.assertEquals(visaCardYellowConfiguration.securityCodePattern, 3)
    }

    @Test
    fun `when setting card logo image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardYellowConfiguration.setCardLogoImage(imageView)
        verify {
            imageView.setImageResource(any())
        }
    }

    @Test
    fun `when setting bank image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardYellowConfiguration.setBankImage(imageView)
        verify {
            imageView.setImageResource(any())
        }
    }

}