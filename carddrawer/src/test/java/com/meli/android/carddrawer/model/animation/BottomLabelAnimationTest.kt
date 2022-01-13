package com.meli.android.carddrawer.model.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.meli.android.carddrawer.BaseTest
import com.meli.android.carddrawer.R
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class BottomLabelAnimationTest: BaseTest() {

    private lateinit var bottomLabelAnimation: BottomLabelAnimation

    @MockK(relaxed = true)
    private lateinit var viewMock: View

    @MockK(relaxed = true)
    private lateinit var animationMock: Animation

    @MockK(relaxed = true)
    private lateinit var bottomLabelAnimationMock: BottomLabelAnimation

    @Before
    override fun setUp() {
        super.setUp()
        mockkStatic(AnimationUtils::class)
        every {
            AnimationUtils.loadAnimation(viewMock.context, R.anim.card_drawer_slide_up_in)
        } returns animationMock
        every {
            AnimationUtils.loadAnimation(viewMock.context, R.anim.card_drawer_slide_down_out)
        } returns animationMock
    }

    @Test
    fun `when call function slideUp then call startAnimation with slideUp`() {
        bottomLabelAnimation = BottomLabelAnimation(viewMock, bottomLabelAnimationMock)
        val slideUp = bottomLabelAnimation.getDeclaredField("slideUp") as Animation
        bottomLabelAnimation.slideUp()
        verify {
            viewMock.startAnimation(slideUp)
        }
    }

    @Test
    fun `when call function slideDown then call startAnimation with slideDown`() {
        bottomLabelAnimation = BottomLabelAnimation(viewMock, bottomLabelAnimationMock)
        val slideDown = bottomLabelAnimation.getDeclaredField("slideDown") as Animation
        bottomLabelAnimation.slideDown()
        verify {
            viewMock.startAnimation(slideDown)
        }
    }

    @Test
    fun `when instance BottomLabelAnimation without nextAnimation then call setAnimationListener of slideDown`() {
        bottomLabelAnimation = BottomLabelAnimation(viewMock)
        val slideDown = bottomLabelAnimation.getDeclaredField("slideDown") as Animation
        verify {
            slideDown.setAnimationListener(
                any()
            )
        }
    }

    @Test
    fun `when instance BottomLabelAnimation without nextAnimation then call setAnimationListener of slideUp`() {
        bottomLabelAnimation = BottomLabelAnimation(viewMock)
        val slideUp = bottomLabelAnimation.getDeclaredField("slideUp") as Animation
        verify {
            slideUp.setAnimationListener(
                any()
            )
        }
    }

    @Test
    fun `when instance BottomLabelAnimation without nextAnimation then don't call slideDown`() {
        bottomLabelAnimation = BottomLabelAnimation(viewMock)
        verify(inverse = true) {
            bottomLabelAnimationMock.slideDown()
        }
    }

    @Test
    fun `when instance BottomLabelAnimation without nextAnimation then don't call slideUp`() {
        bottomLabelAnimation = BottomLabelAnimation(viewMock)
        verify(inverse = true) {
            bottomLabelAnimationMock.slideUp()
        }
    }

    private fun BottomLabelAnimation.getDeclaredField(name: String): Any {
        return javaClass.getDeclaredField(name).let {
            it.isAccessible = true
            it.get(this) as Any
        }
    }

}