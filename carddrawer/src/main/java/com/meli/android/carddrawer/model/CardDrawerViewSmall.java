package com.meli.android.carddrawer.model;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.format.NumberFormatter;

import java.util.Observable;

public class CardDrawerViewSmall extends CardDrawerView {

    public CardDrawerViewSmall(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerViewSmall(@NonNull final Context context,
                                @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerViewSmall(@NonNull final Context context,
                                @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout_small;
    }

    @Override
    public void update(@NonNull final CardUI source) {
        final boolean animate = !CardAnimationType.NONE.equals(source.getAnimationType());
        cardAnimator.colorCard(source.getCardBackgroundColor(), source.getAnimationType());
        updateCardLogo(cardLogoView, source, animate);
        setCardTextColor(source.getFontType(), source.getCardFontColor());
        cardNumber.startAnimation(getFadeInAnimation(getContext()));
    }

    @Override
    protected void updateIssuerLogo(final ImageSwitcher issuerLogoView, @NonNull final CardUI source,
                                    final boolean animate) {
      //nothing to do here
    }

    @VisibleForTesting
    @Override
    protected void updateCardInformation() {
        NumberFormatter cardNumberTextProcessor = new NumberFormatter(source.getCardNumberPattern());
        String number = cardNumberTextProcessor.formatEmptyText();
        if (card.getNumber() != null && !card.getNumber().isEmpty()) {
            cardNumberTextProcessor = new NumberFormatter(source.getCardNumberPattern());
            number = cardNumberTextProcessor.formatTextForVisualFeedback(card.getNumber());
        }

        final NumberFormatter secCodeFormatter = new NumberFormatter(source.getSecurityCodePattern());
        String secCode = secCodeFormatter.formatEmptyText();
        if (card.getSecCode() != null && !card.getSecCode().isEmpty()) {
            secCode = secCodeFormatter.formatTextForVisualFeedback(card.getSecCode());
        }

        codeBack.setText(secCode);
        cardNumber.setText(number);
    }

    @Override
    public void update(final Observable o, final Object arg) {
        updateCardInformation();
    }

    @Override
    public void setBehaviour(@Behaviour final int behaviour) {
        final LayoutParams frontParams = (LayoutParams) cardFrontLayout.getLayoutParams();
        final LayoutParams backParams = (LayoutParams) cardBackLayout.getLayoutParams();

        if (behaviour == Behaviour.RESPONSIVE) {
            frontParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            backParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            final int width = cardFrontLayout.getResources().getDimensionPixelSize(R.dimen.card_drawer_card_width);
            frontParams.width = width;
            backParams.width = width;
        }

        cardFrontLayout.setLayoutParams(frontParams);
        cardBackLayout.setLayoutParams(backParams);
    }

    @Override
    public void hideSecCircle() {
        //nothing to do here
    }

    @Override
    public void setCardTextColor(@NonNull @FontType final String fontType, @ColorInt final int fontColor) {
        cardNumber.init(fontType, getCardNumberPlaceHolder(), fontColor);
    }
}
