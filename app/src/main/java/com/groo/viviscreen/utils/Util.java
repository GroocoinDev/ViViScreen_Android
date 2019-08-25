package com.groo.viviscreen.utils;

import android.animation.ValueAnimator;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Util {
    public static void animateTextView(float initialValue, float finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(new DecimalFormat("###,###").format(valueAnimator.getAnimatedValue()));
            }
        });
        valueAnimator.start();
    }
}
