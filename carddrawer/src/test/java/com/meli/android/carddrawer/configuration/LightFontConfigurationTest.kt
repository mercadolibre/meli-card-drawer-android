package com.meli.android.carddrawer.configuration;

import androidx.core.content.ContextCompat;
import android.text.TextPaint;

import com.meli.android.carddrawer.BasicRobolectricTest;
import com.meli.android.carddrawer.R;

import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration;
import com.meli.android.carddrawer.configuration.shadow.ShadowFontConfiguration;
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
public class LightFontConfigurationTest extends BasicRobolectricTest {
    private LightFontConfiguration lightFontConfiguration;

    @Before
    public void doBefore() {
        ShadowConfiguration shadowFontConfiguration = new ShadowFontConfiguration(getContext());
        lightFontConfiguration = new LightFontConfiguration(getContext(), shadowFontConfiguration);
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
    public void setShadow_DoesNotCallSetShadow() {
        LightFontConfiguration lightNotShadowFontConfiguration = new LightFontConfiguration(getContext());
        TextPaint textPaint = mock(TextPaint.class);

        lightNotShadowFontConfiguration.setShadow(textPaint);

        verify(textPaint, never()).setShadowLayer(anyFloat(), anyFloat(), anyFloat(), anyInt());
    }
}
