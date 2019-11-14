package com.meli.android.carddrawer.model;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.FieldPosition;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;

public class CardDrawerViewMedium extends CardDrawerView {

    private ImageView arrow;
    private ImageView debitIcon;

    public CardDrawerViewMedium(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerViewMedium(@NonNull final Context context,
                                @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerViewMedium(@NonNull final Context context,
                                @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super.init(context, attrs);
        arrow = findViewById(R.id.cho_card_arrow);
        debitIcon = findViewById(R.id.cho_card_debit_icon);
    }

    @Override
    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout_medium;
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

    public void setDebitImage(@DrawableRes final int image) {
        debitIcon.setBackgroundResource(image);
        debitIcon.setVisibility(View.VISIBLE);
    }

    public void setArrowEnabled(final boolean enabled) {
        if (enabled) {
            arrow.setVisibility(View.VISIBLE);
        } else {
            arrow.setVisibility(View.GONE);
        }
    }

    @VisibleForTesting
    @Override
    protected void updateCardInformation() {
        updateNumber();
        updateName();
        updateSecCode();
    }

    @Override
    public void setCardTextColor(@NonNull @FontType final String fontType, @ColorInt final int fontColor) {
        cardNumber.init(fontType, getCardNumberPlaceHolder(), fontColor);
        cardName.init(fontType, source.getNamePlaceHolder(), fontColor);
        arrow.setColorFilter(fontColor);
        debitIcon.setColorFilter(fontColor);
    }

    @Override
    public void showSecurityCode() {
        if (source.getSecurityCodeLocation().equals(SecurityCodeLocation.BACK)) {
            final int securityCodeFieldPosition = FieldPosition.POSITION_BACK;
            cardAnimator.switchView(securityCodeFieldPosition);
            showSecCircle();
        }
    }

    @Override
    public void showSecurityCode(@NonNull final CardUI cardUI) {
        source = cardUI;
        if (source.getSecurityCodeLocation().equals(SecurityCodeLocation.BACK)) {
            final int securityCodeFieldPosition = FieldPosition.POSITION_BACK;
            cardAnimator.switchViewWithoutAnimation(securityCodeFieldPosition);
            update(source);
            showSecCircle();
        }
    }

    @Override
    public void hideSecCircle() {
        //nothing to do here
    }
}