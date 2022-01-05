package com.meli.android.carddrawer.model

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class CardConfigurationTest: BaseTest() {

    @MockK
    private lateinit var cardConfiguration: CardConfiguration

    @Test
    fun `when call function updateSource then if it call one time`() {
        val cardUIMock = mockk<CardUI>()
        every { cardConfiguration.updateSource(cardUIMock) } answers { callOriginal() }
        cardConfiguration.updateSource(cardUIMock)
        verify(exactly = 1) {
            cardConfiguration.updateSource(cardUIMock)
        }
    }

    @Test
    fun `when call function updateConfiguration then if it call one time`() {
        val constraintLayoutMock = mockk<ConstraintLayout>(relaxed = true)
        every { cardConfiguration.updateConfiguration(constraintLayoutMock) } answers { callOriginal() }
        cardConfiguration.updateConfiguration(constraintLayoutMock)
        verify(exactly = 1) {
            cardConfiguration.updateConfiguration(constraintLayoutMock)
        }
    }

    @Test
    fun `when call function resetConfiguration then if it call one time`() {
        val constraintLayoutMock = mockk<ConstraintLayout>()
        every { cardConfiguration.resetConfiguration(constraintLayoutMock) } answers { callOriginal() }
        cardConfiguration.resetConfiguration(constraintLayoutMock)
        verify(exactly = 1) {
            cardConfiguration.resetConfiguration(constraintLayoutMock)
        }
    }

    @Test
    fun `when call function canShow then if it call one time`() {
        val viewMock = mockk<View>()
        every { cardConfiguration.canShow(viewMock) } answers { callOriginal() }
        cardConfiguration.canShow(viewMock)
        verify(exactly = 1) {
            cardConfiguration.canShow(viewMock)
        }
    }

    @Test
    fun `when call function getFormattedNumber with empty parameters then return empty string`() {
        every { cardConfiguration.getFormattedNumber("", 1) } answers { callOriginal() }
        val format = cardConfiguration.getFormattedNumber("", 1)
        Assert.assertEquals(format, "*")
    }

    @Test
    fun `when call function setUpConstraintConfiguration then if it call one time`() {
        val constraintSet = mockk<ConstraintSet>()
        every { cardConfiguration.setUpConstraintConfiguration(constraintSet) } answers { callOriginal() }
        cardConfiguration.setUpConstraintConfiguration(constraintSet)
        verify(exactly = 1) {
            cardConfiguration.setUpConstraintConfiguration(constraintSet)
        }
    }

    @Test
    fun `when call function setUpConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>()
        every { cardConfiguration.setUpConstraintLayoutConfiguration(constraintLayout) } answers { callOriginal() }
        cardConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function v then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>()
        every { cardConfiguration.resetConstraintLayoutConfiguration(constraintLayout) } answers { callOriginal() }
        cardConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        }
    }

}