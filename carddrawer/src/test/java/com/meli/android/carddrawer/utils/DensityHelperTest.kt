package com.meli.android.carddrawer.utils

import android.content.Context
import android.util.DisplayMetrics
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DensityHelperTest {

    @MockK(relaxed = true)
    private lateinit var contextMock: Context

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    private fun mockDensity(density: Float) {
        val metrics: DisplayMetrics = mockk<DisplayMetrics>().apply {
            this.density = density
        }
        every { contextMock.resources.displayMetrics } returns metrics
    }

    @Test
    fun `when getting name of density then it should 'xxxhdpi'`() {
        mockDensity(5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "xxxhdpi")
    }

    @Test
    fun `when getting name of density then it should 'xxhdpi'`() {
        mockDensity(3.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "xxhdpi")
    }

    @Test
    fun `when getting name of density then it should 'xhdpi'`() {
        mockDensity(2.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "xhdpi")
    }

    @Test
    fun `when getting name of density then it should 'hdpi'`() {
        mockDensity(1.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "hdpi")
    }

    @Test
    fun `when getting name of density then it should 'ldpi'`() {
        mockDensity(0.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "ldpi")
    }

    @Test
    fun `when getting name of density then it should 'mdpi'`() {
        mockDensity(1.0F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "mdpi")
    }

}