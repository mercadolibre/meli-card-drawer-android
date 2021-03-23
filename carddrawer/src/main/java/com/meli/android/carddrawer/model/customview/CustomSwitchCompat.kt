package com.meli.android.carddrawer.model.customview

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Layout
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Property
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.CompoundButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.DrawableUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.customview.CustomSwitchHelper.getOpticalBounds
import com.meli.android.carddrawer.model.customview.CustomSwitchHelper.isLayoutRtl
import com.meli.android.carddrawer.model.customview.CustomSwitchHelper.makeTextLayout
import kotlin.math.abs
import kotlin.math.max

private const val THUMB_ANIMATION_DURATION = 250
private const val TOUCH_MODE_IDLE = 0
private const val TOUCH_MODE_DOWN = 1
private const val TOUCH_MODE_DRAGGING = 2
private const val HALF_POSITION = 0.5f
private const val DEFAULT_THUMB_SIZE_MULTIPLIED = 1.5f
private const val DEFAULT_HORIZONTAL_SKEW_FACTOR = -0.25f
private const val ACCESSIBILITY_EVENT_CLASS_NAME = "com.meli.android.carddrawer.model.customview.CustomSwitchCompat"

internal class CustomSwitchCompat @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.switchStyle) : CompoundButton(context, attrs, defStyleAttr) {
    private var thumbDrawable: Drawable? = null
    private var trackDrawable: Drawable? = null
    private var thumbTextPadding: Int = 0
    private var switchPadding: Int = 0
    private var rightText: CharSequence? = null
    private var leftText: CharSequence? = null
    private var showThumbText = false
    private var showTrackText = false
    private var touchMode = 0
    private val touchSlop: Int
    private var touchX = 0f
    private var touchY = 0f
    private val velocityTracker = VelocityTracker.obtain()
    private val trackTempRect = Rect()
    private val minFlingVelocity: Int
    private var switchBottom = 0
    private val thumbTextPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val trackTextPaint: TextPaint
    private var thumbTextOnLayout: Layout? = null
    private var thumbTextOffLayout: Layout? = null
    private var trackTextOnLayout: Layout? = null
    private var trackTextOffLayout: Layout? = null
    private val mTempRect = Rect()
    private val targetCheckedState: Boolean
        get() = switchThumbPosition > HALF_POSITION
    var positionAnimator: ObjectAnimator? = null
    var thumbSizeMultiplied = DEFAULT_THUMB_SIZE_MULTIPLIED
    var switchThumbPosition = 0f

    init {
        thumbTextPaint.density = resources.displayMetrics.density
        trackTextPaint = TextPaint(thumbTextPaint)
        val config = ViewConfiguration.get(context)
        touchSlop = config.scaledTouchSlop
        minFlingVelocity = config.scaledMinimumFlingVelocity
        refreshDrawableState()
    }

    /**
     * Width required to draw the switch track and thumb. Includes padding and optical bounds for both the track and
     * thumb.
     */
    private var switchWidth = 0

    /**
     * Height required to draw the switch track and thumb. Includes padding and optical bounds for both the track and
     * thumb.
     */
    private var switchHeight = 0

    /**
     * Width of the thumb's content region. Does not include padding or optical bounds.
     */
    private var thumbWidth = 0

    /**
     * Left bound for drawing the switch track and thumb.
     */
    private var switchLeft = 0

    /**
     * Top bound for drawing the switch track and thumb.
     */
    private var switchTop = 0

    /**
     * Right bound for drawing the switch track and thumb.
     */
    private var switchRight = 0


    fun setThumbTextSize(textSize: Float) {
        thumbTextPaint.textSize = textSize
        requestLayout()
        invalidate()
    }

    fun setTrackTextSize(textSize: Float) {
        trackTextPaint.textSize = textSize
        requestLayout()
        invalidate()
    }

    fun setThumbTextColor(color: Int) {
        if (thumbTextPaint.color != color) {
            thumbTextPaint.color = color
            requestLayout()
            invalidate()
        }
    }

    fun setTrackTextColor(color: Int) {
        if (trackTextPaint.color != color) {
            trackTextPaint.color = color
            requestLayout()
            invalidate()
        }
    }

    @Suppress("unused")
    fun setThumbTypeface(tf: Typeface?, style: Int) {
        var currentTf = tf
        if (style > 0) {
            currentTf = if (currentTf == null) {
                Typeface.defaultFromStyle(style)
            } else {
                Typeface.create(tf, style)
            }
            setThumbTypeface(tf)
            // now compute what (if any) algorithmic styling is needed
            val typefaceStyle = currentTf?.style ?: 0
            val need = style and typefaceStyle.inv()
            thumbTextPaint.isFakeBoldText = need and Typeface.BOLD != 0
            thumbTextPaint.textSkewX = if (need and Typeface.ITALIC != 0) DEFAULT_HORIZONTAL_SKEW_FACTOR else 0f
        } else {
            thumbTextPaint.isFakeBoldText = false
            thumbTextPaint.textSkewX = 0f
            setThumbTypeface(currentTf)
        }
    }

    fun setThumbTypeface(typeface: Typeface?) {
        if (thumbTextPaint.typeface != null && thumbTextPaint.typeface != typeface
            || thumbTextPaint.typeface == null && typeface != null) {
            thumbTextPaint.typeface = typeface
            requestLayout()
            invalidate()
        }
    }

    @Suppress("unused")
    fun setTrackTypeface(tf: Typeface?, style: Int) {
        var currentTf = tf
        if (style > 0) {
            currentTf = if (currentTf == null) {
                Typeface.defaultFromStyle(style)
            } else {
                Typeface.create(currentTf, style)
            }
            setThumbTypeface(currentTf)
            // now compute what (if any) algorithmic styling is needed
            val typefaceStyle = currentTf?.style ?: 0
            val need = style and typefaceStyle.inv()
            trackTextPaint.isFakeBoldText = need and Typeface.BOLD != 0
            trackTextPaint.textSkewX = if (need and Typeface.ITALIC != 0) DEFAULT_HORIZONTAL_SKEW_FACTOR else 0f
        } else {
            trackTextPaint.isFakeBoldText = false
            trackTextPaint.textSkewX = 0f
            setThumbTypeface(currentTf)
        }
    }

    fun setTrackTypeface(typeface: Typeface?) {
        if (trackTextPaint.typeface != null && trackTextPaint.typeface != typeface
            || trackTextPaint.typeface == null && typeface != null) {
            trackTextPaint.typeface = typeface
            requestLayout()
            invalidate()
        }
    }

    @Suppress("unused")
    fun setSwitchPadding(pixels: Int) {
        switchPadding = pixels
        requestLayout()
    }

    @Suppress("unused")
    fun getSwitchPadding(): Int {
        return switchPadding
    }

    @Suppress("unused")
    fun setThumbTextPadding(pixels: Int) {
        thumbTextPadding = pixels
        requestLayout()
    }

    @Suppress("unused")
    fun getThumbTextPadding(): Int {
        return thumbTextPadding
    }

    fun setTrackDrawable(track: Drawable?) {
        if (trackDrawable != null) {
            trackDrawable!!.callback = null
        }
        trackDrawable = track
        if (track != null) {
            track.callback = this
        }
        requestLayout()
    }

    @Suppress("unused")
    fun setTrackResource(resId: Int) {
        setTrackDrawable(AppCompatResources.getDrawable(context, resId))
    }

    @Suppress("unused")
    fun getTrackDrawable(): Drawable? {
        return trackDrawable
    }

    fun setThumbDrawable(thumb: Drawable?) {
        if (thumbDrawable != null) {
            thumbDrawable!!.callback = null
        }
        thumbDrawable = thumb
        if (thumb != null) {
            thumb.callback = this
        }
        requestLayout()
    }

    @Suppress("unused")
    fun setThumbResource(resId: Int) {
        setThumbDrawable(AppCompatResources.getDrawable(context, resId))
    }

    @Suppress("unused")
    fun getThumbDrawable(): Drawable? {
        return thumbDrawable
    }

    @Suppress("unused")
    fun getRightText(): CharSequence? {
        return rightText
    }

    @Suppress("unused")
    fun setRightText(text: CharSequence) {
        rightText = text
        requestLayout()
    }

    @Suppress("unused")
    fun getLeftText(): CharSequence? {
        return leftText
    }

    fun setLeftText(text: CharSequence) {
        leftText = text
        requestLayout()
    }

    fun setShowThumbText(showText: Boolean) {
        if (this.showThumbText != showText) {
            this.showThumbText = showText
            requestLayout()
        }
    }

    fun setShowTrackText(showText: Boolean) {
        if (showTrackText != showText) {
            showTrackText = showText
            requestLayout()
        }
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        rightText?.takeIf { showThumbText && thumbTextOnLayout == null }
            ?.let { thumbTextOnLayout = makeTextLayout(it, thumbTextPaint) }
        leftText?.takeIf { showThumbText && thumbTextOffLayout == null }
            ?.let { thumbTextOffLayout = makeTextLayout(it, thumbTextPaint) }

        leftText?.takeIf { showTrackText && trackTextOnLayout == null }
            ?.let { trackTextOnLayout = makeTextLayout(it, trackTextPaint) }
        rightText?.takeIf { showTrackText && trackTextOffLayout == null }
            ?.let { trackTextOffLayout = makeTextLayout(it, trackTextPaint) }

        val padding = mTempRect

        val (thumbWidth, thumbHeight) = thumbDrawable?.let {
            it.getPadding(padding)
            it.intrinsicWidth - padding.left - padding.right to it.intrinsicHeight
        } ?: 0 to 0
        val maxTextWidth: Int = if (showThumbText) {
            max(thumbTextOnLayout?.width ?: 0, thumbTextOffLayout?.width ?: 0) + thumbTextPadding * 2
        } else 0
        this.thumbWidth = max(maxTextWidth, thumbWidth)
        val trackHeight = trackDrawable?.let {
            it.getPadding(padding)
            it.intrinsicHeight
        } ?: let {
            padding.setEmpty()
            0
        }

        // Adjust left and right padding to ensure there's enough room for the
        // thumb's padding (when present).
        var paddingLeft = padding.left
        var paddingRight = padding.right
        if (thumbDrawable != null) {
            val inset = getOpticalBounds(thumbDrawable!!)
            paddingLeft = max(paddingLeft, inset.left)
            paddingRight = max(paddingRight, inset.right)
        }
        val switchWidth = (thumbSizeMultiplied * this.thumbWidth + paddingLeft + paddingRight).toInt()
        val switchHeight = max(trackHeight, thumbHeight)
        this.switchWidth = switchWidth
        this.switchHeight = switchHeight
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredHeight = measuredHeight
        if (measuredHeight < switchHeight) {
            setMeasuredDimension(measuredWidthAndState, switchHeight)
        }
    }

    override fun onPopulateAccessibilityEvent(event: AccessibilityEvent) {
        super.onPopulateAccessibilityEvent(event)
        val text = if (isChecked) rightText else leftText
        text?.let { event.text.add(it) }
    }

    private fun hitThumb(x: Float, y: Float): Boolean {
        if (thumbDrawable == null) {
            return false
        }

        // Relies on mTempRect, MUST be called first!
        val thumbOffset = thumbOffset
        thumbDrawable!!.getPadding(mTempRect)
        val thumbTop = switchTop - touchSlop
        val thumbLeft = switchLeft + thumbOffset - touchSlop
        val thumbRight = thumbLeft + thumbWidth +
            mTempRect.left + mTempRect.right + touchSlop
        val thumbBottom = switchBottom + touchSlop
        return x > thumbLeft && x < thumbRight && y > thumbTop && y < thumbBottom
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        velocityTracker.addMovement(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                val x = ev.x
                val y = ev.y
                if (isEnabled && hitThumb(x, y)) {
                    touchMode = TOUCH_MODE_DOWN
                    touchX = x
                    touchY = y
                }
            }
            MotionEvent.ACTION_MOVE -> {
                when (touchMode) {
                    TOUCH_MODE_IDLE -> {
                    }
                    TOUCH_MODE_DOWN -> {
                        val x = ev.x
                        val y = ev.y
                        if (abs(x - touchX) > touchSlop || abs(y - touchY) > touchSlop) {
                            touchMode = TOUCH_MODE_DRAGGING
                            parent.requestDisallowInterceptTouchEvent(true)
                            touchX = x
                            touchY = y
                            return true
                        }
                    }
                    TOUCH_MODE_DRAGGING -> {
                        val x = ev.x
                        val thumbScrollRange = thumbScrollRange
                        val thumbScrollOffset = x - touchX
                        var dPos: Float
                        dPos = if (thumbScrollRange != 0) {
                            thumbScrollOffset / thumbScrollRange
                        } else {
                            // If the thumb scroll range is empty, just use the
                            // movement direction to snap on or off.
                            if (thumbScrollOffset > 0) 1f else -1f
                        }
                        if (isLayoutRtl(this)) {
                            dPos = -dPos
                        }
                        val newPos = constrain(switchThumbPosition + dPos)
                        if (newPos != switchThumbPosition) {
                            touchX = x
                            setThumbPosition(newPos)
                        }
                        return true
                    }
                    else -> {
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (touchMode == TOUCH_MODE_DRAGGING) {
                    stopDrag(ev)
                    // Allow super class to handle pressed state, etc.
                    super.onTouchEvent(ev)
                    return true
                }
                touchMode = TOUCH_MODE_IDLE
                velocityTracker.clear()
            }
            else -> {
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun cancelSuperTouch(ev: MotionEvent) {
        val cancel = MotionEvent.obtain(ev)
        cancel.action = MotionEvent.ACTION_CANCEL
        super.onTouchEvent(cancel)
        cancel.recycle()
    }

    private fun stopDrag(ev: MotionEvent) {
        touchMode = TOUCH_MODE_IDLE

        // Commit the change if the event is up and not canceled and the switch
        // has not been disabled during the drag.
        val commitChange = ev.action == MotionEvent.ACTION_UP && isEnabled
        val oldState = isChecked
        val newState: Boolean
        newState = if (commitChange) {
            velocityTracker.computeCurrentVelocity(1000)
            val xVelocity = velocityTracker.xVelocity
            if (abs(xVelocity) > minFlingVelocity) {
                if (isLayoutRtl(this)) xVelocity < 0 else xVelocity > 0
            } else {
                targetCheckedState
            }
        } else {
            oldState
        }
        if (newState != oldState) {
            playSoundEffect(SoundEffectConstants.CLICK)
        }
        // Always call setChecked so that the thumb is moved back to the correct edge
        isChecked = newState
        cancelSuperTouch(ev)
    }

    private fun animateThumbToCheckedState(newCheckedState: Boolean) {
        val targetPosition = if (newCheckedState) 1f else 0f
        val thumbPos = object : Property<CustomSwitchCompat, Float>(Float::class.java, "thumbPos") {
            override fun get(customSwitchCompat: CustomSwitchCompat): Float {
                return customSwitchCompat.switchThumbPosition
            }

            override fun set(customSwitchCompat: CustomSwitchCompat, value: Float) {
                customSwitchCompat.setThumbPosition(value)
            }
        }
        positionAnimator = ObjectAnimator.ofFloat(this, thumbPos, targetPosition).also {
            it.duration = THUMB_ANIMATION_DURATION.toLong()
            it.setAutoCancel(true)
            it.start()
        }
    }

    private fun cancelPositionAnimator() {
        if (positionAnimator != null) {
            positionAnimator!!.cancel()
        }
    }

    fun setThumbPosition(position: Float) {
        switchThumbPosition = position
        invalidate()
    }

    override fun toggle() {
        isChecked = !isChecked
    }

    override fun setChecked(checked: Boolean) {
        var currentChecked = checked
        super.setChecked(currentChecked)

        // Calling the super method may result in setChecked() getting called
        // recursively with a different value, so load the REAL value...
        currentChecked = isChecked
        if (windowToken != null && ViewCompat.isLaidOut(this)) {
            animateThumbToCheckedState(currentChecked)
        } else {
            // Immediately move the thumb to the new position.
            cancelPositionAnimator()
            setThumbPosition(if (currentChecked) 1f else 0f)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        var opticalInsetLeft = 0
        var opticalInsetRight = 0
        thumbDrawable?.let {
            val trackPadding = mTempRect
            val insets = getOpticalBounds(it)
            trackDrawable?.getPadding(trackPadding) ?: trackPadding.setEmpty()
            opticalInsetLeft = max(0, insets.left - trackPadding.left)
            opticalInsetRight = max(0, insets.right - trackPadding.right)
        }

        val switchRight: Int
        val switchLeft: Int
        if (isLayoutRtl(this)) {
            switchLeft = paddingLeft + opticalInsetLeft
            switchRight = switchLeft + switchWidth - opticalInsetLeft - opticalInsetRight
        } else {
            switchRight = width - paddingRight - opticalInsetRight
            switchLeft = switchRight - switchWidth + opticalInsetLeft + opticalInsetRight
        }
        var switchTop = 0
        var switchBottom = 0
        when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.TOP -> {
                switchTop = paddingTop
                switchBottom = switchTop + switchHeight
            }
            Gravity.CENTER_VERTICAL -> {
                switchTop = (paddingTop + height - paddingBottom) / 2 - switchHeight / 2
                switchBottom = switchTop + switchHeight
            }
            Gravity.BOTTOM -> {
                switchBottom = height - paddingBottom
                switchTop = switchBottom - switchHeight
            }
            else -> {
            }
        }
        this.switchLeft = switchLeft
        this.switchTop = switchTop
        this.switchBottom = switchBottom
        this.switchRight = switchRight
    }

    override fun draw(c: Canvas) {
        val padding = mTempRect
        val switchLeft = switchLeft
        val switchTop = switchTop
        val switchRight = switchRight
        val switchBottom = switchBottom
        var thumbInitialLeft = switchLeft + thumbOffset
        val thumbInsets: Rect? = if (thumbDrawable != null) {
            getOpticalBounds(thumbDrawable!!)
        } else {
            DrawableUtils.INSETS_NONE
        }

        // Layout the track.
        if (trackDrawable != null) {
            trackDrawable!!.getPadding(padding)

            // Adjust thumb position for track padding.
            thumbInitialLeft += padding.left

            // If necessary, offset by the optical insets of the thumb asset.
            var trackLeft = switchLeft
            var trackTop = switchTop
            var trackRight = switchRight
            var trackBottom = switchBottom
            if (thumbInsets != null) {
                if (thumbInsets.left > padding.left) {
                    trackLeft += thumbInsets.left - padding.left
                }
                if (thumbInsets.top > padding.top) {
                    trackTop += thumbInsets.top - padding.top
                }
                if (thumbInsets.right > padding.right) {
                    trackRight -= thumbInsets.right - padding.right
                }
                if (thumbInsets.bottom > padding.bottom) {
                    trackBottom -= thumbInsets.bottom - padding.bottom
                }
            }
            trackDrawable!!.setBounds(trackLeft, trackTop, trackRight, trackBottom)
        }

        thumbDrawable?.let {
            it.getPadding(padding)
            val thumbLeft = thumbInitialLeft - padding.left
            val thumbRight = thumbInitialLeft + thumbWidth + padding.right
            it.setBounds(thumbLeft, switchTop, thumbRight, switchBottom)
            val background = background
            if (background != null) {
                DrawableCompat.setHotspotBounds(background, thumbLeft, switchTop,
                    thumbRight, switchBottom)
            }
        }
        super.draw(c)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = mTempRect
        val trackDrawable = trackDrawable
        trackDrawable?.getPadding(padding) ?: padding.setEmpty()

        val switchTop = switchTop
        val switchBottom = switchBottom
        val switchInnerTop = switchTop + padding.top
        val switchInnerBottom = switchBottom - padding.bottom
        val thumbDrawable = thumbDrawable
        trackDrawable?.let { track ->
            val thumbWidth = thumbDrawable?.let { (it.intrinsicWidth / 1.15f).toInt() } ?: 0

            track.draw(canvas)
            if (trackTextOnLayout != null) {
                trackTempRect.set(track.bounds)
                trackTempRect.right = thumbWidth + trackTempRect.left
                drawText(trackTextOnLayout, canvas, trackTextPaint, switchInnerTop, switchInnerBottom, trackTempRect)
            }
            if (trackTextOffLayout != null) {
                trackTempRect.set(track.bounds)
                trackTempRect.left = trackTempRect.right - thumbWidth
                drawText(trackTextOffLayout, canvas, trackTextPaint, switchInnerTop, switchInnerBottom, trackTempRect)
            }
        }

        val thumbText = if (targetCheckedState) thumbTextOnLayout else thumbTextOffLayout
        val saveCount = canvas.save()

        thumbDrawable?.let {
            it.draw(canvas)
            drawText(thumbText, canvas, thumbTextPaint, switchInnerTop, switchInnerBottom, it.bounds)
        }
        canvas.restoreToCount(saveCount)
    }

    private fun drawText(textLayout: Layout?, canvas: Canvas, paint: TextPaint, switchInnerTop: Int,
        switchInnerBottom: Int, bounds: Rect?) {
        if (textLayout != null) {
            paint.drawableState = drawableState
            val cX: Int = if (bounds != null) {
                bounds.left + bounds.right
            } else {
                width
            }
            val left = cX / 2 - textLayout.width / 2
            val top = (switchInnerTop + switchInnerBottom) / 2 - textLayout.height / 2
            canvas.save()
            canvas.translate(left.toFloat(), top.toFloat())
            textLayout.draw(canvas)
            canvas.restore()
        }
    }

    override fun getCompoundPaddingLeft(): Int {
        if (!isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft()
        }
        var padding = super.getCompoundPaddingLeft() + switchWidth
        if (!TextUtils.isEmpty(text)) {
            padding += switchPadding
        }
        return padding
    }

    override fun getCompoundPaddingRight(): Int {
        if (isLayoutRtl(this)) {
            return super.getCompoundPaddingRight()
        }
        var padding = super.getCompoundPaddingRight() + switchWidth
        if (!TextUtils.isEmpty(text)) {
            padding += switchPadding
        }
        return padding
    }

    private val thumbOffset: Int
        get() {
            val thumbPosition: Float = if (isLayoutRtl(this)) {
                1 - this.switchThumbPosition
            } else {
                this.switchThumbPosition
            }
            return (thumbPosition * thumbScrollRange + HALF_POSITION).toInt()
        }

    private val thumbScrollRange: Int
        get() = trackDrawable?.let { track ->
            val padding = mTempRect
            track.getPadding(padding)
            val insets: Rect = thumbDrawable?.let { getOpticalBounds(it) } ?: DrawableUtils.INSETS_NONE
            (switchWidth - thumbWidth - padding.left - padding.right - insets.left - insets.right)
        } ?: 0

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, intArrayOf(android.R.attr.state_checked))
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        val state = drawableState
        var changed = false
        val thumbDrawable = thumbDrawable
        if (thumbDrawable != null && thumbDrawable.isStateful) {
            changed = changed or thumbDrawable.setState(state)
        }
        val trackDrawable = trackDrawable
        if (trackDrawable != null && trackDrawable.isStateful) {
            changed = changed or trackDrawable.setState(state)
        }
        if (changed) {
            invalidate()
        }
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.drawableHotspotChanged(x, y)
        }
        if (thumbDrawable != null) {
            DrawableCompat.setHotspot(thumbDrawable!!, x, y)
        }
        if (trackDrawable != null) {
            DrawableCompat.setHotspot(trackDrawable!!, x, y)
        }
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who == thumbDrawable || who == trackDrawable
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        thumbDrawable?.jumpToCurrentState()
        trackDrawable?.jumpToCurrentState()
        positionAnimator = positionAnimator?.takeIf { it.isStarted }?.let { it.end() ; null }
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)
        event.className = ACCESSIBILITY_EVENT_CLASS_NAME
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        info.className = ACCESSIBILITY_EVENT_CLASS_NAME
        val switchText = if (isChecked) rightText else leftText
        if (!TextUtils.isEmpty(switchText)) {
            val oldText = info.text
            if (TextUtils.isEmpty(oldText)) {
                info.text = switchText
            } else {
                val newText = StringBuilder()
                newText.append(oldText).append(' ').append(switchText)
                info.text = newText
            }
        }
    }

    private fun constrain(amount: Float): Float {
        return if (amount < 0) 0f else amount.coerceAtMost(1f)
    }
}