package com.meli.android.carddrawer.configuration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.model.CardAnimationType;
import com.meli.android.carddrawer.model.CardUI;

public class DefaultCardConfiguration implements CardUI {

    private final Context context;
    private static final int DEFAULT_NUMBER = 4;

    public DefaultCardConfiguration(Context context) {
        this.context = context;
    }

    @Override
    public int[] getCardNumberPattern() {
        return new int[]{DEFAULT_NUMBER, DEFAULT_NUMBER, DEFAULT_NUMBER, DEFAULT_NUMBER};
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
        return FontType.DARK_TYPE;
    }

    @Override
    public String getAnimationType() {
        return CardAnimationType.RIGHT_BOTTOM;
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
    public void setCardLogoImage(@NonNull ImageView cardLogo) {
        cardLogo.setImageResource(0);
    }

    @Override
    public void setBankImage(@NonNull ImageView bankImage) {
        bankImage.setImageResource(0);
    }

    @Override
    public String getSecurityCodeLocation() {
        return SecurityCodeLocation.FRONT;
    }

    @Override
    public int getCardFontColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_card_default_font_color);
    }

    @Override
    public int getCardBackgroundColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_card_default_color);
    }

    @Override
    public int getSecurityCodePattern() {
        return DEFAULT_NUMBER;
    }
}
