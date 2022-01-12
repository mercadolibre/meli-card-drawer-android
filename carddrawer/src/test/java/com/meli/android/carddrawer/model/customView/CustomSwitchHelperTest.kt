package com.meli.android.carddrawer.model.customView

import android.graphics.drawable.Drawable
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import androidx.appcompat.widget.DrawableUtils
import com.meli.android.carddrawer.TestUtils
import com.meli.android.carddrawer.model.customview.CustomSwitchHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
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
        customSwitchHelper = CustomSwitchHelper
        val textPaint = mockk<TextPaint>(relaxed = true)
        val textToDraw = ""
        val layout = customSwitchHelper.makeTextLayout(textToDraw = textToDraw, textPaint = textPaint)
        verify {
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
    fun `when call getOpticalBounds then return Rect with sides equal to zero`() {
        TestUtils.changeSDKVersion(30)
        val drawable = mockk<Drawable>(relaxed = true)
        val result = customSwitchHelper.getOpticalBounds(drawable)
        Assert.assertEquals(result.left, 0)
        Assert.assertEquals(result.right, 0)
        Assert.assertEquals(result.top, 0)
        Assert.assertEquals(result.bottom, 0)
        TestUtils.changeSDKVersion(0)
    }

    @Test
    fun `when call function isLayoutRtl with layout direction different from LAYOUT_DIRECTION_RTL then return false`() {
        val view = mockk<View>(relaxed = true)
        val result = customSwitchHelper.isLayoutRtl(view)
        Assert.assertFalse(result)
    }

    @Test
    fun `when call function makeTextLayout when SDK Version is 30 then call StaticLayout build`() {
        TestUtils.changeSDKVersion(30)
        mockkStatic(StaticLayout.Builder::class)
        val textPaint = mockk<TextPaint>(relaxed = true)
        val textToDraw = ""

        every {
            StaticLayout
                .Builder
                .obtain(
                    textToDraw,
                    0,
                    textToDraw.length,
                    textPaint,
                    0)
        } returns mockk()

        every {
            StaticLayout
                .Builder
                .obtain(
                    textToDraw,
                    0,
                    textToDraw.length,
                    textPaint,
                    0).build()
        } returns mockk()

        customSwitchHelper.makeTextLayout(textToDraw, textPaint)

        verify {
            StaticLayout
                .Builder
                .obtain(
                    textToDraw,
                    0,
                    textToDraw.length,
                    textPaint,
                    0).build()
        }
        TestUtils.changeSDKVersion(0)
    }

}