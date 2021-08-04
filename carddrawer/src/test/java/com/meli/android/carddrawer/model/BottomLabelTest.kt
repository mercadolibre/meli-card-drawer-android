package com.meli.android.carddrawer.model

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.widget.AppCompatTextView
import com.meli.android.carddrawer.BasicRobolectricTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class BottomLabelTest : BasicRobolectricTest() {
    private lateinit var header: BottomLabel

    @Before
    fun doBefore() {
        header = BottomLabel(context)
    }

    @Test
    fun setLabelWithText() {
        val spyHeader = spy(header)
        val bottomDescription = ReflectionHelpers.getField<AppCompatTextView>(header, "bottomDescription")
        val label = mock(Label::class.java)
        `when`(label.text).thenReturn("Mensaje destacado")

        spyHeader.setLabel(label)

        assertEquals("Mensaje destacado", bottomDescription.text)
        assertEquals(-16734640, (spyHeader.background as ColorDrawable).color)
        assertEquals(-1, bottomDescription.currentTextColor)
    }

    @Test
    fun setLabelWithAllValues() {
        val spyHeader = spy(header)
        val bottomDescription = ReflectionHelpers.getField<AppCompatTextView>(header, "bottomDescription")
        val label = mock(Label::class.java)
        `when`(label.text).thenReturn("Sin recargo")
        `when`(label.backgroundColor).thenReturn("#0E0A09")
        `when`(label.color).thenReturn("#000000")
        `when`(label.weight).thenReturn("BLACK")

        spyHeader.setLabel(label)

        assertEquals("Sin recargo", bottomDescription.text)
        assertEquals(-15857143, (spyHeader.background as ColorDrawable).color)
        assertEquals(-16777216, bottomDescription.currentTextColor)
    }
}
