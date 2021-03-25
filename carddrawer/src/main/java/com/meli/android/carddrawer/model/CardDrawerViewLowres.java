package com.meli.android.carddrawer.model;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import android.util.AttributeSet;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.model.customview.CustomViewConfiguration;

public class CardDrawerViewLowres extends CardDrawerView {

    private float codeFrontTextSize;

    public CardDrawerViewLowres(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerViewLowres(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerViewLowres(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        codeFrontTextSize = getResources().getDimension(R.dimen.card_drawer_font_size_small);
        super.init(context, attrs);
    }

    @NonNull
    @Override
    protected CardConfiguration buildCardConfiguration() {
        return new CardLowResConfiguration(source);
    }

    @NonNull
    @Override
    public CustomViewConfiguration buildCustomViewConfiguration() {
        return new CustomViewConfiguration(Type.LOW, style);
    }

    @Override
    protected float getCodeFrontTextSize() {
        return codeFrontTextSize;
    }

    @Override
    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout_lowres;
    }

    @Override
    @VisibleForTesting
    protected void updateCardInformation() {
        updateNumber();
        updateName();
        updateSecCode();
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final float cardSizeMultiplier = (float) cardFrontLayout.getMeasuredWidth() / defaultCardWidth;
        final float newTextSize = defaultTextSize * cardSizeMultiplier;

        setTextPixelSize(cardNumber, newTextSize);
    }
}