package com.meli.android.carddrawer.model

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.meli.android.carddrawer.BaseTest
import com.meli.android.carddrawer.R
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CardLowResConfigurationTest: BaseTest() {

    private lateinit var cardLowResConfiguration: CardLowResConfiguration

    @MockK(relaxed = true)
    private lateinit var cardUI: CardUI

    @Before
    override fun setUp() {
        super.setUp()
        cardLowResConfiguration = spyk(CardLowResConfiguration(cardUI))
    }

    @Test
    fun `when call function setUpConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        val textView = mockk<TextView>(relaxed = true)
        every { constraintLayout.findViewById<TextView>(R.id.cho_card_code_front) } returns textView
        cardLowResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardLowResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function resetConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        val textView = mockk<TextView>(relaxed = true)
        every { constraintLayout.findViewById<TextView>(R.id.cho_card_code_front) } returns textView
        cardLowResConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardLowResConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function setUpConstraintConfiguration then if it call one time`() {
        val constraintSet = mockk<ConstraintSet>(relaxed = true)
        cardLowResConfiguration.setUpConstraintConfiguration(constraintSet)
        verify(exactly = 1) {
            cardLowResConfiguration.setUpConstraintConfiguration(constraintSet)
        }
    }

}