package com.meli.android.carddrawer.model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meli.android.carddrawer.CircleTransform;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.ViewHelper;
import com.meli.android.carddrawer.configuration.AccountMoneyDefaultConfiguration;
import com.meli.android.carddrawer.configuration.AccountMoneyHybridConfiguration;
import com.meli.android.carddrawer.configuration.CardDrawerStyle;
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration;
import com.meli.android.carddrawer.configuration.FieldPosition;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.format.CardDrawerFont;
import com.meli.android.carddrawer.format.TypefaceHelper;
import com.meli.android.carddrawer.internal.BaseExtensionsKt;
import com.meli.android.carddrawer.internal.TagDimensions;
import com.meli.android.carddrawer.model.customview.CustomViewConfiguration;
import com.mercadolibre.android.picassodiskcache.PicassoDiskLoader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import kotlin.Pair;
import kotlin.Unit;

@SuppressWarnings({"PMD.ConstructorCallsOverridableMethod", "PMD.TooManyFields", "PMD.GodClass"})
public class CardDrawerView extends FrameLayout implements Observer {
    private static final String STATE_CARD = "state_card";
    private static final String STATE_SUPER = "state_super";
    private static final float NUMBER_LETTER_SPACING = 0.125f;

    protected CardAnimator cardAnimator;

    protected ImageSwitcher issuerLogoView;
    protected ImageSwitcher cardLogoView;

    protected GradientTextView codeFront;
    protected TextView codeBack;
    private View codeFrontRedCircle;

    protected GradientTextView cardName;
    protected GradientTextView cardNumber;
    private GradientTextView cardDate;

    protected CardDrawerSource source;
    protected Card card;
    private View frontContainer;
    private View backContainer;
    protected ViewGroup cardFrontLayout;
    protected ViewGroup cardBackLayout;
    protected ViewGroup genericFrontLayout;
    private ViewGroup genericBackLayout;
    protected AppCompatTextView genericTitle;
    private AppCompatTextView genericSubtitle;
    private AppCompatTextView genericDescription;
    protected AppCompatTextView genericTagText;
    protected AppCompatTextView cardTagText;
    protected ImageView cardFrontGradient;
    protected ImageView cardBackGradient;
    protected ImageView overlayImage;
    private View accountMoneyDefaultOverlay;
    private View accountMoneyHybridOverlay;
    protected CornerView safeZone;
    private View customView;
    protected CardConfiguration cardConfiguration;
    protected CardDrawerStyle style;
    private ViewGroup containerBottomLabel;
    private BottomLabel bottomLabel;

    public CardDrawerView(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClipToPadding(false);
        init(context, attrs);
    }

    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout;
    }

    @NonNull
    protected CardConfiguration buildCardConfiguration(@NonNull final CardUI cardUI) {
        return new CardDefaultResConfiguration(cardUI);
    }

    @NonNull
    public CustomViewConfiguration buildCustomViewConfiguration() {
        return new CustomViewConfiguration(Type.HIGH, style);
    }

    /**
     * Initialize this header
     */
    protected void init(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        // Init references
        inflate(context, getLayout(), this);

        bindViews();

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardDrawerView);
        final int internalPadding = typedArray.getDimensionPixelSize(
                R.styleable.CardDrawerView_card_header_internal_padding,
                getResources().getDimensionPixelSize(R.dimen.card_drawer_layout_padding));
        @Behaviour final int behaviour = typedArray.getInt(R.styleable.CardDrawerView_card_header_behaviour, Behaviour.REGULAR);
        final int styleIndex =
            typedArray.getInt(R.styleable.CardDrawerView_card_header_style, CardDrawerStyle.REGULAR.getValue());

        typedArray.recycle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && cardNumber != null) {
            cardNumber.setLetterSpacing(NUMBER_LETTER_SPACING);
        }

        setInternalPadding(internalPadding);
        setBehaviour(behaviour);

        final float distance = getResources().getDimension(R.dimen.card_drawer_camera_distance);
        frontContainer.setCameraDistance(distance);
        backContainer.setCameraDistance(distance);

        cardAnimator = new CardAnimator(context, frontContainer, backContainer);
        final CardUI defaultCardConfiguration = new DefaultCardConfiguration(context);
        source = new PaymentCard(defaultCardConfiguration);
        cardConfiguration = buildCardConfiguration(defaultCardConfiguration);
        final Animation fadeIn = getFadeInAnimation(context);
        final Animation fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        fadeOut.setDuration(getResources().getInteger(R.integer.card_drawer_paint_animation_time));

        setupImageSwitcher(cardLogoView, fadeIn, fadeOut);
        if (issuerLogoView != null) {
            setupImageSwitcher(issuerLogoView, fadeIn, fadeOut);
        }
        card = new Card();
        card.addObserver(this);
        updateCardInformation();
        final CardDrawerStyle style = CardDrawerStyle.fromValue(styleIndex);
        if (style != CardDrawerStyle.REGULAR) {
            setStyle(style);
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        cardFrontLayout.setEnabled(enabled);
        cardBackLayout.setEnabled(enabled);
        genericFrontLayout.setEnabled(enabled);
        genericBackLayout.setEnabled(enabled);
        if (containerBottomLabel != null) {
            containerBottomLabel.setEnabled(enabled);
        }
        updateColor(source);
    }

    private void bindViews() {
        frontContainer = findViewById(R.id.card_drawer_front_container);
        backContainer = findViewById(R.id.card_drawer_back_container);
        cardFrontLayout = findViewById(R.id.card_header_front);
        cardBackLayout = findViewById(R.id.card_header_back);
        genericFrontLayout = findViewById(R.id.card_drawer_generic_front);
        genericBackLayout = findViewById(R.id.card_drawer_generic_back);
        containerBottomLabel = findViewById(R.id.card_drawer_container_bottom_label);

        genericTitle = genericFrontLayout.findViewById(R.id.generic_title);
        genericSubtitle = genericFrontLayout.findViewById(R.id.generic_subtitle);
        genericDescription = genericFrontLayout.findViewById(R.id.generic_description);
        genericTagText = genericFrontLayout.findViewById(R.id.card_tag);
        cardTagText = cardFrontLayout.findViewById(R.id.card_tag);

        overlayImage = cardFrontLayout.findViewById(R.id.cho_card_overlay);
        issuerLogoView = cardFrontLayout.findViewById(R.id.cho_card_issuer);
        cardLogoView = cardFrontLayout.findViewById(R.id.cho_card_logo);
        codeFront = cardFrontLayout.findViewById(R.id.cho_card_code_front);
        codeFrontRedCircle = cardFrontLayout.findViewById(R.id.cho_card_code_front_red_circle);
        cardNumber = cardFrontLayout.findViewById(R.id.cho_card_number);
        cardName = cardFrontLayout.findViewById(R.id.cho_card_name);
        cardDate = cardFrontLayout.findViewById(R.id.cho_card_date);
        accountMoneyDefaultOverlay = cardFrontLayout.findViewById(R.id.cho_am_default_overlay);
        accountMoneyHybridOverlay = cardFrontLayout.findViewById(R.id.cho_am_hybrid_overlay);

        codeBack = cardBackLayout.findViewById(R.id.cho_card_code_back);
        cardFrontGradient = cardFrontLayout.findViewById(R.id.cho_card_gradient_front);
        cardBackGradient = cardBackLayout.findViewById(R.id.cho_card_gradient_back);

        safeZone = cardFrontLayout.findViewById(R.id.safe_zone);

        if (containerBottomLabel != null) {
            bottomLabel = containerBottomLabel.findViewById(R.id.card_drawer_bottom_label);
        }
    }

    @NonNull
    protected Animation getFadeInAnimation(@NonNull final Context context) {
        // the logo image will have a fade effect when changing
        final Animation fadeIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        fadeIn.setDuration(context.getResources().getInteger(R.integer.card_drawer_paint_animation_time));
        fadeIn.setStartOffset(context.getResources().getInteger(R.integer.card_drawer_default_animation_offset));
        return fadeIn;
    }

    /**
     * Paints the front card with animation
     *
     * @param cardUI has the card style and animation type. Use NONE for show without animation.
     * <p>
     * Preserved so we don't break integrators
     */
    // TODO: Maybe we can deprecate this in favor of new show that allows
    //  CardDrawerSource with new tag functionality
    @SuppressWarnings("unused")
    public void show(@NonNull final CardUI cardUI) {
        show(new PaymentCard(cardUI));
    }

    /**
     * Paints the front card with animation
     *
     * @param paymentCard has the CardUI.
     * CardUI has the card style and animation type. Use NONE for show without animation.
     */
    private void show(@NonNull final PaymentCard paymentCard) {
        source = paymentCard;
        cardFrontLayout.setVisibility(VISIBLE);
        cardBackLayout.setVisibility(VISIBLE);
        genericFrontLayout.setVisibility(GONE);
        genericBackLayout.setVisibility(GONE);
        cardConfiguration.updateSource(paymentCard.getCardUI());
        showTag(source, cardTagText, cardFrontLayout);
        hideSecCircle();
        updateCardInformation();
        if (codeFront != null && cardConfiguration.canShow(codeFront)) {
            codeFront.setVisibility(View.VISIBLE);
        }
        update(paymentCard.getCardUI());
    }

    public void show(@NonNull final CardDrawerSource source) {
        BaseExtensionsKt.process(source, genericPaymentMethod -> {
            show(genericPaymentMethod);
            return Unit.INSTANCE;
        }, paymentCard -> {
            show(paymentCard);
            return Unit.INSTANCE;
        });
    }

    private void show(@NonNull final GenericPaymentMethod genericPaymentMethod) {
        source = genericPaymentMethod;
        cardFrontLayout.setVisibility(GONE);
        cardBackLayout.setVisibility(GONE);
        genericFrontLayout.setVisibility(VISIBLE);
        genericBackLayout.setVisibility(VISIBLE);
        final AppCompatImageView paymentMethodImage = genericFrontLayout.findViewById(R.id.generic_image);
        final AppCompatImageView frontBackground = genericFrontLayout.findViewById(R.id.generic_front_background);
        final AppCompatImageView backBackground = genericBackLayout.findViewById(R.id.generic_back_background);

        if (!TextUtils.isEmpty(genericPaymentMethod.getImageUrl())) {
            PicassoDiskLoader
                .get(getContext())
                .load(genericPaymentMethod.getImageUrl())
                .transform(new CircleTransform())
                .into(paymentMethodImage);
        }

        showTag(genericPaymentMethod, genericTagText, genericFrontLayout);

        genericPaymentMethod.setPaymentMethodImage(paymentMethodImage);

        showGenericText(genericPaymentMethod);

        applyBackground(frontBackground, genericPaymentMethod);
        applyBackground(backBackground, genericPaymentMethod);
    }

    private void applyBackground(@NonNull final AppCompatImageView appCompatImageView, @NonNull final GenericPaymentMethod genericPaymentMethod) {
        final List<String> gradientColors = genericPaymentMethod.getGradientColor();
        if (gradientColors != null) {
            final GradientDrawable gradientDrawable = ViewHelper.getGradientDrawable(getContext(), gradientColors);
            appCompatImageView.setImageDrawable(gradientDrawable);
        } else {
            appCompatImageView.getBackground().setColorFilter(genericPaymentMethod.getBackgroundColor(), PorterDuff.Mode.SRC_ATOP);
        }
    }

    protected void showGenericText(@NonNull final GenericPaymentMethod genericPaymentMethod) {
        setGenericText(genericTitle, genericPaymentMethod.getTitle());
        setGenericText(genericDescription, genericPaymentMethod.getDescription());
        setGenericText(genericSubtitle, genericPaymentMethod.getSubtitle());
    }

    protected void setGenericText(@NonNull final AppCompatTextView genericText, @Nullable final GenericPaymentMethod.Text text) {
        if (text != null) {
            genericText.setText(text.getText());
            genericText.setTextColor(text.getColor());
            setTypeface(genericText, text.getWeight());
            genericText.setVisibility(VISIBLE);
        } else {
            genericText.setVisibility(INVISIBLE);
        }
    }

    private void setTypeface(@NonNull final AppCompatTextView genericText, @Nullable final String weight) {
        if (weight != null) {
            genericText.setTypeface(genericText.getTypeface(), CardDrawerFont.from(weight).getStyle());
        }
    }

    /**
     * Shows the card tag if it's assigned.
     * @param source The source to get the tag from
     * @param layout Used to find the card tag views
     */
    @SuppressWarnings("VariableNotUsedInsideIf")
    private void showTag(@NonNull final CardDrawerSource source, @NonNull final AppCompatTextView tagText,
        @NonNull final ViewGroup layout) {
        final CardDrawerSource.Tag tag = source.getTag();
        final ViewGroup tagContainer = layout.findViewById(R.id.card_tag_container);
        if (tag != null) {
            tagText.getBackground().setColorFilter(tag.getBackgroundColor(), PorterDuff.Mode.SRC_ATOP);
            // This is because andes font-configurator is not initialized and therefore TypefaceHelper.set doesn't work
            tagText.setTypeface(tagText.getTypeface(), CardDrawerFont.from(tag.getWeight()).getStyle());
            tagText.setText(tag.getText());
            tagText.setTextColor(tag.getTextColor());
        }
        tagContainer.setVisibility(tag != null ? VISIBLE : GONE);
    }

    /**
     * Shows the front card without animation. Uses the saved card style or default
     */
    public void show() {
        hideSecCircle();
        cardAnimator.switchView(FieldPosition.POSITION_FRONT);
    }

    /**
     * Updates the header to match the position of the security code. Flip the card.
     */
    public void showSecurityCode() {
        BaseExtensionsKt.processPaymentCard(source, paymentCard -> {
            final int securityCodeFieldPosition =
                paymentCard.getCardUI().getSecurityCodeLocation().equals(SecurityCodeLocation.FRONT)
                ? FieldPosition.POSITION_FRONT : FieldPosition.POSITION_BACK;
            cardAnimator.switchView(securityCodeFieldPosition);
            showSecCircle();
            return Unit.INSTANCE;
        });
    }

    /**
     * Shows the security code position with paint animation.
     *
     * @param cardUI has the card style and animation type. Use NONE for show without animation.
     */
    public void showSecurityCode(@NonNull final CardUI cardUI) {
        source = new PaymentCard(cardUI);
        cardConfiguration.updateSource(cardUI);
        int securityCodeFieldPosition = FieldPosition.POSITION_FRONT;
        if (cardConfiguration.canShow(codeFront)) {
            codeFront.setVisibility(View.VISIBLE);
        } else {
            securityCodeFieldPosition = FieldPosition.POSITION_BACK;
        }

        cardAnimator.switchViewWithoutAnimation(securityCodeFieldPosition);
        update(cardUI);
        showSecCircle();
    }

    /**
     * Shows the back card without animation. Uses the saved card style or default
     */
    public void showBack() {
        cardAnimator.switchViewWithoutAnimation(FieldPosition.POSITION_BACK);
    }

    /**
     * Shows the front card without animation. Uses the saved card style or default
     */
    public void showFront() {
        cardAnimator.switchViewWithoutAnimation(FieldPosition.POSITION_FRONT);
    }

    public void setBottomLabel(@NonNull final Label label) {
        bottomLabel.setLabel(label);
    }

    public void showBottomLabel() {
        bottomLabel.show();
    }

    public void hideBottomLabel() {
        bottomLabel.hide();
    }

    protected void setupImageSwitcher(final ImageSwitcher imageSwitcher, final Animation fadeIn,
        final Animation fadeOut) {
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);
    }

    private void updateFont(@Nullable final Typeface customTypeface) {
        if (cardNumber != null) {
            TypefaceHelper.INSTANCE.set(cardNumber, customTypeface);
        }
        if (cardName != null) {
            cardName.setAllCaps(customTypeface == null);
            TypefaceHelper.INSTANCE.set(cardName, customTypeface);
        }
        if (cardDate != null) {
            TypefaceHelper.INSTANCE.set(cardDate, customTypeface);
        }
        if (codeFront != null) {
            TypefaceHelper.INSTANCE.set(codeFront, customTypeface);
        }
        if (codeBack != null) {
            TypefaceHelper.INSTANCE.set(codeBack, customTypeface);
        }
    }

    /**
     * Update the header with this data
     *
     * @param source has the card style and animation type. Use NONE for show without animation
     */
    public void update(@NonNull final CardUI source) {
        final boolean animate = !CardAnimationType.NONE.equals(source.getAnimationType());
        style = source.getStyle() != null ? source.getStyle() : CardDrawerStyle.REGULAR;
        cardConfiguration.updateSource(source);
        updateColor(this.source);
        updateCardBackgroundGradient(source.getCardGradientColors());
        updateIssuerLogo(issuerLogoView, source, animate);
        updateCardLogo(cardLogoView, source, animate);
        if (!isInEditMode()) {
            updateFont(source.getCustomFont());
        }
        updateOverlay(overlayImage, source);
        updateOverlayVisibility();
        setCardTextColor(source);
        if (animate) {
            fadeInAnimateView(cardNumber);
            if (cardName != null) {
                fadeInAnimateView(cardName);
            }
            if (cardDate != null) {
                fadeInAnimateView(cardDate);
            }
            if (codeFront != null && cardConfiguration.canShow(codeFront)) {
                fadeInAnimateView(codeFront);
            }
        }
    }

    private void fadeInAnimateView(@NonNull final View view) {
        cardConfiguration.canAnimate(view, v -> {
            v.startAnimation(getFadeInAnimation(getContext()));
            return Unit.INSTANCE;
        });
    }

    public void setCustomView(@Nullable final View view) {
        customView = view;
        BaseExtensionsKt.process(source, genericPaymentMethod -> {
            setUpCustomViewConfiguration(null);
            return Unit.INSTANCE;
        }, paymentCard -> {
            setUpCustomViewConfiguration(paymentCard.getCardUI());
            return Unit.INSTANCE;
        });
    }

    private void setUpCustomViewConfiguration(@Nullable final CardUI cardUI) {
        if (customView != null) {
            cardConfiguration.updateConfiguration((ConstraintLayout) cardFrontLayout);
            if (cardUI != null) {
                updateNumber(cardUI);
            }
            safeZone.addView(customView);
        } else if (safeZoneIsNotEmpty()) {
            cardConfiguration.resetConfiguration((ConstraintLayout) cardFrontLayout);
            if (cardUI != null) {
                updateNumber(cardUI);
            }
            safeZone.removeAllViews();
        }
    }

    private boolean safeZoneIsNotEmpty() {
        return safeZone.getChildCount() > 0;
    }

    @VisibleForTesting
    protected void updateOverlay(final ImageView overlayImage, @NonNull final CardUI source) {
        source.setOverlayImage(overlayImage);
    }

    @VisibleForTesting
    protected void updateIssuerLogo(final ImageSwitcher issuerLogoView, @NonNull final CardUI source,
        final boolean animate) {
        issuerLogoView.setAnimateFirstView(animate);
        final ImageView bankImageView = (ImageView) issuerLogoView.getNextView();
        //CardUI implementation can define the bank image in getBankImageRes or setBankImage method
        if (!TextUtils.isEmpty(source.getBankImageUrl())) {
            PicassoDiskLoader.get(getContext()).load(source.getBankImageUrl()).into(bankImageView);
        } else {
            bankImageView.setImageResource(source.getBankImageRes());
        }
        source.setBankImage(bankImageView);
        issuerLogoView.showNext();
    }

    @VisibleForTesting
    protected void updateCardLogo(final ImageSwitcher cardLogoView, @NonNull final CardUI source,
        final boolean animate) {
        cardLogoView.setAnimateFirstView(animate);
        final ImageView cardImageView = (ImageView) cardLogoView.getNextView();
        //CardUI implementation can define the card logo in getCardLogoRes or setCardLogo method
        if (!TextUtils.isEmpty(source.getCardLogoImageUrl())) {
            PicassoDiskLoader.get(getContext()).load(source.getCardLogoImageUrl()).into(cardImageView);
        } else {
            cardImageView.setImageResource(source.getCardLogoImageRes());
        }
        source.setCardLogoImage(cardImageView);
        cardLogoView.showNext();
    }

    /**
     * Paints all card fields with this color
     *
     * @param fontType  the font type
     * @param fontColor the font color
     **/
    @Deprecated
    public void setCardTextColor(@NonNull @FontType final String fontType, @ColorInt final int fontColor) {
        BaseExtensionsKt.processPaymentCard(source, paymentCard -> {
            setCardTextColor(paymentCard.getCardUI(), fontType, fontColor);
            return Unit.INSTANCE;
        });
    }

    private void setCardTextColor(@NonNull final CardUI cardUI) {
        setCardTextColor(cardUI, cardUI.getFontType(), cardUI.getCardFontColor());
    }

    protected void setCardTextColor(@NonNull final CardUI cardUI, @NonNull @FontType final String fontType,
        @ColorInt final int fontColor) {
        cardNumber.init(resolveFontType(fontType, true), getCardNumberPlaceHolder(cardUI), fontColor);
        if (cardName != null) {
            cardName.init(resolveFontType(fontType, false), cardUI.getNamePlaceHolder(), fontColor);
        }
        if (cardDate != null) {
            cardDate.init(resolveFontType(fontType, false), cardUI.getExpirationPlaceHolder(), fontColor);
        }
        if (codeFront != null) {
            codeFront.init(resolveFontType(fontType, false), getSecCodePlaceHolder(cardUI), fontColor);
        }
    }

    protected String resolveFontType(@NonNull @FontType final String type, final boolean showShadow) {
        if (!showShadow) {
            switch (type) {
            case FontType.DARK_TYPE: {
                return FontType.DARK_NO_SHADOW_TYPE;
            }
            case FontType.LIGHT_TYPE: {
                return FontType.LIGHT_NO_SHADOW_TYPE;
            }
            default:
                return type;
            }
        }
        return type;
    }

    protected String getCardNumberPlaceHolder(@NonNull final CardUI cardUI) {
        return getFormattedNumber("", cardUI.getCardNumberPattern());
    }

    private String getSecCodePlaceHolder(@NonNull final CardUI cardUI) {
        return getFormattedNumber("", cardUI.getSecurityCodePattern());
    }

    @VisibleForTesting
    protected void updateCardInformation() {
        BaseExtensionsKt.processPaymentCard(source, paymentCard -> {
            final CardUI cardUI = paymentCard.getCardUI();
            updateNumber(cardUI);
            updateName(cardUI);
            updateDate(cardUI);
            updateSecCode(cardUI);
            return Unit.INSTANCE;
        });
    }

    private void updateColor(@NonNull final CardDrawerSource source) {
        final int disabledColor =
            source.getDisabledBackgroundColor() != null ? source.getDisabledBackgroundColor() : Color.GRAY;
        final int backgroundColor = isEnabled() ? source.getBackgroundColor() : disabledColor;
        cardAnimator.colorCard(backgroundColor, source.getAnimationType());
    }

    protected void updateNumber(@NonNull final CardUI cardUI) {
        cardNumber.setText(getFormattedNumber(card.getNumber(), cardUI.getCardNumberPattern()));
    }

    protected void updateName(@NonNull final CardUI cardUI) {
        String name = cardUI.getNamePlaceHolder();
        if (card.getName() != null && !card.getName().isEmpty()) {
            name = card.getName();
        }
        cardName.setText(name);
    }

    protected void updateDate(@NonNull final CardUI cardUI) {
        String date = cardUI.getExpirationPlaceHolder();
        if (card.getExpiration() != null && !card.getExpiration().isEmpty()) {
            date = card.getExpiration();
        }
        cardDate.setText(date);
    }

    protected void updateSecCode(@NonNull final CardUI cardUI) {
        final String secCode = getFormattedNumber(card.getSecCode(), cardUI.getSecurityCodePattern());

        if (codeFront != null) {
            codeFront.setText(secCode);
        }

        if (codeBack != null) {
            codeBack.setText(secCode);
        }
    }

    /**
     * Shows the security code circle
     */
    public void showSecCircle() {
        if (cardConfiguration.canShow(codeFrontRedCircle)) {
            codeFrontRedCircle.setVisibility(VISIBLE);
        }
    }

    /**
     * Hides the security code circle
     */
    public void hideSecCircle() {
        BaseExtensionsKt.processPaymentCard(source, paymentCard -> {
            codeFrontRedCircle.setVisibility(INVISIBLE);
            if (paymentCard.getCardUI().getSecurityCodeLocation().equals(SecurityCodeLocation.BACK)) {
                codeFront.setVisibility(View.GONE);
            }
            return Unit.INSTANCE;
        });
    }

    // Use setOverlayImage from CardUi
    @Deprecated
    public void setOverlayImage(@Nullable @DrawableRes final Integer image) {
        if (image != null) {
            overlayImage.setImageResource(image);
        } else {
            overlayImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void update(final Observable o, final Object arg) {
        updateCardInformation();
    }

    public Card getCard() {
        return card;
    }

    /**
     * Sets the top and bottom internal padding
     *
     * @param padding padding to set
     */
    public void setInternalPadding(final int padding) {
        setPadding(getPaddingStart(), padding, getPaddingEnd(), padding);
    }

    /**
     * Sets card resize behaviour
     *
     * @param behaviour behaviour to set
     */
    public void setBehaviour(@Behaviour final int behaviour) {
        final LayoutParams frontParams = (LayoutParams) frontContainer.getLayoutParams();
        final LayoutParams backParams = (LayoutParams) backContainer.getLayoutParams();

        if (behaviour == Behaviour.RESPONSIVE) {
            frontParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            backParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            final int width = getResources().getDimensionPixelSize(R.dimen.card_drawer_card_width);
            frontParams.width = width;
            backParams.width = width;
        }

        frontContainer.setLayoutParams(frontParams);
        backContainer.setLayoutParams(backParams);
    }

    /**
     * Sets card from style with tag
     */
    public void setStyle(@NonNull final CardDrawerStyle style, @Nullable final CardDrawerSource.Tag tag) {
        this.style = style;
        updateCardConfigurationByStyle(tag);
    }

    public void setStyle(@NonNull final CardDrawerStyle style) {
        setStyle(style, null);
    }

    private void updateCardConfigurationByStyle(@Nullable final CardDrawerSource.Tag tag) {
        if (style == CardDrawerStyle.ACCOUNT_MONEY_DEFAULT) {
            show(new PaymentCard(new AccountMoneyDefaultConfiguration(), tag));
        } else if (style == CardDrawerStyle.ACCOUNT_MONEY_HYBRID) {
            show(new PaymentCard(new AccountMoneyHybridConfiguration(), tag));
        }
    }

    protected void updateOverlayVisibility() {
        accountMoneyDefaultOverlay.setVisibility(style == CardDrawerStyle.ACCOUNT_MONEY_DEFAULT ? VISIBLE : GONE);
        accountMoneyHybridOverlay.setVisibility(style == CardDrawerStyle.ACCOUNT_MONEY_HYBRID ? VISIBLE : GONE);
        overlayImage.setVisibility(style == CardDrawerStyle.REGULAR ? VISIBLE : GONE);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldW, final int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        final Resources resources = getResources();
        final float cardSizeMultiplier = getCardSizeMultiplier();
        final float newTextSize = resources.getDimension(R.dimen.card_drawer_font_size) * cardSizeMultiplier;

        setTextPixelSize(codeBack, newTextSize);
        setTextPixelSize(cardNumber, newTextSize);

        setTextPixelSize(genericTitle, resources.getDimension(R.dimen.card_drawer_font_generic_title) * cardSizeMultiplier);

        setCardTagTextPixelSize(resources, cardSizeMultiplier);

        if (genericSubtitle != null) {
            setTextPixelSize(genericSubtitle, resources.getDimension(R.dimen.card_drawer_font_generic_subtitle) * cardSizeMultiplier);
        }
        if (cardName != null) {
            setTextPixelSize(cardName, newTextSize);
        }
        if (cardDate != null) {
            setTextPixelSize(cardDate, newTextSize);
        }
        if (codeFront != null) {
            setTextPixelSize(codeFront, newTextSize);
        }
        if (genericDescription != null) {
            setTextPixelSize(genericDescription, resources.getDimension(R.dimen.card_drawer_font_generic_description) * cardSizeMultiplier);
        }
    }

    private void setCardTagTextPixelSize(final Resources resources, final float cardSizeMultiplier) {
        final TagDimensions cardTagDimensions = getCardTagDimensions(resources, cardSizeMultiplier);
        setTextPixelSize(genericTagText, cardTagDimensions.getFontSize(), cardTagDimensions.getPaddingH(),
            cardTagDimensions.getPaddingV());
        setTextPixelSize(cardTagText, cardTagDimensions.getFontSize(), cardTagDimensions.getPaddingH(),
            cardTagDimensions.getPaddingV());
    }

    protected TagDimensions getCardTagDimensions(final Resources resources, final float cardSizeMultiplier) {
        return new TagDimensions(resources.getDimension(R.dimen.card_drawer_font_tag) * cardSizeMultiplier,
            Math.round(resources.getDimension(R.dimen.andes_tag_medium_margin) * cardSizeMultiplier),
            Math.round(resources.getDimension(R.dimen.card_drawer_tag_vertical_padding) * cardSizeMultiplier)
        );
    }

    protected void setTextPixelSize(@NonNull final TextView view, final float size, final int paddingH, final int paddingV) {
        view.post(() -> view.setPadding(paddingH, paddingV, paddingH, paddingV));
        setTextPixelSize(view, size);
    }

    protected void setTextPixelSize(@NonNull final TextView view, final float size) {
        view.post(() -> view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size));
    }

    protected float getCardSizeMultiplier() {
        return getCurrentFrontView().getMeasuredWidth() / getResources().getDimension(R.dimen.card_drawer_card_width);
    }

    @Override
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public Parcelable onSaveInstanceState() {
        // Construct bundle
        final Bundle bundle = new Bundle();
        // Store base view state
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState());
        bundle.putParcelable(STATE_CARD, card);
        cardAnimator.saveState(bundle);

        return bundle;
    }

    @Override
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public void onRestoreInstanceState(Parcelable state) {
        // Checks if the state is the bundle we saved
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            final Card savedCard = bundle.getParcelable(STATE_CARD);
            card.fillCard(savedCard);
            updateCardInformation();
            cardAnimator.restoreState(bundle);

            state = bundle.getParcelable(STATE_SUPER);
        }
        // Pass base view state on to super
        super.onRestoreInstanceState(state);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ Behaviour.REGULAR, Behaviour.RESPONSIVE })
    public @interface Behaviour {
        int REGULAR = 0;
        int RESPONSIVE = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ Type.HIGH, Type.MEDIUM, Type.LOW })
    public @interface Type {
        int HIGH = 0;
        int MEDIUM = 1;
        int LOW = 2;
    }

    private void updateCardBackgroundGradient(@Nullable final List<String> gradientColors) {
        final GradientDrawable gradientDrawable = ViewHelper.getGradientDrawable(getContext(), gradientColors);
        cardFrontGradient.setImageDrawable(gradientDrawable);
        cardBackGradient.setImageDrawable(gradientDrawable);
    }

    protected String getFormattedNumber(@NonNull final String input, @NonNull final int... pattern) {
        return cardConfiguration.getFormattedNumber(input, pattern);
    }

    private View getCurrentFrontView() {
        final Pair<CardUI, GenericPaymentMethod> either = BaseExtensionsKt.either(source);
        if (either.getFirst() != null) {
            return cardFrontLayout;
        } else {
            return genericFrontLayout;
        }
    }
}
