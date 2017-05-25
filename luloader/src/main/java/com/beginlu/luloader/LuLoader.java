package com.beginlu.luloader;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by beginlu on 2017/5/25.
 */

public class LuLoader extends LinearLayout {
    private final ValueAnimator valueAnimator;
    //    private final AnimationSet animationSet1;
//    private final AnimationSet animationSet2;
    private int circleColor = Color.BLUE;
    private int circleSize = 4;
    private ImageView[] circles = new ImageView[circleSize];
    private boolean isBack = false;

    public LuLoader(Context context) {
        this(context, null);
    }

    public LuLoader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        for (int i = 0; i < circleSize; i++) {
            ImageView circle = new ImageView(context);
            LayoutParams layoutParams = new LayoutParams(30, 30);
            layoutParams.setMargins(10, 10, 10, 10);
            circle.setLayoutParams(layoutParams);
            circle.setBackgroundColor(circleColor);
            circles[i] = circle;
        }
        for (ImageView circle : circles) {
            addView(circle);
        }

        valueAnimator = ValueAnimator.ofFloat(-1.5707963267948966f, 1.5707963267948966f);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                for (int i = 0; i < circleSize; i++) {
                    alpha += alpha + i * (Math.PI / circleSize);
                    alpha = (float) Math.sin(alpha);
                    alpha = (float) (alpha / 2 + 0.5);
                    if (alpha > 1)
                        continue;
                    if (alpha < 0)
                        alpha = -alpha;
                    circles[i].setAlpha(alpha);
                }
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isBack = !isBack;
            }
        });

//        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//        alphaAnimation.setDuration(1000);
//        animationSet1 = new AnimationSet(true);
//        animationSet1.addAnimation(alphaAnimation);
//        animationSet1.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                for (ImageView circle : circles)
//                    circle.setAnimation(animationSet2);
//                animationSet2.startNow();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        alphaAnimation = new AlphaAnimation(0, 1);
//        alphaAnimation.setDuration(1000);
//        animationSet2 = new AnimationSet(true);
//        animationSet2.addAnimation(alphaAnimation);
//        animationSet2.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                for (ImageView circle : circles)
//                    circle.setAnimation(animationSet1);
//                animationSet1.startNow();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
//        for (ImageView circle : circles)
//            circle.setAnimation(animationSet1);
//        animationSet1.startNow();
        valueAnimator.start();
    }
}
