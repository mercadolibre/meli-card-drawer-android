package com.meli.android.carddrawer.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout

open class FrameLayoutWithDisableSupport @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        val cm = ColorMatrix()
        cm.set(
            floatArrayOf(
                0.2f, 0.2f, 0.2f, 0f, 102f,
                0.2f, 0.2f, 0.2f, 0f, 102f,
                0.2f, 0.2f, 0.2f, 0f, 102f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        paint.colorFilter = ColorMatrixColorFilter(cm)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (!isEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas?.saveLayer(null, paint)
            } else {
                canvas?.saveLayer(null, paint, Canvas.ALL_SAVE_FLAG)
            }
        }

        super.dispatchDraw(canvas)

        if (!isEnabled) {
            canvas?.restore()
        }
    }

    override fun draw(canvas: Canvas?) {
        if (!isEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas?.saveLayer(null, paint)
            } else {
                canvas?.saveLayer(null, paint, Canvas.ALL_SAVE_FLAG)
            }
        }

        super.draw(canvas)

        if (!isEnabled) {
            canvas?.restore()
        }
    }
}
