package com.meli.android.carddrawer.model.animation

import android.view.View
import android.view.animation.Animation
import com.meli.android.carddrawer.BaseTest
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import org.junit.Assert
import org.junit.Test


class InOutAnimationListenerTest: BaseTest() {

    private lateinit var inOutAnimationListener: InOutAnimationListener

    private lateinit var viewMock: View

    @MockK
    private lateinit var animationMock: Animation

    private var nextAnimation: Boolean = false

    override fun setUp() {
        super.setUp()
        nextAnimation = false
        viewMock = spyk(View(contextMock), recordPrivateCalls = true)
        inOutAnimationListener = InOutAnimationListener(viewMock, View.GONE) {
            methodIsCalled()
        }
    }

    @Test
    fun test() {
        inOutAnimationListener.onAnimationEnd(animationMock)
        val targetView = inOutAnimationListener.getDeclaredField2("targetView") as View
        Assert.assertEquals(targetView.visibility, View.VISIBLE)
    }

    @Test
    fun `when call onAnimationEnd with visibility then return targetView visible`() {
        inOutAnimationListener.onAnimationEnd(animationMock)
        val targetView = inOutAnimationListener.getDeclaredField("targetView") as View
        Assert.assertEquals(targetView.visibility, View.VISIBLE)
    }

    @Test
    fun `when call onAnimationEnd then call nextAnimation`() {
        inOutAnimationListener.onAnimationEnd(animationMock)
        Assert.assertTrue(nextAnimation)
    }

    @Test
    fun `when call onAnimationStart then don't call nextAnimation`() {
        inOutAnimationListener.onAnimationStart(animationMock)
        Assert.assertFalse(nextAnimation)
    }

    @Test
    fun `when call onAnimationRepeat then don't call nextAnimation`() {
        inOutAnimationListener.onAnimationRepeat(animationMock)
        Assert.assertFalse(nextAnimation)
    }

    private fun methodIsCalled() { nextAnimation = true }

    private fun InOutAnimationListener.getDeclaredField(name: String): Any {
        return javaClass.getDeclaredField(name).let {
            it.isAccessible = true
            it.get(this) as Any
        }
    }

    private fun InOutAnimationListener.getDeclaredField2(name: String): Any {
        return javaClass.getDeclaredField(name).let {
            it.isAccessible = true
            it.get(this) as Any
        }
    }



}