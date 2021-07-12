package com.meli.android.carddrawer.model.animation

import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import com.meli.android.carddrawer.R
import com.meli.android.carddrawer.model.BottomLabel

internal class InOutAnimationListener(
    private val targetView: View,
    private val visibilityFlag: Int
) : AnimationListener {

    override fun onAnimationStart(animation: Animation) {}

    override fun onAnimationEnd(animation: Animation) {
        targetView.visibility = visibilityFlag
    }

    override fun onAnimationRepeat(animation: Animation) {}
}