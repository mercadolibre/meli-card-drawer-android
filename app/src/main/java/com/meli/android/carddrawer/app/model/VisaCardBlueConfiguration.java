package com.meli.android.carddrawer.app.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import com.meli.android.carddrawer.app.R;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.model.CardAnimationType;
import com.meli.android.carddrawer.model.CardUI;
import java.util.Arrays;
import java.util.List;

/**
 * It is an example of configuration.
 * Defines the images in the set methods. Returns 0 for image resources.
 */

public class VisaCardBlueConfiguration implements CardUI {
    private static final int NUMBER_CARD = 4;
    private static final int NUMBER_SEC_CODE = 3;
    private final Context context;

    public VisaCardBlueConfiguration(@NonNull Context context) {
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
        return ContextCompat.getColor(context, R.color.card_drawer_app_blue_color);
    }

    @Override
    public int getSecurityCodePattern() {
        return NUMBER_SEC_CODE;
    }

    @Override
    public void setCardLogoImage(@NonNull ImageView cardLogo) {
        Drawable cardDrawable = context.getResources().getDrawable(R.drawable.card_drawer_app_card_logo_visa);
        cardLogo.setImageDrawable(cardDrawable);
    }

    @Override
    public void setBankImage(@NonNull ImageView bankImage) {
        Drawable bankDrawable = context.getResources().getDrawable(R.drawable.card_drawer_app_bank_logo_galicia);
        bankImage.setImageDrawable(bankDrawable);
    }

    @Nullable
    @Override
    public List<String> getCardGradientColors() {
        return Arrays.asList("#50ffffff", "#90ff00ff", "#00ffffff");
    }
}