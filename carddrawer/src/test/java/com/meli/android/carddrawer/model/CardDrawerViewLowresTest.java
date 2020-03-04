package com.meli.android.carddrawer.model;

import android.util.AttributeSet;
import android.view.View;

import com.meli.android.carddrawer.R;

import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class CardDrawerViewLowresTest extends CardDrawerViewTest {

    @Override
    public void doBefore() {
        header = new CardDrawerViewLowres(getContext());
    }

    @Override
    public void init_setsPaddingFromAttributes() {
        final int expectedPadding = 23;
        final AttributeSet attr = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.card_header_internal_padding, "23dp")
            .build();
        header = new CardDrawerViewLowres(getContext(), attr);

        assertEquals(expectedPadding, header.getPaddingTop());
        assertEquals(expectedPadding, header.getPaddingBottom());
    }

    @Override
    public void init_loadsViews() {
        assertNotNull(ReflectionHelpers.getField(header, "cardLogoView"));
        assertNotNull(ReflectionHelpers.getField(header, "codeFront"));
        assertNotNull(ReflectionHelpers.getField(header, "codeBack"));
        assertNotNull(ReflectionHelpers.getField(header, "cardNumber"));
        assertNotNull(ReflectionHelpers.getField(header, "cardName"));
        assertNotNull(ReflectionHelpers.getField(header, "cardDate"));
        assertNotNull(ReflectionHelpers.getField(header, "cardAnimator"));
        assertNotNull(ReflectionHelpers.getField(header, "source"));
        assertNotNull(ReflectionHelpers.getField(header, "card"));
    }

    @Override
    public void updateIssuerLogo_setsLogo() {
        //No issuer logo on lowres
    }
}
