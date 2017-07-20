package com.beginlu.lumusicplay;

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

public class LuMusicNext extends View {
    private int mPadding = 10;
    private Paint mPaint;
    private float mLength;
    private float mPaddingLeft;
    private float mPaddingTop;

    public LuMusicNext(Context context) {
        this(context, null);
    }

    public LuMusicNext(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuMusicNext(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

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
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.WHITE);
        Path nextPath = getNextPath();
        canvas.drawPath(nextPath, mPaint);
    }

    private Path getNextPath() {
        Path path = new Path();
        path.moveTo(mPaddingLeft, mPaddingTop);
        path.lineTo(mPaddingLeft, mLength + mPaddingTop);
        path.lineTo((float) (Math.sqrt(3) * mLength / 2), mLength / 2 + mPaddingTop);
        path.lineTo(mPaddingLeft, mPaddingTop);
        path.moveTo((float) (Math.sqrt(3) * mLength / 2), mPaddingTop);
        path.lineTo((float) (Math.sqrt(3) * mLength / 2), mLength + mPaddingTop);
        path.lineTo(mLength + mPaddingLeft, mLength + mPaddingTop);
        path.lineTo(mLength + mPaddingLeft, mPaddingTop);
        path.lineTo((float) (Math.sqrt(3) * mLength / 2), mPaddingTop);
        return path;
    }
}
