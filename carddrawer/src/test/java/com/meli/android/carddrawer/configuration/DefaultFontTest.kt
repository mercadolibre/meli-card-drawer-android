package com.meli.android.carddrawer.configuration

import android.text.TextPaint
import com.meli.android.carddrawer.BaseTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class DefaultFontTest: BaseTest() {

    private lateinit var defaultFontConfiguration: DefaultFontConfiguration
    private val color = 2

    override fun setUp() {
        super.setUp()
        defaultFontConfiguration = DefaultFontConfiguration(2)
    }

    @Test
    fun `when getting color value then return the same value passed by parameter`() {
        Assert.assertEquals(defaultFontConfiguration.color, color)
    }

    @Test
    fun `when call setShadow then call setShadowLayer`() {
        val textPaint = mockk<TextPaint>(relaxed = true)
        defaultFontConfiguration.setShadow(textPaint)
        verify(exactly = 0) {
            textPaint.setShadowLayer(
                any(),
                any(),
                any(),
                any()
            )
        }
    }

}