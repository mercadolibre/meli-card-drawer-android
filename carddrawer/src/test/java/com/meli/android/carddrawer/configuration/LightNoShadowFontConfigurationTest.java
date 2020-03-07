package com.meli.android.carddrawer.configuration;

import android.text.TextPaint;
import org.junit.Test;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class LightNoShadowFontConfigurationTest extends LightFontConfigurationTest {

    @Override
    public void doBefore() {
        lightFontConfiguration = new LightNoShadowFontConfiguration(getContext());
    }

    @Override
    public void setShadow_callSetShadow() {
        //does not apply
    }

    @Test
    public void setShadow_DoesNotCallSetShadow() {
        TextPaint textPaint = mock(TextPaint.class);

        lightFontConfiguration.setShadow(textPaint);

        verify(textPaint, never()).setShadowLayer(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }
}
