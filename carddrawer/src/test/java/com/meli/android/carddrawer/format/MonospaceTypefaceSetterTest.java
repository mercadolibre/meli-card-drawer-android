package com.meli.android.carddrawer.format;

import android.os.Handler;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class MonospaceTypefaceSetterTest {

    @Test
    public void monospaceTypefaceSetter_ActuallySetsTypeface() {
        // Prepare test environment
        // Run the fonts fetching command in the current thread's looper. This way we avoid
        // async waitings and race conditions
        ReflectionHelpers.setStaticField(MonospaceTypefaceSetter.class, "HANDLER", new Handler());
        final TextView mock = new TextView(RuntimeEnvironment.application);

        // Perform op
        MonospaceTypefaceSetter.setRobotoMono(RuntimeEnvironment.application, mock);

        // Assert the test performed as expected
        assertNotNull(mock.getTypeface());
    }

}
