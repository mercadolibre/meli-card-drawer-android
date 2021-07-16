package com.meli.android.carddrawer.model

import android.view.View
import com.meli.android.carddrawer.model.animation.BottomLabelAnimation

internal class BottomLabelAnimationSet(
    firstView: View,
    secondView: View
) {
    private var firstAnimation: BottomLabelAnimation

    init {
        firstAnimation = createAnimation(firstView, createAnimation(secondView, null))
    }

    private fun createAnimation(view: View, nextAnimation: BottomLabelAnimation?): BottomLabelAnimation {
        return BottomLabelAnimation(view, nextAnimation)
    }

    fun slideUp() {
        firstAnimation.slideUp()
    }

    fun slideDown() {
        firstAnimation.slideDown()
    }

}
