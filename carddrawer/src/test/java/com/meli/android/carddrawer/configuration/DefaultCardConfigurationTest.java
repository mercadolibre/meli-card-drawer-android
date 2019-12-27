package com.meli.android.carddrawer.configuration;

import android.support.v4.content.ContextCompat;

import com.meli.android.carddrawer.BasicRobolectricTest;
import com.meli.android.carddrawer.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class DefaultCardConfigurationTest extends BasicRobolectricTest {

    private DefaultCardConfiguration configuration;

    @Before
    public void doBefore() throws Exception {
        configuration = new DefaultCardConfiguration(getContext());
    }

    @Test
    public void getCardNumberPattern_returnsFourGroups() {
        assertEquals(4, configuration.getCardNumberPattern().length);

    }

    @Test
    public void getNamePlaceHolder_returnsWordingOk() {
        assertEquals("Nombre y Apellido", configuration.getNamePlaceHolder());
    }

    @Test
    public void getExpirationPlaceHolder_returnsWordingOk() {
        assertEquals("MM/AA", configuration.getExpirationPlaceHolder());
    }

    @Test
    public void getSecurityCodeLocation_returnsBack() {
        assertEquals(SecurityCodeLocation.BACK, configuration.getSecurityCodeLocation());
    }

    @Test
    public void getCardBackgroundColor_returnsColorOk() {
        int expectedColor = ContextCompat.getColor(getContext(), R.color.card_drawer_card_default_color);
        assertEquals(expectedColor, configuration.getCardBackgroundColor());
    }

    @Test
    public void getCardFontColor_returnsColorOk() {
        int expectedColor = ContextCompat.getColor(getContext(), R.color.card_drawer_card_default_font_color);
        assertEquals(expectedColor, configuration.getCardFontColor());
    }

    @Test
    public void getBankImageRes_returnsImageOk() {
        assertEquals(0, configuration.getBankImageRes());
    }

    @Test
    public void getCardLogoImageRes_returnsImageOk() {
        assertEquals(0, configuration.getBankImageRes());
    }

    @Test
    public void getSecurityCodePattern_returnsFourNumbers() {
        assertEquals(4, configuration.getSecurityCodePattern());
    }
}
