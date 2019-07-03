package com.meli.android.carddrawer.model;

import android.graphics.Canvas;
import android.text.TextPaint;

import com.meli.android.carddrawer.BasicRobolectricTest;
import com.meli.android.carddrawer.configuration.CardFontConfiguration;
import com.meli.android.carddrawer.configuration.DarkFontConfiguration;
import com.meli.android.carddrawer.configuration.FontType;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class GradientTextViewTest extends BasicRobolectricTest {

    @Test
    public void init_loadsViews() {
        GradientTextView gradientTextView = new GradientTextView(getContext());

        assertNotNull(ReflectionHelpers.getField(gradientTextView, "fontType"));
        assertNotNull(ReflectionHelpers.getField(gradientTextView, "placeHolder"));
        assertNotNull(ReflectionHelpers.getField(gradientTextView, "fontColor"));
    }

    @Test
    public void onDraw_setConfigurationGradient() {
        GradientTextView gradientTextViewSpy = spy(new GradientTextView(getContext()));
        CardFontConfiguration configurator = mock(CardFontConfiguration.class);
        doReturn(configurator).when(gradientTextViewSpy).getConfiguration();
        doReturn("anotherCardNumber").when(gradientTextViewSpy).getText();
        ReflectionHelpers.setField(gradientTextViewSpy, "placeHolder", "cardNumber");

        Canvas canvas = mock(Canvas.class);

        gradientTextViewSpy.onDraw(canvas);

        verify(configurator).setGradient(any(TextPaint.class), anyInt(), anyInt());
        verify(configurator).setShadow(any(TextPaint.class));
    }

    @Test
    public void onDraw_doesntSetConfigurationGradient() {
        GradientTextView gradientTextViewSpy = spy(new GradientTextView(getContext()));
        CardFontConfiguration configurator = mock(CardFontConfiguration.class);
        doReturn(configurator).when(gradientTextViewSpy).getConfiguration();
        doReturn("cardNumber").when(gradientTextViewSpy).getText();
        ReflectionHelpers.setField(gradientTextViewSpy, "placeHolder", "cardNumber");

        Canvas canvas = mock(Canvas.class);

        gradientTextViewSpy.onDraw(canvas);

        verify(configurator, never()).setGradient(any(TextPaint.class), anyInt(), anyInt());
        verify(configurator, never()).setShadow(any(TextPaint.class));
    }

    @Test
    public void init_setsValues() {
        GradientTextView gradientTextView = new GradientTextView(getContext());
        gradientTextView.init(FontType.LIGHT_TYPE, "MM/YY", 2);

        assertEquals(FontType.LIGHT_TYPE, ReflectionHelpers.getField(gradientTextView, "fontType"));
        assertEquals("MM/YY", ReflectionHelpers.getField(gradientTextView, "placeHolder"));
        assertEquals(2, ReflectionHelpers.getField(gradientTextView, "fontColor"));
    }

    @Test
    public void getConfiguration_lalal() {
        GradientTextView gradientTextViewSpy = spy(new GradientTextView(getContext()));
        ReflectionHelpers.setField(gradientTextViewSpy, "fontType", FontType.DARK_TYPE);
        ReflectionHelpers.setField(gradientTextViewSpy, "fontColor", 2);

        assertTrue(gradientTextViewSpy.getConfiguration() instanceof DarkFontConfiguration);
    }

}
