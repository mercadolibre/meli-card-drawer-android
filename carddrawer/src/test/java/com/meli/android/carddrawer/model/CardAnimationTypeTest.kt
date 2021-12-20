package com.meli.android.carddrawer.model;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CardAnimationTypeTest {
    @Test
    public void verifyTypesValues() {
        assertEquals("right_bottom", CardAnimationType.RIGHT_BOTTOM);
        assertEquals("left_top", CardAnimationType.LEFT_TOP);
        assertEquals("none", CardAnimationType.NONE);
    }
}
