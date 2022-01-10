package com.meli.android.carddrawer.model

import android.os.Bundle
import android.view.View
import com.meli.android.carddrawer.BaseTest
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

    private fun CardAnimator.getDeclaredField(name: String): Any {
        return javaClass.getDeclaredField(name).let {
            it.isAccessible = true
            it.get(this) as Any
        }
    }

}