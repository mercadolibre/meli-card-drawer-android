package com.meli.android.carddrawer.app.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.meli.android.carddrawer.app.R;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.model.CardAnimationType;
import com.meli.android.carddrawer.model.CardUI;

public class VisaCardYellowConfiguration implements CardUI {
    private static final int NUMBER_CARD = 4;
    private static final int NUMBER_SEC_CODE = 3;
    private final Context context;

    public VisaCardYellowConfiguration(@NonNull Context context) {
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
        return FontType.NONE;
    }

    @Override
    public String getAnimationType() {
        return CardAnimationType.LEFT_TOP;
    }

    @Override
    public int getBankImageRes() {
        return R.drawable.card_drawer_mla_issuer_test01_white;
    }

    @Override
    public int getCardLogoImageRes() {
        return R.drawable.card_drawer_mla_pm_visa_white;
    }

    @Override
    public String getSecurityCodeLocation() {
        return SecurityCodeLocation.FRONT;
    }

    @Override
    public int getCardFontColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_app_black_font_color);
    }

    @Override
    public int getCardBackgroundColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_app_yellow_color);
    }

    @Override
    public int getSecurityCodePattern() {
        return NUMBER_SEC_CODE;
    }

    @Override
    public void setCardLogoImage(@NonNull ImageView cardLogo) {
        cardLogo.setImageResource(this.getCardLogoImageRes());
    }

    @Override
    public void setBankImage(@NonNull ImageView bankImage) {
        bankImage.setImageResource(this.getBankImageRes());
    }
}
