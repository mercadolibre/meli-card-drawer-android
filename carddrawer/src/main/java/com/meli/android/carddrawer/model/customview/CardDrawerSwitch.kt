package com.meli.android.carddrawer.model.customview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.format.TypefaceSetter.set
import com.meli.android.carddrawer.utils.dpToPx


class CardDrawerSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
): ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var switchModel: SwitchModel
    private var onSwitchListener: OnSwitchListener? = null
    private lateinit var switchSelection: String

    private lateinit var switchCompat: SwitchCompat
    private lateinit var description: TextView

    init {
        initComponents()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private fun initComponents() {
        LayoutInflater.from(context).inflate(R.layout.card_drawer_switch, this)
        description = findViewById(R.id.description)
        switchCompat = findViewById(R.id.switch_button)
    }

    fun setSwitchModel(switchModel: SwitchModel) {
        this.switchModel = switchModel
        setUpConfiguration()
    }

    private fun setUpConfiguration() {
        switchSelection = switchModel.default
        onSwitchListener?.onChange(switchSelection)
        setBackgroundColor(Color.parseColor(switchModel.safeZoneBackgroundColor))
        setUpOptions(switchModel.options)
        setUpCheckedText(switchModel.states.checkedState)
        setUpDescription(switchModel.description)
    }

    private fun setUpOptions(options: List<SwitchModel.SwitchOption>) {
        val (op1, op2) = (options.first() to options.last())

        switchCompat.textOff = op1.name
        switchCompat.textOn = op2.name

        switchCompat.setOnCheckedChangeListener { _, isChecked ->

            // If isChecked = true it is in the on position
            switchSelection = if (isChecked) { op1.id } else { op2.id }
            onSwitchListener?.onChange(switchSelection)
        }
    }

    private fun setUpDescription(descriptionText: SwitchModel.Text) {
        description.text = descriptionText.text
        description.setTextColor(Color.parseColor(descriptionText.textColor))
        set(description, Typeface.DEFAULT_BOLD)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        switchCompat.thumbDrawable = getThumbDrawable()

        switchCompat.trackDrawable = getTrackDrawableWithText(
            switchModel.states.uncheckedState.textColor,
            switchCompat.textOff.toString(),
            switchCompat.textOn.toString()
        )
    }

    private fun getThumbDrawable(): Drawable {
        val thumbLayerDrawable = ContextCompat.getDrawable(context, R.drawable.switch_button)!! as LayerDrawable
        val thumbDrawable = thumbLayerDrawable.findDrawableByLayerId(R.id.thumb)
        thumbDrawable.colorFilter = PorterDuffColorFilter(
            Color.parseColor(switchModel.pillBackgroundColor),
            PorterDuff.Mode.SRC_IN)

        return thumbLayerDrawable
    }

    private fun getTrackDrawableWithText(textColor: String, leftText: String, rightText: String): Drawable {
        val trackDrawable = ContextCompat.getDrawable(context, R.drawable.track_selector)!!
        trackDrawable.colorFilter = PorterDuffColorFilter(
            Color.parseColor(switchModel.switchBackgroundColor),
            PorterDuff.Mode.SRC_IN)
        val bitmap = Bitmap.createBitmap(
            (trackDrawable.intrinsicWidth * 1.7).toInt(),
            trackDrawable.intrinsicHeight ,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val textPaint = createTextTrackPaint(textColor)
        val rectText = Rect()

        trackDrawable.bounds = canvas.clipBounds
        trackDrawable.draw(canvas)

        textPaint.getTextBounds(rightText, 0, rightText.length, rectText)

        // The baseline for the text: centered, including the height of the text itself
        val heightBaseline: Float = canvas.clipBounds.height() / 1.6f

        // This is one quarter of the full width, to measure the centers of the texts
        val widthQuarter: Float = canvas.clipBounds.width() / 4f
        canvas.drawText(leftText, 0, leftText.length,
            widthQuarter, heightBaseline,
            textPaint)
        canvas.drawText(rightText, 0, rightText.length,
            widthQuarter * 3, heightBaseline,
            textPaint)

        return BitmapDrawable(resources, bitmap)
    }

    private fun createTextTrackPaint(textColor: String): Paint {
        val textPaint = Paint()
        textPaint.color = Color.parseColor(textColor)
        textPaint.isAntiAlias = true
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = context.dpToPx(14)
        textPaint.typeface = Typeface.DEFAULT_BOLD
        // Set textSize, typeface, etc, as you wish
        return textPaint
    }

    private fun setUpCheckedText(checkedState: SwitchModel.SwitchStates.State) {
        switchCompat.setTextColor(Color.parseColor(checkedState.textColor))
        switchCompat.setSwitchTextAppearance(context, R.style.SwitchTextAppearance)
        switchCompat.setSwitchTypeface(Typeface.DEFAULT_BOLD)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun setSwitchListener(onSwitchListener: OnSwitchListener) {
        this.onSwitchListener = onSwitchListener
    }

    fun getSwitchSelection() = switchSelection

    interface OnSwitchListener {
        fun onChange(id: String)
    }
}