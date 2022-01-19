package com.meli.android.carddrawer.model

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.meli.android.carddrawer.BaseTest
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.TestUtils
import com.meli.android.carddrawer.TestUtils.getDeclaredField
import com.meli.android.carddrawer.configuration.FieldPosition
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class CardAnimatorTest : BaseTest() {

    private lateinit var cardAnimator: CardAnimator
    @MockK
    private lateinit var cardFrontLayoutMock: View
    @MockK
    private lateinit var cardBackLayoutMock: View

    private fun initialize() {
        cardAnimator = CardAnimator(contextMock, cardFrontLayoutMock, cardBackLayoutMock)
    }

    @Test
    fun `when getting cardBackLayout of CardAnimator then return value passed in the constructor`() {
        initialize()
        val cardBackLayout = cardAnimator.getDeclaredField("cardBackLayout")
        Assert.assertEquals(cardBackLayoutMock, cardBackLayout)
    }

    @Test
    fun `when getting cardFrontLayout of CardAnimator then return value passed in the constructor`() {
        initialize()
        val cardFrontLayout = cardAnimator.getDeclaredField("cardFrontLayout")
        Assert.assertEquals(cardFrontLayoutMock, cardFrontLayout)
    }

    @Test
    fun `when getting default card color of CardAnimator then return value not null`() {
        initialize()
        val defaultCardColor = cardAnimator.getDeclaredField("defaultCardColor")
        Assert.assertNotNull(defaultCardColor)
    }

    @Test
    fun `when getting context of CardAnimator then return value not null`() {
        initialize()
        val context = cardAnimator.getDeclaredField("context")
        Assert.assertNotNull(context)
    }

    @Test
    fun `when call function switchView with POSITION_FRONT per parameter then fill showingLayout with the same value`() {
        initialize()
        val showingLayout = cardAnimator.getDeclaredField("showingLayout")
        cardAnimator.switchView(FieldPosition.POSITION_FRONT)
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_FRONT)
    }

    @Test
    fun `when call function switchView with 3 per parameter then fill showingLayout with POSITION_FRONT`() {
        initialize()
        val showingLayout = cardAnimator.getDeclaredField("showingLayout")
        cardAnimator.switchView(3)
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_FRONT)
    }

    @Test
    fun `when call function switchView with POSITION_BACK per parameter then fill showingLayout with the same value`() {
        val mock = mockk<CardAnimator>(relaxed = true)
        every { mock.switchView(FieldPosition.POSITION_BACK) } answers { callOriginal() }
        mock.switchView(FieldPosition.POSITION_BACK)
        val showingLayout = mock.getDeclaredField("showingLayout")
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_BACK)
    }

    @Test
    fun `when call function switchViewWithoutAnimation with POSITION_FRONT per parameter then fill showingLayout with POSITION_FRONT`() {
        initialize()
        cardAnimator.switchViewWithoutAnimation(FieldPosition.POSITION_FRONT)
        val showingLayout = cardAnimator.getDeclaredField("showingLayout")
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_FRONT)
    }

    @Test
    fun `when call function switchViewWithoutAnimation with POSITION_BACK per parameter then fill showingLayout with POSITION_BACK`() {
        initialize()
        cardAnimator.switchViewWithoutAnimation(FieldPosition.POSITION_BACK)
        val showingLayout = cardAnimator.getDeclaredField("showingLayout")
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_BACK)
    }

    @Test
    fun `when call function switchViewWithoutAnimation with 3 per parameter then fill showingLayout with 3`() {
        initialize()
        cardAnimator.switchViewWithoutAnimation(3)
        val showingLayout = cardAnimator.getDeclaredField("showingLayout")
        Assert.assertEquals(showingLayout, 3)
    }

    @Test
    fun `when call function saveState then showingLayout in bundle`() {
        initialize()
        val bundle = mockk<Bundle>(relaxed = true)
        cardAnimator.saveState(bundle)
        val showingLayout: Int = cardAnimator.getDeclaredField("showingLayout") as Int
        verify(exactly = 1) {
            bundle.putInt("showing_side", showingLayout)
        }
    }

    @Test
    fun `when call function saveState then showingColor in bundle`() {
        initialize()
        val bundle = mockk<Bundle>(relaxed = true)
        cardAnimator.saveState(bundle)
        val showingColor: Int = cardAnimator.getDeclaredField("showingColor") as Int
        verify(exactly = 1) {
            bundle.putInt("showing_color", showingColor)
        }
    }

    @Test
    fun `when call function showFront then call flipCardFront`() {
        val mock = mockk<CardAnimator>(relaxed = true)
        every { mock.switchView(FieldPosition.POSITION_BACK) } answers { callOriginal() }
        mock.switchView(FieldPosition.POSITION_BACK)
        every { mock.showFront() } answers { callOriginal() }
        mock.showFront()
        verify(exactly = 1) {
            mock.flipCardFront()
        }
    }

    @Test
    fun `when call function showBack then call flipCardBack`() {
        val mock = mockk<CardAnimator>(relaxed = true)
        every { mock.switchView(FieldPosition.POSITION_FRONT) } answers { callOriginal() }
        mock.switchView(FieldPosition.POSITION_FRONT)
        every { mock.showBack() } answers { callOriginal() }
        mock.showBack()
        verify(exactly = 1) {
            mock.flipCardBack()
        }
    }

    @Test
    fun `when call function colorCard with POSITION_FRONT per parameter then call doColorCard with the same parameters`() {
        val mock = mockk<CardAnimator>(relaxed = true)
        every { mock.colorCard(FieldPosition.POSITION_FRONT, "") } answers { callOriginal() }
        mock.colorCard(FieldPosition.POSITION_FRONT, "")
        verify(exactly = 1) {
            mock.doColorCard(FieldPosition.POSITION_FRONT, "")
        }
    }

    @Test
    fun `when call function colorCard with 0 per parameter then not call doColorCard`() {
        initialize()
        cardAnimator.colorCard(0, "")
        verify(inverse = true) {
            cardAnimator.doColorCard(0, "")
        }
    }

    @Test
    fun `when set color front filters then set color filter in background and reveal`() {
        initialize()
        val mockFrontBackground = mockk<ImageView>(relaxed = true)
        val mockFrontReveal = mockk<ImageView>(relaxed = true)
        cardAnimator.setColorFrontFilters(0, mockFrontBackground, mockFrontReveal)
        verify {
            mockFrontBackground.setColorFilter(0, PorterDuff.Mode.SRC_ATOP)
        }
        verify {
            mockFrontBackground.setColorFilter(0, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Test
    fun `when call function restoreState with state per parameter then call switchViewWithoutAnimation`() {
        initialize()

        every { cardFrontLayoutMock.findViewById<ImageView>(R.id.cho_card_image_front) } returns mockk(relaxed = true)
        every { cardFrontLayoutMock.findViewById<ImageView>(R.id.cho_card_image_front_reveal) } returns mockk(relaxed = true)
        every { cardBackLayoutMock.findViewById<ImageView>(R.id.cho_card_image_back) } returns mockk(relaxed = true)
        every { cardBackLayoutMock.findViewById<ImageView>(R.id.cho_card_image_back_reveal) } returns mockk(relaxed = true)

        cardAnimator.restoreState(mockk(relaxed = true))
        verify {
            cardAnimator.switchViewWithoutAnimation(1)
        }
    }

    @Test
    fun `when call function animationColorChangeForPosition then set color front filters`() {
        initialize()
        val image = mockk<ImageView>(relaxed = true)
        cardAnimator.switchView(3)
        cardAnimator.animateColorChangeForPosition(image,
                                                    image,
                                                    image,
                                                    image,
                                                    0,
                                                    CardAnimationType.NONE)
        verify {
            cardAnimator.setColorFrontFilters(0, image, image)
        }
    }

    @Test
    fun `when call function animateColorChange with AnimationType NONE per parameter then shouldn't call set color filter`() {
        initialize()
        val image = mockk<ImageView>(relaxed = true)
        TestUtils.changeSDKVersion(29)
        cardAnimator.animateColorChange(image, image, 0, CardAnimationType.NONE)
        verify(inverse = true) {
            image.setColorFilter(0, PorterDuff.Mode.SRC_ATOP)
        }
        TestUtils.changeSDKVersion(0)
    }

    @Test
    fun `when call function animateColorChange with AnimationType RIGHT_BOTTOM per parameter then shouldn't call set color filter`() {
        initialize()
        val image = mockk<ImageView>(relaxed = true)
        TestUtils.changeSDKVersion(21)
        cardAnimator.animateColorChange(image, image, 0, CardAnimationType.RIGHT_BOTTOM)
        verify(inverse = true) {
            image.setColorFilter(0, PorterDuff.Mode.SRC_ATOP)
        }
        TestUtils.changeSDKVersion(0)
    }
}