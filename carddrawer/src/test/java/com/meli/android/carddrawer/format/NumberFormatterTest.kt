package com.meli.android.carddrawer.format

import com.meli.android.carddrawer.format.NumberFormatter.format
import com.meli.android.carddrawer.format.NumberFormatter.formatShort
import org.junit.Assert
import org.junit.Test

class NumberFormatterTest {

    @Test
    fun `when format text with separated filled text processor and amex pattern with full number then return grouped string`() {
        val pattern = intArrayOf(4, 6, 5)
        val input = "123412345612345"
        val expected = "1234  123456  12345"
        val expectedShort = "123456 12345"
        val result = format(input, *pattern)
        val resultShort = formatShort(input, *pattern)
        Assert.assertEquals("Text was not properly grouped", expected, result)
        Assert.assertEquals("Text was not properly grouped", expectedShort, resultShort)
    }

    @Test
    fun `when format text with pattern with zero value then return grouped String`() {
        val pattern = intArrayOf(0, 0, 4, 4)
        val input = "12345678"
        val expected = "1234  5678"
        val expectedShort = "1234 5678"
        val result = format(input, *pattern)
        val resultShort = formatShort(input, *pattern)
        Assert.assertEquals("Text was not properly grouped", expected, result)
        Assert.assertEquals("Text was not properly grouped", expectedShort, resultShort)
    }

    @Test
    fun `when format text with empty pattern then return empty String`() {
        val pattern = intArrayOf()
        val input = "12345678"
        val expected = ""
        val result = format(input, *pattern)
        val resultShort = formatShort(input, *pattern)
        Assert.assertEquals("Text was not properly grouped", expected, result)
        Assert.assertEquals("Text was not properly grouped", expected, resultShort)
    }

    @Test
    fun `when format text with separated filled text processor and visa pattern with full number then return grouped string`() {
        val pattern = intArrayOf(4, 4, 4, 4)
        val input = "1234123412341234"
        val expected = "1234  1234  1234  1234"
        val expectedShort = "1234 1234"
        val result = format(input, *pattern)
        val resultShort = formatShort(input, *pattern)
        Assert.assertEquals("Text was not properly grouped", expected, result)
        Assert.assertEquals("Text was not properly grouped", expectedShort, resultShort)
    }

    @Test
    fun `when format text with separated filled text processor and visa pattern with half filled number then return grouped string with fillers`() {
        val pattern = intArrayOf(4, 4, 4, 4)
        val input = "12341234123"
        val expected = "1234  1234  123*  ****"
        val expectedShort = "123* ****"
        val result = format(input, *pattern)
        val resultShort = formatShort(input, *pattern)
        Assert.assertEquals("Text was not properly filled", expected, result)
        Assert.assertEquals("Text was not properly filled", expectedShort, resultShort)
    }
    @Test
    fun `when format text with separated filled text processor and visa pattern with empty number then return grouped filler string`() {
        val pattern = intArrayOf(4, 4, 4, 4)
        val input = ""
        val expected = "****  ****  ****  ****"
        val expectedShort = "**** ****"
        val result = format(input, *pattern)
        val resultShort = formatShort(input, *pattern)
        Assert.assertEquals("Text was not properly filled", expected, result)
        Assert.assertEquals("Text was not properly filled", expectedShort, resultShort)
    }

}