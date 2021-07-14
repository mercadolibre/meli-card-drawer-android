package com.meli.android.carddrawer.model

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.CardDrawerFont
import com.meli.android.carddrawer.format.TypefaceHelper
import com.meli.android.carddrawer.ColorUtils.safeParcelColor
import com.meli.android.carddrawer.model.animation.BottomLabelAnimation

internal class BottomLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var bottomDescription: AppCompatTextView
    private val animation: BottomLabelAnimation = BottomLabelAnimation(this)

    init {
          inflate(context, R.layout.card_drawer_bottom_label, this)
          bottomDescription = findViewById(R.id.bottom_description)
    }

    fun setLabel(label: Label) {
        with(bottomDescription) {
            text = label.text
            setTextColor(safeParcelColor(label.color, Color.WHITE))
        }
        setWeight(label.weight)
        setBackgroundColor(label.backgroundColor)
    }

    fun showAnimation() {
        animation.slideUp()
    }

    fun hideAnimation() {
        animation.slideDown()
    }

    private fun setWeight(weight: String?) {
        if (!weight.isNullOrBlank()) {
            TypefaceHelper.also { typefaceHelper ->
                typefaceHelper.set(bottomDescription, typefaceHelper.get(context,  CardDrawerFont.from(weight)))
            }
        }
    }

    private fun setBackgroundColor(color: String?) {
        runCatching {
            setBackgroundColor(Color.parseColor(color))
        }.getOrElse {
            setBackgroundResource(R.color.card_drawer_color_bottom_label)
        }
    }
}
