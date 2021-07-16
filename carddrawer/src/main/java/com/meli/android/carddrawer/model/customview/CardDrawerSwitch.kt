package com.meli.android.carddrawer.model.customview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.ColorUtils.safeParcelColor
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.configuration.CardDrawerStyle
import com.meli.android.carddrawer.format.CardDrawerFont
import com.meli.android.carddrawer.format.TypefaceHelper.get
import com.meli.android.carddrawer.format.TypefaceHelper.set
import com.meli.android.carddrawer.model.CardDrawerView
import kotlin.math.roundToInt

private const val THUMB_SIZE_MULTIPLIED = 1.73f

class CardDrawerSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var switchModel: SwitchModel
    private lateinit var switchSelection: String
    private lateinit var switchCompatContainer: FrameLayout
    private lateinit var description: TextView
    private var gradientLayerPadding = getDimension(R.dimen.card_drawer_gradient_layer_padding_high_res)
    private var thumbLayerPadding = getDimension(R.dimen.card_drawer_thumb_layer_padding_high_res)
    private var thumbLayerDrawable = (getDrawable(R.drawable.button_switch) as LayerDrawable)
    private var trackGradientDrawable = (getDrawable(R.drawable.track_switch) as GradientDrawable)
    private var switchTextSize = getDimension(R.dimen.card_drawer_switch_text_size_high_res)
    private var defaultSwitchWidth = resources.getDimension(R.dimen.card_drawer_card_width)
    private var onSwitchListener: OnSwitchListener? = null

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    init {
        initComponents()
    }

    private fun initComponents() {
        inflate(context, R.layout.card_drawer_switch, this).also { it.id = R.id.card_drawer_switch }
        description = findViewById(R.id.description)
        switchCompatContainer = findViewById(R.id.card_drawer_switch_container)
    }

    fun setSwitchModel(switchModel: SwitchModel) {
        this.switchModel = switchModel
        setUpConfiguration()
    }

    private fun setUpConfiguration() {
        setUpSwitchCurrentSelection()
        setUpDescription()
        setBackgroundColor(safeParcelColor(switchModel.safeZoneBackgroundColor, Color.BLACK))
    }

    private fun setUpSwitchCurrentSelection() {
        switchSelection = switchModel.default
        onSwitchListener?.onChange(switchSelection)
    }

    private fun setUpDescription() {
        with(switchModel.description) {
            description.text = text
            description.setTextColor(safeParcelColor(textColor, Color.BLACK))
            set(description, CardDrawerFont.from(weight))
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cardSizeMultiplier = measuredWidth / defaultSwitchWidth
        val switchOptionWidthMultiplier = (if (oldw > 0) ((w * 100f) / oldw) / 100f else cardSizeMultiplier)
        val switchOptionHeightMultiplier = (if (oldh > 0) ((h * 100f) / oldh) / 100f else cardSizeMultiplier)

        setUpDescriptionTextSize(cardSizeMultiplier)
        setUpSwitch(cardSizeMultiplier, switchOptionWidthMultiplier, switchOptionHeightMultiplier)
    }

    private fun setUpDescriptionTextSize(multiplier: Float) {
        description.post { description.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextPixelSize(multiplier)) }
    }

    private fun setUpSwitch(cardSizeMultiplier: Float, widthMultiplier: Float, heightMultiplier: Float) {
        val (op1, op2) = switchModel.options.let { it.first() to it.last() }
        val trackTextColor = switchModel.states.uncheckedState.textColor
        val trackTextWeight = switchModel.states.uncheckedState.weight
        val thumbBackgroundColor = switchModel.pillBackgroundColor
        val trackBackgroundColor = switchModel.switchBackgroundColor
        val thumbTextColor = switchModel.states.checkedState.textColor
        val thumbTextWeight = switchModel.states.checkedState.weight
        val switchCompat = CustomSwitchCompat(context)

        setUpTrackDrawable(trackBackgroundColor, widthMultiplier, heightMultiplier)
        setUpThumbDrawable(thumbBackgroundColor, widthMultiplier, heightMultiplier)
        with(switchCompat) {
            id = R.id.custom_switch
            width = MATCH_PARENT
            height = WRAP_CONTENT
            setShowThumbText(true)
            setShowTrackText(true)
            setLeftText(op1.name)
            setRightText(op2.name)
            isChecked = switchSelection != op1.id
            thumbSizeMultiplied = THUMB_SIZE_MULTIPLIED

            if (switchCompatContainer.childCount > 0) {
                switchCompatContainer.removeAllViews()
            }

            setOnCheckedChangeListener { _, isChecked ->
                switchSelection = if (!isChecked) op1.id else op2.id
                onSwitchListener?.onChange(switchSelection)
            }

            switchCompatContainer.addView(switchCompat)

            post {
                setThumbTextColor(safeParcelColor(thumbTextColor, Color.BLACK))
                setTrackTextColor(safeParcelColor(trackTextColor, Color.BLACK))
                setThumbTypeface(get(context, CardDrawerFont.from(thumbTextWeight)))
                setTrackTypeface(get(context, CardDrawerFont.from(trackTextWeight)))
                setTrackTextSize(switchTextSize * cardSizeMultiplier)
                setThumbTextSize(switchTextSize * cardSizeMultiplier)
                setThumbDrawable(thumbLayerDrawable)
                setTrackDrawable(trackGradientDrawable)
            }
        }
    }

    private fun setUpThumbDrawable(thumbBackground: String, widthMultiplier: Float, heightMultiplier: Float) {
        with(thumbLayerDrawable) {
            val thumbDrawable = (findDrawableByLayerId(R.id.thumb) as GradientDrawable)
            val gradientThumbDrawable = (findDrawableByLayerId(R.id.gradient_thumb) as GradientDrawable)
            val newThumbWidth = (thumbDrawable.intrinsicWidth * widthMultiplier).roundToInt()
            val newWidth = (gradientThumbDrawable.intrinsicWidth * widthMultiplier).roundToInt()
            val newThumbHeight = (thumbDrawable.intrinsicHeight * heightMultiplier).roundToInt()
            val newHeight = (gradientThumbDrawable.intrinsicHeight * heightMultiplier).roundToInt()

            gradientLayerPadding = (gradientLayerPadding * widthMultiplier).roundToInt()
            thumbLayerPadding = (thumbLayerPadding * widthMultiplier).roundToInt()
            thumbDrawable.colorFilter = makeColorFilter(thumbBackground)
            thumbDrawable.setSize(newThumbWidth, newThumbHeight)
            gradientThumbDrawable.setSize(newWidth, newHeight)
            setLayerInset(0, thumbLayerPadding, thumbLayerPadding, thumbLayerPadding, thumbLayerPadding)
            setLayerInset(1, gradientLayerPadding, gradientLayerPadding, gradientLayerPadding, gradientLayerPadding)
        }
    }

    private fun setUpTrackDrawable(trackBackgroundColor: String, widthMultiplier: Float, heightMultiplier: Float){
        val width = (getTrackWidth() * widthMultiplier).roundToInt()
        val height = (getTrackHeight() * heightMultiplier).roundToInt()

        with(trackGradientDrawable) {
            bounds = Rect(0, 0, width, height)
            colorFilter = makeColorFilter(trackBackgroundColor)
            setSize(height, height)
        }
    }

    private fun getTrackWidth() = with(trackGradientDrawable) {
        bounds.takeIf { !it.isEmpty }?.right ?: intrinsicWidth }
    private fun getTrackHeight() = with(trackGradientDrawable) {
        bounds.takeIf { !it.isEmpty }?.bottom ?: intrinsicHeight }
    private fun getTextPixelSize(multiplier: Float) = resources.getDimension(R.dimen.card_drawer_font_size) * multiplier

    fun setSwitchListener(onSwitchListener: OnSwitchListener) {
        this.onSwitchListener = onSwitchListener
    }

    fun setConfiguration(configuration: CustomViewConfiguration) {
        with(configuration) {
            when (cardDrawerType) {
                CardDrawerView.Type.LOW -> {
                    val visibility = if (cardDrawerStyle == CardDrawerStyle.REGULAR) {
                        configureForRegularStyle()
                        defaultSwitchWidth /= 2
                        View.GONE
                    } else {
                        configureForHybridStyle()
                        View.VISIBLE
                    }
                    description.visibility = visibility
                    setBackgroundColor(Color.TRANSPARENT)
                    configureForLowRes()
                }
                CardDrawerView.Type.MEDIUM -> {
                    configureForMediumRes()
                }
            }
        }
    }

    private fun configureForMediumRes() {
        ConstraintSet().also {
            it.clone(this@CardDrawerSwitch)
            it.connect(
                    switchCompatContainer.id,
                    ConstraintSet.BOTTOM,
                    R.id.switch_bottom_guideline_medium_res,
                    ConstraintSet.BOTTOM
            )
            it.connect(
                    switchCompatContainer.id,
                    ConstraintSet.TOP,
                    R.id.switch_top_guideline_medium_res,
                    ConstraintSet.TOP
            )
        }.applyTo(this@CardDrawerSwitch)
        customSwitchForMediumAndLowRes()
    }

    private fun customSwitchForMediumAndLowRes() {
        switchTextSize = getDimension(R.dimen.card_drawer_switch_text_size_low_res)
        gradientLayerPadding = getDimension(R.dimen.card_drawer_gradient_layer_padding_low_res)
        thumbLayerPadding = getDimension(R.dimen.card_drawer_thumb_layer_padding_low_res)
        thumbLayerDrawable = (getDrawable(R.drawable.button_switch_low_res) as LayerDrawable)
        trackGradientDrawable = (getDrawable(R.drawable.track_switch_low_res) as GradientDrawable)
    }

    private fun configureForRegularStyle() {
        ConstraintSet().also {
            it.clone(this@CardDrawerSwitch)
            it.connect(
                switchCompatContainer.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            it.connect(
                switchCompatContainer.id,
                ConstraintSet.END,
                R.id.switch_end_guideline_low_res,
                ConstraintSet.END
            )
        }.applyTo(this@CardDrawerSwitch)
    }

    private fun configureForHybridStyle() {
        ConstraintSet().also {
            it.clone(this@CardDrawerSwitch)
            it.connect(
                switchCompatContainer.id,
                ConstraintSet.START,
                R.id.switch_center_guideline,
                ConstraintSet.START
            )
        }.applyTo(this@CardDrawerSwitch)
    }

    private fun configureForLowRes() {
        ConstraintSet().also {
            it.clone(this@CardDrawerSwitch)
            it.connect(
                switchCompatContainer.id,
                ConstraintSet.BOTTOM,
                R.id.switch_bottom_guideline_low_res,
                ConstraintSet.BOTTOM
            )
            it.connect(
                switchCompatContainer.id,
                ConstraintSet.TOP,
                R.id.switch_top_guideline_low_res,
                ConstraintSet.TOP
            )
        }.applyTo(this@CardDrawerSwitch)
        customSwitchForMediumAndLowRes()
    }

    @Suppress("unused")
    fun getSwitchSelection() = switchSelection

    private fun makeColorFilter(color: String): ColorFilter {
        return PorterDuffColorFilter(safeParcelColor(color, Color.BLACK), PorterDuff.Mode.SRC_IN)
    }

    private fun getDimension(@DimenRes dimenRes: Int): Int {
        return resources.getDimensionPixelSize(dimenRes)
    }

    private fun getDrawable(@DrawableRes drawableRes: Int): Drawable {
        return ContextCompat.getDrawable(context, drawableRes)!!.constantState!!.newDrawable().mutate()
    }

    interface OnSwitchListener {
        fun onChange(id: String)
    }
}