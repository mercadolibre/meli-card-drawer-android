package com.meli.android.carddrawer.model.customview;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.DrawableUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.meli.android.carddrawer.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class CustomSwitchCompat extends CompoundButton {
    private static final int THUMB_ANIMATION_DURATION = 250;

    private static final int TOUCH_MODE_IDLE = 0;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    public static final float HALF_POSITION = 0.5f;
    public static final float DEFAULT_THUMB_SIZE_MULTIPLIED = 1.5f;
    public static final float DEFAULT_HORIZONTAL_SKEW_FACTOR = -0.25f;

    // We force the accessibility events to have a class name of Switch, since screen readers
    // already know how to handle their events
    private static final String ACCESSIBILITY_EVENT_CLASS_NAME =
        "com.meli.android.carddrawer.model.customview.CustomSwitchCompat";

    private static final Property<CustomSwitchCompat, Float> THUMB_POS =
        new Property<CustomSwitchCompat, Float>(Float.class, "thumbPos") {
            @Override
            public Float get(final CustomSwitchCompat object) {
                return object.thumbPosition;
            }

            @Override
            public void set(final CustomSwitchCompat object, final Float value) {
                object.setThumbPosition(value);
            }
        };

    private Drawable thumbDrawable;

    private Drawable trackDrawable;

    private int thumbTextPadding;
    private int switchPadding;
    private CharSequence rightText;
    private CharSequence leftText;
    private boolean showThumbText;
    private boolean showTrackText;
    private float thumbSizeMultiplied = DEFAULT_THUMB_SIZE_MULTIPLIED;

    private int touchMode;
    private final int touchSlop;
    private float touchX;
    private float touchY;
    private final VelocityTracker velocityTracker = VelocityTracker.obtain();
    private final Rect trackTempRect = new Rect();
    private final int minFlingVelocity;

    float thumbPosition;

    /**
     * Width required to draw the switch track and thumb. Includes padding and optical bounds for both the track and
     * thumb.
     */
    private int switchWidth;

    /**
     * Height required to draw the switch track and thumb. Includes padding and optical bounds for both the track and
     * thumb.
     */
    private int switchHeight;

    /**
     * Width of the thumb's content region. Does not include padding or optical bounds.
     */
    private int thumbWidth;

    /**
     * Left bound for drawing the switch track and thumb.
     */
    private int switchLeft;

    /**
     * Top bound for drawing the switch track and thumb.
     */
    private int switchTop;

    /**
     * Right bound for drawing the switch track and thumb.
     */
    private int switchRight;

    /**
     * Bottom bound for drawing the switch track and thumb.
     */
    private int switchBottom;

    private final TextPaint thumbTextPaint;
    private final TextPaint trackTextPaint;
    private Layout thumbTextOnLayout;
    private Layout thumbTextOffLayout;
    private Layout trackTextOnLayout;
    private Layout trackTextOffLayout;
    ObjectAnimator mPositionAnimator;

    @SuppressWarnings("hiding")
    private final Rect mTempRect = new Rect();

    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };

    public CustomSwitchCompat(@NonNull final Context context) {
        this(context, null);
    }

    public CustomSwitchCompat(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, R.attr.switchStyle);
    }

    public CustomSwitchCompat(@NonNull final Context context, @Nullable final AttributeSet attrs,
        final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        thumbTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

        final Resources res = getResources();
        thumbTextPaint.density = res.getDisplayMetrics().density;
        trackTextPaint = new TextPaint(thumbTextPaint);

        final TypedArray a = context.obtainStyledAttributes(
            attrs, R.styleable.CustomSwitchCompat, defStyleAttr, 0);
        ViewCompat.saveAttributeDataForStyleable(this,
            context, R.styleable.CustomSwitchCompat, attrs,
            a, defStyleAttr, 0);

        thumbDrawable = a.getDrawable(R.styleable.CustomSwitchCompat_android_thumb);
        if (thumbDrawable != null) {
            thumbDrawable.setCallback(this);
        }
        trackDrawable = a.getDrawable(R.styleable.CustomSwitchCompat_android_track);
        if (trackDrawable != null) {
            trackDrawable.setCallback(this);
        }
        rightText = a.getText(R.styleable.CustomSwitchCompat_android_switchTextOn);
        leftText = a.getText(R.styleable.CustomSwitchCompat_android_switchTextOff);
        showThumbText = a.getBoolean(R.styleable.CustomSwitchCompat_android_showText, true);
        thumbTextPadding = a.getDimensionPixelSize(
            R.styleable.CustomSwitchCompat_android_thumbTextPadding, 0);
        switchPadding = a.getDimensionPixelSize(
            R.styleable.CustomSwitchCompat_android_switchPadding, 0);

        a.recycle();

        final ViewConfiguration config = ViewConfiguration.get(context);
        touchSlop = config.getScaledTouchSlop();
        minFlingVelocity = config.getScaledMinimumFlingVelocity();

        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setThumbTextSize(final float textSize) {
        thumbTextPaint.setTextSize(textSize);
        requestLayout();
        invalidate();
    }

    public void setTrackTextSize(final float textSize) {
        trackTextPaint.setTextSize(textSize);
        requestLayout();
        invalidate();
    }

    public void setThumbTextColor(final int color) {
        if (thumbTextPaint.getColor() != color) {
            thumbTextPaint.setColor(color);
            requestLayout();
            invalidate();
        }
    }

    public void setTrackTextColor(final int color) {
        if (trackTextPaint.getColor() != color) {
            trackTextPaint.setColor(color);
            requestLayout();
            invalidate();
        }
    }

    @SuppressWarnings("unused")
    public void setThumbTypeface(Typeface tf, final int style) {
        if (style > 0) {
            if (tf == null) {
                tf = Typeface.defaultFromStyle(style);
            } else {
                tf = Typeface.create(tf, style);
            }

            setThumbTypeface(tf);
            // now compute what (if any) algorithmic styling is needed
            final int typefaceStyle = tf != null ? tf.getStyle() : 0;
            final int need = style & ~typefaceStyle;
            thumbTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
            thumbTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? DEFAULT_HORIZONTAL_SKEW_FACTOR : 0);
        } else {
            thumbTextPaint.setFakeBoldText(false);
            thumbTextPaint.setTextSkewX(0);
            setThumbTypeface(tf);
        }
    }

    public void setThumbTypeface(final Typeface typeface) {
        if ((thumbTextPaint.getTypeface() != null && !thumbTextPaint.getTypeface().equals(typeface))
            || (thumbTextPaint.getTypeface() == null && typeface != null)) {
            thumbTextPaint.setTypeface(typeface);

            requestLayout();
            invalidate();
        }
    }

    @SuppressWarnings("unused")
    public void setTrackTypeface(Typeface tf, final int style) {
        if (style > 0) {
            if (tf == null) {
                tf = Typeface.defaultFromStyle(style);
            } else {
                tf = Typeface.create(tf, style);
            }

            setThumbTypeface(tf);
            // now compute what (if any) algorithmic styling is needed
            final int typefaceStyle = tf != null ? tf.getStyle() : 0;
            final int need = style & ~typefaceStyle;
            trackTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
            trackTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? DEFAULT_HORIZONTAL_SKEW_FACTOR : 0);
        } else {
            trackTextPaint.setFakeBoldText(false);
            trackTextPaint.setTextSkewX(0);
            setThumbTypeface(tf);
        }
    }

    public void setTrackTypeface(final Typeface typeface) {
        if ((trackTextPaint.getTypeface() != null && !trackTextPaint.getTypeface().equals(typeface))
            || (trackTextPaint.getTypeface() == null && typeface != null)) {
            trackTextPaint.setTypeface(typeface);

            requestLayout();
            invalidate();
        }
    }

    @SuppressWarnings("unused")
    public float getThumbSizeMultiplied() {
        return thumbSizeMultiplied;
    }

    public void setThumbSizeMultiplied(final float thumbSizeMultiplied) {
        this.thumbSizeMultiplied = thumbSizeMultiplied;
    }

    @SuppressWarnings("unused")
    public void setSwitchPadding(final int pixels) {
        switchPadding = pixels;
        requestLayout();
    }

    @SuppressWarnings("unused")
    public int getSwitchPadding() {
        return switchPadding;
    }

    @SuppressWarnings("unused")
    public void setThumbTextPadding(final int pixels) {
        thumbTextPadding = pixels;
        requestLayout();
    }

    @SuppressWarnings("unused")
    public int getThumbTextPadding() {
        return thumbTextPadding;
    }

    public void setTrackDrawable(final Drawable track) {
        if (trackDrawable != null) {
            trackDrawable.setCallback(null);
        }
        trackDrawable = track;
        if (track != null) {
            track.setCallback(this);
        }
        requestLayout();
    }

    @SuppressWarnings("unused")
    public void setTrackResource(final int resId) {
        setTrackDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    @SuppressWarnings("unused")
    public Drawable getTrackDrawable() {
        return trackDrawable;
    }

    public void setThumbDrawable(final Drawable thumb) {
        if (thumbDrawable != null) {
            thumbDrawable.setCallback(null);
        }
        thumbDrawable = thumb;
        if (thumb != null) {
            thumb.setCallback(this);
        }
        requestLayout();
    }

    @SuppressWarnings("unused")
    public void setThumbResource(final int resId) {
        setThumbDrawable(AppCompatResources.getDrawable(getContext(), resId));
    }

    @SuppressWarnings("unused")
    public Drawable getThumbDrawable() {
        return thumbDrawable;
    }

    @SuppressWarnings("unused")
    public CharSequence getRightText() {
        return rightText;
    }

    public void setRightText(final CharSequence text) {
        rightText = text;
        requestLayout();
    }

    @SuppressWarnings("unused")
    public CharSequence getLeftText() {
        return leftText;
    }

    public void setLeftText(final CharSequence text) {
        leftText = text;
        requestLayout();
    }

    public void setShowThumbText(final boolean showText) {
        if (showThumbText != showText) {
            showThumbText = showText;
            requestLayout();
        }
    }

    public void setShowTrackText(final boolean showText) {
        if (showTrackText != showText) {
            showTrackText = showText;
            requestLayout();
        }
    }

    @SuppressWarnings("unused")
    public boolean getShowText() {
        return showThumbText;
    }

    @Override
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        if (showThumbText) {
            if (thumbTextOnLayout == null) {
                thumbTextOnLayout = CardDrawerSwitchHelper.makeTextLayout(rightText, thumbTextPaint);
            }

            if (thumbTextOffLayout == null) {
                thumbTextOffLayout = CardDrawerSwitchHelper.makeTextLayout(leftText, thumbTextPaint);
            }
        }

        if (showTrackText) {
            if (trackTextOnLayout == null) {
                trackTextOnLayout = CardDrawerSwitchHelper.makeTextLayout(leftText, trackTextPaint);
            }

            if (trackTextOffLayout == null) {
                trackTextOffLayout = CardDrawerSwitchHelper.makeTextLayout(rightText, trackTextPaint);
            }
        }

        final Rect padding = mTempRect;
        final int thumbWidth;
        final int thumbHeight;
        if (thumbDrawable != null) {
            // Cached thumb width does not include padding.
            thumbDrawable.getPadding(padding);
            thumbWidth = thumbDrawable.getIntrinsicWidth() - padding.left - padding.right;
            thumbHeight = thumbDrawable.getIntrinsicHeight();
        } else {
            thumbWidth = 0;
            thumbHeight = 0;
        }

        final int maxTextWidth;
        if (showThumbText) {
            maxTextWidth = Math.max(thumbTextOnLayout.getWidth(), thumbTextOffLayout.getWidth()) + thumbTextPadding * 2;
        } else {
            maxTextWidth = 0;
        }

        this.thumbWidth = Math.max(maxTextWidth, thumbWidth);

        final int trackHeight;
        if (trackDrawable != null) {
            trackDrawable.getPadding(padding);
            trackHeight = trackDrawable.getIntrinsicHeight();
        } else {
            padding.setEmpty();
            trackHeight = 0;
        }

        // Adjust left and right padding to ensure there's enough room for the
        // thumb's padding (when present).
        int paddingLeft = padding.left;
        int paddingRight = padding.right;
        if (thumbDrawable != null) {
            final Rect inset = getOpticalBounds(thumbDrawable);
            paddingLeft = Math.max(paddingLeft, inset.left);
            paddingRight = Math.max(paddingRight, inset.right);
        }

        final int switchWidth = (int) (thumbSizeMultiplied * this.thumbWidth + paddingLeft + paddingRight);
        final int switchHeight = Math.max(trackHeight, thumbHeight);
        this.switchWidth = switchWidth;
        this.switchHeight = switchHeight;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int measuredHeight = getMeasuredHeight();
        if (measuredHeight < switchHeight) {
            setMeasuredDimension(getMeasuredWidthAndState(), switchHeight);
        }
    }

    @Override
    public void onPopulateAccessibilityEvent(final AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);

        final CharSequence text = isChecked() ? rightText : leftText;
        if (text != null) {
            event.getText().add(text);
        }
    }

    private boolean hitThumb(final float x, final float y) {
        if (thumbDrawable == null) {
            return false;
        }

        // Relies on mTempRect, MUST be called first!
        final int thumbOffset = getThumbOffset();

        thumbDrawable.getPadding(mTempRect);
        final int thumbTop = switchTop - touchSlop;
        final int thumbLeft = switchLeft + thumbOffset - touchSlop;
        final int thumbRight = thumbLeft + thumbWidth +
            mTempRect.left + mTempRect.right + touchSlop;
        final int thumbBottom = switchBottom + touchSlop;
        return x > thumbLeft && x < thumbRight && y > thumbTop && y < thumbBottom;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        velocityTracker.addMovement(ev);
        final int action = ev.getActionMasked();
        switch (action) {
        case MotionEvent.ACTION_DOWN: {
            final float x = ev.getX();
            final float y = ev.getY();
            if (isEnabled() && hitThumb(x, y)) {
                touchMode = TOUCH_MODE_DOWN;
                touchX = x;
                touchY = y;
            }
            break;
        }

        case MotionEvent.ACTION_MOVE: {
            switch (touchMode) {
            case TOUCH_MODE_IDLE:
                // Didn't target the thumb, treat normally.
                break;

            case TOUCH_MODE_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();
                if (Math.abs(x - touchX) > touchSlop ||
                    Math.abs(y - touchY) > touchSlop) {
                    touchMode = TOUCH_MODE_DRAGGING;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    touchX = x;
                    touchY = y;
                    return true;
                }
                break;
            }

            case TOUCH_MODE_DRAGGING: {
                final float x = ev.getX();
                final int thumbScrollRange = getThumbScrollRange();
                final float thumbScrollOffset = x - touchX;
                float dPos;
                if (thumbScrollRange != 0) {
                    dPos = thumbScrollOffset / thumbScrollRange;
                } else {
                    // If the thumb scroll range is empty, just use the
                    // movement direction to snap on or off.
                    dPos = thumbScrollOffset > 0 ? 1 : -1;
                }
                if (isLayoutRtl(this)) {
                    dPos = -dPos;
                }
                final float newPos = constrain(thumbPosition + dPos);
                if (newPos != thumbPosition) {
                    touchX = x;
                    setThumbPosition(newPos);
                }
                return true;
            }
            default:
            }
            break;
        }

        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL: {
            if (touchMode == TOUCH_MODE_DRAGGING) {
                stopDrag(ev);
                // Allow super class to handle pressed state, etc.
                super.onTouchEvent(ev);
                return true;
            }
            touchMode = TOUCH_MODE_IDLE;
            velocityTracker.clear();
            break;
        }
        default:
        }

        return super.onTouchEvent(ev);
    }

    private void cancelSuperTouch(final MotionEvent ev) {
        final MotionEvent cancel = MotionEvent.obtain(ev);
        cancel.setAction(MotionEvent.ACTION_CANCEL);
        super.onTouchEvent(cancel);
        cancel.recycle();
    }

    /**
     * Called from onTouchEvent to end a drag operation.
     *
     * @param ev Event that triggered the end of drag mode - ACTION_UP or ACTION_CANCEL
     */
    private void stopDrag(final MotionEvent ev) {
        touchMode = TOUCH_MODE_IDLE;

        // Commit the change if the event is up and not canceled and the switch
        // has not been disabled during the drag.
        final boolean commitChange = ev.getAction() == MotionEvent.ACTION_UP && isEnabled();
        final boolean oldState = isChecked();
        final boolean newState;
        if (commitChange) {
            velocityTracker.computeCurrentVelocity(1000);
            final float xVelocity = velocityTracker.getXVelocity();
            if (Math.abs(xVelocity) > minFlingVelocity) {
                newState = isLayoutRtl(this) ? (xVelocity < 0) : (xVelocity > 0);
            } else {
                newState = getTargetCheckedState();
            }
        } else {
            newState = oldState;
        }

        if (newState != oldState) {
            playSoundEffect(SoundEffectConstants.CLICK);
        }
        // Always call setChecked so that the thumb is moved back to the correct edge
        setChecked(newState);
        cancelSuperTouch(ev);
    }

    private void animateThumbToCheckedState(final boolean newCheckedState) {
        final float targetPosition = newCheckedState ? 1 : 0;
        mPositionAnimator = ObjectAnimator.ofFloat(this, THUMB_POS, targetPosition);
        mPositionAnimator.setDuration(THUMB_ANIMATION_DURATION);
        mPositionAnimator.setAutoCancel(true);
        mPositionAnimator.start();
    }

    private void cancelPositionAnimator() {
        if (mPositionAnimator != null) {
            mPositionAnimator.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return thumbPosition > HALF_POSITION;
    }

    void setThumbPosition(final float position) {
        thumbPosition = position;
        invalidate();
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        // Calling the super method may result in setChecked() getting called
        // recursively with a different value, so load the REAL value...
        checked = isChecked();

        if (getWindowToken() != null && ViewCompat.isLaidOut(this)) {
            animateThumbToCheckedState(checked);
        } else {
            // Immediately move the thumb to the new position.
            cancelPositionAnimator();
            setThumbPosition(checked ? 1 : 0);
        }
    }

    @Override
    protected void onLayout(final boolean changed, final int left, final int top, final int right, final int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int opticalInsetLeft = 0;
        int opticalInsetRight = 0;
        if (thumbDrawable != null) {
            final Rect trackPadding = mTempRect;
            if (trackDrawable != null) {
                trackDrawable.getPadding(trackPadding);
            } else {
                trackPadding.setEmpty();
            }

            final Rect insets = getOpticalBounds(thumbDrawable);
            opticalInsetLeft = Math.max(0, insets.left - trackPadding.left);
            opticalInsetRight = Math.max(0, insets.right - trackPadding.right);
        }

        final int switchRight;
        final int switchLeft;
        if (isLayoutRtl(this)) {
            switchLeft = getPaddingLeft() + opticalInsetLeft;
            switchRight = switchLeft + switchWidth - opticalInsetLeft - opticalInsetRight;
        } else {
            switchRight = getWidth() - getPaddingRight() - opticalInsetRight;
            switchLeft = switchRight - switchWidth + opticalInsetLeft + opticalInsetRight;
        }

        int switchTop = 0;
        int switchBottom = 0;
        switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
        case Gravity.TOP:
            switchTop = getPaddingTop();
            switchBottom = switchTop + switchHeight;
            break;

        case Gravity.CENTER_VERTICAL:
            switchTop = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2 - switchHeight / 2;
            switchBottom = switchTop + switchHeight;
            break;

        case Gravity.BOTTOM:
            switchBottom = getHeight() - getPaddingBottom();
            switchTop = switchBottom - switchHeight;
            break;
        default:
        }

        this.switchLeft = switchLeft;
        this.switchTop = switchTop;
        this.switchBottom = switchBottom;
        this.switchRight = switchRight;
    }

    @Override
    public void draw(final Canvas c) {
        final Rect padding = mTempRect;
        final int switchLeft = this.switchLeft;
        final int switchTop = this.switchTop;
        final int switchRight = this.switchRight;
        final int switchBottom = this.switchBottom;

        int thumbInitialLeft = switchLeft + getThumbOffset();

        final Rect thumbInsets;
        if (thumbDrawable != null) {
            thumbInsets = getOpticalBounds(thumbDrawable);
        } else {
            thumbInsets = DrawableUtils.INSETS_NONE;
        }

        // Layout the track.
        if (trackDrawable != null) {
            trackDrawable.getPadding(padding);

            // Adjust thumb position for track padding.
            thumbInitialLeft += padding.left;

            // If necessary, offset by the optical insets of the thumb asset.
            int trackLeft = switchLeft;
            int trackTop = switchTop;
            int trackRight = switchRight;
            int trackBottom = switchBottom;
            if (thumbInsets != null) {
                if (thumbInsets.left > padding.left) {
                    trackLeft += thumbInsets.left - padding.left;
                }
                if (thumbInsets.top > padding.top) {
                    trackTop += thumbInsets.top - padding.top;
                }
                if (thumbInsets.right > padding.right) {
                    trackRight -= thumbInsets.right - padding.right;
                }
                if (thumbInsets.bottom > padding.bottom) {
                    trackBottom -= thumbInsets.bottom - padding.bottom;
                }
            }
            trackDrawable.setBounds(trackLeft, trackTop, trackRight, trackBottom);
        }

        // Layout the thumb.
        if (thumbDrawable != null) {
            thumbDrawable.getPadding(padding);

            final int thumbLeft = thumbInitialLeft - padding.left;
            final int thumbRight = thumbInitialLeft + thumbWidth + padding.right;
            thumbDrawable.setBounds(thumbLeft, switchTop, thumbRight, switchBottom);

            final Drawable background = getBackground();
            if (background != null) {
                DrawableCompat.setHotspotBounds(background, thumbLeft, switchTop,
                    thumbRight, switchBottom);
            }
        }

        // Draw the background.
        super.draw(c);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        final Rect padding = mTempRect;
        final Drawable trackDrawable = this.trackDrawable;
        if (trackDrawable != null) {
            trackDrawable.getPadding(padding);
        } else {
            padding.setEmpty();
        }

        final int switchTop = this.switchTop;
        final int switchBottom = this.switchBottom;
        final int switchInnerTop = switchTop + padding.top;
        final int switchInnerBottom = switchBottom - padding.bottom;

        final Drawable thumbDrawable = this.thumbDrawable;
        if (trackDrawable != null) {
            final int thumbWidth = (int) (thumbDrawable.getIntrinsicWidth() / 1.15f);

            trackDrawable.draw(canvas);

            if (trackTextOnLayout != null) {
                trackTempRect.set(trackDrawable.getBounds());
                trackTempRect.right = thumbWidth + trackTempRect.left;
                drawText(trackTextOnLayout, canvas, trackTextPaint, switchInnerTop, switchInnerBottom, trackTempRect);
            }

            if (trackTextOffLayout != null) {
                trackTempRect.set(trackDrawable.getBounds());
                trackTempRect.left = trackTempRect.right - thumbWidth;
                drawText(trackTextOffLayout, canvas, trackTextPaint, switchInnerTop, switchInnerBottom, trackTempRect);
            }
        }

        final int saveCount = canvas.save();

        if (thumbDrawable != null) {
            thumbDrawable.draw(canvas);
        }

        final Layout thumbText = getTargetCheckedState() ? thumbTextOnLayout : thumbTextOffLayout;
        drawText(thumbText, canvas, thumbTextPaint, switchInnerTop, switchInnerBottom, thumbDrawable.getBounds());

        canvas.restoreToCount(saveCount);
    }

    private void drawText(final Layout textLayout, final Canvas canvas, final TextPaint paint, final int switchInnerTop,
        final int switchInnerBottom, @Nullable final Rect bounds) {
        if (textLayout != null) {
            paint.drawableState = getDrawableState();

            final int cX;
            if (bounds != null) {
                cX = bounds.left + bounds.right;
            } else {
                cX = getWidth();
            }

            final int left = cX / 2 - textLayout.getWidth() / 2;
            final int top = (switchInnerTop + switchInnerBottom) / 2 - textLayout.getHeight() / 2;
            canvas.save();
            canvas.translate(left, top);
            textLayout.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    public int getCompoundPaddingLeft() {
        if (!isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft();
        }
        int padding = super.getCompoundPaddingLeft() + switchWidth;
        if (!TextUtils.isEmpty(getText())) {
            padding += switchPadding;
        }
        return padding;
    }

    @Override
    public int getCompoundPaddingRight() {
        if (isLayoutRtl(this)) {
            return super.getCompoundPaddingRight();
        }
        int padding = super.getCompoundPaddingRight() + switchWidth;
        if (!TextUtils.isEmpty(getText())) {
            padding += switchPadding;
        }
        return padding;
    }

    private int getThumbOffset() {
        final float thumbPosition;
        if (isLayoutRtl(this)) {
            thumbPosition = 1 - this.thumbPosition;
        } else {
            thumbPosition = this.thumbPosition;
        }
        return (int) (thumbPosition * getThumbScrollRange() + HALF_POSITION);
    }

    private int getThumbScrollRange() {
        if (trackDrawable != null) {
            final Rect padding = mTempRect;
            trackDrawable.getPadding(padding);

            final Rect insets;
            if (thumbDrawable != null) {
                insets = getOpticalBounds(thumbDrawable);
            } else {
                insets = DrawableUtils.INSETS_NONE;
            }

            return switchWidth - thumbWidth - padding.left - padding.right
                - insets.left - insets.right;
        } else {
            return 0;
        }
    }

    @Override
    protected int[] onCreateDrawableState(final int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        final int[] state = getDrawableState();
        boolean changed = false;

        final Drawable thumbDrawable = this.thumbDrawable;
        if (thumbDrawable != null && thumbDrawable.isStateful()) {
            changed |= thumbDrawable.setState(state);
        }

        final Drawable trackDrawable = this.trackDrawable;
        if (trackDrawable != null && trackDrawable.isStateful()) {
            changed |= trackDrawable.setState(state);
        }

        if (changed) {
            invalidate();
        }
    }

    @Override
    public void drawableHotspotChanged(final float x, final float y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.drawableHotspotChanged(x, y);
        }

        if (thumbDrawable != null) {
            DrawableCompat.setHotspot(thumbDrawable, x, y);
        }

        if (trackDrawable != null) {
            DrawableCompat.setHotspot(trackDrawable, x, y);
        }
    }

    @Override
    protected boolean verifyDrawable(final Drawable who) {
        return super.verifyDrawable(who) || who.equals(thumbDrawable) || who.equals(trackDrawable);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();

        if (thumbDrawable != null) {
            thumbDrawable.jumpToCurrentState();
        }

        if (trackDrawable != null) {
            trackDrawable.jumpToCurrentState();
        }

        if (mPositionAnimator != null && mPositionAnimator.isStarted()) {
            mPositionAnimator.end();
            mPositionAnimator = null;
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(final AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(final AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ACCESSIBILITY_EVENT_CLASS_NAME);
        final CharSequence switchText = isChecked() ? rightText : leftText;
        if (!TextUtils.isEmpty(switchText)) {
            final CharSequence oldText = info.getText();
            if (TextUtils.isEmpty(oldText)) {
                info.setText(switchText);
            } else {
                final StringBuilder newText = new StringBuilder();
                newText.append(oldText).append(' ').append(switchText);
                info.setText(newText);
            }
        }
    }

    private static float constrain(final float amount) {
        return amount < 0 ? 0 : (Math.min(amount, 1));
    }

    public static boolean isLayoutRtl(final View view) {
        return ViewCompat.getLayoutDirection(view) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    public static Rect getOpticalBounds(Drawable drawable) {
        Class<?> sInsetsClazz = null;
        try {
            sInsetsClazz = Class.forName("android.graphics.Insets");
        } catch (final ClassNotFoundException e) {
            // Oh well...
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            final android.graphics.Insets insets = drawable.getOpticalInsets();
            final Rect result = new Rect();
            result.left = insets.left;
            result.right = insets.right;
            result.top = insets.top;
            result.bottom = insets.bottom;
            return result;
        }
        if (sInsetsClazz != null) {
            try {
                // If the Drawable is wrapped, we need to manually unwrap it and process
                // the wrapped drawable.
                drawable = DrawableCompat.unwrap(drawable);

                final Method getOpticalInsetsMethod = drawable.getClass()
                    .getMethod("getOpticalInsets");
                final Object insets = getOpticalInsetsMethod.invoke(drawable);

                if (insets != null) {
                    // If the drawable has some optical insets, let's copy them into a Rect
                    final Rect result = new Rect();

                    for (final Field field : sInsetsClazz.getFields()) {
                        switch (field.getName()) {
                        case "left":
                            result.left = field.getInt(insets);
                            break;
                        case "top":
                            result.top = field.getInt(insets);
                            break;
                        case "right":
                            result.right = field.getInt(insets);
                            break;
                        default:
                            result.bottom = field.getInt(insets);
                        }
                    }
                    return result;
                }
            } catch (final Exception e) {
                Log.e("CARD_DRAWER", "Couldn't obtain the optical insets. Ignoring.");
            }
        }

        // If we reach here, either we're running on a device pre-v18, the Drawable didn't have
        // any optical insets, or a reflection issue, so we'll just return an empty rect
        return DrawableUtils.INSETS_NONE;
    }
}