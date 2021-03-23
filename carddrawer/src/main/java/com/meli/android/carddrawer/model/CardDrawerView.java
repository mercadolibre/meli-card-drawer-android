package com.meli.android.carddrawer.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.ViewHelper;
import com.meli.android.carddrawer.configuration.AccountMoneyDefaultConfiguration;
import com.meli.android.carddrawer.configuration.AccountMoneyHybridConfiguration;
import com.meli.android.carddrawer.configuration.CardDrawerStyle;
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration;
import com.meli.android.carddrawer.configuration.FieldPosition;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.format.TypefaceHelper;
import com.meli.android.carddrawer.model.customview.CustomViewConfiguration;
import com.mercadolibre.android.picassodiskcache.PicassoDiskLoader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import kotlin.Unit;

@SuppressWarnings({ "PMD.ConstructorCallsOverridableMethod", "PMD.TooManyFields", "PMD.GodClass" })
public class CardDrawerView extends FrameLayoutWithDisableSupport implements Observer {
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

    protected CardUI source;
    protected Card card;
    protected View cardFrontLayout;
    protected View cardBackLayout;
    protected View frontBackground;
    protected ImageView cardFrontGradient;
    protected ImageView cardBackGradient;
    private ImageView overlayImage;
    private View accountMoneyDefaultOverlay;
    private View accountMoneyHybridOverlay;
    protected float defaultTextSize;
    protected float defaultCardWidth;
    protected CornerView safeZone;
    private View customView;
    protected CardConfiguration cardConfiguration;
    protected CardDrawerStyle style;

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
    protected CardConfiguration buildCardConfiguration() {
        return new CardHighResConfiguration(source);
    }

    @NonNull
    public CustomViewConfiguration getCustomViewConfiguration() {
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
        @Behaviour
        final int behaviour = typedArray.getInt(R.styleable.CardDrawerView_card_header_behaviour, Behaviour.REGULAR);
        final int styleIndex =
            typedArray.getInt(R.styleable.CardDrawerView_card_header_style, CardDrawerStyle.REGULAR.getValue());

        typedArray.recycle();
        defaultCardWidth = getResources().getDimension(R.dimen.card_drawer_card_width);
        defaultTextSize = getResources().getDimension(R.dimen.card_drawer_font_size);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardNumber.setLetterSpacing(NUMBER_LETTER_SPACING);
        }

        setInternalPadding(internalPadding);
        setBehaviour(behaviour);

        final float distance = getResources().getDimension(R.dimen.card_drawer_camera_distance);
        cardFrontLayout.setCameraDistance(distance);
        cardBackLayout.setCameraDistance(distance);

        cardAnimator = new CardAnimator(context, cardFrontLayout, cardBackLayout);
        source = new DefaultCardConfiguration(context);
        cardConfiguration = buildCardConfiguration();
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
        updateColor(source);
    }

    private void bindViews() {
        cardFrontLayout = findViewById(R.id.card_header_front);
        cardBackLayout = findViewById(R.id.card_header_back);

        frontBackground = cardFrontLayout.findViewById(R.id.front_background);
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
     */
    public void show(@NonNull final CardUI cardUI) {
        source = cardUI;
        cardConfiguration.updateSource(source);
        hideSecCircle();
        updateCardInformation();
        if (codeFront != null && cardConfiguration.canShow(codeFront)) {
            codeFront.setVisibility(View.VISIBLE);
        }
        update(source);
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
        final int securityCodeFieldPosition = source.getSecurityCodeLocation().equals(SecurityCodeLocation.FRONT)
            ? FieldPosition.POSITION_FRONT : FieldPosition.POSITION_BACK;
        cardAnimator.switchView(securityCodeFieldPosition);
        showSecCircle();
    }

    /**
     * Shows the security code position with paint animation.
     *
     * @param cardUI has the card style and animation type. Use NONE for show without animation.
     */
    public void showSecurityCode(@NonNull final CardUI cardUI) {
        source = cardUI;
        cardConfiguration.updateSource(source);
        int securityCodeFieldPosition = FieldPosition.POSITION_FRONT;
        if (cardConfiguration.canShow(codeFront)) {
            codeFront.setVisibility(View.VISIBLE);
        } else {
            securityCodeFieldPosition = FieldPosition.POSITION_BACK;
        }

        cardAnimator.switchViewWithoutAnimation(securityCodeFieldPosition);
        update(source);
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
        cardConfiguration.updateSource(source);
        updateColor(source);
        updateCardBackgroundGradient(source.getCardGradientColors());
        updateIssuerLogo(issuerLogoView, source, animate);
        updateCardLogo(cardLogoView, source, animate);
        if (!isInEditMode()) {
            updateFont(source.getCustomFont());
        }
        updateOverlay(overlayImage, source);
        internalSetStyle(source.getStyle() != null ? source.getStyle() : CardDrawerStyle.REGULAR);
        setCardTextColor(source.getFontType(), source.getCardFontColor());
        if (animate) {
            fadeInAnimateView(cardNumber);
            fadeInAnimateView(cardName);

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
        setUpCustomViewConfiguration();
    }

    private void setUpCustomViewConfiguration() {
        if (customView != null) {
            cardConfiguration.updateConfiguration((ConstraintLayout) cardFrontLayout);
            updateNumber();
            safeZone.addView(customView);
        } else if (safeZoneIsNotEmpty()) {
            cardConfiguration.resetConfiguration((ConstraintLayout) cardFrontLayout);
            updateNumber();
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
    public void setCardTextColor(@NonNull @FontType final String fontType, @ColorInt final int fontColor) {
        cardNumber.init(resolveFontType(fontType, true), getCardNumberPlaceHolder(), fontColor);
        cardName.init(resolveFontType(fontType, false), source.getNamePlaceHolder(), fontColor);
        if (cardDate != null) {
            cardDate.init(resolveFontType(fontType, false), source.getExpirationPlaceHolder(), fontColor);
        }
        if (codeFront != null) {
            codeFront.init(resolveFontType(fontType, false), getSecCodePlaceHolder(), fontColor);
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

    protected String getCardNumberPlaceHolder() {
        return getFormattedNumber("", source.getCardNumberPattern());
    }

    private String getSecCodePlaceHolder() {
        return getFormattedNumber("", source.getSecurityCodePattern());
    }

    @VisibleForTesting
    protected void updateCardInformation() {
        updateNumber();
        updateName();
        updateDate();
        updateSecCode();
    }

    private void updateColor(@NonNull final CardUI source) {
        final int disabledColor = source.getDisabledColor() != null ? source.getDisabledColor() : Color.GRAY;
        final int backgroundColor = isEnabled() ? source.getCardBackgroundColor() : disabledColor;
        cardAnimator.colorCard(backgroundColor, source.getAnimationType());
    }

    protected void updateNumber() {
        cardNumber.setText(getFormattedNumber(card.getNumber(), source.getCardNumberPattern()));
    }

    protected void updateName() {
        String name = source.getNamePlaceHolder();
        if (card.getName() != null && !card.getName().isEmpty()) {
            name = card.getName();
        }
        cardName.setText(name);
    }

    protected void updateDate() {
        String date = source.getExpirationPlaceHolder();
        if (card.getExpiration() != null && !card.getExpiration().isEmpty()) {
            date = card.getExpiration();
        }
        cardDate.setText(date);
    }

    protected void updateSecCode() {
        final String secCode = getFormattedNumber(card.getSecCode(), source.getSecurityCodePattern());

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
        codeFrontRedCircle.setVisibility(INVISIBLE);
        if (source.getSecurityCodeLocation().equals(SecurityCodeLocation.BACK)) {
            codeFront.setVisibility(View.GONE);
        }
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
        setPadding(getPaddingLeft(), padding, getPaddingRight(), padding);
    }

    /**
     * Sets card resize behaviour
     *
     * @param behaviour behaviour to set
     */
    public void setBehaviour(@Behaviour final int behaviour) {
        final LayoutParams frontParams = (LayoutParams) cardFrontLayout.getLayoutParams();
        final LayoutParams backParams = (LayoutParams) cardBackLayout.getLayoutParams();

        if (behaviour == Behaviour.RESPONSIVE) {
            frontParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            backParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            frontParams.width = Math.round(defaultCardWidth);
            backParams.width = Math.round(defaultCardWidth);
        }

        cardFrontLayout.setLayoutParams(frontParams);
        cardBackLayout.setLayoutParams(backParams);
    }

    public void setStyle(@NonNull final CardDrawerStyle style) {
        if (style == CardDrawerStyle.ACCOUNT_MONEY_DEFAULT) {
            show(new AccountMoneyDefaultConfiguration());
        } else if (style == CardDrawerStyle.ACCOUNT_MONEY_HYBRID) {
            show(new AccountMoneyHybridConfiguration());
        }
        this.style = style;
    }

    private void internalSetStyle(@NonNull final CardDrawerStyle style) {
        accountMoneyDefaultOverlay.setVisibility(style == CardDrawerStyle.ACCOUNT_MONEY_DEFAULT ? VISIBLE : GONE);
        accountMoneyHybridOverlay.setVisibility(style == CardDrawerStyle.ACCOUNT_MONEY_HYBRID ? VISIBLE : GONE);
        overlayImage.setVisibility(style == CardDrawerStyle.REGULAR ? VISIBLE : GONE);
        this.style = style;
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final float cardSizeMultiplier = (float) cardFrontLayout.getMeasuredWidth() / defaultCardWidth;

        final float newTextSize = defaultTextSize * cardSizeMultiplier;

        setTextPixelSize(cardName, newTextSize);
        setTextPixelSize(codeBack, newTextSize);
        setTextPixelSize(cardNumber, newTextSize);
        if (cardDate != null) {
            setTextPixelSize(cardDate, newTextSize);
        }
        if (codeFront != null) {
            setTextPixelSize(codeFront, getCodeFrontTextSize() * cardSizeMultiplier);
        }
    }

    protected void setTextPixelSize(@NonNull final TextView view, final float size) {
        view.post(() -> view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size));
    }

    @Override
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public Parcelable onSaveInstanceState() {
        // Construct bundle
        final Bundle bundle = new Bundle();
        // Store base view state
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putParcelable("card", card);
        cardAnimator.saveState(bundle);

        return bundle;
    }

    @Override
    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public void onRestoreInstanceState(Parcelable state) {
        // Checks if the state is the bundle we saved
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            final Card savedCard = bundle.getParcelable("card");
            card.fillCard(savedCard);
            updateCardInformation();
            cardAnimator.restoreState(bundle);

            state = bundle.getParcelable("instanceState");
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

    protected float getCodeFrontTextSize() {
        return defaultTextSize;
    }
}
