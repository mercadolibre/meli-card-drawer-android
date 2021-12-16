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
class DarkFontConfigurationTest : ConfigurationTestBase() {

    @Mock
    private lateinit var displayMock: DisplayMetrics

    private lateinit var darkFontConfiguration: DarkFontConfiguration

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
        darkFontConfiguration = DarkFontConfiguration(contextMock, shadowFontConfiguration)
    }

    @Test
    fun `should test getColor`() {
        initWithShadow()
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_dark_font_empty_color)
        assert(darkFontConfiguration.color == validColor)
    }

    @Test
    fun `should test setShadow`() {
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
    fun`should test getColor without pass shadow by parameter`() {
        darkFontConfiguration = DarkFontConfiguration(contextMock)
        val validColor =
            ContextCompat.getColor(contextMock, R.color.card_drawer_dark_font_empty_color)
        assert(darkFontConfiguration.color == validColor)
    }

    @Test
    fun`should test setShadow without pass shadow by parameter`() {
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