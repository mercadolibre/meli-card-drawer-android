package com.meli.android.carddrawer.configuration

import android.text.TextPaint
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.base.ConfigurationTestBase
import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration
import com.meli.android.carddrawer.configuration.shadow.ShadowFontConfiguration
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class DarkFontConfigurationTest : ConfigurationTestBase() {

    private lateinit var darkFontConfiguration: DarkFontConfiguration

    private fun initWithShadow() {
        val shadowFontConfiguration: ShadowConfiguration = ShadowFontConfiguration(contextMock)
        darkFontConfiguration = DarkFontConfiguration(contextMock, shadowFontConfiguration)
    }

    @Test
    fun `when getting color with shadow font per parameter then return card_drawer_dark_font_empty_color`() {
        initWithShadow()
        val validColor = ContextCompat.getColor(contextMock, R.color.card_drawer_dark_font_empty_color)
        assert(darkFontConfiguration.color == validColor)
    }

    @Test
    fun `when call function setShadow then call setShadowLayer`() {
        initWithShadow()
        val textPaint = mockk<TextPaint>(relaxed = true)
        darkFontConfiguration.setShadow(textPaint)
        verify {
            textPaint.setShadowLayer(
                any(),
                any(),
                any(),
                any()
            )
        }
    }

    @Test
    fun`when getting color without shadow per parameter then return card_drawer_dark_font_empty_color`() {
        darkFontConfiguration = DarkFontConfiguration(contextMock)
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_dark_font_empty_color)
        assert(darkFontConfiguration.color == validColor)
    }

    @Test
    fun`when call setShadow without shadow per parameter then call setShadowLayer`() {
        val textPaint = mockk<TextPaint>()
        darkFontConfiguration = DarkFontConfiguration(contextMock)
        darkFontConfiguration.setShadow(textPaint)
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