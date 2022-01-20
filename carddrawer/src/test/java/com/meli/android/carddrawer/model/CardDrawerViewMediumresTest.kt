package com.meli.android.carddrawer.model

import android.widget.TextView
import com.meli.android.carddrawer.R
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class CardDrawerViewMediumresTest : CardDrawerViewUnitTest() {

    @Before
    override fun setUp() {
        cardDrawerView = CardDrawerViewMediumres(context)
    }

    override fun `when set padding from attributes then should fill paddingTop and paddingBottom`() {
        val expectedPadding = 23
        val attr = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.card_header_internal_padding, "23dp")
                .build()
        cardDrawerView = CardDrawerViewMediumres(context, attr)
        Assert.assertEquals(expectedPadding.toLong(), cardDrawerView.paddingTop.toLong())
        Assert.assertEquals(expectedPadding.toLong(), cardDrawerView.paddingBottom.toLong())
    }

    override fun `when init view then initialize fields with values`() {
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "issuerLogoView"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardLogoView"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "codeFront"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "codeBack"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardNumber"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardName"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardDate"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "cardAnimator"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "source"))
        Assert.assertNotNull(ReflectionHelpers.getField(cardDrawerView, "card"))
    }

    override fun `when update card information then set numbers with format`() {
        val card = mockk<Card>(relaxed = true)
        every { card.name } returns "Juan Perez"
        every { card.number } returns "12346666"
        every { card.expiration } returns "10/19"
        every { card.secCode } returns "555"
        ReflectionHelpers.setField(cardDrawerView, "card", card)
        cardDrawerView.updateCardInformation()
        val cardNumber = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardNumber")
        val cardName = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardName")
        val cardDate = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardDate")
        val codeFront = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeFront")
        val codeBack = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeBack")
        Assert.assertEquals("1234  6666  ****  ****", cardNumber.text.toString())
        Assert.assertEquals("Juan Perez", cardName.text.toString())
        Assert.assertEquals("10/19", cardDate.text.toString())
        Assert.assertEquals("555*", codeFront.text.toString())
        Assert.assertEquals("555*", codeBack.text.toString())
    }

    override fun `when update card information without values then set default values`() {
        val card = mockk<Card>(relaxed = true)
        every { card.name } returns ""
        every { card.number } returns ""
        every { card.expiration } returns ""
        every { card.secCode } returns ""
        ReflectionHelpers.setField(cardDrawerView, "card", card)
        cardDrawerView.updateCardInformation()
        val cardNumber = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardNumber")
        val cardName = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardName")
        val cardDate = ReflectionHelpers.getField<TextView>(cardDrawerView, "cardDate")
        val codeFront = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeFront")
        val codeBack = ReflectionHelpers.getField<TextView>(cardDrawerView, "codeBack")
        Assert.assertEquals("****  ****  ****  ****", cardNumber.text.toString())
        Assert.assertEquals("Nombre y Apellido", cardName.text.toString())
        Assert.assertEquals("MM/AA", cardDate.text.toString())
        Assert.assertEquals("****", codeFront.text.toString())
        Assert.assertEquals("****", codeBack.text.toString())
    }
}