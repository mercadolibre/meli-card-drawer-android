package com.meli.android.carddrawer.configuration

import android.util.DisplayMetrics
import com.meli.android.carddrawer.configuration.base.ConfigurationTestBase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CardFontConfigurationFactoryTest : ConfigurationTestBase() {

    @Mock
    private lateinit var displayMock: DisplayMetrics

    @Before
    override fun init() {
        super.init()
        Mockito.`when`(contextMock.resources.displayMetrics).thenReturn(displayMock)
    }

    @Test
    fun `should test when getConfiguration with DARK_TYPE returns DarkFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.DARK_TYPE, 0, contextMock)
        assert(configuration is DarkFontConfiguration)
    }

    @Test
    fun `should test when getConfiguration with LIGHT_TYPE returns LightFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.LIGHT_TYPE, 0, contextMock)
        assert(configuration is LightFontConfiguration)
    }

    @Test
    fun `should test when getConfiguration with DARK_NO_SHADOW_TYPE returns DarkFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.DARK_NO_SHADOW_TYPE, 0, contextMock)
        assert(configuration is DarkFontConfiguration)
    }

    @Test
    fun `should test when getConfiguration with LIGHT_NO_SHADOW_TYPE returns LightFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.LIGHT_NO_SHADOW_TYPE, 0, contextMock)
        assert(configuration is LightFontConfiguration)
    }

    @Test
    fun `should test when getConfiguration with NONE returns DefaultFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.NONE, 0, contextMock)
        assert(configuration is DefaultFontConfiguration)
    }

}