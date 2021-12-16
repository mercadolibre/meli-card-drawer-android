package com.meli.android.carddrawer.configuration

import org.junit.Assert.assertEquals
import org.junit.Test

class FontTypeTest {

    @Test
    fun `should return value FontType DARK_TYPE`() {
        assertEquals(FontType.DARK_TYPE, "dark")
    }

    @Test
    fun `should return value FontType LIGHT_TYPE`() {
        assertEquals(FontType.LIGHT_TYPE, "light")
    }

}