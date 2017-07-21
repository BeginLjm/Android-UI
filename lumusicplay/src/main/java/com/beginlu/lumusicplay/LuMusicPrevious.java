package com.beginlu.lumusicplay;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by BeginLu on 2017/7/20.
 */

public class LuMusicPrevious extends View {
    private int mPadding = 40;
    private float mOffSet = 0f;
    private Paint mPaint;
    private float mLength;
    private float mPaddingLeft;
    private float mPaddingTop;

    public LuMusicPrevious(Context context) {
        this(context, null);
    }

    public LuMusicPrevious(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuMusicPrevious(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LuMusicNext);
//
//        mPadding = typedArray.getDimensionPixelOffset(R.styleable.LuMusicNext_padding, mPadding);
//
//        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
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
        mLength = getHeight() > getWidth() ? getWidth() : getHeight() - mPadding * 2;
        mPaddingLeft = (getWidth() - mLength) / 2;
        mPaddingTop = (getHeight() - mLength) / 2;
        mPaint.setColor(Color.WHITE);
        Path circlePath = getCirclePath(false);
        canvas.clipPath(circlePath);
        canvas.drawPath(circlePath, mPaint);
        mPaint.setColor(Color.BLUE);
        Path nextPath = getNextPath();
        canvas.drawPath(nextPath, mPaint);

        circlePath = getCirclePath(true);
        canvas.clipPath(circlePath);
        canvas.drawPath(circlePath, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawPath(nextPath, mPaint);

    }

    private Path getCirclePath(boolean offSet) {
        Path path = new Path();
        float x = getWidth() > getHeight() ? getHeight() / 2 : getWidth() / 2;
        if (offSet)
            path.addCircle(3 * x - 2 * x * mOffSet, x, x, Path.Direction.CW);
        else
            path.addCircle(x, x, x, Path.Direction.CW);
        return path;
    }

    private Path getNextPath() {
        Path path = new Path();
        path.moveTo(getWidth() - mPaddingLeft, mPaddingTop);
        path.lineTo(getWidth() - mPaddingLeft, mLength + mPaddingTop);
        path.lineTo(getWidth() - ((float) (Math.sqrt(3) * mLength / 2) + mPaddingLeft), mLength / 2 + mPaddingTop);
        path.lineTo(getWidth() - mPaddingLeft, mPaddingTop);
        path.moveTo(getWidth() - ((float) (Math.sqrt(3) * mLength / 2) + mPaddingLeft), mPaddingTop);
        path.lineTo(getWidth() - ((float) (Math.sqrt(3) * mLength / 2) + mPaddingLeft), mLength + mPaddingTop);
        path.lineTo(getWidth() - (mLength + mPaddingLeft), mLength + mPaddingTop);
        path.lineTo(getWidth() - (mLength + mPaddingLeft), mPaddingTop);
        path.lineTo(getWidth() - ((float) (Math.sqrt(3) * mLength / 2) + mPaddingLeft), mPaddingTop);
        return path;
    }

    public void setPadding(int padding) {
        this.mPadding = padding;
    }

    public void setOffSet(float offSet) {
        if (offSet < 0)
            offSet = 0;
        else if (offSet > 1)
            offSet = 1;
        this.mOffSet = offSet;
        postInvalidate();
    }

    public void stop() {
        ValueAnimator valueAnimator;
        if (mOffSet > 0.5f)
            valueAnimator = ValueAnimator.ofFloat(mOffSet, 1);
        else
            valueAnimator = ValueAnimator.ofFloat(mOffSet, 0);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffSet = (float) animation.getAnimatedValue();
                postInvalidate();
                if (mOffSet == 0 || mOffSet == 1)
                    LuMusicPrevious.this.setVisibility(GONE);
            }
        });
        valueAnimator.start();
        if (mOffSet > 0.5f && mOnNextListener != null)
            mOnNextListener.onNext();
    }

    private OnNextListener mOnNextListener;

    public void setOnNextListener(OnNextListener onNextListener) {
        this.mOnNextListener = onNextListener;
    }

    interface OnNextListener {
        void onNext();
    }
}
