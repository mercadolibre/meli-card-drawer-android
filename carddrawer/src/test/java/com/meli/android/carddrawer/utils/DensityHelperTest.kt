package com.meli.android.carddrawer.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.mock


@RunWith(MockitoJUnitRunner::class)
class DensityHelperTest {

    @Mock
    private lateinit var contextMock: Context

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        val resourcesMock: Resources = mock(Resources::class.java)
        Mockito.`when`(contextMock.resources).thenReturn(resourcesMock)
    }

    private fun mockDensity(density: Float) {
        val metrics: DisplayMetrics = mock(DisplayMetrics::class.java).apply {
            this.density = density
        }
        Mockito.`when`(contextMock.resources.displayMetrics).thenReturn(metrics)
    }

    @Test
    fun `should return name of Density 'xxxhdpi'`() {
        mockDensity(5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "xxxhdpi")
    }

    @Test
    fun `should return name of Density 'xxhdpi'`() {
        mockDensity(3.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "xxhdpi")
    }

    @Test
    fun `should return name of Density 'xhdpi'`() {
        mockDensity(2.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "xhdpi")
    }

    @Test
    fun `should return name of Density 'hdpi'`() {
        mockDensity(1.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "hdpi")
    }

    @Test
    fun `should return name of Density 'ldpi'`() {
        mockDensity(0.5F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "ldpi")
    }

    @Test
    fun `should return name of Density 'mdpi'`() {
        mockDensity(1.0F)
        val name = DensityHelper.getName(contextMock)
        assertEquals(name, "mdpi")
    }

}