package com.meli.android.carddrawer.configuration

import android.text.TextPaint
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultFontConfigurationTest {

    private lateinit var defaultFontConfiguration: DefaultFontConfiguration
    private val color = 2

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        defaultFontConfiguration = DefaultFontConfiguration(2)
    }

    @Test
    fun `should return color`() {
        Assert.assertEquals(defaultFontConfiguration.color, color)
    }

    @Test
    fun `should test function setShadow`() {
        val textPaint = Mockito.mock(TextPaint::class.java)
        defaultFontConfiguration.setShadow(textPaint)
        Mockito.verify(textPaint, Mockito.never()).setShadowLayer(
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyInt()
        )
    }

}