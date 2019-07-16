package com.meli.android.carddrawer.model;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.meli.android.carddrawer.R;

public class CardDrawerViewSmall extends CardDrawerView {

    private static final float LOGO_ASPECT_RATIO = 0.73f;

    public CardDrawerViewSmall(@NonNull final Context context) {
        this(context, null);
    }

    public CardDrawerViewSmall(@NonNull final Context context,
                                @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardDrawerViewSmall(@NonNull final Context context,
                                @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @LayoutRes
    protected int getLayout() {
        return R.layout.card_drawer_layout_small;
    }


}
