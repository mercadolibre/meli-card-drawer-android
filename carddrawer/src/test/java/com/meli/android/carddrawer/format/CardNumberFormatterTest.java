package com.meli.android.carddrawer.format;

import com.meli.android.carddrawer.TestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class CardNumberFormatterTest {

    // --- Separated Filled processor
    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andAmexPattern_withFullNumber_shouldReturnGroupedString() {
        int[] pattern = new int[]{4, 6, 5};
        NumberFormatter textProcessor = new NumberFormatter(pattern);
        String input = "123412345612345";
        String expected = "1234  123456  12345";

        String result = textProcessor.formatTextForVisualFeedback(input);

        assertEquals("Text was not properly grouped", expected, result);
    }

    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withFullNumber_shouldReturnGroupedString() {
        int[] pattern = new int[]{4, 4, 4, 4};
        NumberFormatter textProcessor = new NumberFormatter(pattern);
        String input = "1234123412341234";
        String expected = "1234  1234  1234  1234";

        String result = textProcessor.formatTextForVisualFeedback(input);

        assertEquals("Text was not properly grouped", expected, result);
    }

    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withHalfFilledNumber_shouldReturnGroupedStringWithFillers() {
        int[] pattern = new int[]{4, 4, 4, 4};
        NumberFormatter textProcessor = new NumberFormatter(pattern);
        String input = "12341234123";
        String expected = "1234  1234  123*  ****";

        String result = textProcessor.formatTextForVisualFeedback(input);

        assertEquals("Text was not properly filled", expected, result);
    }

    @Test
    public void formatTextForVisualFeedback_withSeparatedFilledTextProcessor_andVisaPattern_withEmptyNumber_shouldReturnGroupedFillerString() {
        int[] pattern = new int[]{4, 4, 4, 4};
        NumberFormatter textProcessor = new NumberFormatter(pattern);
        String input = "";
        String expected = "****  ****  ****  ****";

        String result = textProcessor.formatTextForVisualFeedback(input);

        assertEquals("Text was not properly filled", expected, result);
    }

    @Test
    public void formatTextForInput_withSeparatedFilledTextProcessor_andVisaPattern_withEmptyNumber_shouldReturnEmptyString() {
        int[] pattern = new int[]{4, 4, 4, 4};
        NumberFormatter textProcessor = new NumberFormatter(pattern);
        String expected = "****  ****  ****  ****";

        String result = textProcessor.formatEmptyText();

        assertEquals("Text shouldn't be filled", expected, result);
    }

    @Test
    public void newParcelable_worksOK() {
        int[] pattern = new int[]{4, 4, 4, 4};
        NumberFormatter textProcessor = new NumberFormatter(pattern);

        NumberFormatter clone = TestUtils.cloneParcelable(textProcessor, NumberFormatter.CREATOR);

        assertEquals(textProcessor.pattern.length, clone.pattern.length);
    }

}
