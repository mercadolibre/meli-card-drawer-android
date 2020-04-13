package com.meli.android.carddrawer.configuration;

import android.graphics.LinearGradient;
import android.text.TextPaint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class DefaultFontConfigurationTest {
    private DefaultFontConfiguration defaultFontConfiguration;

    @Before
    public void doBefore() {
        defaultFontConfiguration = new DefaultFontConfiguration(2);
    }

    @Test
    public void getColor_returnsValidColor() {
        assertEquals(2, defaultFontConfiguration.getColor());
    }

    @Test
    public void setShadow_doesntCallSetShadow() {
        TextPaint textPaint = mock(TextPaint.class);

        defaultFontConfiguration.setShadow(textPaint);

        verify(textPaint, never()).setShadowLayer(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }
}
