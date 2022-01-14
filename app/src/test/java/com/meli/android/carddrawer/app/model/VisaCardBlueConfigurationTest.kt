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

class VisaCardBlueConfigurationTest: BaseTest() {

    private lateinit var visaCardBlueConfiguration: VisaCardBlueConfiguration

    override fun setUp() {
        super.setUp()
        visaCardBlueConfiguration = VisaCardBlueConfiguration(contextMock)
    }

    @Test
    fun `when getting card number pattern then return array with size 4`() {
        Assert.assertEquals(visaCardBlueConfiguration.cardNumberPattern.size, 4)
    }

    @Test
    fun `when getting card number pattern then return array with values 4, 4, 4, 4`() {
        Assert.assertEquals(visaCardBlueConfiguration.cardNumberPattern[0], 4)
        Assert.assertEquals(visaCardBlueConfiguration.cardNumberPattern[1], 4)
        Assert.assertEquals(visaCardBlueConfiguration.cardNumberPattern[2], 4)
        Assert.assertEquals(visaCardBlueConfiguration.cardNumberPattern[3], 4)
    }

    @Test
    fun `when getting name placeholder then return card_drawer_card_hint_name`() {
        Assert.assertEquals(visaCardBlueConfiguration.namePlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_name))
    }

    @Test
    fun `when getting expiration placeholder then return card_drawer_card_hint_date`() {
        Assert.assertEquals(visaCardBlueConfiguration.expirationPlaceHolder, contextMock.getString(R.string.card_drawer_card_hint_date))
    }

    @Test
    fun `when getting fontType then return FontType LIGHT_TYPE`() {
        Assert.assertEquals(visaCardBlueConfiguration.fontType, FontType.LIGHT_TYPE)
    }

    @Test
    fun `when getting animationType then return CardAnimationType LEFT_TOP`() {
        Assert.assertEquals(visaCardBlueConfiguration.animationType, CardAnimationType.LEFT_TOP)
    }

    @Test
    fun `when getting bank image res then return 0`() {
        Assert.assertEquals(visaCardBlueConfiguration.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image res then return 0`() {
        Assert.assertEquals(visaCardBlueConfiguration.cardLogoImageRes, 0)
    }

    @Test
    fun `when getting security code location then return SecurityCodeLocation BACK`() {
        Assert.assertEquals(visaCardBlueConfiguration.securityCodeLocation, SecurityCodeLocation.BACK)
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(visaCardBlueConfiguration.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(visaCardBlueConfiguration.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting security code pattern then return 3`() {
        Assert.assertEquals(visaCardBlueConfiguration.securityCodePattern, 3)
    }

    @Test
    fun `when setting card logo image then call setImageDrawable`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardBlueConfiguration.setCardLogoImage(imageView)
        verify {
            imageView.setImageDrawable(any())
        }
    }

    @Test
    fun `when setting bank image then call setImageDrawable`() {
        val imageView = mockk<ImageView>(relaxed = true)
        visaCardBlueConfiguration.setBankImage(imageView)
        verify {
            imageView.setImageDrawable(any())
        }
    }

    @Test
    fun `when getting card gradient colors then return size 3`() {
        val result = visaCardBlueConfiguration.cardGradientColors
        Assert.assertEquals(result?.size, 3)
    }

    @Test
    fun `when getting card gradient colors then return list of colors`() {
        val result: MutableList<String>? = visaCardBlueConfiguration.cardGradientColors
        result?.let { list ->
            Assert.assertTrue(list.contains("#50ffffff"))
            Assert.assertTrue(list.contains("#90ff00ff"))
            Assert.assertTrue(list.contains("#00ffffff"))
        }
    }


}