package com.meli.android.carddrawer.format

import android.graphics.Typeface
import com.meli.android.carddrawer.TestUtils
import com.mercadolibre.android.andesui.font.Font
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CardDrawerFontTest {

    @Before
    fun setUp() {
        val typeface = mockk<Typeface>(relaxed = true)
        TestUtils.setFinalStatic(Typeface::class.java.getField("MONOSPACE"), typeface)
    }

    @Test
    fun `when getting index from CardDrawerStyle BLACK then return 0`() {
        assertEquals(CardDrawerFont.BLACK.index, 0)
    }

    @Test
    fun `when getting font from CardDrawerStyle BLACK then return BLACK`() {
        assertEquals(CardDrawerFont.BLACK.font, Font.BLACK)
    }

    @Test
    fun `when getting style from CardDrawerStyle BLACK then return BOLD`() {
        assertEquals(CardDrawerFont.BLACK.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'black' per parameter then return CardDrawerFont BLACK`() {
        assertEquals(CardDrawerFont.from("black"), CardDrawerFont.BLACK)
    }

    @Test
    fun `when getting index from CardDrawerStyle BOLD then return 1`() {
        assertEquals(CardDrawerFont.BOLD.index, 1)
    }

    @Test
    fun `when getting font from CardDrawerStyle BOLD then return BOLD`() {
        assertEquals(CardDrawerFont.BOLD.font, Font.BOLD)
    }

    @Test
    fun `when getting style from CardDrawerStyle BOLD then return BOLD`() {
        assertEquals(CardDrawerFont.BOLD.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'bold' per parameter then return CardDrawerFont BOLD`() {
        assertEquals(CardDrawerFont.from("bold"), CardDrawerFont.BOLD)
    }

    @Test
    fun `when getting index from CardDrawerStyle EXTRA_BOLD then return 2`() {
        assertEquals(CardDrawerFont.EXTRA_BOLD.index, 2)
    }

    @Test
    fun `when getting font from CardDrawerStyle EXTRA_BOLD then return EXTRA_BOLD`() {
        assertEquals(CardDrawerFont.EXTRA_BOLD.font, Font.EXTRA_BOLD)
    }

    @Test
    fun `when getting style from CardDrawerStyle EXTRA_BOLD then return BOLD`() {
        assertEquals(CardDrawerFont.EXTRA_BOLD.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'extra_bold' per parameter then return CardDrawerFont EXTRA_BOLD`() {
        assertEquals(CardDrawerFont.from("extra_bold"), CardDrawerFont.EXTRA_BOLD)
    }

    @Test
    fun `when getting index from CardDrawerStyle LIGHT then return 3`() {
        assertEquals(CardDrawerFont.LIGHT.index, 3)
    }

    @Test
    fun `when getting font from CardDrawerStyle LIGHT then return LIGHT`() {
        assertEquals(CardDrawerFont.LIGHT.font, Font.LIGHT)
    }

    @Test
    fun `when getting style from CardDrawerStyle LIGHT then return NORMAL`() {
        assertEquals(CardDrawerFont.LIGHT.style, Typeface.NORMAL)
    }

    @Test
    fun `when call function from with 'light' per parameter then return CardDrawerFont LIGHT`() {
        assertEquals(CardDrawerFont.from("light"), CardDrawerFont.LIGHT)
    }

    @Test
    fun `when getting index from CardDrawerStyle REGULAR then return 4`() {
        assertEquals(CardDrawerFont.REGULAR.index, 4)
    }

    @Test
    fun `when getting font from CardDrawerStyle REGULAR then return REGULAR`() {
        assertEquals(CardDrawerFont.REGULAR.font, Font.REGULAR)
    }

    @Test
    fun `when getting style from CardDrawerStyle REGULAR then return NORMAL`() {
        assertEquals(CardDrawerFont.REGULAR.style, Typeface.NORMAL)
    }

    @Test
    fun `when call function from with 'regular' per parameter then return CardDrawerFont REGULAR`() {
        assertEquals(CardDrawerFont.from("regular"), CardDrawerFont.REGULAR)
    }

    @Test
    fun `when getting index from CardDrawerStyle SEMI_BOLD then return 5`() {
        assertEquals(CardDrawerFont.SEMI_BOLD.index, 5)
    }

    @Test
    fun `when getting font from CardDrawerStyle SEMI_BOLD then return SEMI_BOLD`() {
        assertEquals(CardDrawerFont.SEMI_BOLD.font, Font.SEMI_BOLD)
    }

    @Test
    fun `when getting style from CardDrawerStyle SEMI_BOLD then return BOLD`() {
        assertEquals(CardDrawerFont.SEMI_BOLD.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'semi_bold' per parameter then return CardDrawerFont SEMI_BOLD`() {
        assertEquals(CardDrawerFont.from("semi_bold"), CardDrawerFont.SEMI_BOLD)
    }

    @Test
    fun `when getting index from CardDrawerStyle MEDIUM then return 6`() {
        assertEquals(CardDrawerFont.MEDIUM.index, 6)
    }

    @Test
    fun `when getting font from CardDrawerStyle MEDIUM then return MEDIUM`() {
        assertEquals(CardDrawerFont.MEDIUM.font, Font.MEDIUM)
    }

    @Test
    fun `when getting style from CardDrawerStyle MEDIUM then return BOLD`() {
        assertEquals(CardDrawerFont.MEDIUM.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'medium' per parameter then return CardDrawerFont MEDIUM`() {
        assertEquals(CardDrawerFont.from("medium"), CardDrawerFont.MEDIUM)
    }

    @Test
    fun `when getting index from CardDrawerStyle THIN then return 7`() {
        assertEquals(CardDrawerFont.THIN.index, 7)
    }

    @Test
    fun `when getting font from CardDrawerStyle THIN then return THIN`() {
        assertEquals(CardDrawerFont.THIN.font, Font.THIN)
    }

    @Test
    fun `when getting style from CardDrawerStyle THIN then return NORMAL`() {
        assertEquals(CardDrawerFont.THIN.style, Typeface.NORMAL)
    }

    @Test
    fun `when call function from with 'thin' per parameter then return CardDrawerFont THIN`() {
        assertEquals(CardDrawerFont.from("thin"), CardDrawerFont.THIN)
    }

    @Test
    fun `when getting index from CardDrawerStyle MONOSPACE then return 8`() {
        assertEquals(CardDrawerFont.MONOSPACE.index, 8)
    }

    @Test
    fun `when getting font from CardDrawerStyle MONOSPACE then return REGULAR`() {
        assertEquals(CardDrawerFont.MONOSPACE.font, Font.REGULAR)
    }

    @Test
    fun `when getting style from CardDrawerStyle MONOSPACE then return MONOSPACE`() {
        assertEquals(CardDrawerFont.MONOSPACE.style, Typeface.MONOSPACE.style)
    }

    @Test
    fun `when call function from with 'monospace' per parameter then return CardDrawerFont MONOSPACE`() {
        assertEquals(CardDrawerFont.from("monospace"), CardDrawerFont.MONOSPACE)
    }

    @Test
    fun `when call function from with 'none' then return CardDrawerFont REGULAR`() {
        assertEquals(CardDrawerFont.from("none"), CardDrawerFont.REGULAR)
    }

}
