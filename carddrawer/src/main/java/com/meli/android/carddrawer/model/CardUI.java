package com.meli.android.carddrawer.model;

import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.widget.ImageView;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import java.util.List;

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
     * @return the bank logo url to show
     */
    @Nullable
    default String getBankImageUrl() { return null; }

    /**
     * @return the card logo url to show
     */
    @Nullable
    default String getCardLogoImageUrl() { return null; }

    /**
     * @return the bank logo to show if url is null
     */
    @DrawableRes
    int getBankImageRes();

    /**
     * @return the card logo to show if url is null
     */
    @DrawableRes
    int getCardLogoImageRes();

    /**
     * Sets the card logo image to the Imageview
     * @param cardLogo the card logo imageview
     */
    default void setCardLogoImage(@NonNull final ImageView cardLogo) {}

    /**
     * Sets bank image to the Imageview
     * @param bankImage the card logo imageview
     */
    default void setBankImage(@NonNull final ImageView bankImage) {}

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
     * List of colors for the gradient that is drawn on top of the background color of the card.
     * one color: no gradient, will fill the whole card
     * two colors: start and end colors
     * three colors: start, center and end colors
     *
     * note: if colors opacity are 100% they will override the background color
     *
     * @return array of colors as string, ex: #ff000000
     */
    @Nullable @Size(min = 1, max = 3)
    default List<String> getCardGradientColors() { return null; }

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

    /**
     * @return custom font for the card
     */
    @Nullable
    default Typeface getCustomFont() { return null; }
}