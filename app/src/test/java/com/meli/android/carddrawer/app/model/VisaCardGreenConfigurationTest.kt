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

class VisaCardGreenConfigurationTest: BaseTest() {

    private lateinit var visaCardGreenConfiguration: VisaCardGreenConfiguration

    override fun setUp() {
        super.setUp()
        visaCardGreenConfiguration = VisaCardGreenConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern then return array with size 4`() {
        Assert.assertEquals(visaCardGreenConfiguration.cardNumberPattern.size, 4)
    }

    @Test
    fun `when getting card number pattern then return array with values 4, 4, 4, 4`() {
        Assert.assertEquals(visaCardGreenConfiguration.cardNumberPattern[0], 4)
        Assert.assertEquals(visaCardGreenConfiguration.cardNumberPattern[1], 4)
        Assert.assertEquals(visaCardGreenConfiguration.cardNumberPattern[2], 4)
        Assert.assertEquals(visaCardGreenConfiguration.cardNumberPattern[3], 4)
    }

    @Test
    fun `when getting name placeholder then return card_drawer_card_hint_name`() {
        Assert.assertEquals(visaCardGreenConfiguration.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return card_drawer_card_hint_date`() {
        Assert.assertEquals(visaCardGreenConfiguration.expirationPlaceHolder, contextMock.getString(
            R.string.card_drawer_card_hint_date))
    }

    @Test
    fun `when getting fontType then return FontType LIGHT_TYPE`() {
        Assert.assertEquals(visaCardGreenConfiguration.fontType, FontType.LIGHT_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType LEFT_TOP`() {
        Assert.assertEquals(visaCardGreenConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting bank image res then return 0`() {
        Assert.assertEquals(visaCardGreenConfiguration.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image res then return 0`() {
        Assert.assertEquals(visaCardGreenConfiguration.cardLogoImageRes, 0)
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation FRONT`() {
        Assert.assertEquals(visaCardGreenConfiguration.securityCodeLocation, SecurityCodeLocation.FRONT)
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(visaCardGreenConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(visaCardGreenConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 3`() {
        Assert.assertEquals(visaCardGreenConfiguration.securityCodePattern, 3)
    }

    @Test
    fun `when setting card logo image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardGreenConfiguration.setCardLogoImage(imageView)
        verify {
            imageView.setImageResource(R.drawable.card_drawer_app_card_logo_visa)
        }
    }

    @Test
    fun `when setting bank image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardGreenConfiguration.setBankImage(imageView)
        verify {
            imageView.setImageResource(R.drawable.card_drawer_app_bank_logo_galicia)
        }
    }

}