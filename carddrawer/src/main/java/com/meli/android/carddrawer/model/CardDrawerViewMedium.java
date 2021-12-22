package com.meli.android.carddrawer.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.FontType;

public class CardDrawerViewMedium extends CardDrawerViewLowres {

    private ImageView arrow;
    private AppCompatTextView genericText;

    public CardDrawerViewMedium(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerViewMedium(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerViewMedium(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super.init(context, attrs);
        arrow = findViewById(R.id.cho_card_arrow);
        genericText = findViewById(R.id.generic_text);
    }

    @Override
    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout_medium;
    }

    public void setArrowEnabled(final boolean enabled) {
        if (enabled) {
            arrow.setVisibility(View.VISIBLE);
        } else {
            arrow.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideSecCircle() {
        //Nothing to do here
    }

    @Override
    public void showSecCircle() {
        //Nothing to do here
    }

    @NonNull
    @Override
    protected CardConfiguration buildCardConfiguration(@NonNull final CardUI cardUI) {
        return new CardMediumConfiguration(cardUI);
    }

    @Override
    protected void setCardTextColor(@NonNull final CardUI cardUI, @NonNull @FontType final String fontType,
        final int fontColor) {
        super.setCardTextColor(cardUI, fontType, fontColor);
        arrow.setColorFilter(getArrowColor(fontType, fontColor));
    }

    private int getArrowColor(@NonNull @FontType final String fontType, @ColorInt final int fontColor) {
        switch (fontType) {
            case FontType.DARK_TYPE:
                return ContextCompat.getColor(getContext(), R.color.card_drawer_dark_font_empty_color);
            case FontType.LIGHT_TYPE:
                return ContextCompat.getColor(getContext(), R.color.card_drawer_light_font_empty_color);
            default:
                return fontColor;
        }
    }

    @Override
    protected void showGenericText(@NonNull final GenericPaymentMethod genericPaymentMethod) {
        if (genericPaymentMethod.getDescription() != null) {
            setGenericText(genericText, genericPaymentMethod.getDescription());
        } else {
            setGenericText(genericText, genericPaymentMethod.getSubtitle());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setTextPixelSize(genericText, getResources().getDimension(R.dimen.card_drawer_font_generic_text) * getCardSizeMultiplier());
    }
}
