package com.meli.android.carddrawer.model

import android.widget.ImageView
import com.meli.android.carddrawer.BaseTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardUITest: BaseTest() {

    private lateinit var cardUI: CardUI

    @Before
    override fun setUp() {
        super.setUp()
        cardUI = mockkClass(CardUI::class)
        every { cardUI.bankImageUrl } answers { callOriginal() }
        every { cardUI.cardLogoImageUrl } answers { callOriginal() }
        every { cardUI.bankImageRes } returns 0
        every { cardUI.cardLogoImageRes } returns 0
        every { cardUI.securityCodeLocation } returns ""
        every { cardUI.cardFontColor } returns 0
        every { cardUI.cardBackgroundColor } returns 0
        every { cardUI.cardGradientColors } answers { callOriginal() }
        every { cardUI.securityCodePattern } returns 0
        every { cardUI.cardNumberPattern } returns intArrayOf()
        every { cardUI.namePlaceHolder } returns ""
        every { cardUI.expirationPlaceHolder } answers { callOriginal() }
        every { cardUI.fontType } answers { callOriginal() }
        every { cardUI.animationType } returns ""
        every { cardUI.customFont } answers { callOriginal() }
        every { cardUI.style } answers { callOriginal() }
        every { cardUI.disabledColor } answers { callOriginal() }
    }

    @Test
    fun `when getting bank image url then return null`() {
        Assert.assertNull(cardUI.bankImageUrl)
    }

    @Test
    fun `when getting card logo image url then return null`() {
        Assert.assertNull(cardUI.cardLogoImageUrl)
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
        every { cardUI.setCardLogoImage(imageView) } answers { callOriginal() }
        cardUI.setCardLogoImage(imageView)
        verify(exactly = 1) {
            cardUI.setCardLogoImage(imageView)
        }
    }

    @Test
    fun `when setting bank image to the Imageview then call function setBankImage one time`() {
        val imageView = mockk<ImageView>()
        every { cardUI.setBankImage(imageView) } answers { callOriginal() }
        cardUI.setBankImage(imageView)
        verify(exactly = 1) {
            cardUI.setBankImage(imageView)
        }
    }

    @Test
    fun `when set overlay image to the Imageview then call function setOverlayImage one time`() {
        val imageView = mockk<ImageView>()
        every { cardUI.setOverlayImage(imageView) } answers { callOriginal() }
        cardUI.setOverlayImage(imageView)
        verify(exactly = 1) {
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
    fun `when getting card gradient colors then return null 0`() {
        Assert.assertNull(cardUI.cardGradientColors?.size)
    }

    @Test
    fun `when getting card gradient colors then return null`() {
        Assert.assertNull(cardUI.cardGradientColors)
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
    fun `when getting card number pattern then return type IntArray`() {
        assert(cardUI.cardNumberPattern is IntArray)
    }

    @Test
    fun `when getting name placeholder then return empty string`() {
        Assert.assertEquals(cardUI.namePlaceHolder, "")
    }

    @Test
    fun `when getting expiration date place holder then return null`() {
        Assert.assertNull(cardUI.expirationPlaceHolder)
    }

    @Test
    fun `when getting font type then return null`() {
        Assert.assertNull(cardUI.fontType)
    }

    @Test
    fun `when getting animation type then return empty string`() {
        Assert.assertEquals(cardUI.animationType, "")
    }

    @Test
    fun `when getting custom font then return null`() {
        Assert.assertNull(cardUI.customFont)
    }

    @Test
    fun `when getting style then return null`() {
        Assert.assertNull(cardUI.style)
    }

    @Test
    fun `when getting disabledColor then return null`() {
        Assert.assertNull(cardUI.disabledColor)
    }

}
