package com.meli.android.carddrawer.format

import android.graphics.Typeface
import com.mercadolibre.android.andesui.font.Font
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class CardDrawerFontTest {

    @Before
    fun setUp() {
        val typeface = mockk<Typeface>(relaxed = true)
        setFinalStatic(Typeface::class.java.getField("MONOSPACE"), typeface)
    }

    private fun setFinalStatic(field: Field, newValue: Any?) {
        field.isAccessible = true
        val modifiersField: Field = Field::class.java.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, newValue)
    }

    @Test
    fun `when getting index from CardDrawerStyle BLACK should return 0`() {
        assertEquals(CardDrawerFont.BLACK.index, 0)
    }

    @Test
    fun `when getting font from CardDrawerStyle BLACK should return BLACK`() {
        assertEquals(CardDrawerFont.BLACK.font, Font.BLACK)
    }

    @Test
    fun `when getting style from CardDrawerStyle BLACK should return BOLD`() {
        assertEquals(CardDrawerFont.BLACK.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'black' per parameter should return CardDrawerFont BLACK`() {
        assertEquals(CardDrawerFont.from("black"), CardDrawerFont.BLACK)
    }

    @Test
    fun `when getting index from CardDrawerStyle BOLD should return 1`() {
        assertEquals(CardDrawerFont.BOLD.index, 1)
    }

    @Test
    fun `when getting font from CardDrawerStyle BOLD should return BOLD`() {
        assertEquals(CardDrawerFont.BOLD.font, Font.BOLD)
    }

    @Test
    fun `when getting style from CardDrawerStyle BOLD should return BOLD`() {
        assertEquals(CardDrawerFont.BOLD.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'bold' per parameter should return CardDrawerFont BOLD`() {
        assertEquals(CardDrawerFont.from("bold"), CardDrawerFont.BOLD)
    }

    @Test
    fun `when getting index from CardDrawerStyle EXTRA_BOLD should return 2`() {
        assertEquals(CardDrawerFont.EXTRA_BOLD.index, 2)
    }

    @Test
    fun `when getting font from CardDrawerStyle EXTRA_BOLD should return EXTRA_BOLD`() {
        assertEquals(CardDrawerFont.EXTRA_BOLD.font, Font.EXTRA_BOLD)
    }

    @Test
    fun `when getting style from CardDrawerStyle EXTRA_BOLD should return BOLD`() {
        assertEquals(CardDrawerFont.EXTRA_BOLD.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'extra_bold' per parameter should return CardDrawerFont EXTRA_BOLD`() {
        assertEquals(CardDrawerFont.from("extra_bold"), CardDrawerFont.EXTRA_BOLD)
    }

    @Test
    fun `when getting index from CardDrawerStyle LIGHT should return 3`() {
        assertEquals(CardDrawerFont.LIGHT.index, 3)
    }

    @Test
    fun `when getting font from CardDrawerStyle LIGHT should return LIGHT`() {
        assertEquals(CardDrawerFont.LIGHT.font, Font.LIGHT)
    }

    @Test
    fun `when getting style from CardDrawerStyle LIGHT should return NORMAL`() {
        assertEquals(CardDrawerFont.LIGHT.style, Typeface.NORMAL)
    }

    @Test
    fun `when call function from with 'light' per parameter should return CardDrawerFont LIGHT`() {
        assertEquals(CardDrawerFont.from("light"), CardDrawerFont.LIGHT)
    }

    @Test
    fun `when getting index from CardDrawerStyle REGULAR should return 4`() {
        assertEquals(CardDrawerFont.REGULAR.index, 4)
    }

    @Test
    fun `when getting font from CardDrawerStyle REGULAR should return REGULAR`() {
        assertEquals(CardDrawerFont.REGULAR.font, Font.REGULAR)
    }

    @Test
    fun `when getting style from CardDrawerStyle REGULAR should return NORMAL`() {
        assertEquals(CardDrawerFont.REGULAR.style, Typeface.NORMAL)
    }

    @Test
    fun `when call function from with 'regular' per parameter should return CardDrawerFont REGULAR`() {
        assertEquals(CardDrawerFont.from("regular"), CardDrawerFont.REGULAR)
    }

    @Test
    fun `when getting index from CardDrawerStyle SEMI_BOLD should return 5`() {
        assertEquals(CardDrawerFont.SEMI_BOLD.index, 5)
    }

    @Test
    fun `when getting font from CardDrawerStyle SEMI_BOLD should return SEMI_BOLD`() {
        assertEquals(CardDrawerFont.SEMI_BOLD.font, Font.SEMI_BOLD)
    }

    @Test
    fun `when getting style from CardDrawerStyle SEMI_BOLD should return BOLD`() {
        assertEquals(CardDrawerFont.SEMI_BOLD.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'semi_bold' per parameter should return CardDrawerFont SEMI_BOLD`() {
        assertEquals(CardDrawerFont.from("semi_bold"), CardDrawerFont.SEMI_BOLD)
    }

    @Test
    fun `when getting index from CardDrawerStyle MEDIUM should return 6`() {
        assertEquals(CardDrawerFont.MEDIUM.index, 6)
    }

    @Test
    fun `when getting font from CardDrawerStyle MEDIUM should return MEDIUM`() {
        assertEquals(CardDrawerFont.MEDIUM.font, Font.MEDIUM)
    }

    @Test
    fun `when getting style from CardDrawerStyle MEDIUM should return BOLD`() {
        assertEquals(CardDrawerFont.MEDIUM.style, Typeface.BOLD)
    }

    @Test
    fun `when call function from with 'medium' per parameter should return CardDrawerFont MEDIUM`() {
        assertEquals(CardDrawerFont.from("medium"), CardDrawerFont.MEDIUM)
    }

    @Test
    fun `when getting index from CardDrawerStyle THIN should return 7`() {
        assertEquals(CardDrawerFont.THIN.index, 7)
    }

    @Test
    fun `when getting font from CardDrawerStyle THIN should return THIN`() {
        assertEquals(CardDrawerFont.THIN.font, Font.THIN)
    }

    @Test
    fun `when getting style from CardDrawerStyle THIN should return NORMAL`() {
        assertEquals(CardDrawerFont.THIN.style, Typeface.NORMAL)
    }

    @Test
    fun `when call function from with 'thin' per parameter should return CardDrawerFont THIN`() {
        assertEquals(CardDrawerFont.from("thin"), CardDrawerFont.THIN)
    }

    @Test
    fun `when getting index from CardDrawerStyle MONOSPACE should return 8`() {
        assertEquals(CardDrawerFont.MONOSPACE.index, 8)
    }

    @Test
    fun `when getting font from CardDrawerStyle MONOSPACE should return REGULAR`() {
        assertEquals(CardDrawerFont.MONOSPACE.font, Font.REGULAR)
    }

    @Test
    fun `when getting style from CardDrawerStyle MONOSPACE should return MONOSPACE`() {
        assertEquals(CardDrawerFont.MONOSPACE.style, Typeface.MONOSPACE.style)
    }

    @Test
    fun `when call function from with 'monospace' per parameter should return CardDrawerFont MONOSPACE`() {
        assertEquals(CardDrawerFont.from("monospace"), CardDrawerFont.MONOSPACE)
    }

    @Test
    fun `when call function from with 'none' should return CardDrawerFont REGULAR`() {
        assertEquals(CardDrawerFont.from("none"), CardDrawerFont.REGULAR)
    }

}
