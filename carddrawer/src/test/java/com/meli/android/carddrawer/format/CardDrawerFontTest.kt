package com.meli.android.carddrawer.format

import android.graphics.Typeface
import com.mercadolibre.android.andesui.font.Font
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardDrawerFontTest {

    @Test
    fun `should return information about CardDrawerStyle BLACK`() {
        assertEquals(CardDrawerFont.BLACK.index, 0)
        assertEquals(CardDrawerFont.BLACK.font, Font.BLACK)
        assertEquals(CardDrawerFont.BLACK.style, Typeface.BOLD)
        assertEquals(CardDrawerFont.from("black"), CardDrawerFont.BLACK)
    }

    @Test
    fun `should return information about CardDrawerStyle BOLD`() {
        assertEquals(CardDrawerFont.BOLD.index, 1)
        assertEquals(CardDrawerFont.BOLD.font, Font.BOLD)
        assertEquals(CardDrawerFont.BOLD.style, Typeface.BOLD)
        assertEquals(CardDrawerFont.from("bold"), CardDrawerFont.BOLD)
    }

    @Test
    fun `should return information about CardDrawerStyle EXTRA_BOLD`() {
        assertEquals(CardDrawerFont.EXTRA_BOLD.index, 2)
        assertEquals(CardDrawerFont.EXTRA_BOLD.font, Font.EXTRA_BOLD)
        assertEquals(CardDrawerFont.EXTRA_BOLD.style, Typeface.BOLD)
        assertEquals(CardDrawerFont.from("extra_bold"), CardDrawerFont.EXTRA_BOLD)
    }

    @Test
    fun `should return information about CardDrawerStyle LIGHT`() {
        assertEquals(CardDrawerFont.LIGHT.index, 3)
        assertEquals(CardDrawerFont.LIGHT.font, Font.LIGHT)
        assertEquals(CardDrawerFont.LIGHT.style, Typeface.NORMAL)
        assertEquals(CardDrawerFont.from("light"), CardDrawerFont.LIGHT)
    }

    @Test
    fun `should return information about CardDrawerStyle REGULAR`() {
        assertEquals(CardDrawerFont.REGULAR.index, 4)
        assertEquals(CardDrawerFont.REGULAR.font, Font.REGULAR)
        assertEquals(CardDrawerFont.REGULAR.style, Typeface.NORMAL)
        assertEquals(CardDrawerFont.from("regular"), CardDrawerFont.REGULAR)
    }

    @Test
    fun `should return information about CardDrawerStyle SEMI_BOLD`() {
        assertEquals(CardDrawerFont.SEMI_BOLD.index, 5)
        assertEquals(CardDrawerFont.SEMI_BOLD.font, Font.SEMI_BOLD)
        assertEquals(CardDrawerFont.SEMI_BOLD.style, Typeface.BOLD)
        assertEquals(CardDrawerFont.from("semi_bold"), CardDrawerFont.SEMI_BOLD)
    }

    @Test
    fun `should return information about CardDrawerStyle MEDIUM`() {
        assertEquals(CardDrawerFont.MEDIUM.index, 6)
        assertEquals(CardDrawerFont.MEDIUM.font, Font.MEDIUM)
        assertEquals(CardDrawerFont.MEDIUM.style, Typeface.BOLD)
        assertEquals(CardDrawerFont.from("medium"), CardDrawerFont.MEDIUM)
    }

    @Test
    fun `should return information about CardDrawerStyle THIN`() {
        assertEquals(CardDrawerFont.THIN.index, 7)
        assertEquals(CardDrawerFont.THIN.font, Font.THIN)
        assertEquals(CardDrawerFont.THIN.style, Typeface.NORMAL)
        assertEquals(CardDrawerFont.from("thin"), CardDrawerFont.THIN)
    }

    @Test
    fun `should return information about CardDrawerStyle MONOSPACE`() {
        assertEquals(CardDrawerFont.MONOSPACE.index, 8)
        assertEquals(CardDrawerFont.MONOSPACE.font, Font.REGULAR)
        assertEquals(CardDrawerFont.MONOSPACE.style, Typeface.NORMAL)
        assertEquals(CardDrawerFont.from("monospace"), CardDrawerFont.MONOSPACE)
    }

    @Test
    fun `should return font with font invalid`() {
        assertEquals(CardDrawerFont.from("none"), CardDrawerFont.REGULAR)
    }

}