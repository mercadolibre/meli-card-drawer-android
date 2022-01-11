package com.meli.android.carddrawer.model

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CardDefaultResConfigurationTest: BaseTest() {

    @MockK
    private lateinit var cardDefaultResConfiguration: CardDefaultResConfiguration

    @Test
    fun `when call function setUpConstraintConfiguration then if it call one time`() {
        val constraintSet = mockk<ConstraintSet>(relaxed = true)
        every { cardDefaultResConfiguration.setUpConstraintConfiguration(constraintSet) } answers { callOriginal() }
        cardDefaultResConfiguration.setUpConstraintConfiguration(constraintSet)
        verify(exactly = 1) {
            cardDefaultResConfiguration.setUpConstraintConfiguration(constraintSet)
        }
    }

    @Test
    fun `when call function setUpConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        every { cardDefaultResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout) } answers { callOriginal() }
        cardDefaultResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardDefaultResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function resetConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        every { cardDefaultResConfiguration.resetConstraintLayoutConfiguration(constraintLayout) } answers { callOriginal() }
        cardDefaultResConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardDefaultResConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        }
    }

}