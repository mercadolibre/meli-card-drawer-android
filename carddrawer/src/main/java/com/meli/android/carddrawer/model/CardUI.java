package com.meli.android.carddrawer.model;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;


/**
 * Contains all configuration data for show the card.
 * <p>
 * ********************************
 * card_logo            bank_image
 * <p>
 * **** **** **** ****
 * <p>
 * NOMBRE Y APELLIDO        MM/AA
 * ********************************
 *
 * Provides two methods for loading images. Choose which one to implement (Ex: getBankImageRes or setBankImage)
 */
public interface CardUI {
    /**
     * @return the bank logo to show
     */
    @DrawableRes
    int getBankImageRes();

    /**
     * @return the card logo to show
     */
    @DrawableRes
    int getCardLogoImageRes();

    /**
     * Sets the card logo image to the Imageview
     * @param cardLogo
     */
    void setCardLogoImage(@NonNull ImageView cardLogo);

    /**
     * Sets bank image to the Imageview
     * @param bankImage
     */
    void setBankImage(@NonNull ImageView bankImage);

    /**
     * @return the security code position
     */
    @SecurityCodeLocation
    String getSecurityCodeLocation();

    /**
     * @return color for text
     */
    @ColorInt
    int getCardFontColor();

    /**
     * @return color for paint de card
     */
    @ColorInt
    int getCardBackgroundColor();

    /**
     * @return number long
     */
    int getSecurityCodePattern();

    /**
     * Ej: **** **** **** ****
     *
     * @return the group of numbers
     */
    int[] getCardNumberPattern();

    /**
     * Ej: NOMBRE Y APELLIDO
     *
     * @return the name place holder to show
     */
    String getNamePlaceHolder();

    /**
     * Ej: MM/AA
     *
     * @return the expiration date place holder to show
     */
    String getExpirationPlaceHolder();

    /**
     * @return the font type
     */
    @FontType
    String getFontType();

    /**
     * @return the animation type
     */
    @CardAnimationType
    String getAnimationType();
}
