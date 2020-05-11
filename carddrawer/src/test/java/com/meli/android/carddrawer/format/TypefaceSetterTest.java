package com.meli.android.carddrawer.format;

import android.graphics.Typeface;
import android.os.Handler;
import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
public class TypefaceSetterTest {

    @Before
    public void setUp() {
        // Run the fonts fetching command in the current thread's looper. This way we avoid
        // async waitings and race conditions
        ReflectionHelpers.setStaticField(TypefaceSetter.class, "HANDLER", new Handler());

        TypefaceSetter.INSTANCE.init(RuntimeEnvironment.application);
    }

    @Test
    public void monospaceTypefaceSetter_ActuallySetsTypeface() {
        // Prepare test environment
        final TextView textView = new TextView(RuntimeEnvironment.application);
        final Typeface typeface = mock(Typeface.class);

        // Perform op
        TypefaceSetter.INSTANCE.set(textView, typeface);

        // Assert the test performed as expected
        assertEquals(typeface, textView.getTypeface());
    }
}