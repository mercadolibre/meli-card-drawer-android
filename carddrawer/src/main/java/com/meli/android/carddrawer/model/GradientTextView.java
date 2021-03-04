package com.meli.android.carddrawer.model;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.CardFontConfiguration;
import com.meli.android.carddrawer.configuration.CardFontConfigurationFactory;
import com.meli.android.carddrawer.configuration.FontType;

public class GradientTextView extends AppCompatTextView {
    @FontType private String fontType;
    private String placeHolder;
    private int fontColor;

    public GradientTextView(@NonNull final Context context) {
        this(context, null);
    }

    public GradientTextView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull final Context context) {
        fontType = FontType.NONE;
        placeHolder = " ";
        fontColor = ContextCompat.getColor(context, R.color.card_drawer_card_default_font_color);
    }

    public void init(@FontType final String fontType, final String placeHolder, final int fontColor) {
        this.fontType = fontType;
        this.placeHolder = placeHolder;
        this.fontColor = fontColor;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final TextPaint paint = getPaint();
        paint.clearShadowLayer();
        paint.setShader(null);

        final CardFontConfiguration configuration = getConfiguration();
        setTextColor(configuration.getColor());
        if (getText() != null && !getText().equals(placeHolder)) {
            // draw the shadow
            configuration.setShadow(paint);
            paint.setShader(null);
        }
        super.onDraw(canvas);
    }

    @VisibleForTesting
    @NonNull
    protected CardFontConfiguration getConfiguration() {
        return CardFontConfigurationFactory.getConfiguration(fontType, fontColor, getContext());
    }
}