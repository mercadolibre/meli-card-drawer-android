package com.meli.android.carddrawer.model

import android.widget.ImageView
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardDrawerSourceTest {

    private lateinit var genericPaymentMethod: GenericPaymentMethod
    private lateinit var paymentCard: PaymentCard

    @MockK
    private lateinit var mockText: GenericPaymentMethod.Text

    @MockK
    private lateinit var mockTag: CardDrawerSource.Tag

    private val backgroundColor by lazy { 0 }
    private val title by lazy { mockText }
    private val imageUrl by lazy { "url test" }
    private val subtitle by lazy { mockText }
    private val tag by lazy { mockTag }
    private val cardUI by lazy { mockk<CardUI>(relaxed = true) }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        genericPaymentMethod = returnGenericPaymentMethod()
    }

    private fun returnGenericPaymentMethod(): GenericPaymentMethod {
        return GenericPaymentMethod(
            backgroundColor = backgroundColor,
            title = title,
            imageUrl = imageUrl,
            subtitle = subtitle,
            tag = tag
        )
    }

    @Test
    fun `when getting backgroundColor of GenericPaymentMethod then return value that was set`() {
        Assert.assertEquals(genericPaymentMethod.backgroundColor, backgroundColor)
    }

    @Test
    fun `when getting title of GenericPaymentMethod then return value that was set`() {
        Assert.assertEquals(genericPaymentMethod.title, title)
    }

    @Test
    fun `when getting imageUrl of GenericPaymentMethod then return value that was set`() {
        Assert.assertEquals(genericPaymentMethod.imageUrl, imageUrl)
    }

    @Test
    fun `when getting subtitle of GenericPaymentMethod then return value that was set`() {
        Assert.assertEquals(genericPaymentMethod.subtitle, subtitle)
    }

    @Test
    fun `when getting tag of GenericPaymentMethod then return value that was set`() {
        Assert.assertEquals(genericPaymentMethod.tag, tag)
    }

    @Test
    fun `when getting animationType of PaymentCard with CardUI and tag in constructor then return animationType of CardUI`() {
        val cardUI = mockk<CardUI>(relaxed = true)
        paymentCard = PaymentCard(cardUI, mockTag)
        Assert.assertEquals(paymentCard.animationType, cardUI.animationType)
    }

    @Test
    fun `when getting disabledBackgroundColor of PaymentCard with CardUI and tag in constructor then return disabledColor of CardUI`() {
        val cardUI = mockk<CardUI>(relaxed = true)
        paymentCard = PaymentCard(cardUI, mockTag)
        Assert.assertEquals(paymentCard.disabledBackgroundColor, cardUI.disabledColor)
    }


    @Test
    fun `when getting backgroundColor of PaymentCard with CardUI and tag in constructor then return cardBackgroundColor of CardUI`() {
        paymentCard = PaymentCard(cardUI, mockTag)
        Assert.assertEquals(paymentCard.backgroundColor, cardUI.cardBackgroundColor)
    }

    @Test
    fun `when getting animationType of PaymentCard with CardUI in constructor then return animationType of CardUI`() {
        paymentCard = PaymentCard(cardUI)
        Assert.assertEquals(paymentCard.animationType, cardUI.animationType)
    }

    @Test
    fun `when getting disabledBackgroundColor of PaymentCard with CardUI in constructor then return disabledColor of CardUI`() {
        paymentCard = PaymentCard(cardUI)
        Assert.assertEquals(paymentCard.disabledBackgroundColor, cardUI.disabledColor)
    }

    @Test
    fun `when getting backgroundColor of PaymentCard with CardUI in constructor then return cardBackgroundColor of CardUI`() {
        paymentCard = PaymentCard(cardUI)
        Assert.assertEquals(paymentCard.backgroundColor, cardUI.cardBackgroundColor)
    }

    @Test
    fun `when setting payment method image then setting Card Logo Image`() {
        val imageView = mockk<ImageView>()
        paymentCard = PaymentCard(cardUI)
        paymentCard.setPaymentMethodImage(imageView)
        verify(exactly = 1) {
            cardUI.setCardLogoImage(imageView)
        }
    }

}