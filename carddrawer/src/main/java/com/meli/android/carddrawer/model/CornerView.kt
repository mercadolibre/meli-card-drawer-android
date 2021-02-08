package com.meli.android.carddrawer.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.meli.android.carddrawer.R

private const val VIEW_ID = 3216432

internal class CornerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var topLeftCornerRadius = 0f
    private var topRightCornerRadius = 0f
    private var bottomRightCornerRadius = 0f
    private var bottomLeftCornerRadius = 0f
    private val path = Path()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerView, 0, 0)

        typedArray.getDimension(R.styleable.CornerView_corner_radius, -1f).takeIf { it != -1f }?.let {
            topLeftCornerRadius = it
            topRightCornerRadius = it
            bottomLeftCornerRadius = it
            bottomRightCornerRadius = it
        } ?: let {
            topLeftCornerRadius = typedArray.getDimension(R.styleable.CornerView_top_left_corner_radius, 0f)
            topRightCornerRadius = typedArray.getDimension(R.styleable.CornerView_top_right_corner_radius, 0f)
            bottomLeftCornerRadius = typedArray.getDimension(R.styleable.CornerView_bottom_left_corner_radius, 0f)
            bottomRightCornerRadius = typedArray.getDimension(R.styleable.CornerView_bottom_right_corner_radius, 0f)
        }
        typedArray.recycle()
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        if (id == View.NO_ID) {
            id = VIEW_ID
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), floatArrayOf(
            topLeftCornerRadius, topLeftCornerRadius,
            topRightCornerRadius, topRightCornerRadius,
            bottomRightCornerRadius, bottomRightCornerRadius,
            bottomLeftCornerRadius, bottomLeftCornerRadius), Path.Direction.CW)
        path.close()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.let {
            val save = canvas.save()
            canvas.clipPath(path)
            super.dispatchDraw(canvas)
            canvas.restoreToCount(save)
        } ?: super.dispatchDraw(canvas)
    }

    override fun addView(child: View?) {
        if (childCount > 0) {
            removeAllViews()
        }
        super.addView(child)
    }
}