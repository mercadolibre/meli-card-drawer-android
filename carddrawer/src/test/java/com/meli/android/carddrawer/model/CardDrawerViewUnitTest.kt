package com.meli.android.carddrawer.model

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.meli.android.carddrawer.BasicRobolectricTest
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.TestUtils.initTypefaceSetter
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration
import com.meli.android.carddrawer.configuration.FieldPosition
import com.meli.android.carddrawer.configuration.FontType
import com.meli.android.carddrawer.configuration.SecurityCodeLocation
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
open class CardDrawerViewUnitTest : BasicRobolectricTest() {

    protected lateinit var cardDrawerView: CardDrawerView

    @Before
    open fun setUp() {
        initTypefaceSetter()
        cardDrawerView = CardDrawerView(context)
    }

    private fun getTestTag(): CardDrawerSource.Tag {
        return CardDrawerSource.Tag(
            "Novo",
            Color.parseColor("#CCCCCC"),
            Color.parseColor("#FFFFFF"),
            "regular"
        )
    }

    @Test
    open fun `when set padding from attributes then shoud fill paddingTop and paddingBottom`() {
        val expectedPadding = 23
        val attr = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.card_header_internal_padding, "23dp")
            .build()
        cardDrawerView = CardDrawerView(context, attr)
        Assert.assertEquals(expectedPadding.toLong(), cardDrawerView.paddingTop.toLong())
        Assert.assertEquals(expectedPadding.toLong(), cardDrawerView.paddingBottom.toLong())
    }

    @Test
    open fun `when init view then initialize fields with values`() {
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

    @Test
    open fun `when update card information then set numbers with format`() {
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

    @Test
    open fun `when update card information without values then set default values`() {
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

    @Test
    fun `when call function show then updates information, set colors and call animator`() {
        val spyCardDrawerView = spyk(cardDrawerView, recordPrivateCalls = true)
        val cardUI: CardUI = DefaultCardConfiguration(context)
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        every { spyCardDrawerView.updateCardInformation() } answers { nothing }
        ReflectionHelpers.setField(spyCardDrawerView, "cardAnimator", cardAnimatorMock)
        spyCardDrawerView.show(cardUI)
        verify {
            cardAnimatorMock.colorCard(any(), any())
        }
        verify {
            spyCardDrawerView.updateCardInformation()
        }
    }

    @Test
    fun `when call function show then call animator`() {
        val spuCardDrawerView = spyk(cardDrawerView, recordPrivateCalls = true)
        val cardUI: CardUI = DefaultCardConfiguration(context)
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        ReflectionHelpers.setField(spuCardDrawerView, "cardAnimator", cardAnimatorMock)
        ReflectionHelpers.setField(spuCardDrawerView, "source", PaymentCard(cardUI))
        spuCardDrawerView.show()
        verify {
            cardAnimatorMock.switchView(FieldPosition.POSITION_FRONT)
        }
        verify {
            spuCardDrawerView.hideSecCircle()
        }
    }

    @Test
    fun `when call function securityCode with front location then show sec code circle and call animator`() {
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.securityCodeLocation } returns SecurityCodeLocation.FRONT
        every { cardUI.animationType } returns SecurityCodeLocation.NONE
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        val codeFront = ReflectionHelpers.getField<View>(cardDrawerView, "codeFront")
        val codeFrontRedCircle = ReflectionHelpers.getField<View>(cardDrawerView, "codeFrontRedCircle")
        ReflectionHelpers.setField(cardDrawerView, "cardAnimator", cardAnimatorMock)
        ReflectionHelpers.setField(cardDrawerView, "source", PaymentCard(cardUI))
        cardDrawerView.showSecurityCode()
        verify {
            cardAnimatorMock.switchView(FieldPosition.POSITION_FRONT)
        }
        Assert.assertEquals(View.VISIBLE.toLong(), codeFront.visibility.toLong())
        Assert.assertEquals(View.VISIBLE.toLong(), codeFrontRedCircle.visibility.toLong())
    }

    @Test
    fun `when call function securityCode with back location then show sec code circle and call animator`() {
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.securityCodeLocation } returns SecurityCodeLocation.BACK
        every { cardUI.animationType } returns CardAnimationType.NONE
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        val codeBack = ReflectionHelpers.getField<View>(cardDrawerView, "codeBack")
        ReflectionHelpers.setField(cardDrawerView, "cardAnimator", cardAnimatorMock)
        ReflectionHelpers.setField(cardDrawerView, "source", PaymentCard(cardUI))
        cardDrawerView.showSecurityCode()
        verify {
            cardAnimatorMock.switchView(FieldPosition.POSITION_BACK)
        }
        Assert.assertEquals(View.VISIBLE.toLong(), codeBack.visibility.toLong())
    }

    @Test
    fun `when call function secCode with front position then call SwitchView with front position`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val codeFront = GradientTextView(context)
        codeFront.visibility = View.INVISIBLE
        val codeBack = TextView(context)
        codeBack.visibility = View.INVISIBLE
        ReflectionHelpers.setField(spyCardDrawerView, "codeFront", codeFront)
        ReflectionHelpers.setField(spyCardDrawerView, "codeBack", codeBack)
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.securityCodeLocation } returns SecurityCodeLocation.FRONT
        every { cardUI.cardLogoImageRes } returns 0
        every { cardUI.bankImageRes } returns 0
        every { cardUI.animationType } returns CardAnimationType.NONE
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        ReflectionHelpers.setField(spyCardDrawerView, "cardAnimator", cardAnimatorMock)
        every { spyCardDrawerView.update(any()) } answers { nothing }
        spyCardDrawerView.showSecurityCode(cardUI)
        verify {
            cardAnimatorMock.switchViewWithoutAnimation(FieldPosition.POSITION_FRONT)
        }
        verify {
            spyCardDrawerView.update(any())
        }
        verify {
            spyCardDrawerView.showSecCircle()
        }
        Assert.assertEquals(View.VISIBLE.toLong(), codeFront.visibility.toLong())
    }

    @Test
    fun `when call function secCode with back position then call SwitchView with back position`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.securityCodeLocation } returns SecurityCodeLocation.BACK
        every { cardUI.animationType } returns CardAnimationType.NONE
        every { spyCardDrawerView.update(any())} answers { nothing }
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        val codeBack = ReflectionHelpers.getField<View>(spyCardDrawerView, "codeBack")
        ReflectionHelpers.setField(spyCardDrawerView, "cardAnimator", cardAnimatorMock)
        spyCardDrawerView.showSecurityCode(cardUI)
        verify {
            cardAnimatorMock.switchViewWithoutAnimation(FieldPosition.POSITION_BACK)
        }
        Assert.assertEquals(View.VISIBLE.toLong(), codeBack.visibility.toLong())
    }

    @Test
    fun `when call function showBack then calls SwitchView without animation with back position`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val codeFront = GradientTextView(context)
        codeFront.visibility = View.INVISIBLE
        val codeBack = TextView(context)
        codeBack.visibility = View.INVISIBLE
        ReflectionHelpers.setField(spyCardDrawerView, "codeFront", codeFront)
        ReflectionHelpers.setField(spyCardDrawerView, "codeBack", codeBack)
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        ReflectionHelpers.setField(spyCardDrawerView, "cardAnimator", cardAnimatorMock)
        spyCardDrawerView.showBack()
        verify {
            cardAnimatorMock.switchViewWithoutAnimation(FieldPosition.POSITION_BACK)
        }
    }

    @Test
    fun `when call function hideSecCircle with front position then hide sec code`() {
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.securityCodeLocation } returns SecurityCodeLocation.FRONT
        every { cardUI.animationType } returns CardAnimationType.NONE
        val codeFront = ReflectionHelpers.getField<View>(cardDrawerView, "codeFront")
        val codeFrontRedCircle = ReflectionHelpers.getField<View>(cardDrawerView, "codeFrontRedCircle")
        ReflectionHelpers.setField(cardDrawerView, "source", PaymentCard(cardUI))
        cardDrawerView.hideSecCircle()
        Assert.assertEquals(View.VISIBLE.toLong(), codeFront.visibility.toLong())
        Assert.assertEquals(View.INVISIBLE.toLong(), codeFrontRedCircle.visibility.toLong())
    }

    @Test
    fun `when call function hideSecCircle with back position then hide sec code`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val cardAnimatorMock = mockk<CardAnimator>(relaxed = true)
        val codeFront = GradientTextView(context)
        codeFront.visibility = View.VISIBLE
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.securityCodeLocation } returns SecurityCodeLocation.BACK
        every { cardUI.animationType } returns CardAnimationType.NONE
        ReflectionHelpers.setField(spyCardDrawerView, "source", PaymentCard(cardUI))
        ReflectionHelpers.setField(spyCardDrawerView, "cardAnimator", cardAnimatorMock)
        ReflectionHelpers.setField(spyCardDrawerView, "codeFront", codeFront)
        spyCardDrawerView.hideSecCircle()
        Assert.assertEquals(View.GONE.toLong(), codeFront.visibility.toLong())
    }

    @Test
    open fun `when initialize the card then should check if the fields are filled`() {
        val spyCardDrawerView = spyk(cardDrawerView, recordPrivateCalls = true)
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
        spyCardDrawerView.setCardTextColor(cardUI, fontType, color)
        verify {
            cardNumber.init(
                any(),
                "****  ****  ****  ****",
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

    @Test
    fun `when update issuer logo then set image logo`() {
        val issuerLogoView = mockk<ImageSwitcher>(relaxed = true)
        val bankImageView = mockk<ImageView>(relaxed = true)
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.bankImageRes } returns 3
        every { issuerLogoView.nextView } returns bankImageView
        val spyCardDrawerView = spyk(cardDrawerView)
        spyCardDrawerView.updateIssuerLogo(issuerLogoView, cardUI, false)
        verify {
            bankImageView.setImageResource(3)
        }
        verify {
            cardUI.setBankImage(bankImageView)
        }
    }

    @Test
    fun `when update card image then sets logo`() {
        val cardImageSwitcher = mockk<ImageSwitcher>(relaxed = true)
        val cardImageView = mockk<ImageView>(relaxed = true)
        val cardUI = mockk<CardUI>(relaxed = true)
        every { cardUI.cardLogoImageRes } returns 3
        every { cardImageSwitcher.nextView } returns cardImageView
        val spyHeader = spyk(cardDrawerView)
        spyHeader.updateCardLogo(cardImageSwitcher, cardUI, false)
        verify {
            cardImageView.setImageResource(3)
        }
        verify {
            cardUI.setCardLogoImage(cardImageView)
        }
    }

    @Test
    fun `when call function updateOverlay then call setOverlayImage`() {
        val overlayImageView = mockk<ImageView>(relaxed = true)
        val source = mockk<CardUI>(relaxed = true)
        val spyHeader = spyk(cardDrawerView)
        spyHeader.updateOverlay(overlayImageView, source)
        verify {
            source.setOverlayImage(overlayImageView)
        }
        verify {
            overlayImageView wasNot Called
        }
    }

    @Test
    fun `when call function show when GenericPaymentMethod has no tag then not show tag`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val genericMethod = GenericPaymentMethod(
            0,
            GenericPaymentMethod.Text("Test", Color.parseColor("#000000")),
            null, null, null
        )
        spyCardDrawerView.show(genericMethod)
        Assert.assertEquals(
            View.GONE.toLong(),
            spyCardDrawerView.genericFrontLayout.findViewById<View>(R.id.card_tag_container).visibility.toLong()
        )
    }

    @Test
    fun `when call function show tag when GenericPagmentMethod has tag then show tag`() {
        val tag: CardDrawerSource.Tag = getTestTag()
        val spyCardDrawerView = spyk(cardDrawerView)
        val genericMethod = GenericPaymentMethod(
            0,
            GenericPaymentMethod.Text("Test", Color.parseColor("#000000")),
            null,
            null,
            tag
        )
        spyCardDrawerView.show(genericMethod)
        Assert.assertEquals(
            View.VISIBLE.toLong(),
            spyCardDrawerView.genericFrontLayout.findViewById<View>(R.id.card_tag_container).visibility.toLong()
        )
    }

    @Test
    fun `when call function show when PaymentCard has tag then show tag`() {
        val tag: CardDrawerSource.Tag = getTestTag()
        val spyCardDrawerView = spyk(cardDrawerView)
        val cardUI: CardUI = DefaultCardConfiguration(context)
        val paymentCard = PaymentCard(cardUI, tag)
        spyCardDrawerView.show(paymentCard)
        Assert.assertEquals(
            View.VISIBLE.toLong(),
            spyCardDrawerView.cardFrontLayout.findViewById<View>(R.id.card_tag_container).visibility.toLong()
        )
    }

    @Test
    fun `when call function show when PaymentCard has no tag then not show tag`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val cardUI: CardUI = DefaultCardConfiguration(context)
        val paymentCard = PaymentCard(cardUI)
        spyCardDrawerView.show(paymentCard)
        Assert.assertEquals(
            View.GONE.toLong(),
            spyCardDrawerView.cardFrontLayout.findViewById<View>(R.id.card_tag_container).visibility.toLong()
        )
    }

    @Test
    fun `when call show then set test and colors from tag for GenericPaymentMethod`() {
        val tag: CardDrawerSource.Tag = getTestTag()
        val spyCardDrawerView = spyk(cardDrawerView)
        val genericMethod = GenericPaymentMethod(
            0,
            GenericPaymentMethod.Text("Test", Color.parseColor("#000000")),
            null,
            null,
            tag
        )
        spyCardDrawerView.show(genericMethod)
        val textView: AppCompatTextView = spyCardDrawerView.genericFrontLayout.findViewById(R.id.card_tag)
        Assert.assertEquals(tag.text, textView.text)
        Assert.assertEquals(tag.textColor.toLong(), textView.currentTextColor.toLong())
        Assert.assertEquals(
            PorterDuffColorFilter(tag.backgroundColor, PorterDuff.Mode.SRC_ATOP),
            textView.background.colorFilter
        )
    }

    @Test
    fun `when call function showBottomLabel then change visibility to VISIBLE`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val bottomLabel = ReflectionHelpers.getField<BottomLabel>(spyCardDrawerView, "bottomLabel")
        spyCardDrawerView.showBottomLabel()
        verify {
            spyCardDrawerView.showBottomLabel()
        }
        Assert.assertEquals(View.VISIBLE.toLong(), bottomLabel.visibility.toLong())
    }

    @Test
    fun `when call function hideBottomLabel then change visibility to INVISIBLE`() {
        val spyCardDrawerView = spyk(cardDrawerView)
        val bottomLabel = ReflectionHelpers.getField<BottomLabel>(spyCardDrawerView, "bottomLabel")
        spyCardDrawerView.hideBottomLabel()
        verify {
            spyCardDrawerView.hideBottomLabel()
        }
        Assert.assertEquals(View.INVISIBLE.toLong(), bottomLabel.visibility.toLong())
    }

}