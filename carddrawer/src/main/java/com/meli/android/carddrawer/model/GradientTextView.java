package com.meli.android.carddrawer.model;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.CardFontConfiguration;
import com.meli.android.carddrawer.configuration.CardFontConfigurationFactory;
import com.meli.android.carddrawer.configuration.FontType;

public class GradientTextView extends android.support.v7.widget.AppCompatTextView {
    @FontType private String fontType;
    private String placeHolder;
    private int fontColor;

    public GradientTextView(Context context) {
        super(context);
        init();
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.fontType = FontType.NONE;
        this.placeHolder = " ";
        this.fontColor = ContextCompat.getColor(getContext(), R.color.card_drawer_card_default_font_color);
    }

    public void init(@FontType final String fontType, final String placeHolder, final int fontColor) {
        this.fontType = fontType;
        this.placeHolder = placeHolder;
        this.fontColor = fontColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final TextPaint paint = getPaint();
        paint.clearShadowLayer();
        paint.setShader(null);

        CardFontConfiguration configuration = getConfiguration();
        super.setTextColor(configuration.getColor());
        if (getText() != null && !getText().equals(placeHolder)) {
            // draw the shadow
            configuration.setShadow(paint);
            paint.setShader(null);
            super.onDraw(canvas);
        } else {
            super.onDraw(canvas);
        }
    }

    @VisibleForTesting
    @NonNull
    protected CardFontConfiguration getConfiguration() {
        return CardFontConfigurationFactory.getConfiguration(fontType, fontColor, getContext());
    }
}
