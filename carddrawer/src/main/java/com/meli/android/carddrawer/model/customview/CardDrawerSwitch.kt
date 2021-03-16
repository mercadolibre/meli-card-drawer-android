package com.meli.android.carddrawer.model.customview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.text.StaticLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.ColorUtils.safeParcelColor
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.TypefaceSetter.set
import com.meli.android.carddrawer.model.customview.CardDrawerSwitchHelper.getThumbTextAppearance
import com.meli.android.carddrawer.model.customview.CardDrawerSwitchHelper.makeTextLayout
import com.meli.android.carddrawer.model.customview.CardDrawerSwitchHelper.makeTrackTextPaint
import com.meli.android.carddrawer.utils.dpToPx
import kotlin.math.roundToInt

class CardDrawerSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var onSwitchListener: OnSwitchListener? = null
    private lateinit var switchModel: SwitchModel
    private lateinit var switchSelection: String
    private lateinit var switchCompatContainer: FrameLayout
    private lateinit var description: TextView

    private var thumbLayerPadding = context.dpToPx(4).roundToInt()
    private var gradientLayerPadding = context.dpToPx(6).roundToInt()

    private val thumbLayerDrawable = (ContextCompat.getDrawable(context, R.drawable.button_switch)!!
        .constantState!!.newDrawable().mutate() as LayerDrawable)

    private val trackDrawable = (ContextCompat.getDrawable(context, R.drawable.track_switch)!!
        .constantState!!.newDrawable().mutate() as GradientDrawable)

    init {
        initComponents()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

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
        setBackgroundColor(safeParcelColor(switchModel.safeZoneBackgroundColor))
    }

    private fun setUpSwitchCurrentSelection() {
        switchSelection = switchModel.default
        onSwitchListener?.onChange(switchSelection)
    }

    private fun setUpDescription() {
        with(switchModel.description) {
            description.text = text
            description.setTextColor(safeParcelColor(textColor))
            set(description, typeface)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cardSizeMultiplier = measuredWidth / resources.getDimension(R.dimen.card_drawer_card_width)
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
        val trackTextTypeFace = switchModel.states.uncheckedState.typeface
        val thumbBackgroundColor = switchModel.pillBackgroundColor
        val trackBackgroundColor = switchModel.switchBackgroundColor
        val thumbTextColor = switchModel.states.checkedState.textColor
        val thumbTextTypeface = switchModel.states.checkedState.typeface
        val switchCompat = SwitchCompat(context)
        val trackDrawable = makeTrackDrawableWithText(
            trackTextColor,
            trackBackgroundColor,
            trackTextTypeFace,
            op1.name,
            op2.name,
            cardSizeMultiplier,
            widthMultiplier,
            heightMultiplier)

        setUpThumbDrawable(thumbBackgroundColor, widthMultiplier, heightMultiplier)

        with(switchCompat) {
            id = R.id.custom_switch
            width = MATCH_PARENT
            height = WRAP_CONTENT
            showText = true
            textOff = op1.name
            textOn = op2.name
            isChecked = switchSelection != op1.id

            if (switchCompatContainer.childCount > 0) {
                switchCompatContainer.removeAllViews()
            }

            setOnCheckedChangeListener { _, isChecked ->
                // If isChecked = true it is in the On position
                switchSelection = if (!isChecked) op1.id else op2.id
                onSwitchListener?.onChange(switchSelection)
            }

            switchCompatContainer.addView(this)

            post {
                setTextColor(Color.parseColor(thumbTextColor))
                setSwitchTextAppearance(context, getThumbTextAppearance(context, cardSizeMultiplier))
                setSwitchTypeface(thumbTextTypeface)
                this.thumbDrawable = thumbLayerDrawable
                this.trackDrawable = trackDrawable
            }
        }
    }

    private fun setUpThumbDrawable(thumbBackground: String, widthMultiplier: Float, heightMultiplier: Float) {
        val thumbDrawable = (thumbLayerDrawable.findDrawableByLayerId(R.id.thumb) as GradientDrawable)
        val gradientThumbDrawable = (thumbLayerDrawable.findDrawableByLayerId(R.id.gradient_thumb) as GradientDrawable)
        val newThumbWidth = (thumbDrawable.intrinsicWidth * widthMultiplier).roundToInt()
        val newWidth = (gradientThumbDrawable.intrinsicWidth * widthMultiplier).roundToInt()
        val newThumbHeight = (thumbDrawable.intrinsicHeight * heightMultiplier).roundToInt()
        val newHeight = (gradientThumbDrawable.intrinsicHeight * heightMultiplier).roundToInt()

        thumbLayerPadding = (thumbLayerPadding * widthMultiplier).roundToInt()
        gradientLayerPadding = (gradientLayerPadding * widthMultiplier).roundToInt()
        thumbDrawable.colorFilter = PorterDuffColorFilter(safeParcelColor(thumbBackground), PorterDuff.Mode.SRC_IN)
        thumbDrawable.setSize(newThumbWidth, newThumbHeight)
        gradientThumbDrawable.setSize(newWidth, newHeight)
        setUpPaddingForLayerItem(0, thumbLayerPadding)
        setUpPaddingForLayerItem(1, gradientLayerPadding)
    }

    private fun setUpPaddingForLayerItem(index: Int, padding: Int) {
        thumbLayerDrawable.setLayerInset(index, padding, padding, padding, padding)
    }

    private fun makeTrackDrawableWithText(
        trackTextColor: String,
        trackBackgroundColor: String,
        trackTextTypeFace: Typeface,
        leftText: String,
        rightText: String,
        cardSizeMultiplier: Float,
        widthMultiplier: Float,
        heightMultiplier: Float): Drawable {
        trackDrawable.colorFilter = PorterDuffColorFilter(safeParcelColor(trackBackgroundColor), PorterDuff.Mode.SRC_IN)
        val bitmap = Bitmap.createBitmap(
            (getTrackWidth() * widthMultiplier).roundToInt(),
            (getTrackHeight() * heightMultiplier).roundToInt(),
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val currentBounds = canvas.clipBounds
        val textPaint = makeTrackTextPaint(context, trackTextColor, trackTextTypeFace, cardSizeMultiplier)
        val sLeftText = makeTextLayout(leftText, textPaint)
        val sRightText = makeTextLayout(rightText, textPaint)

        trackDrawable.bounds = currentBounds
        trackDrawable.draw(canvas)

        drawText(canvas, sLeftText)
        drawText(canvas, sRightText, 3)

        return BitmapDrawable(resources, bitmap)
    }

    private fun drawText(canvas: Canvas, textLayout: StaticLayout, widthMultiplier: Int = 1) {
        val bounds = canvas.clipBounds
        val widthQuarter = bounds.width() / 4f
        canvas.save()
        val top = (bounds.top + bounds.bottom) / 2 - textLayout.height / 2f
        canvas.translate(widthQuarter * widthMultiplier, top)
        textLayout.draw(canvas)
        canvas.restore()
    }

    private fun getTrackWidth() = trackDrawable.bounds.takeIf { !it.isEmpty }?.right ?: trackDrawable.intrinsicWidth
    private fun getTrackHeight() = trackDrawable.bounds.takeIf { !it.isEmpty }?.bottom ?: trackDrawable.intrinsicHeight
    private fun getTextPixelSize(multiplier: Float) = resources.getDimension(R.dimen.card_drawer_font_size) * multiplier

    fun setSwitchListener(onSwitchListener: OnSwitchListener) {
        this.onSwitchListener = onSwitchListener
    }

    @Suppress("unused")
    fun getSwitchSelection() = switchSelection

    interface OnSwitchListener {
        fun onChange(id: String)
    }
}