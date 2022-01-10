package com.meli.android.carddrawer.configuration

import com.meli.android.carddrawer.BaseTest
import org.junit.Test

class CardFontConfigurationFactoryTest : BaseTest() {

    @Test
    fun `when call function getConfiguration of CardFontConfigurationFactory with FontType DARK_TYPE per parameter then return DarkFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.DARK_TYPE, 0, contextMock)
        assert(configuration is DarkFontConfiguration)
    }

    @Test
    fun `when call function getConfiguration of CardFontConfigurationFactory with FontType LIGHT_TYPE per parameter then return LightFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.LIGHT_TYPE, 0, contextMock)
        assert(configuration is LightFontConfiguration)
    }

    @Test
    fun `when call function getConfiguration of CardFontConfigurationFactory with FontType DARK_NO_SHADOW_TYPE per parameter then return DarkFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.DARK_NO_SHADOW_TYPE, 0, contextMock)
        assert(configuration is DarkFontConfiguration)
    }

    @Test
    fun `when call function getConfiguration of CardFontConfigurationFactory with FontType LIGHT_NO_SHADOW_TYPE per parameter then return LightFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.LIGHT_NO_SHADOW_TYPE, 0, contextMock)
        assert(configuration is LightFontConfiguration)
    }

    @Test
    fun `when call function getConfiguration of CardFontConfigurationFactory with FontType NONE per parameter then return DefaultFontConfiguration`() {
        val configuration = CardFontConfigurationFactory.getConfiguration(FontType.NONE, 0, contextMock)
        assert(configuration is DefaultFontConfiguration)
    }

}