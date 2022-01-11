package com.meli.android.carddrawer.model

import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.android.carddrawer.BaseTest
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CardMediumConfigurationTest: BaseTest() {

    private lateinit var cardMediumConfiguration: CardMediumConfiguration

    @MockK
    private lateinit var cardUI: CardUI

    @MockK
    private lateinit var constraintLayout: ConstraintLayout

    @Before
    override fun setUp() {
        super.setUp()
        cardMediumConfiguration = spyk(CardMediumConfiguration(cardUI))
    }

    @Test
    fun `when call function setUpConstraintLayoutConfiguration then if it call one time`() {
        cardMediumConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardMediumConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function resetConstraintLayoutConfiguration then if it call one time`() {
        cardMediumConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardMediumConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        }
    }

}