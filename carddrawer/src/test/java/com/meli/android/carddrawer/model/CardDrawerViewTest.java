package com.meli.android.carddrawer.model;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import com.meli.android.carddrawer.BasicRobolectricTest;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.TestUtils;
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration;
import com.meli.android.carddrawer.configuration.FieldPosition;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.model.animation.BottomLabelAnimation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


/**
 * Test for the card header.. many visual and animations changes can't be tested with just junit :(
 */
@RunWith(RobolectricTestRunner.class)
public class CardDrawerViewTest extends BasicRobolectricTest {
    public CardDrawerView header;

    @Before
    public void doBefore() {
        TestUtils.initTypefaceSetter();
        header = new CardDrawerView(getContext());
    }

    public CardDrawerSource.Tag getTestTag(){
        return new CardDrawerSource.Tag("Novo",
            Color.parseColor("#CCCCCC"),
            Color.parseColor("#FFFFFF"),
            "regular");
    }

    @Test
    public void init_setsPaddingFromAttributes() {
        final int expectedPadding = 23;
        final AttributeSet attr = Robolectric.buildAttributeSet()
            .addAttribute(R.attr.card_header_internal_padding, "23dp")
            .build();
        header = new CardDrawerView(getContext(), attr);

        assertEquals(expectedPadding, header.getPaddingTop());
        assertEquals(expectedPadding, header.getPaddingBottom());
    }

    @Test
    public void init_loadsViews() {
        assertNotNull(ReflectionHelpers.getField(header, "issuerLogoView"));
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

    @Test
    public void updateCardInformation_setsNumbersWithFormat() {
        Card card = mock(Card.class);
        when(card.getName()).thenReturn("Juan Perez");
        when(card.getNumber()).thenReturn("12346666");
        when(card.getExpiration()).thenReturn("10/19");
        when(card.getSecCode()).thenReturn("555");
        ReflectionHelpers.setField(header, "card", card);
        header.updateCardInformation();
        TextView cardNumber = ReflectionHelpers.getField(header, "cardNumber");
        TextView cardName = ReflectionHelpers.getField(header, "cardName");
        TextView cardDate = ReflectionHelpers.getField(header, "cardDate");
        TextView codeFront = ReflectionHelpers.getField(header, "codeFront");
        TextView codeBack = ReflectionHelpers.getField(header, "codeBack");

        assertEquals("1234  6666  ****  ****", cardNumber.getText().toString());
        assertEquals("Juan Perez", cardName.getText().toString());
        assertEquals("10/19", cardDate.getText().toString());
        assertEquals("555*", codeFront.getText().toString());
        assertEquals("555*", codeBack.getText().toString());
    }

    @Test
    public void updateCardInformation_cardWithoutValuesSetsDefaultValues() {
        Card card = mock(Card.class);
        when(card.getName()).thenReturn("");
        when(card.getNumber()).thenReturn("");
        when(card.getExpiration()).thenReturn("");
        when(card.getSecCode()).thenReturn("");
        ReflectionHelpers.setField(header, "card", card);
        header.updateCardInformation();
        TextView cardNumber = ReflectionHelpers.getField(header, "cardNumber");
        TextView cardName = ReflectionHelpers.getField(header, "cardName");
        TextView cardDate = ReflectionHelpers.getField(header, "cardDate");
        TextView codeFront = ReflectionHelpers.getField(header, "codeFront");
        TextView codeBack = ReflectionHelpers.getField(header, "codeBack");

        assertEquals("****  ****  ****  ****", cardNumber.getText().toString());
        assertEquals("Nombre y Apellido", cardName.getText().toString());
        assertEquals("MM/AA", cardDate.getText().toString());
        assertEquals("****", codeFront.getText().toString());
        assertEquals("****", codeBack.getText().toString());
    }

    @Test
    public void show_updatesInformationAndSetsColorsAndCallsAnimator() {
        CardDrawerView spyHeader = spy(header);
        CardUI cardUI = new DefaultCardConfiguration(getContext());
        CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        doNothing().when(spyHeader).updateCardInformation();
        ReflectionHelpers.setField(spyHeader, "cardAnimator", cardAnimatorMock);
        spyHeader.show(cardUI);
        verify(cardAnimatorMock).colorCard(anyInt(), anyString());
        verify(spyHeader).updateCardInformation();
    }

    @Test
    public void show_callsAnimator() {
        CardDrawerView spyHeader = spy(header);
        CardUI cardUI = new DefaultCardConfiguration(getContext());
        CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        ReflectionHelpers.setField(spyHeader, "cardAnimator", cardAnimatorMock);
        ReflectionHelpers.setField(spyHeader, "source", new PaymentCard(cardUI));
        spyHeader.show();

        verify(cardAnimatorMock).switchView(FieldPosition.POSITION_FRONT);
        verify(spyHeader).hideSecCircle();
    }

    @Test
    public void showSecurityCode_withFrontLocation_showsSecCodeCircleAndCallsAnimator() {
        final CardUI cardUI = mock(CardUI.class);
        when(cardUI.getSecurityCodeLocation()).thenReturn(SecurityCodeLocation.FRONT);
        when(cardUI.getAnimationType()).thenReturn(CardAnimationType.NONE);
        final CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        final View codeFront = ReflectionHelpers.getField(header, "codeFront");
        final View codeFrontRedCircle = ReflectionHelpers.getField(header, "codeFrontRedCircle");
        ReflectionHelpers.setField(header, "cardAnimator", cardAnimatorMock);
        ReflectionHelpers.setField(header, "source", new PaymentCard(cardUI));

        header.showSecurityCode();

        verify(cardAnimatorMock).switchView(FieldPosition.POSITION_FRONT);
        verifyNoMoreInteractions(cardAnimatorMock);
        assertEquals(View.VISIBLE, codeFront.getVisibility());
        assertEquals(View.VISIBLE, codeFrontRedCircle.getVisibility());
    }

    @Test
    public void showSecurityCode_withBackLocation_showsSecCodeCircleAndCallsAnimator() {
        final CardUI cardUI = mock(CardUI.class);
        when(cardUI.getSecurityCodeLocation()).thenReturn(SecurityCodeLocation.BACK);
        when(cardUI.getAnimationType()).thenReturn(CardAnimationType.NONE);
        final CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        final View codeBack = ReflectionHelpers.getField(header, "codeBack");
        ReflectionHelpers.setField(header, "cardAnimator", cardAnimatorMock);
        ReflectionHelpers.setField(header, "source", new PaymentCard(cardUI));

        header.showSecurityCode();

        verify(cardAnimatorMock).switchView(FieldPosition.POSITION_BACK);
        verifyNoMoreInteractions(cardAnimatorMock);
        assertEquals(View.VISIBLE, codeBack.getVisibility());
    }

    @Test
    public void showSecCode_withFrontPosition_callsSwitchViewWithFrontPosition() {
        CardDrawerView spyHeader = spy(header);
        GradientTextView codeFront = new GradientTextView(getContext());
        codeFront.setVisibility(View.INVISIBLE);
        TextView codeBack = new TextView((getContext()));
        codeBack.setVisibility(View.INVISIBLE);
        ReflectionHelpers.setField(spyHeader, "codeFront", codeFront);
        ReflectionHelpers.setField(spyHeader, "codeBack", codeBack);
        CardUI cardUI = mock(CardUI.class);
        when(cardUI.getSecurityCodeLocation()).thenReturn(SecurityCodeLocation.FRONT);
        when(cardUI.getCardLogoImageRes()).thenReturn(0);
        when(cardUI.getBankImageRes()).thenReturn(0);
        when(cardUI.getAnimationType()).thenReturn(CardAnimationType.NONE);
        CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        ReflectionHelpers.setField(spyHeader, "cardAnimator", cardAnimatorMock);
        doNothing().when(spyHeader).update(any(CardUI.class));

        spyHeader.showSecurityCode(cardUI);

        verify(cardAnimatorMock).switchViewWithoutAnimation(FieldPosition.POSITION_FRONT);
        verify(spyHeader).update(any(CardUI.class));
        verify(spyHeader).showSecCircle();
        assertEquals(View.VISIBLE, codeFront.getVisibility());
    }

    @Test
    public void showSecCode_withBackPosition_callsSwitchViewWithBackPosition() {
        final CardDrawerView spyHeader = spy(header);
        final CardUI source = mock(CardUI.class);
        when(source.getSecurityCodeLocation()).thenReturn(SecurityCodeLocation.BACK);
        when(source.getAnimationType()).thenReturn(CardAnimationType.NONE);
        doNothing().when(spyHeader).update(any(CardUI.class));
        final CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        final View codeBack = ReflectionHelpers.getField(spyHeader, "codeBack");
        ReflectionHelpers.setField(spyHeader, "cardAnimator", cardAnimatorMock);

        spyHeader.showSecurityCode(source);

        verify(cardAnimatorMock).switchViewWithoutAnimation(FieldPosition.POSITION_BACK);
        verifyNoMoreInteractions(cardAnimatorMock);
        assertEquals(View.VISIBLE, codeBack.getVisibility());
    }


    @Test
    public void showBack_callsSwitchViewWithoutAnimationUsesBackPosition() {
        CardDrawerView spyHeader = spy(header);
        GradientTextView codeFront = new GradientTextView(getContext());
        codeFront.setVisibility(View.INVISIBLE);
        TextView codeBack = new TextView((getContext()));
        codeBack.setVisibility(View.INVISIBLE);
        ReflectionHelpers.setField(spyHeader, "codeFront", codeFront);
        ReflectionHelpers.setField(spyHeader, "codeBack", codeBack);
        CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        ReflectionHelpers.setField(spyHeader, "cardAnimator", cardAnimatorMock);

        spyHeader.showBack();

        verify(cardAnimatorMock).switchViewWithoutAnimation(FieldPosition.POSITION_BACK);
    }

    @Test
    public void hideSecCircle_withFrontPosition_hidesSecCode() {
        final CardUI cardUI = mock(CardUI.class);
        when(cardUI.getSecurityCodeLocation()).thenReturn(SecurityCodeLocation.FRONT);
        when(cardUI.getAnimationType()).thenReturn(CardAnimationType.NONE);
        final View codeFront = ReflectionHelpers.getField(header, "codeFront");
        final View codeFrontRedCircle = ReflectionHelpers.getField(header, "codeFrontRedCircle");
        ReflectionHelpers.setField(header, "source", new PaymentCard(cardUI));

        header.hideSecCircle();

        assertEquals(View.VISIBLE, codeFront.getVisibility());
        assertEquals(View.INVISIBLE, codeFrontRedCircle.getVisibility());
    }

    @Test
    public void hideSecCircle_withBackPosition_hidesSecCode() {
        CardDrawerView spyHeader = spy(header);
        CardAnimator cardAnimatorMock = mock(CardAnimator.class);
        GradientTextView codeFront = new GradientTextView(getContext());
        codeFront.setVisibility(View.VISIBLE);
        CardUI cardUI = mock(CardUI.class);
        when(cardUI.getSecurityCodeLocation()).thenReturn(SecurityCodeLocation.BACK);
        when(cardUI.getAnimationType()).thenReturn(CardAnimationType.NONE);

        ReflectionHelpers.setField(spyHeader, "source", new PaymentCard(cardUI));
        ReflectionHelpers.setField(spyHeader, "cardAnimator", cardAnimatorMock);
        ReflectionHelpers.setField(spyHeader, "codeFront", codeFront);

        spyHeader.hideSecCircle();

        assertEquals(View.GONE, codeFront.getVisibility());
    }

    @Test
    public void setCardTextColor_initViews() {
        CardDrawerView spyHeader = spy(header);

        GradientTextView cardNumber = mock(GradientTextView.class);
        GradientTextView cardName = mock(GradientTextView.class);
        GradientTextView cardDate = mock(GradientTextView.class);
        GradientTextView codeFront = mock(GradientTextView.class);

        ReflectionHelpers.setField(spyHeader, "cardNumber", cardNumber);
        ReflectionHelpers.setField(spyHeader, "cardName", cardName);
        ReflectionHelpers.setField(spyHeader, "cardDate", cardDate);
        ReflectionHelpers.setField(spyHeader, "codeFront", codeFront);

        CardUI cardUI = new DefaultCardConfiguration(getContext());

        ReflectionHelpers.setField(spyHeader, "source", new PaymentCard(cardUI));

        String fontType = FontType.LIGHT_TYPE;
        int color = 2;

        spyHeader.setCardTextColor(fontType, color);

        verify(cardNumber).init(spyHeader.resolveFontType(fontType, true), "****  ****  ****  ****", color);
        verify(cardName).init(spyHeader.resolveFontType(fontType, false), cardUI.getNamePlaceHolder(), color);
        verify(cardDate).init(spyHeader.resolveFontType(fontType, false), cardUI.getExpirationPlaceHolder(), color);
        verify(codeFront).init(spyHeader.resolveFontType(fontType, false), "****", color);
    }


    @Test
    public void updateIssuerLogo_setsLogo() {
        ImageSwitcher issuerLogoView = mock(ImageSwitcher.class);
        ImageView bankImageView = mock(ImageView.class);
        CardUI source = mock(CardUI.class);
        when(source.getBankImageRes()).thenReturn(3);
        when(issuerLogoView.getNextView()).thenReturn(bankImageView);
        CardDrawerView spyHeader = spy(header);

        spyHeader.updateIssuerLogo(issuerLogoView, source, false);

        verify(bankImageView).setImageResource(3);
        verify(source).setBankImage(bankImageView);
    }

    @Test
    public void updateCardImage_setsLogo() {
        ImageSwitcher cardImageSwitcher = mock(ImageSwitcher.class);
        ImageView cardImageView = mock(ImageView.class);
        CardUI source = mock(CardUI.class);
        when(source.getCardLogoImageRes()).thenReturn(3);
        when(cardImageSwitcher.getNextView()).thenReturn(cardImageView);
        CardDrawerView spyHeader = spy(header);

        spyHeader.updateCardLogo(cardImageSwitcher, source, false);

        verify(cardImageView).setImageResource(3);
        verify(source).setCardLogoImage(cardImageView);
    }

    @Test
    public void updateOverlay() {
        ImageView overlayImageView = mock(ImageView.class);
        CardUI source = mock(CardUI.class);
        CardDrawerView spyHeader = spy(header);

        spyHeader.updateOverlay(overlayImageView, source);
        verify(source).setOverlayImage(overlayImageView);
        verifyNoMoreInteractions(source);
        verifyNoMoreInteractions(overlayImageView);
    }

    @Test
    public void showTagShouldNotShowTagWhenGenericPaymentMethodHasNoTag(){
        final CardDrawerView spyHeader = spy(header);
        final GenericPaymentMethod genericMethod = new GenericPaymentMethod(0,
            new GenericPaymentMethod.Text("Test", Color.parseColor("#000000")),
            null, null, null);
        spyHeader.show(genericMethod);
        assertEquals(View.GONE, spyHeader.genericFrontLayout.findViewById(R.id.card_tag_container).getVisibility());
    }

    @Test
    public void showTagShouldShowTagWhenGenericPaymentMethodHasTag() {
        final CardDrawerSource.Tag tag = getTestTag();
        final CardDrawerView spyHeader = spy(header);
        final GenericPaymentMethod genericMethod = new GenericPaymentMethod(0,
            new GenericPaymentMethod.Text("Test", Color.parseColor("#000000")),
            null,
            null,
            tag);
        spyHeader.show(genericMethod);
        assertEquals(View.VISIBLE, spyHeader.genericFrontLayout.findViewById(R.id.card_tag_container).getVisibility());
    }

    @Test
    public void showShouldShowTagWhenPaymentCardHasTag() {
        final CardDrawerSource.Tag tag = getTestTag();
        final CardDrawerView spyHeader = spy(header);
        final CardUI cardUI = new DefaultCardConfiguration(getContext());
        final PaymentCard paymentCard = new PaymentCard(cardUI ,tag);
        spyHeader.show(paymentCard);
        assertEquals(View.VISIBLE, spyHeader.cardFrontLayout.findViewById(R.id.card_tag_container).getVisibility());
    }

    @Test
    public void showShouldNotShowTagWhenPaymentCardHasNoTag() {
        final CardDrawerView spyHeader = spy(header);
        final CardUI cardUI = new DefaultCardConfiguration(getContext());
        final PaymentCard paymentCard = new PaymentCard(cardUI);
        spyHeader.show(paymentCard);
        assertEquals(View.GONE, spyHeader.cardFrontLayout.findViewById(R.id.card_tag_container).getVisibility());
    }

    @Test
    public void showShouldSetTestAndColorsFromTagForGenericPaymentMethod() {
        final CardDrawerSource.Tag tag = getTestTag();
        final CardDrawerView spyHeader = spy(header);
        final GenericPaymentMethod genericMethod = new GenericPaymentMethod(0,
            new GenericPaymentMethod.Text("Test", Color.parseColor("#000000")),
            null,
            null,
            tag);
        spyHeader.show(genericMethod);
        final AppCompatTextView textView = spyHeader.genericFrontLayout.findViewById(R.id.card_tag);
        assertEquals(tag.getText(), textView.getText());
        assertEquals(tag.getTextColor(), textView.getCurrentTextColor());
        assertEquals(new PorterDuffColorFilter(tag.getBackgroundColor(), PorterDuff.Mode.SRC_ATOP),
            textView.getBackground().getColorFilter());
    }

    @Test
    public void showBottomLabel() {
        final CardDrawerView spyHeader = spy(header);
        final BottomLabel bottomLabel = ReflectionHelpers.getField(header, "bottomLabel");
        final Label label = mock(Label.class);

        spyHeader.showCustomLabel(label);

        verify(spyHeader).showCustomLabel(label);
        assertEquals(View.VISIBLE, bottomLabel.getVisibility());
    }

    @Test
    public void hideBottomLabel() {
        final CardDrawerView spyHeader = spy(header);
        final BottomLabel bottomLabel = ReflectionHelpers.getField(header, "bottomLabel");

        spyHeader.hideCustomLabel();

        verify(spyHeader).hideCustomLabel();
        assertEquals(View.INVISIBLE, bottomLabel.getVisibility());
    }
}
