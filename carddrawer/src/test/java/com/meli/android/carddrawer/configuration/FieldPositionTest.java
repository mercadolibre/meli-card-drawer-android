package com.meli.android.carddrawer.configuration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldPositionTest {
    @Test
    public void verifyPositionValues() {
        assertEquals(1, FieldPosition.POSITION_FRONT);
        assertEquals(2, FieldPosition.POSITION_BACK);
    }
}
