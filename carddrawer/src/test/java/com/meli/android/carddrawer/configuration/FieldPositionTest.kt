package com.meli.android.carddrawer.configuration

import org.junit.Assert.assertEquals
import org.junit.Test

class FieldPositionTest {

    @Test
    fun `should return values FieldPosition front`() {
        assertEquals(FieldPosition.POSITION_FRONT, 1)
    }

    @Test
    fun `should return values FieldPosition back`() {
        assertEquals(FieldPosition.POSITION_BACK, 2)
    }

}