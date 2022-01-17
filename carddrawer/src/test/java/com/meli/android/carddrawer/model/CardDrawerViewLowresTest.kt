package com.meli.android.carddrawer.model

import android.widget.TextView
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration
import com.meli.android.carddrawer.configuration.FontType
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class CardDrawerViewLowresTest : CardDrawerViewUnitTest() {

    override fun setUp() {
        cardDrawerView = CardDrawerViewLowres(context)
    }

    override fun setCardTextColor_initViews() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val cardNumber = mockk<GradientTextView>(relaxed = true)
        val cardName = mockk<GradientTextView>(relaxed = true)
        val cardDate = mockk<GradientTextView>(relaxed = true)
        val codeFront = mockk<GradientTextView>(relaxed = true)
        ReflectionHelpers.setField(spyCardDrawerView, "cardNumber", cardNumber)
        ReflectionHelpers.setField(spyCardDrawerView, "cardName", cardName)
        ReflectionHelpers.setField(spyCardDrawerView, "cardDate", cardDate)
        ReflectionHelpers.setField(spyCardDrawerView, "codeFront", codeFront)
        val cardUI: CardUI = DefaultCardConfiguration(context)
        ReflectionHelpers.setField(spyCardDrawerView, "source", PaymentCard(cardUI))
        val fontType = FontType.LIGHT_TYPE
        val color = 2
        spyCardDrawerView.setCardTextColor(fontType, color)
        verify {
            cardNumber.init(
                any(),
                "**** ****",
                color
            )
        }
        verify {
            cardName.init(
                any(),
                cardUI.namePlaceHolder,
                color
            )
        }
        verify {
            cardDate.init(
                any(),
                cardUI.expirationPlaceHolder,
                color
            )
        }
        verify {
            codeFront.init(
                any(),
                "****",
                color
            )
        }
    }

    override fun updateCardInformation_cardWithoutValuesSetsDefaultValues() {
        val card = mockk<Card>(relaxed = true)
        every { card.name } returns ""
        every { card.number } returns ""
        every { card.expiration } returns ""
        every { card.secCode } returns ""
        ReflectionHelpers.setField(cardDrawerView, "card", card)
        cardDrawerView.updateCardInformation()
        val cardNumber = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardNumber")
        val cardName = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardName")
        val codeFront = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeFront")
        val codeBack = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeBack")
        Assert.assertEquals("**** ****", cardNumber.text.toString())
        Assert.assertEquals("Nombre y Apellido", cardName.text.toString())
        Assert.assertEquals("****", codeFront.text.toString())
        Assert.assertEquals("****", codeBack.text.toString())
    }

    override fun updateCardInformation_setsNumbersWithFormat() {
        val card = mockk<Card>(relaxed = true)
        every { card.name } returns "Juan Perez"
        every { card.number } returns "000012346666"
        every { card.secCode } returns "555"
        ReflectionHelpers.setField(cardDrawerView, "card", card)
        cardDrawerView.updateCardInformation()
        val cardNumber = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardNumber")
        val cardName = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardName")
        val codeFront = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeFront")
        val codeBack = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeBack")
        Assert.assertEquals("6666 ****", cardNumber.text.toString())
        Assert.assertEquals("Juan Perez", cardName.text.toString())
        Assert.assertEquals("555*", codeFront.text.toString())
        Assert.assertEquals("555*", codeBack.text.toString())
    }

    override fun init_setsPaddingFromAttributes() {
        val expectedPadding = 23
        val attr = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.card_header_internal_padding, "23dp")
            .build()
        cardDrawerView = CardDrawerViewLowres(context, attr)
        Assert.assertEquals(expectedPadding, cardDrawerView.paddingTop)
        Assert.assertEquals(expectedPadding, cardDrawerView.paddingBottom)
    }

    override fun init_loadsViews() {
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardLogoView"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "codeFront"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "codeBack"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardNumber"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardName"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardAnimator"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "source"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "card"))
    }

}