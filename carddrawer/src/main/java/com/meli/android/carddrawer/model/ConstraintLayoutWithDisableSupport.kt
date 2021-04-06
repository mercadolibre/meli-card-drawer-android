package com.meli.android.carddrawer.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

open class ConstraintLayoutWithDisabledSupport @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

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

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        val shouldBeGreyedOut = child !is Child || child.shouldBeGreyedOut()
        var restoreCount: Int? = null
        if (!isEnabled && shouldBeGreyedOut) {
            restoreCount = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas?.saveLayer(null, paint)
            } else {
                canvas?.saveLayer(null, paint, Canvas.ALL_SAVE_FLAG)
            }
        }
        val returnValue = super.drawChild(canvas, child, drawingTime)
        restoreCount?.let {
            canvas?.restoreToCount(it)
        }
        return returnValue
    }

    interface Child {
        fun shouldBeGreyedOut(): Boolean
    }
}
