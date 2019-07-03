package com.meli.android.carddrawer.configuration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecurityCodeLocationTest {
    @Test
    public void verifyVariantNames() {
        assertEquals("front", SecurityCodeLocation.FRONT);
        assertEquals("back", SecurityCodeLocation.BACK);
        assertEquals("none", SecurityCodeLocation.NONE);
    }
}
