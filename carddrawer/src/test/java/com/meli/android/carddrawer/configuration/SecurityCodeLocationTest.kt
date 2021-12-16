package com.meli.android.carddrawer.configuration

import org.junit.Assert.assertEquals
import org.junit.Test

class SecurityCodeLocationTest {

    @Test
    fun `should return value SecurityCodeLocation FRONT`() {
        assertEquals(SecurityCodeLocation.FRONT, "front")
    }

    @Test
    fun `should return value SecurityCodeLocation BACK`() {
        assertEquals(SecurityCodeLocation.BACK, "back")
    }

    @Test
    fun `should return value SecurityCodeLocation NONE`() {
        assertEquals(SecurityCodeLocation.NONE, "none")
    }

}