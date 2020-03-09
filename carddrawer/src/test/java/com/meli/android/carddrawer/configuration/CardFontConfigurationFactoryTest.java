package com.meli.android.carddrawer.configuration;

import com.meli.android.carddrawer.BasicRobolectricTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CardFontConfigurationFactoryTest extends BasicRobolectricTest {

    @Test
    public void getConfiguration_withDarkType_returnsDarkFontConfiguration() {
        CardFontConfiguration configuration = CardFontConfigurationFactory.getConfiguration(FontType.DARK_TYPE, 0, getContext());

        assertTrue(configuration instanceof DarkFontConfiguration);
    }

    @Test
    public void getConfiguration_withLightType_returnsLightFontConfiguration() {
        CardFontConfiguration configuration = CardFontConfigurationFactory.getConfiguration(FontType.LIGHT_TYPE, 0, getContext());

        assertTrue(configuration instanceof LightFontConfiguration);
    }

    @Test
    public void getConfiguration_withInvalidType_returnsDefaultFontConfiguration() {
        CardFontConfiguration configuration = CardFontConfigurationFactory.getConfiguration("invalid_type", 2, getContext());

        assertTrue(configuration instanceof DefaultFontConfiguration);
    }

}
