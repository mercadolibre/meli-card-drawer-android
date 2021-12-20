package com.meli.android.carddrawer.model

import org.junit.Assert
import org.junit.Test

class CardAnimationTypeTest {

    @Test
    fun verifyTypesValues() {
        Assert.assertEquals("right_bottom", CardAnimationType.RIGHT_BOTTOM)
        Assert.assertEquals("left_top", CardAnimationType.LEFT_TOP)
        Assert.assertEquals("none", CardAnimationType.NONE)
    }
}