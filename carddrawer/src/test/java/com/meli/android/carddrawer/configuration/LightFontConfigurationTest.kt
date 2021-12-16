package com.meli.android.carddrawer.configuration

import android.text.TextPaint
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.base.ConfigurationTestBase
import com.meli.android.carddrawer.configuration.shadow.ShadowConfiguration
import com.meli.android.carddrawer.configuration.shadow.ShadowFontConfiguration
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LightFontConfigurationTest: ConfigurationTestBase() {

    @Mock
    private lateinit var displayMock: DisplayMetrics

    private lateinit var lightFontConfiguration: LightFontConfiguration

    @Before
    override fun init() {
        super.init()
        Mockito.`when`(contextMock.resources.displayMetrics).thenReturn(displayMock)
        Mockito.`when`(contextMock.resources.getDimensionPixelSize(R.dimen.card_drawer_shadow_radius)).thenReturn(2)
        Mockito.`when`(ContextCompat.getColor(contextMock, R.color.card_drawer_number_shadow_color)).thenReturn(1929379840)
        Mockito.`when`(ContextCompat.getColor(contextMock, R.color.card_drawer_dark_font_empty_color)).thenReturn(-872415232)
    }

    private fun initWithShadow() {
        val shadowFontConfiguration: ShadowConfiguration = ShadowFontConfiguration(contextMock)
        lightFontConfiguration = LightFontConfiguration(contextMock, shadowFontConfiguration)
    }

    @Test
    fun `should test getColor`() {
        initWithShadow()
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_light_font_empty_color)
        assert(lightFontConfiguration.color == validColor)
    }

    @Test
    fun `should test setShadow`() {
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
    fun`should test getColor without pass shadow by parameter`() {
        lightFontConfiguration = LightFontConfiguration(contextMock)
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_light_font_empty_color)
        assert(lightFontConfiguration.color == validColor)
    }

    @Test
    fun`should test setShadow without pass shadow by parameter`() {
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