package com.beginlu.luloader;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by beginlu on 2017/5/25.
 */

public class LuLoader extends View {

    private int mWidth;
    private int mHeight;
    private String mText = "陆";
    private int mTextSize = 180;
    private int mColor = Color.BLUE;
    private float mProgress = 0.5f;
    private float mDeviation = 1;

    public LuLoader(Context context) {
        this(context, null);
    }

    public LuLoader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuLoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LuLoader);

        mTextSize = a.getDimensionPixelSize(R.styleable.LuLoader_text_size, mTextSize);
        mColor = a.getColor(R.styleable.LuLoader_color, mColor);
        String text = a.getString(R.styleable.LuLoader_text);
        if (text != null)
            mText = text;

        a.recycle();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-1, 0);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDeviation = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(380, MeasureSpec.EXACTLY);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(380, MeasureSpec.EXACTLY);
        }
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
        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(mTextSize);
        paint.setDither(true);
        int textX = (int) (mWidth / 2 - paint.measureText(mText) / 2);
        int textY = (int) (mHeight / 2 - (paint.ascent() + paint.descent()) / 2);
        canvas.drawText(mText, textX, textY, paint);

        Path circlePath = new Path();
        circlePath.addCircle(mWidth / 2, mHeight / 2, mHeight / 2, Path.Direction.CW);
        circlePath.close();
        canvas.clipPath(circlePath);

        Path path = getWavePath();

        canvas.translate(mWidth * mDeviation, 0);
        canvas.drawPath(path, paint);

        canvas.clipPath(path);
        paint.setColor(Color.WHITE);
        canvas.drawText(mText, textX - mWidth * mDeviation, textY, paint);
    }

    private Path getWavePath() {
        Path path = new Path();
        path.moveTo(0, mHeight * (1 - mProgress));
        path.quadTo(mWidth / 4 * 1, mHeight * (1 - mProgress) - mHeight / 6, mWidth / 2 * 1, mHeight * (1 - mProgress));
        path.quadTo(mWidth / 4 * 3, mHeight * (1 - mProgress) + mHeight / 6, mWidth / 2 * 2, mHeight * (1 - mProgress));
        path.quadTo(mWidth / 4 * 5, mHeight * (1 - mProgress) - mHeight / 6, mWidth / 2 * 3, mHeight * (1 - mProgress));
        path.quadTo(mWidth / 4 * 7, mHeight * (1 - mProgress) + mHeight / 6, mWidth / 2 * 4, mHeight * (1 - mProgress));
        path.lineTo(mWidth * 2, mHeight);
        path.lineTo(0, mHeight);
        path.close();
        return path;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if (progress > 1 || progress < 0)
            return;
        this.mProgress = progress;
        postInvalidate();
    }
}
