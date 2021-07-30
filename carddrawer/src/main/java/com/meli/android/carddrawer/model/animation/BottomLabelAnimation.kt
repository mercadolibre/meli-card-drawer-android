package com.meli.android.carddrawer.model.animation

import android.view.View
import android.view.View.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.meli.android.carddrawer.R

internal class BottomLabelAnimation(
    private val targetView: View,
    nextAnimation: BottomLabelAnimation? = null
) {

    private val slideUp: Animation =
        AnimationUtils.loadAnimation(targetView.context, R.anim.card_drawer_slide_up_in)
    private val slideDown: Animation =
        AnimationUtils.loadAnimation(targetView.context, R.anim.card_drawer_slide_down_out)

    init {
        slideUp.setAnimationListener(
            InOutAnimationListener(
                targetView,
                VISIBLE
            ) {
                nextAnimation?.slideUp()
            }
        )
        slideDown.setAnimationListener(
            InOutAnimationListener(
                targetView,
                INVISIBLE
            ) {
                nextAnimation?.slideDown()
            }
        )
    }

    fun slideUp() {
        targetView.startAnimation(slideUp)
    }

    fun slideDown() {
        targetView.startAnimation(slideDown)
    }
}