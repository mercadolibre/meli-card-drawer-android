package com.meli.android.carddrawer.app.model;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import com.meli.android.carddrawer.app.R;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.model.CardAnimationType;
import com.meli.android.carddrawer.model.CardUI;

/**
 * It is an example of configuration.
 * Defines the images in the set methods. Returns 0 for image resources.
 */

public class UrlTestConfiguration implements CardUI {
    private static final int NUMBER_CARD = 4;
    private static final int NUMBER_SEC_CODE = 3;
    private final Context context;

    public UrlTestConfiguration(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int[] getCardNumberPattern() {
        return new int[]{NUMBER_CARD, NUMBER_CARD, NUMBER_CARD, NUMBER_CARD};
    }

    @Override
    public String getNamePlaceHolder() {
        return context.getString(R.string.card_drawer_card_hint_name);
    }

    @Override
    public String getExpirationPlaceHolder() {
        return context.getString(R.string.card_drawer_card_hint_date);
    }

    @Override
    public String getFontType() {
        return FontType.LIGHT_TYPE;
    }

    @Override
    public String getAnimationType() {
        return CardAnimationType.LEFT_TOP;
    }

    @Override
    public int getBankImageRes() {
        return 0;
    }

    @Override
    public int getCardLogoImageRes() {
        return 0;
    }

    @Override
    public String getSecurityCodeLocation() {
        return SecurityCodeLocation.BACK;
    }

    @Override
    public int getCardFontColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_app_white_font_color);
    }

    @Override
    public int getCardBackgroundColor() {
        return Color.parseColor("#d18cce");
    }

    @Override
    public int getSecurityCodePattern() {
        return NUMBER_SEC_CODE;
    }

    @Nullable
    @Override
    public String getCardLogoImageUrl() {
        return "https://i.imgur.com/GaTELY9.png";
    }

    @Nullable
    @Override
    public String getBankImageUrl() {
        return "https://i.imgur.com/df3Bc7K.png";
    }
}