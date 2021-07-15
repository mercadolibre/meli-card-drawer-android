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
import com.meli.android.carddrawer.model.ConstraintLayoutWithDisabledSupport.Child

internal class BottomLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Child {

    private var bottomDescription: AppCompatTextView
    private var animation: BottomLabelAnimationSet

    init {
          inflate(context, R.layout.card_drawer_bottom_label, this)
          bottomDescription = findViewById(R.id.bottom_description)
          animation = BottomLabelAnimationSet(this, bottomDescription)
    }

    fun setLabel(label: Label) {
        with(bottomDescription) {
            text = label.text
            setTextColor(safeParcelColor(label.color, Color.WHITE))
        }
        setWeight(label.weight)
        setBackgroundColor(label.backgroundColor)
    }

    fun showAnimation(animate: Boolean) {
        shouldAnimate(animate, VISIBLE) { animation.slideUp() }
    }

    fun hideAnimation(animate: Boolean) {
        shouldAnimate(animate, INVISIBLE) { animation.slideDown() }
    }

    private fun shouldAnimate(animate: Boolean, visibility: Int, function: () -> Unit) {
        if (animate) {
            function.invoke()
        } else {
            this.visibility = visibility
            bottomDescription.visibility = visibility
        }
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
