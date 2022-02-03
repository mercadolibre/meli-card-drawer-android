package com.meli.android.carddrawer

import android.animation.Animator
import android.graphics.PorterDuff
import android.view.animation.Animation
import android.widget.ImageView
import com.meli.android.carddrawer.TestUtils.getDeclaredField
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test

class EndAnimationListenerTest: BaseTest() {

    private lateinit var endAnimationListener: EndAnimationListener

    @MockK
    private lateinit var imageViewMock: ImageView

    private val color = 0

    override fun setUp() {
        super.setUp()
        endAnimationListener = EndAnimationListener(imageViewMock, color)
    }

    @Test
    fun `when call constructor then verify if image was set`() {
        val imageView = endAnimationListener.getDeclaredField("image")
        Assert.assertEquals(imageView, imageViewMock)
    }

    @Test
    fun `when call constructor then verify if color was set`() {
        val colorInt = endAnimationListener.getDeclaredField("color")
        Assert.assertEquals(color, colorInt)
    }

    @Test
    fun `when call function onAnimationStart with animator per parameter then don't call setColorFilter`() {
        val animator = mockk<Animator>()
        endAnimationListener.onAnimationStart(animator)
        verify(inverse = true) {
            imageViewMock.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Test
    fun `when call function onAnimationEnd with animator per parameter then call setColorFilter`() {
        val animator = mockk<Animator>()
        endAnimationListener.onAnimationEnd(animator)
        verify {
            imageViewMock.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Test
    fun `when call function onAnimationCancel with animator per parameter then don't call setColorFilter`() {
        val animator = mockk<Animator>()
        endAnimationListener.onAnimationCancel(animator)
        verify(inverse = true) {
            imageViewMock.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Test
    fun `when call function onAnimationRepeat with animator per parameter then don't call setColorFilter`() {
        val animator = mockk<Animator>()
        endAnimationListener.onAnimationRepeat(animator)
        verify(inverse = true) {
            imageViewMock.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Test
    fun `when call function onAnimationStart with animation per parameter then don't call setColorFilter`() {
        val animation = mockk<Animation>()
        endAnimationListener.onAnimationStart(animation)
        verify(inverse = true) {
            imageViewMock.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Test
    fun `when call function onAnimationEnd with animation per parameter then call setColorFilter`() {
        val animation = mockk<Animation>()
        endAnimationListener.onAnimationEnd(animation)
        verify {
            imageViewMock.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    @Test
    fun `when call function onAnimationRepeat with animation per parameter then don't call setColorFilter`() {
        val animation = mockk<Animation>()
        endAnimationListener.onAnimationRepeat(animation)
        verify(inverse = true) {
            imageViewMock.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

}
