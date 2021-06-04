package com.meli.android.carddrawer.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.internal.TagDimensions;
import com.meli.android.carddrawer.model.customview.CustomViewConfiguration;

public class CardDrawerViewLowres extends CardDrawerView {

    public CardDrawerViewLowres(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerViewLowres(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerViewLowres(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @NonNull
    @Override
    protected CardConfiguration buildCardConfiguration(@NonNull final CardUI cardUI) {
        return new CardLowResConfiguration(cardUI);
    }

    @NonNull
    @Override
    public CustomViewConfiguration buildCustomViewConfiguration() {
        return new CustomViewConfiguration(Type.LOW, style);
    }

    @Override
    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout_lowres;
    }

    @Override
    protected void updateDate(@NonNull final CardUI cardUI) {
        //Nothing to do here
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final Resources resources = getResources();
        final float cardSizeMultiplier = getCardSizeMultiplier();

        setTextPixelSize(cardNumber, resources.getDimension(R.dimen.card_drawer_font_size) * cardSizeMultiplier);
        if (codeFront != null) {
            setTextPixelSize(codeFront, resources.getDimension(R.dimen.card_drawer_font_size_small) * cardSizeMultiplier);
        }
    }

    @Override
    protected TagDimensions getCardTagDimensions(final Resources resources, final float cardSizeMultiplier){
        return new TagDimensions(
            resources.getDimension(R.dimen.card_drawer_font_tag_small) * cardSizeMultiplier,
            Math.round(resources.getDimension(R.dimen.andes_tag_medium_margin) * cardSizeMultiplier),
            Math.round(resources.getDimension(R.dimen.card_drawer_tag_vertical_padding_small) * cardSizeMultiplier)
        );
    }
}
