package com.meli.android.carddrawer.model

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.TestBase
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CardDefaultResConfigurationTest: TestBase() {

    @MockK
    private lateinit var cardDefaultResConfiguration: CardDefaultResConfiguration

    @Test
    fun `when call function setUpConstraintConfiguration then if it call one time`() {
        val constraintSet = mockk<ConstraintSet>(relaxed = true)
        cardDefaultResConfiguration.setUpConstraintConfiguration(constraintSet)
        verify(exactly = 1) {
            cardDefaultResConfiguration.setUpConstraintConfiguration(constraintSet)
        }
    }

    @Test
    fun `when call function setUpConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        cardDefaultResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardDefaultResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function resetConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayoutMock = mockk<ConstraintLayout>(relaxed = true)
        cardDefaultResConfiguration.resetConstraintLayoutConfiguration(constraintLayoutMock)
        verify(exactly = 1) {
            cardDefaultResConfiguration.resetConstraintLayoutConfiguration(constraintLayoutMock)
        }
    }

}