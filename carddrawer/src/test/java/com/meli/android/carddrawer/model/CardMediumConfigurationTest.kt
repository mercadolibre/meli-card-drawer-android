package com.meli.android.carddrawer.model

import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.android.carddrawer.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CardMediumConfigurationTest: BaseTest() {

    @MockK
    private lateinit var cardMediumConfiguration: CardMediumConfiguration

    @Test
    fun `when call function setUpConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        every { cardMediumConfiguration.setUpConstraintLayoutConfiguration(constraintLayout) } answers { callOriginal() }
        cardMediumConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardMediumConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function resetConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        every { cardMediumConfiguration.resetConstraintLayoutConfiguration(constraintLayout) } answers { callOriginal() }
        cardMediumConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardMediumConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        }
    }

}