package com.meli.android.carddrawer.model

import android.view.View
import com.meli.android.carddrawer.BaseTest
import com.meli.android.carddrawer.configuration.FieldPosition
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
    fun `when call function switchView with POSITION_FRON per parameter then fill showingLayout with the same value`() {
        initialize()
        val showingLayout = cardAnimator.getDeclaredField("showingLayout")
        cardAnimator.switchView(FieldPosition.POSITION_FRONT)
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_FRONT)
    }

    @Test
    fun `when call function switchView with other value per parameter then fill showingLayout POSITION_FRONT`() {
        initialize()
        val showingLayout = cardAnimator.getDeclaredField("showingLayout")
        cardAnimator.switchView(3)
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_FRONT)
    }

    @Test
    fun `when call function switchView with POSITION_BACK per parameter then fill showingLayout with the same value`() {
        val mock =  mockk<CardAnimator>(relaxed = true)
        every { mock.switchView(FieldPosition.POSITION_BACK) } answers { callOriginal() }
        mock.switchView(FieldPosition.POSITION_BACK)
        val showingLayout = mock.getDeclaredField("showingLayout")
        Assert.assertEquals(showingLayout, FieldPosition.POSITION_BACK)
    }

    private fun CardAnimator.getDeclaredField(name: String): Any {
        return javaClass.getDeclaredField(name).let {
            it.isAccessible = true
            it.get(this) as Any
        }
    }

}