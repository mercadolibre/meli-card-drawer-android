package com.meli.android.carddrawer.configuration

import android.text.TextPaint
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.base.ConfigurationTestBase
import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration
import com.meli.android.carddrawer.configuration.shadow.ShadowFontConfiguration
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class DarkFontConfigurationTest : ConfigurationTestBase() {

    private lateinit var darkFontConfiguration: DarkFontConfiguration

    private fun initWithShadow() {
        val shadowFontConfiguration: ShadowConfiguration = ShadowFontConfiguration(contextMock)
        darkFontConfiguration = DarkFontConfiguration(contextMock, shadowFontConfiguration)
    }

    @Test
    fun `when getting color with shadow font per parameter then it should return card_drawer_dark_font_empty_color`() {
        initWithShadow()
        val validColor = ContextCompat.getColor(contextMock, R.color.card_drawer_dark_font_empty_color)
        assert(darkFontConfiguration.color == validColor)
    }

    @Test
    fun `when call function setShadow then it should call setShadowLayer`() {
        initWithShadow()
        val textPaint = Mockito.mock(TextPaint::class.java)
        darkFontConfiguration.setShadow(textPaint)
        Mockito.verify(textPaint).setShadowLayer(
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyInt()
        )
    }

    @Test
    fun`when getting color without shadow per parameter then it should return card_drawer_dark_font_empty_color`() {
        darkFontConfiguration = DarkFontConfiguration(contextMock)
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_dark_font_empty_color)
        assert(darkFontConfiguration.color == validColor)
    }

    @Test
    fun`when call setShadow without shadow per parameter then it should call setShadowLayer`() {
        val textPaint = Mockito.mock(TextPaint::class.java)
        darkFontConfiguration = DarkFontConfiguration(contextMock)
        darkFontConfiguration.setShadow(textPaint)
        Mockito.verify(textPaint, Mockito.never()).setShadowLayer(
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyInt()
        )
    }
}