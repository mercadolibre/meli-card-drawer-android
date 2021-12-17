package com.meli.android.carddrawer.format;

import android.graphics.Typeface;
import android.widget.TextView;
import com.meli.android.carddrawer.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
public class TypefaceHelperTest {

    @Before
    public void setUp() {
        TestUtils.initTypefaceSetter();
    }

    @Test
    public void typefaceSetter_SetsCustomTypeface() {
        // Prepare test environment
        final TextView textView = new TextView(RuntimeEnvironment.application);
        final Typeface typeface = mock(Typeface.class);

        // Perform op
        TypefaceHelper.INSTANCE.set(textView, typeface);

        // Assert the test performed as expected
        assertEquals(typeface, textView.getTypeface());
    }

    @Test
    public void typefaceSetter_SetsDefaultTypeface() {
        // Prepare test environment
        final TextView textView = new TextView(RuntimeEnvironment.application);

        // Perform op
        TypefaceHelper.INSTANCE.set(textView, (Typeface) null);

        // Assert the test performed as expected
        assertEquals(ReflectionHelpers.getStaticField(TypefaceHelper.class, "robotoMono"), textView.getTypeface());
    }
}