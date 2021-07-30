package com.meli.android.carddrawer.model.animation

import android.view.View
import android.view.animation.Animation

internal class InOutAnimationListener(
    private val targetView: View,
    private val newVisibility: Int,
    private val nextAnimation: () -> Unit?
) : Animation.AnimationListener {

    init {
        targetView.clearAnimation()
    }

    override fun onAnimationStart(animation: Animation?) = Unit
    override fun onAnimationRepeat(animation: Animation?) = Unit

    override fun onAnimationEnd(animation: Animation?) {
        with(targetView) {
            visibility = newVisibility
            postOnAnimation {
                clearAnimation()
            }
        }
        nextAnimation()
    }
}
