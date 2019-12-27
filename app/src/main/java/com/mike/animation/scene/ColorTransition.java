package com.mike.animation.scene;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

public class ColorTransition extends Transition {

    static final String PROPNAME_TRANSITION_COLOR = "android:color:transitioncolor";
    static final int INVALID_TRANSITION_COLOR = -1;

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Drawable backgroundDrawable = view.getBackground();
        int color = INVALID_TRANSITION_COLOR;
        if (backgroundDrawable != null && (backgroundDrawable instanceof ColorDrawable)) {
            color = ((ColorDrawable) backgroundDrawable).getColor();
        }
        transitionValues.values.put(PROPNAME_TRANSITION_COLOR, color);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureStartValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        int startColor = ((Integer) startValues.values.get(PROPNAME_TRANSITION_COLOR)).intValue();
        int endColor = ((Integer) endValues.values.get(PROPNAME_TRANSITION_COLOR)).intValue();
        if (startColor == INVALID_TRANSITION_COLOR || endColor == INVALID_TRANSITION_COLOR) {
            return null;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                endValues.view.setBackgroundColor(getCurrentColor(startColor, endColor, fraction));
            }
        });
        addListener(new TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                endValues.view.setBackgroundColor(endColor);
            }
        });
        return animator;
    }

    private int getCurrentColor(int startColor, int endColor, float fraction) {
        int startInt = (Integer) startColor;
        float startA = ((startInt >> 24) & 0xff) / 255.0f;
        float startR = ((startInt >> 16) & 0xff) / 255.0f;
        float startG = ((startInt >> 8) & 0xff) / 255.0f;
        float startB = (startInt & 0xff) / 255.0f;

        int endInt = (Integer) endColor;
        float endA = ((endInt >> 24) & 0xff) / 255.0f;
        float endR = ((endInt >> 16) & 0xff) / 255.0f;
        float endG = ((endInt >> 8) & 0xff) / 255.0f;
        float endB = (endInt & 0xff) / 255.0f;

        // convert from sRGB to linear
        startR = (float) Math.pow(startR, 2.2);
        startG = (float) Math.pow(startG, 2.2);
        startB = (float) Math.pow(startB, 2.2);

        endR = (float) Math.pow(endR, 2.2);
        endG = (float) Math.pow(endG, 2.2);
        endB = (float) Math.pow(endB, 2.2);

        // compute the interpolated color in linear space
        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        // convert back to sRGB in the [0..255] range
        a = a * 255.0f;
        r = (float) Math.pow(r, 1.0 / 2.2) * 255.0f;
        g = (float) Math.pow(g, 1.0 / 2.2) * 255.0f;
        b = (float) Math.pow(b, 1.0 / 2.2) * 255.0f;

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
