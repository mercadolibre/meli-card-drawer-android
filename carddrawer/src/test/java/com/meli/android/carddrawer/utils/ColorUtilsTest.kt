package com.meli.android.carddrawer.utils

import android.graphics.Color
import com.meli.android.carddrawer.ColorUtils
import com.meli.android.carddrawer.model.Label
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorUtilsTest {

    @Test
    fun `when call function safeParcelColor with color and default color per parameter then it should return 0` () {
        val label = mockk<Label>(relaxed = true)
        val value = ColorUtils.safeParcelColor(label.color, Color.WHITE)
        assertEquals(value, 0)
    }

}