package com.meli.android.carddrawer.configuration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FontTypeTest {
    @Test
    public void verifyTypesValues() {
        assertEquals("dark", FontType.DARK_TYPE);
        assertEquals("light", FontType.LIGHT_TYPE);
    }
}
