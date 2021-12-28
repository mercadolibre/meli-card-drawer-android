package com.meli.android.carddrawer.model

import org.junit.Assert
import org.junit.Test

class CardAnimationTypeTest {

    @Test
    fun `when getting value of CardAnimationType RIGHT_BOTTOM then return 'right_bottom'`() {
        Assert.assertEquals(CardAnimationType.RIGHT_BOTTOM, "right_bottom")
    }

    @Test
    fun `when getting value of CardAnimationType LEFT_TOP then return 'left_top'`() {
        Assert.assertEquals(CardAnimationType.LEFT_TOP, "left_top")
    }

    @Test
    fun `when getting value of CardAnimationType NONE then return 'none'`() {
        Assert.assertEquals(CardAnimationType.NONE, "none")
    }

}