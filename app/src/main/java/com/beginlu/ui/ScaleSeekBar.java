package com.beginlu.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

/**
 * Created by lujunming on 2017/11/23.
 */

public class ScaleSeekBar extends AppCompatSeekBar {
    private float mTextMarginBottom;
    private int mTextColor = Color.WHITE;
    private float mTextSize = 30;
    private final Paint mTextPaint;

    public ScaleSeekBar(Context context) {
        this(context, null);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.seekBarStyle);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ScaleSeekBar);
        if (attributes != null) {
            mTextColor = attributes.getColor(R.styleable.ScaleSeekBar_textColor, Color.WHITE);
            mTextSize = attributes.getDimension(R.styleable.ScaleSeekBar_textSize, 30);
            mTextMarginBottom = attributes.getDimension(R.styleable.ScaleSeekBar_textMarginBottom, 10);
            attributes.recycle();
        }

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        if (heightMode == MeasureSpec.AT_MOST) {
            height = getMeasuredHeight() + (int) (((mTextPaint.descent() - mTextPaint.ascent()) + mTextMarginBottom) * 2);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        int bottom = this.getBottom();
        int top = this.getTop();
        this.setBottom((int) (bottom - (mTextPaint.descent() - mTextPaint.ascent() + getPaddingTop() + mTextMarginBottom)));
        this.setTop((int) (top + (mTextPaint.descent() - mTextPaint.ascent() + getPaddingTop() + mTextMarginBottom)));
        canvas.save();
        canvas.translate(0, mTextPaint.descent() - mTextPaint.ascent() + getPaddingTop() + mTextMarginBottom);
        super.onDraw(canvas);
        canvas.restore();
        this.setBottom(bottom);
        this.setTop(top);

        int progress = getProgress();
        String progressStr = String.valueOf(progress);
        canvas.drawText(progressStr,
                (getWidth() - getPaddingLeft() - getPaddingRight()) * progress / 100 + getPaddingLeft() - mTextPaint.measureText(progressStr) / 2,
                mTextPaint.descent() - mTextPaint.ascent() + getPaddingTop(),
                mTextPaint);
    }
}
