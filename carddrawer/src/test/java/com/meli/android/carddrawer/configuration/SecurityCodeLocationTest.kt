package com.meli.android.carddrawer.configuration

import org.junit.Assert.assertEquals
import org.junit.Test

class SecurityCodeLocationTest {

    @Test
    fun `when getting FRONT of SecurityCodeLocation then it should return front`() {
        assertEquals(SecurityCodeLocation.FRONT, "front")
    }

    @Test
    fun `when getting BACK, of SecurityCodeLocation then it should return back`() {
        assertEquals(SecurityCodeLocation.BACK, "back")
    }

    @Test
    fun `when getting NONE of SecurityCodeLocation then it should return none`() {
        assertEquals(SecurityCodeLocation.NONE, "none")
    }

}