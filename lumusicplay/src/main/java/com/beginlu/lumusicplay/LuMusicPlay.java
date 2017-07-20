package com.beginlu.lumusicplay;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by BeginLu on 2017/7/19.
 */

public class LuMusicPlay extends View {
    private Paint mPaint;
    private int mColor = Color.BLUE;
    private int mBackgroundColor = Color.WHITE;
    private int mPaddingItem = 1;
    private int mPadding = 40;
    private int d = 0;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int mPaddingTop;
    private int mPaddingLeft;
    private ValueAnimator mValueAnimator;

    public LuMusicPlay(Context context) {
        this(context, null);
    }

    public LuMusicPlay(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuMusicPlay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LuMusicPlay);
        mBackgroundColor = a.getColor(R.styleable.LuMusicPlay_background_color, mBackgroundColor);
        mColor = a.getColor(R.styleable.LuMusicPlay_color, mColor);
        mPadding = a.getDimensionPixelSize(R.styleable.LuMusicPlay_padding, mPadding);
        mPaddingItem = a.getDimensionPixelSize(R.styleable.LuMusicPlay_padding_item, mPaddingItem);
        a.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);

        mValueAnimator = ValueAnimator.ofInt(0, 17);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                d = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST)
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY);
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = mWidth > mHeight ? mHeight / 2 : mWidth / 2;

        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);

        mWidth -= mPadding * 2;
        mHeight -= mPadding * 2;
        mRadius = mWidth > mHeight ? mHeight / 2 : mWidth / 2;
        mPaddingTop = mHeight / 2 - mRadius;
        mPaddingLeft = mWidth / 2 - mRadius;

        canvas.translate(mPadding, mPadding);

        mPaint.setColor(Color.BLUE);
        for (int i = 0; i < 8; i++) {
            int h = Math.abs(8 - Math.abs(i - d) + 1);
            int left = mPaddingLeft + mRadius / 4 * i + mPaddingItem;
            int top = mPaddingTop + mRadius - mRadius / 8 * h;
            int right = mPaddingLeft + mRadius / 4 * (i + 1) - mPaddingItem;
            int bottom = mHeight - mPaddingTop - mRadius + mRadius / 8 * h;
            canvas.drawRoundRect(new RectF(left, top, right, bottom), 30, 30, mPaint);
        }
    }

    public void start() {
        post(new Runnable() {
            @Override
            public void run() {
                mValueAnimator.start();
            }
        });
    }

    public void stop() {
        post(new Runnable() {
            @Override
            public void run() {
                mValueAnimator.cancel();
            }
        });
    }
}
