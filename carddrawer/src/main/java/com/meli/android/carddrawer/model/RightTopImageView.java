package com.meli.android.carddrawer.model;

import android.content.Context;
import android.graphics.Matrix;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import org.jetbrains.annotations.Nullable;

public final class RightTopImageView extends AppCompatImageView {

    public RightTopImageView(final Context context) {
        this(context, null);
    }

    public RightTopImageView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightTopImageView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected boolean setFrame(final int l, final int t, final int width, final int height) {
        final boolean changed = super.setFrame(l, t, width, height);
        if (changed && getDrawable() != null) {
            final int sourceHeight = getDrawable().getIntrinsicHeight();
            float scaleRatio = 1.0f;
            if (sourceHeight > height) {
                scaleRatio = (float) height / sourceHeight;
            }
            final Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(scaleRatio, scaleRatio, width, 0);
            setImageMatrix(scaleMatrix);
        }
        return changed;
    }
}