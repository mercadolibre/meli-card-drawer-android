package com.meli.android.carddrawer.internal

import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.widget.AppCompatTextView
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.CardDrawerFont
import com.meli.android.carddrawer.format.TypefaceHelper
import com.meli.android.carddrawer.utils.TextUtil
import com.meli.android.carddrawer.utils.ViewUtils

internal class CardDrawerTextView(
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

    fun setText(text: Text) {
        setText(text.message)
        ViewUtils.setTextColor(this, text.textColor)
        if (TextUtil.isNotEmpty(text.weigth)) {
            TypefaceHelper.set(this, TypefaceHelper.get(context,  CardDrawerFont.from(text.weigth)))
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