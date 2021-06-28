package com.meli.android.carddrawer.model

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.widget.AppCompatTextView
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.CardDrawerFont
import com.meli.android.carddrawer.format.TypefaceHelper

internal class CardDrawerTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CardDrawerTextView)
        val font: CardDrawerFont =
            CardDrawerFont.from(typedArray.getInt(R.styleable.CardDrawerTextView_customStyle,
                CardDrawerFont.REGULAR.index))

        typedArray.recycle()

        if (!isInEditMode) {
            TypefaceHelper.set(this, TypefaceHelper.get(context, font))
        }

        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                configureEllipsize()
            }
        })
    }

    fun setText(text: Label.Text) {
        setText(text.text)
        setTextColor(Color.parseColor(text.color))
        if (text.weight.isNotEmpty()) {
            TypefaceHelper.set(this, TypefaceHelper.get(context,  CardDrawerFont.from(text.weight)))
        }
    }

    private fun configureEllipsize() {
        val truncateAt: TextUtils.TruncateAt = ellipsize
        if (truncateAt == TextUtils.TruncateAt.END && lineCount > maxLines) {
            val indexLastLine: Int = layout.getLineEnd(maxLines - 1)
            val text: String = text.subSequence(0, indexLastLine - 3).toString() + "..."
            setText(text)
        }
    }

}