package com.beginlu.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lujunming on 2018/1/29.
 */

public class PKProgressBar extends View {

    private static final int DEFAULT_PROGRESS_ANIMATOR_TIME = 300;
    private static final int DEFAULT_TEXT_ANIMATOR_TIME = 300;
    private static final int DEFAULT_LEFT_COLOR = Color.RED;
    private static final int DEFAULT_RIGHT_COLOR = Color.BLUE;
    private static final int DEFAULT_LEFT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_RIGHT_TEXT_COLOR = Color.WHITE;
    private static final float DEFAULT_LEFT_TEXT_SIZE = 18;
    private static final float DEFAULT_RIGHT_TEXT_SIZE = 18;
    private static final float DEFAULT_TEXT_ANIMATOR_SIZE = 25;

    private int mLeftColor;
    private int mRightColor;
    private float mAnimatorTextSize;
    private float mProgress;
    private final Paint mPaint;
    private int mLeftTextColor;
    private int mRightTextColor;
    private float mLeftTextSize;
    private float mRightTextSize;
    private int mLeftValue;
    private int mRightValue;
    private float mLeftTextPaddingLeft;
    private float mRightTextPaddingRight;

    private ValueAnimator mProgressAnimator;
    private ValueAnimator mLeftTextAnimator;
    private ValueAnimator mRightTextAnimator;

    public PKProgressBar(Context context) {
        this(context, null);
    }

    public PKProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.seekBarStyle);
    }

    public PKProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.PKProgressBar);
        if (attributes != null) {
            mLeftColor = attributes.getColor(R.styleable.PKProgressBar_left_color, DEFAULT_LEFT_COLOR);
            mRightColor = attributes.getColor(R.styleable.PKProgressBar_right_color, DEFAULT_RIGHT_COLOR);
            mLeftTextColor = attributes.getColor(R.styleable.PKProgressBar_left_text_color, DEFAULT_LEFT_TEXT_COLOR);
            mRightTextColor = attributes.getColor(R.styleable.PKProgressBar_right_text_color, DEFAULT_RIGHT_TEXT_COLOR);
            mLeftTextSize = attributes.getDimension(R.styleable.PKProgressBar_left_text_size, DEFAULT_LEFT_TEXT_SIZE);
            mRightTextSize = attributes.getDimension(R.styleable.PKProgressBar_right_text_size, DEFAULT_RIGHT_TEXT_SIZE);
            mAnimatorTextSize = attributes.getDimension(R.styleable.PKProgressBar_text_animator_size, DEFAULT_TEXT_ANIMATOR_SIZE);
            mLeftTextPaddingLeft = attributes.getDimension(R.styleable.PKProgressBar_left_text_padding_left, 0);
            mRightTextPaddingRight = attributes.getDimension(R.styleable.PKProgressBar_right_text_padding_right, 0);
            mLeftValue = attributes.getInteger(R.styleable.PKProgressBar_left_value, 0);
            mRightValue = attributes.getInteger(R.styleable.PKProgressBar_right_value, 0);
            attributes.recycle();
        }
        if ((mRightValue + mLeftValue) == 0) {
            mProgress = 0.5f;
        } else {
            mProgress = (float) mLeftValue / (float) (mLeftValue + mRightValue);
        }

        mPaint = new Paint();
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    private Path getClipPath() {
        float paddingLeft = getLeftPaddingOffset();
        float paddingTop = getTopPaddingOffset();
        float paddingRight = getRightPaddingOffset();
        float paddingBottom = getBottomPaddingOffset();
        float width = getWidth() - paddingLeft - paddingRight;
        float height = getHeight() - paddingTop - paddingBottom;
        Path path = new Path();
        path.moveTo(height / 2 + paddingLeft, paddingTop);
        path.lineTo(width + paddingLeft - (height / 2), paddingTop);
        path.arcTo(new RectF(width + paddingLeft - height, paddingTop, width + paddingLeft, height + paddingTop), -90, 180);
        path.lineTo(height / 2 + paddingLeft, height + paddingTop);
        path.arcTo(new RectF(paddingLeft, paddingTop, height + paddingLeft, height + paddingTop), 90, 180);
        path.close();
        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipPath(getClipPath());
        mPaint.setColor(mLeftColor);
        canvas.drawRect(new RectF(0, 0, mProgress * getWidth(), getHeight()), mPaint);
        mPaint.setColor(mRightColor);
        canvas.drawRect(new RectF(mProgress * getWidth(), 0, getWidth(), getHeight()), mPaint);

        mPaint.setTextSize(mLeftTextSize);
        mPaint.setColor(mLeftTextColor);
        int textY = (int) (getHeight() / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
        String leftText = "我方 " + mLeftValue;
        canvas.drawText(leftText, mLeftTextPaddingLeft, textY, mPaint);
        mPaint.setTextSize(mRightTextSize);
        mPaint.setColor(mRightTextColor);
        textY = (int) (getHeight() / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
        String rightText = mRightValue + " 对方";
        canvas.drawText(rightText,
                getWidth() - mRightTextPaddingRight - mPaint.measureText(rightText),
                textY, mPaint);
    }

    public void setLeftValue(final int leftValue) {
        mLeftValue = leftValue;
        if ((mRightValue + mLeftValue) == 0) {
            setProgress(0.5f);
        } else {
            setProgress((float) mLeftValue / (mLeftValue + mRightValue));
        }
        if (mLeftTextAnimator != null && mLeftTextAnimator.isRunning()) {
            return;
        }
        mLeftTextAnimator = ValueAnimator.ofFloat(mLeftTextSize, mAnimatorTextSize);
        mLeftTextAnimator.setDuration(DEFAULT_TEXT_ANIMATOR_TIME);
        mLeftTextAnimator.setRepeatCount(1);
        mLeftTextAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mLeftTextAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLeftTextSize = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mLeftTextAnimator.start();
    }

    public void setRightValue(final int rightValue) {
        mRightValue = rightValue;
        if ((mRightValue + mLeftValue) == 0) {
            setProgress(0.5f);
        } else {
            setProgress((float) mLeftValue / (mLeftValue + mRightValue));
        }
        if (mRightTextAnimator != null && mRightTextAnimator.isRunning()) {
            return;
        }
        mRightTextAnimator = ValueAnimator.ofFloat(mRightTextSize, mAnimatorTextSize);
        mRightTextAnimator.setDuration(DEFAULT_TEXT_ANIMATOR_TIME);
        mRightTextAnimator.setRepeatCount(1);
        mRightTextAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mRightTextAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRightTextSize = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mRightTextAnimator.start();
    }

    private void setProgress(float progress) {
        float oldProgress = mProgress;
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) {
            mProgressAnimator.clone();
        }
        mProgressAnimator = ValueAnimator.ofFloat(oldProgress, progress);
        mProgressAnimator.setDuration(DEFAULT_PROGRESS_ANIMATOR_TIME);
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mProgressAnimator.start();
    }

    public int getLeftValue() {
        return mLeftValue;
    }

    public int getRightValue() {
        return mRightValue;
    }

    public void reset(){
        mLeftValue = 0;
        mRightValue = 0;
        mProgress = 0.5f;
        postInvalidate();
    }
}
