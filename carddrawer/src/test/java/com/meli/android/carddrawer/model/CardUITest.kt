package com.meli.android.carddrawer.model

import android.graphics.Typeface
import android.widget.ImageView
import com.meli.android.carddrawer.configuration.CardDrawerStyle
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardUITest {

    @MockK
    private lateinit var cardUI: CardUI

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `when getting bank image url then return empty`() {
        Assert.assertEquals(cardUI.bankImageUrl, "")
    }

    @Test
    fun `when getting card logo image url then return empty`() {
        Assert.assertEquals(cardUI.cardLogoImageUrl, "")
    }

    @Test
    fun `when getting bank image res then return 0`() {
        Assert.assertEquals(cardUI.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image res then return 0`() {
        Assert.assertEquals(cardUI.cardLogoImageRes, 0)
    }

    @Test
    fun `when setting card logo image then call function setCardLogoImage one time`() {
        val imageView = mockk<ImageView>()
        cardUI.setCardLogoImage(imageView)
        verify (exactly = 1) {
            cardUI.setCardLogoImage(imageView)
        }
    }

    @Test
    fun `when set ets bank image to the Imageview then call function setBankImage one time`() {
        val imageView = mockk<ImageView>()
        cardUI.setBankImage(imageView)
        verify (exactly = 1) {
            cardUI.setBankImage(imageView)
        }
    }

    @Test
    fun `when set overlay image to the Imageview then call function setOverlayImage one time`() {
        val imageView = mockk<ImageView>()
        cardUI.setOverlayImage(imageView)
        verify (exactly = 1) {
            cardUI.setOverlayImage(imageView)
        }
    }

    @Test
    fun `when getting security code location then return empty string`() {
        Assert.assertEquals(cardUI.securityCodeLocation, "")
    }

    @Test
    fun `when getting card font color then return 0`() {
        Assert.assertEquals(cardUI.cardFontColor, 0)
    }

    @Test
    fun `when getting card background color then return 0`() {
        Assert.assertEquals(cardUI.cardBackgroundColor, 0)
    }

    @Test
    fun `when getting card gradient colors then return size 0`() {
        Assert.assertEquals(cardUI.cardGradientColors?.size, 0)
    }

    @Test
    fun `when getting card gradient colors then return type list cardUI`() {
        assert(cardUI.cardGradientColors is List<*>)
    }

    @Test
    fun `when getting security code pattern then return 0`() {
        Assert.assertEquals(cardUI.securityCodePattern, 0)
    }

    @Test
    fun `when getting card number pattern then return size 0`() {
        Assert.assertEquals(cardUI.cardNumberPattern.size, 0)
    }

    @Test
    fun `when getting card number pattern then return IntArray`() {
        assert(cardUI.cardNumberPattern is IntArray)
    }

    @Test
    fun `when getting name placeholder then return empty string`() {
        Assert.assertEquals(cardUI.namePlaceHolder, "")
    }

    @Test
    fun `when getting expiration date place holder then return empty string`() {
        Assert.assertEquals(cardUI.expirationPlaceHolder, "")
    }

    @Test
    fun `when getting font type then return empty string`() {
        Assert.assertEquals(cardUI.fontType, "")
    }

    @Test
    fun `when getting animation type then return empty string`() {
        Assert.assertEquals(cardUI.animationType, "")
    }

    @Test
    fun `when getting custom font then return type Typeface`() {
        assert(cardUI.customFont is Typeface)
    }

    @Test
    fun `when getting style then return type CardDrawerStyle`() {
        assert(cardUI.style is CardDrawerStyle)
    }

    @Test
    fun `when getting disabledColor then return 0`() {
        Assert.assertEquals(cardUI.disabledColor,0)
    }

}