package com.meli.android.carddrawer.configuration

import org.junit.Assert.assertEquals
import org.junit.Test

class SecurityCodeLocationTest {

    @Test
    fun `when getting FRONT of SecurityCodeLocation then return front`() {
        assertEquals(SecurityCodeLocation.FRONT, "front")
    }

    @Test
    fun `when getting BACK of SecurityCodeLocation then return back`() {
        assertEquals(SecurityCodeLocation.BACK, "back")
    }

    @Test
    fun `when getting NONE of SecurityCodeLocation then return none`() {
        assertEquals(SecurityCodeLocation.NONE, "none")
    }

}