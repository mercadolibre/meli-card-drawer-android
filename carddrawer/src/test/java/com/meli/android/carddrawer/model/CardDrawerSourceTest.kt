package com.meli.android.carddrawer.model

import android.widget.ImageView
import com.meli.android.carddrawer.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardDrawerSourceTest: BaseTest() {

    private lateinit var genericPaymentMethod: GenericPaymentMethod
    private lateinit var paymentCard: PaymentCard

    @MockK
    private lateinit var cardDrawerSource: CardDrawerSource

    private lateinit var text: GenericPaymentMethod.Text

    private lateinit var tag: CardDrawerSource.Tag

    private val backgroundColor by lazy { 0 }
    private val title by lazy { text }
    private val imageUrl by lazy { "url test" }
    private val subtitle by lazy { text }
    private val cardUI by lazy { mockk<CardUI>(relaxed = true) }

    private val textValue by lazy { "Text" }
    private val color by lazy { 0 }
    private val weight by lazy { "24dp" }

    @Before
    override fun setUp() {
        super.setUp()
        text = returnText()
        tag = returnTag()
        genericPaymentMethod = returnGenericPaymentMethod()
    }

    private fun returnText(): GenericPaymentMethod.Text {
        return GenericPaymentMethod.Text(
            text = textValue,
            color = color
        )
    }

    private fun returnTag(): CardDrawerSource.Tag {
        return CardDrawerSource.Tag(
            text = textValue,
            backgroundColor = backgroundColor,
            textColor = color,
            weight = weight
        )
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
    fun `when getting text by GenericPaymentMethod Text then return value that was set`() {
        Assert.assertEquals(text.text, textValue)
    }

    @Test
    fun `when getting color by GenericPaymentMethod Text then return value that was set`() {
        Assert.assertEquals(text.color, color)
    }

    @Test
    fun `when getting animationType of PaymentCard with CardUI and tag in constructor then return animationType of CardUI`() {
        val cardUI = mockk<CardUI>(relaxed = true)
        paymentCard = PaymentCard(cardUI, tag)
        Assert.assertEquals(paymentCard.animationType, cardUI.animationType)
    }

    @Test
    fun `when getting disabledBackgroundColor of PaymentCard with CardUI and tag in constructor then return disabledColor of CardUI`() {
        val cardUI = mockk<CardUI>(relaxed = true)
        paymentCard = PaymentCard(cardUI, tag)
        Assert.assertEquals(paymentCard.disabledBackgroundColor, cardUI.disabledColor)
    }


    @Test
    fun `when getting backgroundColor of PaymentCard with CardUI and tag in constructor then return cardBackgroundColor of CardUI`() {
        paymentCard = PaymentCard(cardUI, tag)
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

    @Test
    fun `when getting disabled background color of CardDrawerSource then return null`() {
        every { cardDrawerSource.disabledBackgroundColor } answers { callOriginal() }
        Assert.assertEquals(cardDrawerSource.disabledBackgroundColor, null)
    }

    @Test
    fun `when getting animation type of CardDrawerSource then return null`() {
        every { cardDrawerSource.animationType } answers { callOriginal() }
        Assert.assertEquals(cardDrawerSource.animationType, null)
    }

    @Test
    fun `when setting payment method image of CardDrawerSource then setting Card Logo Image`() {
        val imageView = mockk<ImageView>()
        every { cardDrawerSource.setPaymentMethodImage(imageView) } answers { callOriginal() }
        cardDrawerSource.setPaymentMethodImage(imageView)
        verify(exactly = 1) {
            cardDrawerSource.setPaymentMethodImage(imageView)
        }
    }

}
