package com.meli.android.carddrawer.model

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.meli.android.carddrawer.ColorUtils.safeParcelColor
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.CardDrawerFont
import com.meli.android.carddrawer.format.TypefaceHelper
import com.meli.android.carddrawer.model.animation.BottomLabelAnimation
import kotlin.math.roundToInt

internal class BottomLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var bottomDescription: AppCompatTextView
    private var animation: BottomLabelAnimation? = null
    private var defaultBottomLabelWidth = resources.getDimension(R.dimen.card_drawer_card_width)
    private var color: String? = null

    init {
        inflate(context, R.layout.card_drawer_bottom_label, this)
        orientation = VERTICAL
        bottomDescription = findViewById(R.id.card_drawer_bottom_description)
    }

    fun setLabel(label: Label) {
        if (label.animate) animation = BottomLabelAnimation(this, BottomLabelAnimation(bottomDescription))
        with(bottomDescription) {
            text = label.text
            setTextColor(safeParcelColor(label.color, Color.WHITE))
        }
        setWeight(label.weight)
        color = label.backgroundColor
        setBackgroundColor(color)
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
                typefaceHelper.set(bottomDescription, typefaceHelper.get(context, CardDrawerFont.from(weight)))
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cardSizeMultiplier = measuredWidth / defaultBottomLabelWidth
        val bottomLabelHeightMultiplier = (if (oldh > 0) ((h * 100f) / oldh) / 100f else cardSizeMultiplier)

        setUpBottomDescriptionTextSize(cardSizeMultiplier)
        val containerBottomLabelParams = layoutParams
        val height = (containerBottomLabelParams.height * bottomLabelHeightMultiplier).roundToInt()
        containerBottomLabelParams.height = height
        layoutParams = containerBottomLabelParams
    }

    private fun setUpBottomDescriptionTextSize(multiplier: Float) {
        bottomDescription.post { bottomDescription.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextPixelSize(multiplier)) }
    }

    private fun getTextPixelSize(multiplier: Float) = resources.getDimension(R.dimen.card_drawer_font_size_small) * multiplier
}
