package com.meli.android.carddrawer.model.customView

import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.view.View
import androidx.appcompat.widget.DrawableUtils
import com.meli.android.carddrawer.model.customview.CustomSwitchHelper
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.math.roundToInt

class CustomSwitchHelperTest {

    private lateinit var customSwitchHelper: CustomSwitchHelper

    @Before
    fun setUp() {
        customSwitchHelper = CustomSwitchHelper
    }

    @Test
    fun `when call function makeTextLayout then return StaticLayout and calculate text width`() {
        val textPaint = mockk<TextPaint>(relaxed = true)
        val textToDraw = ""
        val layout = customSwitchHelper.makeTextLayout(textToDraw = textToDraw, textPaint = textPaint)
        verifyAll {
            textPaint.measureText(textToDraw).roundToInt()
        }
        Assert.assertNotNull(layout)
    }

    @Test
    fun `when call getOpticalBounds then return DrawableUtils INSETS_NONE`() {
        val drawable = mockk<Drawable>(relaxed = true)
        val result = customSwitchHelper.getOpticalBounds(drawable)
        Assert.assertSame(result, DrawableUtils.INSETS_NONE)
    }

    @Test
    fun `when call function isLayoutRtl with layout direction different from LAYOUT_DIRECTION_RTL then return false`() {
        val view = mockk<View>()
        val result = customSwitchHelper.isLayoutRtl(view)
        Assert.assertFalse(result)
    }

}