package com.meli.android.carddrawer.configuration

import org.junit.Assert.assertEquals
import org.junit.Test

class FieldPositionTest {

    @Test
    fun `when getting POSITION_FRONT of FieldPosition then return 1`() {
        assertEquals(FieldPosition.POSITION_FRONT, 1)
    }

    @Test
    fun `when getting POSITION_FRONT of POSITION_BACK then return 2`() {
        assertEquals(FieldPosition.POSITION_BACK, 2)
    }

}