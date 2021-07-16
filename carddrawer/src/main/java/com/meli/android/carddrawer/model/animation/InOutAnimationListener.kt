package com.meli.android.carddrawer.model.animation

import android.view.View
import android.view.animation.Animation

internal class InOutAnimationListener(
    private val targetView: View,
    private val visibility: Int,
    private val nextAnimation: () -> Unit?
) : Animation.AnimationListener {

    override fun onAnimationStart(animation: Animation?) {}

    override fun onAnimationEnd(animation: Animation?) {
        targetView.visibility = visibility
        nextAnimation.invoke()
    }

    override fun onAnimationRepeat(animation: Animation?) {}
}
