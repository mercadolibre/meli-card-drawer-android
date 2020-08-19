package com.meli.android.carddrawer.app.model;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.widget.ImageView;
import com.meli.android.carddrawer.app.R;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.model.CardAnimationType;
import com.meli.android.carddrawer.model.CardUI;

/**
 * It is an example of configuration.
 * Defines the images in the get res methods. Do nothing for set card/bank method.
 */

public class MasterCardConfiguration implements CardUI {
    private static final int NUMBER_SEC_CODE = 4;
    private final Context context;

    public MasterCardConfiguration(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public int[] getCardNumberPattern() {
        return new int[]{4, 6, 5};
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
        return R.drawable.card_drawer_app_bank_logo_macro;
    }

    @Override
    public int getCardLogoImageRes() {
        return R.drawable.card_drawer_app_card_logo_master_dark;
    }

    @Override
    public void setCardLogoImage(@NonNull ImageView cardLogo) {
        //Do nothing
    }

    @Override
    public void setBankImage(@NonNull ImageView bankImage) {
        //Do nothing
    }

    @Override
    public String getSecurityCodeLocation() {
        return SecurityCodeLocation.FRONT;
    }

    @Override
    public int getCardFontColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_app_white_font_color);
    }

    @Override
    public int getCardBackgroundColor() {
        return ContextCompat.getColor(context, R.color.card_drawer_app_light_green_color);
    }

    @Override
    public int getSecurityCodePattern() {
        return NUMBER_SEC_CODE;
    }
}