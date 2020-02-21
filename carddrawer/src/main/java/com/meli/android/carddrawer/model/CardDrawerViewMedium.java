package com.meli.android.carddrawer.model;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.FieldPosition;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;

public class CardDrawerViewMedium extends CardDrawerView {

    private static final int CORNER_RATIO = 56;

    private ImageView arrow;
    private ImageView complementaryCardLogo;

    public CardDrawerViewMedium(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerViewMedium(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerViewMedium(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        cornerRatio = CORNER_RATIO;
        super.init(context, attrs);
        arrow = findViewById(R.id.cho_card_arrow);
        complementaryCardLogo = findViewById(R.id.cho_card_complementary_card_logo);
    }

    @Override
    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout_medium;
    }

    public void setComplementaryCardLogo(@DrawableRes final int image) {
        complementaryCardLogo.setBackgroundResource(image);
        complementaryCardLogo.setVisibility(View.VISIBLE);
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
        arrow.setColorFilter(getArrowColor(fontType, fontColor));
        complementaryCardLogo.setColorFilter(fontColor);
    }

    private int getArrowColor(@NonNull @FontType final String fontType, @ColorInt final int fontColor) {
        switch (fontType) {
            case FontType.DARK_TYPE:
                return ContextCompat.getColor(getContext(), R.color.card_drawer_dark_font_empty_color);
            case FontType.LIGHT_TYPE:
                return ContextCompat.getColor(getContext(), R.color.card_drawer_light_font_empty_color);
            default:
                return fontColor;
        }
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

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        //nothing to do here
    }
}