package com.meli.android.carddrawer.model

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardConfigurationTest {

    @MockK
    private lateinit var cardConfiguration: CardConfiguration

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `when call function updateSource then if it call one time`() {
        val cardUIMock = mockk<CardUI>()
        cardConfiguration.updateSource(cardUIMock)
        verify(exactly = 1) {
            cardConfiguration.updateSource(cardUIMock)
        }
    }

    @Test
    fun `when call function updateConfiguration then if it call one time`() {
        val constraintLayoutMock = mockk<ConstraintLayout>()
        cardConfiguration.updateConfiguration(constraintLayoutMock)
        verify(exactly = 1) {
            cardConfiguration.updateConfiguration(constraintLayoutMock)
        }
    }

    @Test
    fun `when call function resetConfiguration then if it call one time`() {
        val constraintLayoutMock = mockk<ConstraintLayout>()
        cardConfiguration.resetConfiguration(constraintLayoutMock)
        verify(exactly = 1) {
            cardConfiguration.resetConfiguration(constraintLayoutMock)
        }
    }

    @Test
    fun `when call function canShow then if it call one time`() {
        val viewMock = mockk<View>()
        cardConfiguration.canShow(viewMock)
        verify(exactly = 1) {
            cardConfiguration.canShow(viewMock)
        }
    }

    @Test
    fun `when call function getFormattedNumber with empty parameters then return empty string`() {
        val format = cardConfiguration.getFormattedNumber("", 1)
        Assert.assertEquals(format, "")
    }
}