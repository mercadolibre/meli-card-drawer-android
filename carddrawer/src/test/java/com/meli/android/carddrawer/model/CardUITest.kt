package com.meli.android.carddrawer.model

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CardUITest {

    @MockK
    private lateinit var cardUI: CardUI

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun `when getting bank image url then return empty`() {
        Assert.assertEquals(cardUI.bankImageUrl, "")
    }

    @Test
    fun `when getting card logo image url then return empty`() {
        Assert.assertEquals(cardUI.cardLogoImageUrl, "")
    }

    @Test
    fun `when getting bank image res then return 0`() {
        Assert.assertEquals(cardUI.bankImageRes, 0)
    }

    @Test
    fun `when getting card logo image res then return 0`() {
        Assert.assertEquals(cardUI.cardLogoImageRes, 0)
    }

}