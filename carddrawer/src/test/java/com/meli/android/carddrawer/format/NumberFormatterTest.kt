package com.meli.android.carddrawer.format;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CardNumberFormatterTest {

    // --- Separated Filled processor
    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andAmexPattern_withFullNumber_shouldReturnGroupedString() {
        final int[] pattern = {4, 6, 5};
        final String input = "123412345612345";
        final String expected = "1234  123456  12345";
        final String expectedShort = "123456 12345";

        final String result = NumberFormatter.INSTANCE.format(input, pattern);
        final String resultShort = NumberFormatter.INSTANCE.formatShort(input, pattern);

        Assert.assertEquals("Text was not properly grouped", expected, result);
        Assert.assertEquals("Text was not properly grouped", expectedShort, resultShort);
    }

    @Test
    public void formatTextForVisualFeedback_withPatternsWithZeroValue_shouldReturnGroupedString() {
        final int[] pattern = {0, 0, 4, 4};
        final String input = "12345678";
        final String expected = "1234  5678";
        final String expectedShort = "1234 5678";

        final String result = NumberFormatter.INSTANCE.format(input, pattern);
        final String resultShort = NumberFormatter.INSTANCE.formatShort(input, pattern);

        Assert.assertEquals("Text was not properly grouped", expected, result);
        Assert.assertEquals("Text was not properly grouped", expectedShort, resultShort);
    }

    @Test
    public void formatTextForVisualFeedback_withEmptyPattern_shouldReturnEmptyString() {
        final int[] pattern = {};
        final String input = "12345678";
        final String expected = "";

        final String result = NumberFormatter.INSTANCE.format(input, pattern);
        final String resultShort = NumberFormatter.INSTANCE.formatShort(input, pattern);

        Assert.assertEquals("Text was not properly grouped", expected, result);
        Assert.assertEquals("Text was not properly grouped", expected, resultShort);
    }

    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withFullNumber_shouldReturnGroupedString() {
        final int[] pattern = {4, 4, 4, 4};
        final String input = "1234123412341234";
        final String expected = "1234  1234  1234  1234";
        final String expectedShort = "1234 1234";

        final String result = NumberFormatter.INSTANCE.format(input, pattern);
        final String resultShort = NumberFormatter.INSTANCE.formatShort(input, pattern);

        Assert.assertEquals("Text was not properly grouped", expected, result);
        Assert.assertEquals("Text was not properly grouped", expectedShort, resultShort);
    }

    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withHalfFilledNumber_shouldReturnGroupedStringWithFillers() {
        final int[] pattern = {4, 4, 4, 4};
        final String input = "12341234123";
        final String expected = "1234  1234  123*  ****";
        final String expectedShort = "123* ****";

        final String result = NumberFormatter.INSTANCE.format(input, pattern);
        final String resultShort = NumberFormatter.INSTANCE.formatShort(input, pattern);

        Assert.assertEquals("Text was not properly filled", expected, result);
        Assert.assertEquals("Text was not properly filled", expectedShort, resultShort);
    }

    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withEmptyNumber_shouldReturnGroupedFillerString() {
        final int[] pattern = {4, 4, 4, 4};
        final String input = "";
        final String expected = "****  ****  ****  ****";
        final String expectedShort = "**** ****";

        final String result = NumberFormatter.INSTANCE.format(input, pattern);
        final String resultShort = NumberFormatter.INSTANCE.formatShort(input, pattern);

        Assert.assertEquals("Text was not properly filled", expected, result);
        Assert.assertEquals("Text was not properly filled", expectedShort, resultShort);
    }
}