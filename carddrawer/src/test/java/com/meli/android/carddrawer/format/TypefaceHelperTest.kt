package com.meli.android.carddrawer.format

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.TextView
import com.meli.android.carddrawer.BasicRobolectricTest
import com.meli.android.carddrawer.TestUtils
import com.mercadolibre.android.andesui.font.TypefaceHelper as TypefaceHelperFont
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class TypefaceHelperTest: BasicRobolectricTest() {

    @Mock
    private lateinit var contextMock: Context

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        TestUtils.initTypefaceSetter()
    }

    @Test
    fun `should test function set with TextView and Typeface per parameter`() {
        val typeface = mock<Typeface>()
        val textView = mock<TextView> {
            on { this.typeface }.thenReturn(typeface)
        }
        TypefaceHelper.set(textView, typeface)
        assertEquals(typeface, textView.typeface)
    }

    @Test
    fun `should test function set with pass TextView per parameter`() {
        val typeface = null
        val textView = mock<TextView>()
        TypefaceHelper.set(textView, typeface)
        assertEquals(typeface, textView.typeface)
    }

    @Test
    fun `should test function set with pass TextView and CardDrawerFont per parameter`() {
        val cardDrawerFont = CardDrawerFont.BLACK
        val type = TypefaceHelperFont.getFontTypeface(contextMock, cardDrawerFont.font)
        val textView = mock<TextView>()
        TypefaceHelper.set(textView, cardDrawerFont)
        assertEquals(type, textView.typeface)
    }

    @Test
    fun `should test function set with pass Context, Paint and CardDrawerFont per parameter`() {
        val cardDrawerFont = CardDrawerFont.BLACK
        val type = TypefaceHelperFont.getFontTypeface(contextMock, cardDrawerFont.font)
        val paint = mock<Paint>()
        TypefaceHelper.set(contextMock, paint, cardDrawerFont)
        assertEquals(type, paint.typeface)
    }

    @Test
    fun `should test function get with pass Context and CardDrawerFont per parameter`() {
        TestUtils.initTypefaceSetter()
        val cardDrawerFont = CardDrawerFont.BLACK
        val type = TypefaceHelper.get(contextMock, cardDrawerFont)
        assertEquals(type,null)
    }

}