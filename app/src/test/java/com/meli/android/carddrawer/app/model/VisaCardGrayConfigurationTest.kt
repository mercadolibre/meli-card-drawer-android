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

class VisaCardGrayConfigurationTest: BaseTest() {

    private lateinit var visaCardGrayConfigurationTest: VisaCardGrayConfiguration

    override fun setUp() {
        super.setUp()
        visaCardGrayConfigurationTest = VisaCardGrayConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern then return array with size 4`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.cardNumberPattern.size, 4)
    }

    @Test
    fun `when getting card number pattern then return array with values 4, 4, 4, 4`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.cardNumberPattern[0], 4)
        Assert.assertEquals(visaCardGrayConfigurationTest.cardNumberPattern[1], 4)
        Assert.assertEquals(visaCardGrayConfigurationTest.cardNumberPattern[2], 4)
        Assert.assertEquals(visaCardGrayConfigurationTest.cardNumberPattern[3], 4)
    }

    @Test
    fun `when getting name placeholder then return card_drawer_card_hint_name`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return card_drawer_card_hint_date`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.expirationPlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_date))
    }

    @Test
    fun `when getting fontType then return FontType DARK_TYPE`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.fontType, FontType.DARK_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType LEFT_TOP`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting bank image res then return card_drawer_mla_issuer_160_white`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.bankImageRes, R.drawable.card_drawer_mla_issuer_160_white)
    }

    @Test
    fun `when getting card logo image res then return card_drawer_mla_pm_debvisa_white`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.cardLogoImageRes, R.drawable.card_drawer_mla_pm_debvisa_white)
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation FRONT`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.securityCodeLocation, SecurityCodeLocation.FRONT)
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 3`() {
        Assert.assertEquals(visaCardGrayConfigurationTest.securityCodePattern, 3)
    }

    @Test
    fun `when setting card logo image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardGrayConfigurationTest.setCardLogoImage(imageView)
        verify {
            imageView.setImageResource(any())
        }
    }

    @Test
    fun `when setting bank image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardGrayConfigurationTest.setBankImage(imageView)
        verify {
            imageView.setImageResource(any())
        }
    }

}