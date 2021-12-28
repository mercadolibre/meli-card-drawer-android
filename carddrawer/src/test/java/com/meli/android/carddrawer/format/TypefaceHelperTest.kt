package com.meli.android.carddrawer.format

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.TextView
import com.meli.android.carddrawer.TestUtils
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import com.mercadolibre.android.andesui.font.TypefaceHelper as TypefaceHelperFont

class TypefaceHelperTest {

    @MockK(relaxed = true)
    private lateinit var contextMock: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        val typeface = mockk<Typeface>(relaxed = true)
        TestUtils.setFinalStatic(Typeface::class.java.getField("MONOSPACE"), typeface)
    }

    @Test
    fun `when call function set with textView and typeFace per parameter then return the same typeFace of textView`() {
        TestUtils.initTypefaceSetter()
        val textView = mockk<TextView>(relaxed = true)
        val typeface = mockk<Typeface>(relaxed = true)
        every { textView.typeface } returns typeface
        TypefaceHelper.set(textView, typeface)
        Assert.assertEquals(typeface, textView.typeface)
    }

    @Test
    fun `when call function set with textView and typeFace null per parameter then return the same typeFace of textView`() {
        TestUtils.initTypefaceSetter()
        val typeface = null
        val textView = mockk<TextView>(relaxed = true)
        every { textView.typeface } returns typeface
        TypefaceHelper.set(textView, typeface)
        Assert.assertEquals(typeface, textView.typeface)
    }

    @Test
    fun `when call function set with textView and typeFace from getFontTypeFace per parameter then return the same typeFace of textView`() {
        val cardDrawerFont = CardDrawerFont.BLACK
        val typeface = TypefaceHelperFont.getFontTypeface(contextMock, cardDrawerFont.font)
        val textView = mockk<TextView>(relaxed = true)
        every { textView.typeface } returns typeface
        TypefaceHelper.set(textView, cardDrawerFont)
        Assert.assertEquals(typeface, textView.typeface)
    }

    @Test
    fun `when call function set with textView, paint and typeFace from getFontTypeFace per parameter then return the same typeFace of textView`() {
        val cardDrawerFont = CardDrawerFont.BLACK
        val typeface = TypefaceHelperFont.getFontTypeface(contextMock, cardDrawerFont.font)
        val paint = mockk<Paint>(relaxed = true)
        val textView = mockk<TextView>(relaxed = true)
        every { paint.typeface } returns typeface
        TypefaceHelper.set(contextMock, paint, cardDrawerFont)
        Assert.assertEquals(typeface, paint.typeface)
    }

    @Test
    fun `when call function get with context and CardDrawerFont per parameter then return null`() {
        val cardDrawerFont = CardDrawerFont.BLACK
        val type = TypefaceHelper.get(contextMock, cardDrawerFont)
        Assert.assertEquals(type,null)
    }

}