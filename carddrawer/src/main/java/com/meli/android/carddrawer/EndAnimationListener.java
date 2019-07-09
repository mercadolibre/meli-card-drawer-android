package com.meli.android.carddrawer;

import android.animation.Animator;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * When the animation finishes.. tint the background for future changes
 */
public class EndAnimationListener implements Animator.AnimatorListener, Animation.AnimationListener {

    private final ImageView image;
    @ColorInt
    private final int color;

    /**
     * @param image to show
     * @param color for background
     */
    public EndAnimationListener(ImageView image, @ColorInt int color) {
        this.image = image;
        this.color = color;
    }

    // --- animator interface

    @Override
    public void onAnimationStart(Animator animation) {
        //do nothing
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        //do nothing
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        //do nothing
    }

    // -- animation interface
    @Override
    public void onAnimationStart(Animation animation) {
        //do nothing
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //do nothing
    }
}

