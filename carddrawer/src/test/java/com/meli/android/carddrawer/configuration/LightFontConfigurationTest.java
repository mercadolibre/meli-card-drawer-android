package com.meli.android.carddrawer.configuration;

import android.graphics.LinearGradient;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.BasicRobolectricTest;
import com.meli.android.carddrawer.R;

import org.jetbrains.annotations.TestOnly;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class LightFontConfigurationTest extends BasicRobolectricTest {
    LightFontConfiguration lightFontConfiguration;

    @Before
    public void doBefore() {
        lightFontConfiguration = new LightFontConfiguration(getContext());
    }

    @Test
    public void getColor_returnsValidColor() {
        int validColor = ContextCompat.getColor(getContext(), R.color.card_drawer_light_font_empty_color);

        assertEquals(validColor, lightFontConfiguration.getColor());
    }

    @Test
    public void setShadow_callSetShadow() {
        TextPaint textPaint = mock(TextPaint.class);

        lightFontConfiguration.setShadow(textPaint);

        verify(textPaint).setShadowLayer(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }

    @Test
    public void setGradient_callsSetShaderWithGradient() {
        TextPaint textPaint = mock(TextPaint.class);

        lightFontConfiguration.setGradient(textPaint, 10, 10);

        verify(textPaint).setShader(any(LinearGradient.class));
    }
}
