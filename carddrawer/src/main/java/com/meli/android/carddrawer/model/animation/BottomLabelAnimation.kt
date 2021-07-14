package com.meli.android.carddrawer.model.animation

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.meli.android.carddrawer.R

internal class BottomLabelAnimation(
    private val targetView: View
) {

    private val slideUp: Animation =
        AnimationUtils.loadAnimation(targetView.context, R.anim.card_drawer_slide_up_in)
    private val slideDown: Animation =
        AnimationUtils.loadAnimation(targetView.context, R.anim.card_drawer_slide_down_out)

    fun slideUp() {
        initAnimation(targetView, slideUp, VISIBLE)
    }

    fun slideDown() {
        initAnimation(targetView, slideDown, INVISIBLE)
    }

    private fun initAnimation(targetView: View, animation: Animation, visibility: Int) {
        targetView.also {
            it.clearAnimation()
            it.startAnimation(animation)
            it.visibility = visibility
        }
    }
}