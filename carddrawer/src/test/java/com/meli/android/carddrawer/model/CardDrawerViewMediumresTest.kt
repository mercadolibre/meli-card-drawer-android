package com.meli.android.carddrawer.model

import android.widget.TextView
import com.meli.android.carddrawer.R
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

/**
 * Test for the card header.. many visual and animations changes can't be tested with just junit :(
 */
@RunWith(RobolectricTestRunner::class)
class CardDrawerViewMediumresTest : CardDrawerViewTest() {
    @Before
    override fun doBefore() {
        header = CardDrawerViewMediumres(context)
    }

    override fun init_setsPaddingFromAttributes() {
        val expectedPadding = 23
        val attr = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.card_header_internal_padding, "23dp")
                .build()
        header = CardDrawerViewMediumres(context, attr)
        Assert.assertEquals(expectedPadding.toLong(), header.paddingTop.toLong())
        Assert.assertEquals(expectedPadding.toLong(), header.paddingBottom.toLong())
    }

    override fun init_loadsViews() {
        Assert.assertNotNull(ReflectionHelpers.getField(header, "issuerLogoView"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "cardLogoView"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "codeFront"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "codeBack"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "cardNumber"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "cardName"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "cardDate"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "cardAnimator"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "source"))
        Assert.assertNotNull(ReflectionHelpers.getField(header, "card"))
    }

    override fun updateCardInformation_setsNumbersWithFormat() {
        val card = Mockito.mock(Card::class.java)
        Mockito.`when`(card.name).thenReturn("Juan Perez")
        Mockito.`when`(card.number).thenReturn("12346666")
        Mockito.`when`(card.expiration).thenReturn("10/19")
        Mockito.`when`(card.secCode).thenReturn("555")
        ReflectionHelpers.setField(header, "card", card)
        header.updateCardInformation()
        val cardNumber = ReflectionHelpers.getField<TextView>(header, "cardNumber")
        val cardName = ReflectionHelpers.getField<TextView>(header, "cardName")
        val cardDate = ReflectionHelpers.getField<TextView>(header, "cardDate")
        val codeFront = ReflectionHelpers.getField<TextView>(header, "codeFront")
        val codeBack = ReflectionHelpers.getField<TextView>(header, "codeBack")
        Assert.assertEquals("1234  6666  ****  ****", cardNumber.text.toString())
        Assert.assertEquals("Juan Perez", cardName.text.toString())
        Assert.assertEquals("10/19", cardDate.text.toString())
        Assert.assertEquals("555*", codeFront.text.toString())
        Assert.assertEquals("555*", codeBack.text.toString())
    }

    override fun updateCardInformation_cardWithoutValuesSetsDefaultValues() {
        val card = Mockito.mock(Card::class.java)
        Mockito.`when`(card.name).thenReturn("")
        Mockito.`when`(card.number).thenReturn("")
        Mockito.`when`(card.expiration).thenReturn("")
        Mockito.`when`(card.secCode).thenReturn("")
        ReflectionHelpers.setField(header, "card", card)
        header.updateCardInformation()
        val cardNumber = ReflectionHelpers.getField<TextView>(header, "cardNumber")
        val cardName = ReflectionHelpers.getField<TextView>(header, "cardName")
        val cardDate = ReflectionHelpers.getField<TextView>(header, "cardDate")
        val codeFront = ReflectionHelpers.getField<TextView>(header, "codeFront")
        val codeBack = ReflectionHelpers.getField<TextView>(header, "codeBack")
        Assert.assertEquals("****  ****  ****  ****", cardNumber.text.toString())
        Assert.assertEquals("Nombre y Apellido", cardName.text.toString())
        Assert.assertEquals("MM/AA", cardDate.text.toString())
        Assert.assertEquals("****", codeFront.text.toString())
        Assert.assertEquals("****", codeBack.text.toString())
    }
}