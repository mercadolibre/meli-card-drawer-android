package com.meli.android.carddrawer.format

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.TextView
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.TestUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import com.mercadolibre.android.andesui.font.TypefaceHelper as TypefaceHelperFont

@RunWith(MockitoJUnitRunner::class)
class TypefaceHelperTest {

    @Mock
    private lateinit var contextMock: Context

    @Mock
    private lateinit var resourcesMock: Resources

    @Before
    open fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should test function set with TextView and Typeface per parameter`() {
        TestUtils.initTypefaceSetter()
        val typeface = mock<Typeface>()
        val textView = mock<TextView> {
            on { this.typeface }.thenReturn(typeface)
        }
        TypefaceHelper.set(textView, typeface)
        assertEquals(typeface, textView.typeface)
    }

    @Test
    fun `should test function set with pass TextView per parameter`() {
        TestUtils.initTypefaceSetter()
        val typeface = null
        val textView = mock<TextView>()
        TypefaceHelper.set(textView, typeface)
        assertEquals(typeface, textView.typeface)
    }

    @Test
    fun `should test function set with pass TextView per parameter and TypeFace null`() {
        val textView = mock<TextView>()
        val typeface = null
        Mockito.`when`(textView.context).thenReturn(contextMock)
        Mockito.`when`(textView.context.resources).thenReturn(resourcesMock)
        Mockito.`when`(textView.context.resources.getString(R.string.card_drawer_gms_font_provider_authority)).thenReturn("com.google.android.gms.fonts")
        Mockito.`when`(textView.context.resources.getString(R.string.card_drawer_gms_font_provider_package)).thenReturn("com.google.android.gms")
        Mockito.`when`(textView.context.resources.getString(R.string.card_drawer_roboto_mono_query)).thenReturn("Roboto Mono")
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
        val cardDrawerFont = CardDrawerFont.BLACK
        val type = TypefaceHelper.get(contextMock, cardDrawerFont)
        assertEquals(type,null)
    }

}