package com.meli.android.carddrawer.configuration

import android.text.TextPaint
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.TestBase
import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration
import com.meli.android.carddrawer.configuration.shadow.ShadowFontConfiguration
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class LightFontTest: TestBase() {

    private lateinit var lightFontConfiguration: LightFontConfiguration

    private fun initWithShadow() {
        val shadowFontConfiguration: ShadowConfiguration = ShadowFontConfiguration(contextMock)
        lightFontConfiguration = LightFontConfiguration(contextMock, shadowFontConfiguration)
    }

    @Test
    fun `when getting color with shadow font per parameter then return card_drawer_dark_font_empty_color`() {
        initWithShadow()
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_light_font_empty_color)
        assert(lightFontConfiguration.color == validColor)
    }

    @Test
    fun `when call function setShadow then call setShadowLayer`() {
        initWithShadow()
        val textPaint = mockk<TextPaint>(relaxed = true)
        lightFontConfiguration.setShadow(textPaint)
        verify() {
            textPaint.setShadowLayer(
                any(),
                any(),
                any(),
                any()
            )
        }
    }

    @Test
    fun`when getting color without shadow per parameter then return card_drawer_light_font_empty_color`() {
        lightFontConfiguration = LightFontConfiguration(contextMock)
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_light_font_empty_color)
        assert(lightFontConfiguration.color == validColor)
    }

    @Test
    fun`when call setShadow without shadow per parameter then call setShadowLayer`() {
        val textPaint = mockk<TextPaint>()
        lightFontConfiguration = LightFontConfiguration(contextMock)
        lightFontConfiguration.setShadow(textPaint)
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