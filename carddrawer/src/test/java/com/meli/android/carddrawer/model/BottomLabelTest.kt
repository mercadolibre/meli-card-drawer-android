package com.meli.android.carddrawer.model

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.widget.AppCompatTextView
import com.meli.android.carddrawer.BasicRobolectricTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class BottomLabelTest : BasicRobolectricTest() {

    private lateinit var bottomLabel: BottomLabel

    @Before
    fun setUp() {
        bottomLabel = BottomLabel(context)
    }

    @Test
    fun `when set label then return bottom description, background and text color with the values`() {
        val spyHeader = spyk(bottomLabel)
        val bottomDescription = ReflectionHelpers.getField<AppCompatTextView>(bottomLabel, "bottomDescription")
        val label = mockk<Label>(relaxed = true)

        every { label.text } returns "Mensaje destacado"

        spyHeader.setLabel(label)

        assertEquals("Mensaje destacado", bottomDescription.text)
        assertEquals(-16734640, (spyHeader.background as ColorDrawable).color)
        assertEquals(-1, bottomDescription.currentTextColor)
    }

    @Test
    fun `when set label with all values then fill text, background and textColor`() {
        val spyHeader = spyk(bottomLabel)
        val bottomDescription = ReflectionHelpers.getField<AppCompatTextView>(bottomLabel, "bottomDescription")
        val label = mockk<Label>(relaxed = true)

        every { label.text } returns "Sin recargo"
        every { label.backgroundColor } returns "#0E0A09"
        every { label.color } returns "#000000"
        every { label.weight } returns "BLACK"

        spyHeader.setLabel(label)

        assertEquals("Sin recargo", bottomDescription.text)
        assertEquals(-15857143, (spyHeader.background as ColorDrawable).color)
        assertEquals(-16777216, bottomDescription.currentTextColor)
    }
}
