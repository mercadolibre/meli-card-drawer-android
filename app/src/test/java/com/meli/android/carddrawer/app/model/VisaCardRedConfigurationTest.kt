package com.meli.android.carddrawer.app.model

import android.graphics.Typeface
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

class VisaCardRedConfigurationTest: BaseTest() {

    private lateinit var visaCardRedConfiguration: VisaCardRedConfiguration

    override fun setUp() {
        super.setUp()
        visaCardRedConfiguration = VisaCardRedConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern then return array with size 4`() {
        Assert.assertEquals(visaCardRedConfiguration.cardNumberPattern.size, 4)
    }

    @Test
    fun `when getting card number pattern then return array with values 4, 4, 4, 4`() {
        Assert.assertEquals(visaCardRedConfiguration.cardNumberPattern[0], 4)
        Assert.assertEquals(visaCardRedConfiguration.cardNumberPattern[1], 4)
        Assert.assertEquals(visaCardRedConfiguration.cardNumberPattern[2], 4)
        Assert.assertEquals(visaCardRedConfiguration.cardNumberPattern[3], 4)
    }

    @Test
    fun `when getting name placeholder then return card_drawer_card_hint_name`() {
        Assert.assertEquals(visaCardRedConfiguration.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return card_drawer_card_hint_date`() {
        Assert.assertEquals(visaCardRedConfiguration.expirationPlaceHolder, contextMock.getString(
            R.string.card_drawer_card_hint_date))
    }

    @Test
    fun `when getting fontType then return FontType LIGHT_TYPE`() {
        Assert.assertEquals(visaCardRedConfiguration.fontType, FontType.LIGHT_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType LEFT_TOP`() {
        Assert.assertEquals(visaCardRedConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting bank image res then return card_drawer_app_bank_logo_galicia`() {
        Assert.assertEquals(visaCardRedConfiguration.bankImageRes, R.drawable.card_drawer_app_bank_logo_galicia)
    }

    @Test
    fun `when getting card logo image res then return card_drawer_app_card_logo_visa`() {
        Assert.assertEquals(visaCardRedConfiguration.cardLogoImageRes, R.drawable.card_drawer_app_card_logo_visa)
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation FRONT`() {
        Assert.assertEquals(visaCardRedConfiguration.securityCodeLocation, SecurityCodeLocation.FRONT)
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(visaCardRedConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(visaCardRedConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 4`() {
        Assert.assertEquals(visaCardRedConfiguration.securityCodePattern, 4)
    }

    @Test
    fun `when setting card logo image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardRedConfiguration.setCardLogoImage(imageView)
        verify {
            imageView.setImageResource(any())
        }
    }

    @Test
    fun `when setting bank image then call setImageResource`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardRedConfiguration.setBankImage(imageView)
        verify {
            imageView.setImageResource(any())
        }
    }

    @Test
    fun `when getting custom font then return Typeface SERIF`() {
        Assert.assertEquals(visaCardRedConfiguration.customFont, Typeface.SERIF)
    }

}