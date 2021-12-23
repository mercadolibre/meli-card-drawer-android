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

class LightFontConfigurationTest: ConfigurationTestBase() {

    private lateinit var lightFontConfiguration: LightFontConfiguration

    private fun initWithShadow() {
        val shadowFontConfiguration: ShadowConfiguration = ShadowFontConfiguration(contextMock)
        lightFontConfiguration = LightFontConfiguration(contextMock, shadowFontConfiguration)
    }

    @Test
    fun `when getting color with shadow font per parameter then it should card_drawer_light_font_empty_color card_drawer_dark_font_empty_color`() {
        initWithShadow()
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_light_font_empty_color)
        assert(lightFontConfiguration.color == validColor)
    }

    @Test
    fun `when call function setShadow then it should call setShadowLayer`() {
        initWithShadow()
        val textPaint = Mockito.mock(TextPaint::class.java)
        lightFontConfiguration.setShadow(textPaint)
        Mockito.verify(textPaint).setShadowLayer(
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyInt()
        )
    }

    @Test
    fun`when getting color without shadow per parameter then it should return card_drawer_light_font_empty_color`() {
        lightFontConfiguration = LightFontConfiguration(contextMock)
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_light_font_empty_color)
        assert(lightFontConfiguration.color == validColor)
    }

    @Test
    fun`when call setShadow without shadow per parameter then it should call setShadowLayer`() {
        val textPaint = Mockito.mock(TextPaint::class.java)
        lightFontConfiguration = LightFontConfiguration(contextMock)
        lightFontConfiguration.setShadow(textPaint)
        Mockito.verify(textPaint, Mockito.never()).setShadowLayer(
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyFloat(),
            ArgumentMatchers.anyInt()
        )
    }
}