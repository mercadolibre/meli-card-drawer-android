package com.meli.android.carddrawer.configuration

import org.junit.Assert.assertEquals
import org.junit.Test

class FontTypeTest {

    @Test
    fun `when getting DARK_TYPE of FontType then it should return dark`() {
        assertEquals(FontType.DARK_TYPE, "dark")
    }

    @Test
    fun `when getting LIGHT_TYPE of FontType then it should return light`() {
        assertEquals(FontType.LIGHT_TYPE, "light")
    }

}