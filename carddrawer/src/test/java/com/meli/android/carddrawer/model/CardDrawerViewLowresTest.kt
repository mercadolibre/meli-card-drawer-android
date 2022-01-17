package com.meli.android.carddrawer.model;

import android.util.AttributeSet;
import android.view.View;

import android.widget.TextView;
import com.meli.android.carddrawer.R;

import com.meli.android.carddrawer.configuration.DefaultCardConfiguration;
import com.meli.android.carddrawer.configuration.FontType;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class CardDrawerViewLowresTest extends CardDrawerViewTest {

    @Override
    public void doBefore() {
        header = new CardDrawerViewLowres(getContext());
    }

    @Override
    public void setCardTextColor_initViews() {
        final CardDrawerView spyHeader = spy(header);

        final GradientTextView cardNumber = mock(GradientTextView.class);
        final GradientTextView cardName = mock(GradientTextView.class);
        final GradientTextView cardDate = mock(GradientTextView.class);
        final GradientTextView codeFront = mock(GradientTextView.class);

        ReflectionHelpers.setField(spyHeader, "cardNumber", cardNumber);
        ReflectionHelpers.setField(spyHeader, "cardName", cardName);
        ReflectionHelpers.setField(spyHeader, "cardDate", cardDate);
        ReflectionHelpers.setField(spyHeader, "codeFront", codeFront);

        final CardUI cardUI = new DefaultCardConfiguration(getContext());

        ReflectionHelpers.setField(spyHeader, "source", new PaymentCard(cardUI));

        final String fontType = FontType.LIGHT_TYPE;
        final int color = 2;

        spyHeader.setCardTextColor(fontType, color);

        verify(cardNumber).init(spyHeader.resolveFontType(fontType, true), "**** ****", color);
        verify(cardName).init(spyHeader.resolveFontType(fontType, false), cardUI.getNamePlaceHolder(), color);
        verify(cardDate).init(spyHeader.resolveFontType(fontType, false), cardUI.getExpirationPlaceHolder(), color);
        verify(codeFront).init(spyHeader.resolveFontType(fontType, false), "****", color);
    }

    @Override
    public void updateCardInformation_cardWithoutValuesSetsDefaultValues() {
        final Card card = mock(Card.class);
        when(card.getName()).thenReturn("");
        when(card.getNumber()).thenReturn("");
        when(card.getExpiration()).thenReturn("");
        when(card.getSecCode()).thenReturn("");
        ReflectionHelpers.setField(header, "card", card);
        header.updateCardInformation();
        final TextView cardNumber = ReflectionHelpers.getField(header, "cardNumber");
        final TextView cardName = ReflectionHelpers.getField(header, "cardName");
        final TextView codeFront = ReflectionHelpers.getField(header, "codeFront");
        final TextView codeBack = ReflectionHelpers.getField(header, "codeBack");

        assertEquals("**** ****", cardNumber.getText().toString());
        assertEquals("Nombre y Apellido", cardName.getText().toString());
        assertEquals("****", codeFront.getText().toString());
        assertEquals("****", codeBack.getText().toString());
    }

    @Override
    public void updateCardInformation_setsNumbersWithFormat() {
        final Card card = mock(Card.class);
        when(card.getName()).thenReturn("Juan Perez");
        when(card.getNumber()).thenReturn("000012346666");
        when(card.getSecCode()).thenReturn("555");
        ReflectionHelpers.setField(header, "card", card);
        header.updateCardInformation();
        final TextView cardNumber = ReflectionHelpers.getField(header, "cardNumber");
        final TextView cardName = ReflectionHelpers.getField(header, "cardName");
        final TextView codeFront = ReflectionHelpers.getField(header, "codeFront");
        final TextView codeBack = ReflectionHelpers.getField(header, "codeBack");

        assertEquals("6666 ****", cardNumber.getText().toString());
        assertEquals("Juan Perez", cardName.getText().toString());
        assertEquals("555*", codeFront.getText().toString());
        assertEquals("555*", codeBack.getText().toString());
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
        assertNotNull(ReflectionHelpers.getField(header, "cardAnimator"));
        assertNotNull(ReflectionHelpers.getField(header, "source"));
        assertNotNull(ReflectionHelpers.getField(header, "card"));
    }

    @Override
    public void updateIssuerLogo_setsLogo() {
        //No issuer logo on lowres
    }
}
