package com.meli.android.carddrawer.utils

import android.graphics.Color
import com.meli.android.carddrawer.ColorUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorUtilsTest {

    @Test
    fun `should test parseColor` () {
        val value: Int = ColorUtils.safeParcelColor("#101820", Color.RED)
        assertEquals(value, 0)
    }

}