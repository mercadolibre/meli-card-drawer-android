package com.meli.android.carddrawer.format

import com.meli.android.carddrawer.format.NumberFormatter.format
import com.meli.android.carddrawer.format.NumberFormatter.formatShort
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NumberFormatterTest {

    @Test
    fun formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andAmexPattern_withFullNumber_shouldReturnGroupedString() {
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
    fun formatTextForVisualFeedback_withPatternsWithZeroValue_shouldReturnGroupedString() {
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
    fun formatTextForVisualFeedback_withEmptyPattern_shouldReturnEmptyString() {
        val pattern = intArrayOf()
        val input = "12345678"
        val expected = ""
        val result = format(input, *pattern)
        val resultShort = formatShort(input, *pattern)
        Assert.assertEquals("Text was not properly grouped", expected, result)
        Assert.assertEquals("Text was not properly grouped", expected, resultShort)
    }

    @Test
    fun formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withFullNumber_shouldReturnGroupedString() {
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
    fun formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withHalfFilledNumber_shouldReturnGroupedStringWithFillers() {
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
    fun formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withEmptyNumber_shouldReturnGroupedFillerString() {
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