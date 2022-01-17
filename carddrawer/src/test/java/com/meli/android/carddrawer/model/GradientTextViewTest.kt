package com.meli.android.carddrawer.model

import android.graphics.Canvas
import com.meli.android.carddrawer.BasicRobolectricTest
import com.meli.android.carddrawer.configuration.CardFontConfiguration
import com.meli.android.carddrawer.configuration.FontType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class GradientTextViewTest : BasicRobolectricTest() {

    @Test
    fun init_loadsViews() {
        val gradientTextView = GradientTextView(context)
        Assert.assertNotNull(ReflectionHelpers.getField(gradientTextView, "fontType"))
        Assert.assertNotNull(ReflectionHelpers.getField(gradientTextView, "placeHolder"))
        Assert.assertNotNull(ReflectionHelpers.getField(gradientTextView, "fontColor"))
    }

    @Test
    fun onDraw_setConfigurationGradient() {
        val gradientTextView = mockk<GradientTextView>(relaxed = true)
        val configurator = mockk<CardFontConfiguration>(relaxed = true)
        val canvas = mockk<Canvas>(relaxed = true)
        every { gradientTextView.onDraw(canvas) } answers { callOriginal() }
        every { gradientTextView.configuration } returns configurator
        ReflectionHelpers.setField(gradientTextView, "placeHolder", "cardNumber")
        gradientTextView.onDraw(canvas)
        verify {
            configurator.setShadow(
                any()
            )
        }
    }

    @Test
    fun onDraw_doesntSetConfigurationGradient() {
        val gradientTextView = mockk<GradientTextView>(relaxed = true)
        val configurator = mockk<CardFontConfiguration>(relaxed = true)
        val canvas = mockk<Canvas>(relaxed = true)
        every { gradientTextView.configuration } returns configurator
        ReflectionHelpers.setField(gradientTextView, "placeHolder", "cardNumber")
        gradientTextView.onDraw(canvas)
        verify(inverse = true) {
            configurator.setShadow(
                any()
            )
        }
    }

    @Test
    fun init_setsValues() {
        val gradientTextView = GradientTextView(context)
        gradientTextView.init(FontType.LIGHT_TYPE, "MM/YY", 2)
        Assert.assertEquals(
            FontType.LIGHT_TYPE,
            ReflectionHelpers.getField(gradientTextView, "fontType")
        )
        Assert.assertEquals("MM/YY", ReflectionHelpers.getField(gradientTextView, "placeHolder"))
        Assert.assertEquals(
            2,
            ReflectionHelpers.getField<Any>(gradientTextView, "fontColor") as Int
        )
    }
}