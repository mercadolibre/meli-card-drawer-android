package com.meli.android.carddrawer.model

import android.view.View
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
import org.junit.Assert
import org.junit.Test

class CardDefaultResConfigurationTest: BaseTest() {

    private lateinit var cardDefaultResConfiguration: CardDefaultResConfiguration

    @MockK(relaxed = true)
    private lateinit var cardUI: CardUI

    override fun setUp() {
        super.setUp()
        cardDefaultResConfiguration = spyk(CardDefaultResConfiguration(cardUI))
    }

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
        every { constraintLayout.findViewById<TextView>(R.id.cho_card_code_front) } returns mockk(relaxed = true)
        cardDefaultResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardDefaultResConfiguration.setUpConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when call function resetConstraintLayoutConfiguration then if it call one time`() {
        val constraintLayout = mockk<ConstraintLayout>(relaxed = true)
        every { constraintLayout.findViewById<TextView>(R.id.cho_card_code_front) } returns mockk(relaxed = true)
        cardDefaultResConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        verify(exactly = 1) {
            cardDefaultResConfiguration.resetConstraintLayoutConfiguration(constraintLayout)
        }
    }

    @Test
    fun `when format value with pattern equal 2 then return the first two numbers`() {
        every { cardDefaultResConfiguration.getFormattedNumber(
            input = "1234",
            pattern = *intArrayOf(2)
        ) } answers { callOriginal() }
        val format = cardDefaultResConfiguration.getFormattedNumber(
            input = "1234",
            pattern = *intArrayOf(2)
        )
        Assert.assertEquals(format, "12")
    }

    @Test
    fun `when format value null with pattern equal 2 then return pattern with two positions`() {
        val format = cardDefaultResConfiguration.getFormattedNumber(null,2)
        Assert.assertEquals(format, "**")
    }

    @Test
    fun `when call function canAnimate with view id then don't call block`() {
        val view = mockk<View>(relaxed = true)
        var callBock = false
        every { view.id } returns R.id.cho_card_code_front_red_circle
        cardDefaultResConfiguration.canAnimate(view) {
            callBock = true
        }
        Assert.assertFalse(callBock)
    }

    @Test
    fun `when call function canAnimate with view id then call block`() {
        val view = mockk<View>(relaxed = true)
        var callBock = false
        every { view.id } returns R.id.cho_am_default_overlay
        cardDefaultResConfiguration.canAnimate(view) {
            callBock = true
        }
        Assert.assertTrue(callBock)
    }

}
