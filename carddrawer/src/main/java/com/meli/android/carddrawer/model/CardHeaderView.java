package com.meli.android.carddrawer.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.TextViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.DefaultCardConfiguration;
import com.meli.android.carddrawer.configuration.FieldPosition;
import com.meli.android.carddrawer.configuration.FontType;
import com.meli.android.carddrawer.configuration.SecurityCodeLocation;
import com.meli.android.carddrawer.format.MonospaceTypefaceSetter;
import com.meli.android.carddrawer.format.NumberFormatter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings({"PMD.ConstructorCallsOverridableMethod", "PMD.TooManyFields", "PMD.GodClass"})
public class CardHeaderView extends FrameLayout implements Observer {

    private static final int DELAY_MILLIS = 1000;
    private static final int CORNER_RATIO = 32;

    private CardAnimator cardAnimator;

    private ImageSwitcher issuerLogoView;
    private ImageSwitcher cardLogoView;

    protected GradientTextView codeFront;
    private TextView codeBack;
    private View codeFrontRedCircle;

    private GradientTextView cardName;
    private GradientTextView cardNumber;
    private GradientTextView cardDate;

    protected CardUI source;
    private Card card;
    private View cardFrontLayout;
    private View cardBackLayout;
    private int previousCardWidth;
    private GradientDrawable cardFrontGradient;
    private GradientDrawable cardBackGradient;

    public CardHeaderView(@NonNull final Context context) {
        this(context, null);
    }

    public CardHeaderView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardHeaderView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout;
    }

    /**
     * Initialize this header
     */
    private void init(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        // Init references
        inflate(context, getLayout(), this);

        bindViews();

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CardHeaderView);
        final int internalPadding = typedArray.getDimensionPixelSize(
                R.styleable.CardHeaderView_card_header_internal_padding,
                getResources().getDimensionPixelSize(R.dimen.card_drawer_layout_padding));
        @Behaviour final int behaviour = typedArray.getInt(
                R.styleable.CardHeaderView_card_header_behaviour, Behaviour.REGULAR);
        typedArray.recycle();

        setInternalPadding(internalPadding);
        setBehaviour(behaviour);

        final float distance = cardFrontLayout.getResources().getDimension(R.dimen.card_drawer_camera_distance);
        cardFrontLayout.setCameraDistance(distance);
        cardBackLayout.setCameraDistance(distance);

        cardAnimator = new CardAnimator(context, cardFrontLayout, cardBackLayout);
        source = new DefaultCardConfiguration(context);
        final Animation fadeIn = getFadeInAnimation(context);
        final Animation fadeOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        fadeOut.setDuration(context.getResources().getInteger(R.integer.card_drawer_paint_animation_time));

        setupImageSwitcher(cardLogoView, fadeIn, fadeOut);
        setupImageSwitcher(issuerLogoView, fadeIn, fadeOut);

        setMonospaceFont();
        card = new Card();
        card.addObserver(this);
        updateCardInformation();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateCornerRadius();
            }
        });
    }

    private void bindViews() {
        issuerLogoView = findViewById(R.id.cho_card_issuer);
        cardLogoView = findViewById(R.id.cho_card_logo);
        codeFront = findViewById(R.id.cho_card_code_front);
        codeBack = findViewById(R.id.cho_card_code_back);
        codeFrontRedCircle = findViewById(R.id.cho_card_code_front_red_circle);
        cardNumber = findViewById(R.id.cho_card_number);
        cardName = findViewById(R.id.cho_card_name);
        cardDate = findViewById(R.id.cho_card_date);
        cardFrontLayout = findViewById(R.id.card_header_front);
        cardBackLayout = findViewById(R.id.card_header_back);
        final ImageView cardFrontGradientView = findViewById(R.id.cho_card_gradient_front);
        cardFrontGradient = (GradientDrawable) cardFrontGradientView.getDrawable();
        final ImageView cardBackGradientView = findViewById(R.id.cho_card_gradient_back);
        cardBackGradient = (GradientDrawable) cardBackGradientView.getDrawable();
    }

    @NonNull
    private Animation getFadeInAnimation(@NonNull final Context context) {
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
        hideSecCircle();
        updateCardInformation();
        if (cardUI.getSecurityCodeLocation().equals(SecurityCodeLocation.FRONT)) {
            codeFront.setVisibility(View.VISIBLE);
        }
        update(source);
    }

    /**
     * Shows the front card without animation.
     * Uses the saved card style or default
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
        int securityCodeFieldPosition = FieldPosition.POSITION_FRONT;
        if (SecurityCodeLocation.FRONT.equals(cardUI.getSecurityCodeLocation())) {
            codeFront.setVisibility(View.VISIBLE);
        } else {
            securityCodeFieldPosition = FieldPosition.POSITION_BACK;
        }

        cardAnimator.switchViewWithoutAnimation(securityCodeFieldPosition);
        update(source);
        showSecCircle();
    }

    protected void setupImageSwitcher(final ImageSwitcher imageSwitcher, final Animation fadeIn, final Animation fadeOut) {
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);
    }

    private void setMonospaceFont() {
        setMonospaceFont(R.id.cho_card_name);
        setMonospaceFont(R.id.cho_card_date);
        setMonospaceFont(R.id.cho_card_code_front);
        setMonospaceFont(R.id.cho_card_code_back);
        setMonospaceFont(R.id.cho_card_number);
    }

    private void setMonospaceFont(@IdRes final int viewId) {
        final TextView view = findViewById(viewId);
        if (view != null) {
            MonospaceTypefaceSetter.setRobotoMono(getContext(), view);
        }
    }

    /**
     * Update the header with this data
     *
     * @param source has the card style and animation type. Use NONE for show without animation
     */
    public void update(@NonNull final CardUI source) {
        final boolean animate = !CardAnimationType.NONE.equals(source.getAnimationType());
        cardAnimator.colorCard(source.getCardBackgroundColor(), source.getAnimationType());
        updateIssuerLogo(issuerLogoView, source, animate);
        updateCardLogo(cardLogoView, source, animate);
        setCardTextColor(source.getFontType(), source.getCardFontColor());
        cardNumber.startAnimation(getFadeInAnimation(getContext()));
        cardName.startAnimation(getFadeInAnimation(getContext()));
        cardDate.startAnimation(getFadeInAnimation(getContext()));
        if (source.getSecurityCodeLocation().equals(SecurityCodeLocation.FRONT)) {
            codeFront.startAnimation(getFadeInAnimation(getContext()));
        }
    }

    @VisibleForTesting
    protected void updateIssuerLogo(final ImageSwitcher issuerLogoView, @NonNull final CardUI source,
                                    final boolean animate) {
        issuerLogoView.setAnimateFirstView(animate);
        final ImageView bankImageView = (ImageView) issuerLogoView.getNextView();
        //CardUI implementation can define the bank image in getBankImageRes or setBankImage method
        bankImageView.setImageResource(source.getBankImageRes());
        source.setBankImage(bankImageView);
        issuerLogoView.showNext();
    }

    @VisibleForTesting
    protected void updateCardLogo(final ImageSwitcher cardLogoView, @NonNull final CardUI source,
                                  final boolean animate) {
        cardLogoView.setAnimateFirstView(animate);
        final ImageView cardImageView = (ImageView) cardLogoView.getNextView();
        //CardUI implementation can define the card logo in getCardLogoRes or setCardLogo method
        cardImageView.setImageResource(source.getCardLogoImageRes());
        source.setCardLogoImage(cardImageView);
        cardLogoView.showNext();
    }

    /**
     * Paints all card fields with this color
     **/
    public void setCardTextColor(@NonNull @FontType final String fontType, @ColorInt final int fontColor) {
        cardNumber.init(fontType, getCardNumberPlaceHolder(), fontColor);
        cardName.init(fontType, source.getNamePlaceHolder(), fontColor);
        cardDate.init(fontType, source.getExpirationPlaceHolder(), fontColor);
        codeFront.init(fontType, getSecCodePlaceHolder(), fontColor);
    }

    private String getCardNumberPlaceHolder() {
        final NumberFormatter cardNumberTextProcessor = new NumberFormatter(source.getCardNumberPattern());
        return cardNumberTextProcessor.formatEmptyText();
    }

    private String getSecCodePlaceHolder() {
        final NumberFormatter cardNumberTextProcessor = new NumberFormatter(source.getSecurityCodePattern());
        return cardNumberTextProcessor.formatEmptyText();
    }

    @VisibleForTesting
    protected void updateCardInformation() {
        NumberFormatter cardNumberTextProcessor = new NumberFormatter(source.getCardNumberPattern());
        String number = cardNumberTextProcessor.formatEmptyText();
        if (card.getNumber() != null && !card.getNumber().isEmpty()) {
            cardNumberTextProcessor = new NumberFormatter(source.getCardNumberPattern());
            number = cardNumberTextProcessor.formatTextForVisualFeedback(card.getNumber());
        }

        String name = source.getNamePlaceHolder();
        if (card.getName() != null && !card.getName().isEmpty()) {
            name = card.getName();
        }

        String date = source.getExpirationPlaceHolder();
        if (card.getExpiration() != null && !card.getExpiration().isEmpty()) {
            date = card.getExpiration();
        }

        final NumberFormatter secCodeFormatter = new NumberFormatter(source.getSecurityCodePattern());
        String secCode = secCodeFormatter.formatEmptyText();
        if (card.getSecCode() != null && !card.getSecCode().isEmpty()) {
            secCode = secCodeFormatter.formatTextForVisualFeedback(card.getSecCode());
        }

        cardNumber.setText(number);
        cardName.setText(name);
        cardDate.setText(date);
        codeFront.setText(secCode);
        codeBack.setText(secCode);
    }

    /**
     * Shows the security code circle
     */
    public void showSecCircle() {
        if (source.getSecurityCodeLocation().equals(SecurityCodeLocation.FRONT)) {
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

    @Override
    public void update(final Observable o, final Object arg) {
        updateCardInformation();
    }

    public Card getCard() {
        return card;
    }

    /**
     * Sets the top and bottom internal padding
     */
    public void setInternalPadding(final int padding) {
        cardFrontLayout.setPadding(cardFrontLayout.getPaddingLeft(), padding,
                cardFrontLayout.getPaddingRight(), padding);
        cardBackLayout.setPadding(cardBackLayout.getPaddingLeft(), padding,
                cardBackLayout.getPaddingRight(), padding);
    }

    /**
     * Sets card resize behaviour
     */
    public void setBehaviour(@Behaviour final int behaviour) {
        final LayoutParams frontParams = (LayoutParams) cardFrontLayout.getLayoutParams();
        final LayoutParams backParams = (LayoutParams) cardBackLayout.getLayoutParams();

        if (behaviour == Behaviour.RESPONSIVE) {
            frontParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            backParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            final int width = cardFrontLayout.getResources().getDimensionPixelSize(R.dimen.card_drawer_card_width);
            frontParams.width = width;
            backParams.width = width;
        }

        //Recalculate card name text size
        TextViewCompat.setAutoSizeTextTypeWithDefaults(cardName, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TextViewCompat.setAutoSizeTextTypeWithDefaults(cardName, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
            }
        }, DELAY_MILLIS);

        cardFrontLayout.setLayoutParams(frontParams);
        cardBackLayout.setLayoutParams(backParams);
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
    @IntDef({Behaviour.REGULAR, Behaviour.RESPONSIVE})
    public @interface Behaviour {
        int REGULAR = 0;
        int RESPONSIVE = 1;
    }

    private void calculateCornerRadius() {
        if (cardFrontLayout.getWidth() != previousCardWidth) {
            previousCardWidth = cardFrontLayout.getWidth();
            final float cornerRadius = (float) previousCardWidth / CORNER_RATIO;
            cardFrontGradient.setCornerRadius(cornerRadius);
            cardBackGradient.setCornerRadius(cornerRadius);
        }
    }
}

