package com.meli.android.carddrawer.model;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.meli.android.carddrawer.BasicRobolectricTest;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.FieldPosition;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ReflectionHelpers;

import java.lang.ref.WeakReference;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class CardAnimatorTest extends BasicRobolectricTest {
    CardAnimator cardAnimator;

    @Before
    public void doBefore() {
        cardAnimator = new CardAnimator(getContext(), mock(View.class), mock(View.class));
    }

    @Test
    public void constructor_setsValues() {
        View cardFrontLayout = mock(View.class);
        View cardBackLayout = mock(View.class);
        CardAnimator cardAnimator = new CardAnimator(getContext(), cardFrontLayout, cardBackLayout);
        View frontLayout = ReflectionHelpers.getField(cardAnimator, "cardFrontLayout");
        View backLayout = ReflectionHelpers.getField(cardAnimator, "cardBackLayout");
        int defaultColor = ReflectionHelpers.getField(cardAnimator, "defaultCardColor");
        WeakReference<Context> context = ReflectionHelpers.getField(cardAnimator, "context");
        assertNotNull(context);
        assertNotNull(defaultColor);
        assertEquals(cardFrontLayout, frontLayout);
        assertEquals(cardBackLayout, backLayout);
    }

    //********* switchView ********
    @Test
    public void switchView_withCurrentPositionFrontAndNewPositionFront_doesNothing() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_FRONT);
        cardAnimatorSpy.switchView(FieldPosition.POSITION_FRONT);
        int showingLayout = ReflectionHelpers.getField(cardAnimatorSpy, "showingLayout");
        verify(cardAnimatorSpy, never()).showFront();
        assertEquals(FieldPosition.POSITION_FRONT, showingLayout);
    }

    @Test
    public void switchView_withCurrentPositionFrontAndNewPositionBack_callsShowBackAndNewCurrentIsBack() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        doNothing().when(cardAnimatorSpy).showBack();
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_FRONT);
        cardAnimatorSpy.switchView(FieldPosition.POSITION_BACK);
        int showingLayout = ReflectionHelpers.getField(cardAnimatorSpy, "showingLayout");
        verify(cardAnimatorSpy).showBack();
        assertEquals(FieldPosition.POSITION_BACK, showingLayout);
    }

    @Test
    public void switchView_withCurrentPositionBackAndNewPositionBack_doesNothing() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_BACK);
        cardAnimatorSpy.switchView(FieldPosition.POSITION_BACK);
        int showingLayout = ReflectionHelpers.getField(cardAnimatorSpy, "showingLayout");
        verify(cardAnimatorSpy, never()).showBack();
        assertEquals(FieldPosition.POSITION_BACK, showingLayout);
    }

    @Test
    public void switchView_withCurrentPositionBackAndNewPositionFront_callsShowFrontAndCurrentIsFront() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        doNothing().when(cardAnimatorSpy).showFront();
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_BACK);
        cardAnimatorSpy.switchView(FieldPosition.POSITION_FRONT);
        int showingLayout = ReflectionHelpers.getField(cardAnimatorSpy, "showingLayout");
        verify(cardAnimatorSpy).showFront();
        assertEquals(FieldPosition.POSITION_FRONT, showingLayout);
    }

    @Test
    public void showFront_withCurrentPositionBack_callsFlipCardFront() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_BACK);
        cardAnimatorSpy.showFront();
        verify(cardAnimatorSpy).flipCardFront();
    }

    @Test
    public void showFront_withCurrentPositionFront_doNothing() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_FRONT);
        cardAnimatorSpy.showFront();
        verify(cardAnimatorSpy, never()).flipCardFront();
    }

    @Test
    public void showBack_withCurrentPositionFront_callsFlipCardBack() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_FRONT);
        cardAnimatorSpy.showBack();
        verify(cardAnimatorSpy).flipCardBack();
    }

    @Test
    public void showBack_withCurrentPositionBack_doesNothing() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_BACK);
        cardAnimatorSpy.showBack();
        verify(cardAnimatorSpy, never()).flipCardBack();
    }

    @Test
    public void doColorCard_withCardAnimationTypeNONE_callsSetColorFilters() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);

        ImageView frontBackground = new ImageView(getContext());
        ImageView frontReveal = new ImageView(getContext());
        ImageView backBackground = new ImageView(getContext());
        ImageView backReveal = new ImageView(getContext());

        View cardFrontLayout = mock(View.class);
        when(cardFrontLayout.findViewById(R.id.cho_card_image_front)).thenReturn(frontBackground);
        when(cardFrontLayout.findViewById(R.id.cho_card_image_front_reveal)).thenReturn(frontReveal);

        View cardBackLayout = mock(View.class);
        when(cardBackLayout.findViewById(R.id.cho_card_image_back)).thenReturn(backBackground);
        when(cardBackLayout.findViewById(R.id.cho_card_image_back_reveal)).thenReturn(backReveal);

        ReflectionHelpers.setField(cardAnimatorSpy, "cardFrontLayout", cardFrontLayout);
        ReflectionHelpers.setField(cardAnimatorSpy, "cardBackLayout", cardBackLayout);

        doNothing().when(cardAnimatorSpy).setColorFrontFilters(anyInt(), any(ImageView.class), any(ImageView.class));
        doNothing().when(cardAnimatorSpy).setColorBackFilters(anyInt(), any(ImageView.class), any(ImageView.class));

        cardAnimatorSpy.doColorCard(2, CardAnimationType.NONE);

        verify(cardAnimatorSpy).setColorFrontFilters(2, frontBackground, frontReveal);
        verify(cardAnimatorSpy).setColorBackFilters(2, backBackground, backReveal);
    }

    @Test
    public void doColorCard_withCardAnimationTypeLEFT_TOP_callsAnimateColorChangeForPosition() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ImageView frontBackground = new ImageView(getContext());
        ImageView frontReveal = new ImageView(getContext());
        ImageView backBackground = new ImageView(getContext());
        ImageView backReveal = new ImageView(getContext());

        View cardFrontLayout = mock(View.class);
        when(cardFrontLayout.findViewById(R.id.cho_card_image_front)).thenReturn(frontBackground);
        when(cardFrontLayout.findViewById(R.id.cho_card_image_front_reveal)).thenReturn(frontReveal);

        View cardBackLayout = mock(View.class);
        when(cardBackLayout.findViewById(R.id.cho_card_image_back)).thenReturn(backBackground);
        when(cardBackLayout.findViewById(R.id.cho_card_image_back_reveal)).thenReturn(backReveal);

        ReflectionHelpers.setField(cardAnimatorSpy, "cardFrontLayout", cardFrontLayout);
        ReflectionHelpers.setField(cardAnimatorSpy, "cardBackLayout", cardBackLayout);

        doNothing().when(cardAnimatorSpy).animateColorChange(any(ImageView.class), any(ImageView.class), anyInt(), anyString());

        cardAnimatorSpy.doColorCard(2, CardAnimationType.LEFT_TOP);

        verify(cardAnimatorSpy).animateColorChangeForPosition(frontBackground, frontReveal, backBackground, backReveal, 2, CardAnimationType.LEFT_TOP);
    }

    @Test
    public void animateColorChangeForPosition_withFrontPosition_callsAnimateColorChangeWithFrontImages() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ImageView frontBackground = new ImageView(getContext());
        ImageView frontReveal = new ImageView(getContext());
        ImageView backBackground = new ImageView(getContext());
        ImageView backReveal = new ImageView(getContext());

        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_FRONT);

        doNothing().when(cardAnimatorSpy).animateColorChange(any(ImageView.class), any(ImageView.class), anyInt(), anyString());

        cardAnimatorSpy.animateColorChangeForPosition(frontBackground, frontReveal, backBackground, backReveal, 2, CardAnimationType.LEFT_TOP);

        verify(cardAnimatorSpy).animateColorChange(frontReveal, frontBackground, 2, CardAnimationType.LEFT_TOP);
        verify(cardAnimatorSpy).setColorBackFilters(2, backBackground, backReveal);
    }

    @Test
    public void animateColorChangeForPosition_withBackPosition_callsAnimateColorChangeWithBackImages() {
        CardAnimator cardAnimatorSpy = spy(cardAnimator);
        ImageView frontBackground = new ImageView(getContext());
        ImageView frontReveal = new ImageView(getContext());
        ImageView backBackground = new ImageView(getContext());
        ImageView backReveal = new ImageView(getContext());

        ReflectionHelpers.setField(cardAnimatorSpy, "showingLayout", FieldPosition.POSITION_BACK);

        doNothing().when(cardAnimatorSpy).animateColorChange(any(ImageView.class), any(ImageView.class), anyInt(), anyString());

        cardAnimatorSpy.animateColorChangeForPosition(frontBackground, frontReveal, backBackground, backReveal, 2, CardAnimationType.LEFT_TOP);

        verify(cardAnimatorSpy).animateColorChange(backReveal, backBackground, 2, CardAnimationType.LEFT_TOP);
        verify(cardAnimatorSpy).setColorFrontFilters(2, frontBackground, frontReveal);
    }
}
