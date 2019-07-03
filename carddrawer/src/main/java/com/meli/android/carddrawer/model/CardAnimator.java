package com.meli.android.carddrawer.model;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.meli.android.carddrawer.EndAnimationListener;
import com.meli.android.carddrawer.R;
import com.meli.android.carddrawer.configuration.FieldPosition;

import java.lang.ref.WeakReference;

public class CardAnimator {
    private static final int ANIMATION_EXTRA_FACTOR = 3;

    @FieldPosition
    private int showingLayout;

    @ColorInt
    private int showingColor;

    private final View cardBackLayout;
    private final View cardFrontLayout;

    private final WeakReference<Context> context;

    private AnimatorSet animatorSetFront;
    private AnimatorSet animatorSetBack;

    private final int defaultCardColor;

    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public CardAnimator(final Context context, final View cardFrontLayout, final View cardBackLayout) {
        this.cardBackLayout = cardBackLayout;
        this.cardFrontLayout = cardFrontLayout;
        this.context = new WeakReference<>(context);
        defaultCardColor = ContextCompat.getColor(context, R.color.card_drawer_card_default_color);
        switchViewWithoutAnimation(FieldPosition.POSITION_FRONT);
    }

    /**
     * If not showing the specified side, flip the card with an animation
     */
    public void switchView(@FieldPosition final int fieldPosition) {
        if (fieldPosition != showingLayout) {
            switch (fieldPosition) {
                case FieldPosition.POSITION_FRONT:
                    showFront();
                    break;
                case FieldPosition.POSITION_BACK:
                    showBack();
                    break;
                default:
                    break;
            }

            showingLayout = fieldPosition;
        }
    }

    /**
     * Change the view to the specified side, without animating
     *
     * @param fieldPosition position to change to
     */
    public void switchViewWithoutAnimation(@FieldPosition final int fieldPosition) {
        showingLayout = fieldPosition;

        switch (fieldPosition) {
            case FieldPosition.POSITION_FRONT:
                cardFrontLayout.setAlpha(1.0f);
                cardBackLayout.setAlpha(0f);
                break;
            case FieldPosition.POSITION_BACK:
                cardFrontLayout.setAlpha(0f);
                cardBackLayout.setAlpha(1.0f);
                break;
            default:
                break;
        }

    }

    @VisibleForTesting
    protected void showFront() {
        if (showingLayout == FieldPosition.POSITION_BACK) {
            flipCardFront();
        }
    }

    @VisibleForTesting
    protected void showBack() {
        if (showingLayout == FieldPosition.POSITION_FRONT) {
            flipCardBack();
        }
    }

    /**
     * Animate card to show the back. The card will flip
     */
    @VisibleForTesting
    protected void flipCardBack() {
        resetLayouts();

        animatorSetBack = (AnimatorSet) AnimatorInflater.loadAnimator(context.get(),
                R.animator.card_drawer_card_flip_right_in);
        animatorSetFront = (AnimatorSet) AnimatorInflater.loadAnimator(context.get(),
                R.animator.card_drawer_card_flip_left_out);

        animatorSetBack.setTarget(cardBackLayout);
        animatorSetFront.setTarget(cardFrontLayout);

        animatorSetBack.start();
        animatorSetFront.start();
    }

    /**
     * Animate card to show the front. The card will flip
     */
    public void flipCardFront() {
        resetLayouts();

        animatorSetFront = (AnimatorSet) AnimatorInflater.loadAnimator(context.get(),
                R.animator.card_drawer_card_flip_left_in);
        animatorSetBack = (AnimatorSet) AnimatorInflater.loadAnimator(context.get(),
                R.animator.card_drawer_card_flip_right_out);

        animatorSetFront.setTarget(cardFrontLayout);
        animatorSetBack.setTarget(cardBackLayout);

        animatorSetFront.start();
        animatorSetBack.start();
    }

    /**
     * Called when the activity is about to be destroyed
     *
     * @param state bundle where to save current state
     */
    public void saveState(final Bundle state) {
        state.putInt("showing_side", showingLayout);
        state.putInt("showing_color", showingColor);
    }

    /**
     * Called when activity is being restored.
     *
     * @param savedState bundle where the state was saved. Null if no previous state
     */
    public void restoreState(@Nullable final Bundle savedState) {
        if (savedState == null) {
            doColorCard(defaultCardColor, CardAnimationType.NONE);
        } else {
            @FieldPosition final int position = savedState.getInt("showing_side");
            showingLayout = position;
            switchViewWithoutAnimation(showingLayout);

            showingColor = savedState.getInt("showing_color");
            doColorCard(showingColor, CardAnimationType.NONE);
        }
    }

    private void resetLayouts() {
        cardBackLayout.clearAnimation();
        cardFrontLayout.clearAnimation();
        resetAnimators();
    }

    private void resetAnimators() {
        if (animatorSetBack != null) {
            animatorSetBack.cancel();
            cardBackLayout.setRotationY(0);
        }

        if (animatorSetFront != null) {
            animatorSetFront.cancel();
            cardFrontLayout.setRotationY(0);
        }
    }

    /**
     * Change the color of the card
     *
     * @param color   color to change to
     */
    public void colorCard(@ColorInt final int color, @NonNull @CardAnimationType final String animationType) {
        if (color != showingColor) {
            doColorCard(color, animationType);
        }
    }

    @VisibleForTesting
    protected void doColorCard(@ColorInt final int color, @CardAnimationType final String animationType) {
        showingColor = color;

        final ImageView frontBackground = cardFrontLayout.findViewById(R.id.cho_card_image_front);
        final ImageView frontReveal = cardFrontLayout.findViewById(R.id.cho_card_image_front_reveal);
        final ImageView backBackground = cardBackLayout.findViewById(R.id.cho_card_image_back);
        final ImageView backReveal = cardBackLayout.findViewById(R.id.cho_card_image_back_reveal);

        if (animationType.equals(CardAnimationType.NONE)) {
            setColorFrontFilters(color, frontBackground, frontReveal);
            setColorBackFilters(color, backBackground, backReveal);
        } else {
            animateColorChangeForPosition(frontBackground, frontReveal, backBackground, backReveal, color, animationType);
        }
    }

    @VisibleForTesting
    protected void setColorFrontFilters(@ColorInt final int color, final ImageView frontBackground,
                                        final ImageView frontReveal) {
        frontReveal.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        frontBackground.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    @VisibleForTesting
    protected void setColorBackFilters(@ColorInt final int color, final ImageView backBackground,
                                       final ImageView backReveal) {
        backReveal.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        backBackground.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    @VisibleForTesting
    protected void animateColorChangeForPosition(final ImageView frontBackground, final ImageView frontReveal,
                                                 final ImageView backBackground, final ImageView backReveal, @ColorInt final int color,
                                                 @CardAnimationType final String animationType) {
        if (showingLayout == FieldPosition.POSITION_FRONT) {
            animateColorChange(frontReveal, frontBackground, color, animationType);
            setColorBackFilters(color, backBackground, backReveal);
        } else {
            animateColorChange(backReveal, backBackground, color, animationType);
            setColorFrontFilters(color, frontBackground, frontReveal);
        }
    }

    /**
     * Uses 2 images to create a color transition effect.
     * For Lollipop and up, it's a circular reveal; for KitKat and before it's an alpha transition
     *
     * @param imageReveal     the top image that will create the reveal effect
     * @param imageBackground the background image that will hold the original color
     * @param color           color of the new image
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @VisibleForTesting
    protected void animateColorChange(final ImageView imageReveal, final ImageView imageBackground,
                                      @ColorInt final int color, @NonNull @CardAnimationType final String animationType) {
        final Context ctx = context.get();
        if (ctx != null) {

            // now animate the blend
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (animationType.equals(CardAnimationType.RIGHT_BOTTOM)) {
                    animateUnveal(imageReveal, imageBackground, color);
                } else {
                    animateReveal(imageReveal, imageBackground, color);
                }
            } else {
                // first tint the top image
                imageReveal.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);

                final AlphaAnimation anim = new AlphaAnimation(0f, 1f);
                anim.setFillBefore(true);
                anim.setFillAfter(true);
                anim.setDuration(ctx.getResources().getInteger(R.integer.card_drawer_paint_animation_time));
                anim.setAnimationListener(new EndAnimationListener(imageBackground, color));
                imageReveal.startAnimation(anim);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateReveal(final ImageView imageReveal, final ImageView imageBackground, @ColorInt final int color) {
        // Wait for view to be ready. This is needed because of some illegal state exceptions in prod.
        imageReveal.post(new Runnable() {
            @Override
            public void run() {
                // first tint the top image
                imageReveal.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                final int width = imageReveal.getWidth();

                // we multiply by an extra value as a hack because otherwise the end listener
                // gets fired way too soon and we see glitches
                final Animator anim = ViewAnimationUtils.createCircularReveal(
                        imageReveal, 0, 0, 0, ANIMATION_EXTRA_FACTOR * width);
                anim.setDuration(imageReveal.getResources().getInteger(R.integer.card_drawer_paint_animation_time));
                anim.setInterpolator(new FastOutSlowInInterpolator());
                anim.addListener(new EndAnimationListener(imageBackground, color));
                anim.start();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateUnveal(final ImageView imageReveal, final ImageView imageBackground, @ColorInt final int color) {
        // Wait for view to be ready. This is needed because of some illegal state exceptions in prod.
        imageReveal.post(new Runnable() {
            @Override
            public void run() {
                // first tint the background image
                imageBackground.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                final int width = imageReveal.getWidth();
                // we multiply by an extra value as a hack because otherwise the end
                // listener gets fired way too soon and we see glitches
                final Animator anim = ViewAnimationUtils.createCircularReveal(
                        imageReveal, 0, 0, ANIMATION_EXTRA_FACTOR * width, 0);
                anim.setDuration(imageReveal.getResources().getInteger(R.integer.card_drawer_paint_animation_time));
                anim.setInterpolator(new FastOutSlowInInterpolator());
                anim.addListener(new EndAnimationListener(imageReveal, color));
                anim.start();
            }
        });

    }

}
