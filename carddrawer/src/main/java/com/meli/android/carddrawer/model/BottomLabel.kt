package com.meli.android.carddrawer.model

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.CardDrawerFont
import com.meli.android.carddrawer.format.TypefaceHelper
import com.meli.android.carddrawer.ColorUtils.safeParcelColor
import com.meli.android.carddrawer.model.ConstraintLayoutWithDisabledSupport.Child
import com.meli.android.carddrawer.model.animation.BottomLabelAnimation

internal class BottomLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Child {

    private var bottomDescription: AppCompatTextView
    private var animation: BottomLabelAnimation? = null

    init {
          inflate(context, R.layout.card_drawer_bottom_label, this)
          bottomDescription = findViewById(R.id.bottom_description)
    }

    fun setLabel(label: Label) {
        if (label.animate) animation = BottomLabelAnimation(this, BottomLabelAnimation(bottomDescription))
        with(bottomDescription) {
            text = label.text
            setTextColor(safeParcelColor(label.color, Color.WHITE))
        }
        setWeight(label.weight)
        setBackgroundColor(label.backgroundColor)
    }

    fun show() {
        animation?.slideUp() ?: showWithoutAnimation()
    }

    fun hide() {
        animation?.slideDown() ?: hideWithoutAnimation()
    }

    private fun showWithoutAnimation() {
        visibility = VISIBLE
        bottomDescription.visibility = VISIBLE
    }

    private fun hideWithoutAnimation() {
        visibility = INVISIBLE
        bottomDescription.visibility = INVISIBLE
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

    override fun shouldBeGreyedOut() = false
}
