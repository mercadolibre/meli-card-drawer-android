package com.meli.android.carddrawer.configuration;

import android.text.TextPaint;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class DarkNotShadowFontConfigurationTest extends DarkFontConfigurationTest {

    @Before
    public void doBefore() {
        darkFontConfiguration = new DarkNoShadowFontConfiguration(getContext());
    }

    @Override
    public void setShadow_callSetShadow() {
        //does not apply
    }

    @Test
    public void setShadow_DoesNotCallSetShadow() {
        TextPaint textPaint = mock(TextPaint.class);

        darkFontConfiguration.setShadow(textPaint);

        verify(textPaint, never()).setShadowLayer(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }
}
