package com.beginlu.arcseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by beginlu on 2017/5/24.
 */

public class ArcSeekBar extends View {
    private float centerX;
    private float centerY;
    private int r;
    private float progress = 25;
    private RectF rectF;
    private boolean isTouch = false;

    /*默认的数值 */
    private int seekSize = 6;
    private int barSize = 15;
    private int textSize = 30;
    private int arcColor = 0xff757575;
    private int circleColor = 0xff009688;
    private int textColor = 0xffFFFFFF;
    private int barColor = 0xff03A9F4;
    private int mWidth = 800;
    private int mHeight = 800;
    private OnArcSeekBarChangeListener onArcSeekBarChangeListener;


    public ArcSeekBar(Context context) {
        this(context, null);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBar);

        seekSize = a.getDimensionPixelSize(R.styleable.ArcSeekBar_seek_size, 6);
        barSize = a.getDimensionPixelSize(R.styleable.ArcSeekBar_bar_size, 15);
        textSize = a.getDimensionPixelSize(R.styleable.ArcSeekBar_text_size, 30);
        arcColor = a.getColor(R.styleable.ArcSeekBar_arc_color, 0xff757575);
        circleColor = a.getColor(R.styleable.ArcSeekBar_circle_color, 0xff009688);
        textColor = a.getColor(R.styleable.ArcSeekBar_text_color, 0xffFFFFFF);
        barColor = a.getColor(R.styleable.ArcSeekBar_bar_color, 0xff03A9F4);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.AT_MOST)
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (heightMode != MeasureSpec.AT_MOST)
            mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        r = (getMeasuredWidth() / 2);
        centerX = getLeft() + r;
        centerY = getTop() + r;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        //画圆环
        paint.setColor(arcColor);
        canvas.drawArc(new RectF(getLeft() + barSize - seekSize, getTop() + barSize - seekSize, getRight() - barSize + seekSize, getBottom() - barSize + seekSize), -90, (float) (3.6 * progress), true, paint);
        //画圆
        paint.setColor(circleColor);
        canvas.drawCircle(centerX, centerY, (float) (r - barSize - seekSize), paint);
        TextPaint textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);
        String[] split = (progress + "").split("\\.");
        //画文字
        textPaint.setColor(textColor);
        canvas.drawText(split[0] + "." + split[1].subSequence(0, 1) + "%", centerX - textPaint.measureText(split[0] + "." + split[1].subSequence(0, 1) + "%") / 2, centerY - (textPaint.ascent() + textPaint.descent()) / 2, textPaint);
        float touchX = centerX + (float) Math.sin(Math.toRadians(3.6 * progress)) * (r - barSize);
        float touchY = centerY - (float) Math.cos(Math.toRadians(3.6 * progress)) * (r - barSize);
        //画滑块
        paint.setColor(barColor);
        canvas.drawCircle(touchX, touchY, barSize, paint);
        this.rectF = new RectF(touchX - barSize, touchY - barSize, touchX + barSize, touchY + barSize);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                isTouch = rectF.contains(x, y);
                if (isTouch && onArcSeekBarChangeListener != null)
                    onArcSeekBarChangeListener.onStartTrackingTouch(this);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouch) {
                    float moveX = event.getX();
                    float moveY = event.getY();
                    progress = (float) ((Math.atan((moveX - centerX) / (centerY - moveY)) / Math.PI) * 50);
                    if (moveX > centerX) {
                        if (moveY > centerY) {
                            progress = 50 + progress;
                        }
                    } else {
                        if (moveY > centerY) {
                            progress = 50 + progress;
                        } else {
                            progress = 100 + progress;
                        }
                    }
                    onArcSeekBarChangeListener.onProgressChanged(this, progress, false);
                    this.postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isTouch && onArcSeekBarChangeListener != null)
                    onArcSeekBarChangeListener.onStopTrackingTouch(this);
                isTouch = false;
                break;
        }
        return isTouch;
    }

    public void setProgress(float progress) {
        if (progress < 0 || progress >= 100)
            return;
        this.progress = progress;
        onArcSeekBarChangeListener.onProgressChanged(this, progress, true);
        this.postInvalidate();
    }

    public void setOnArcSeekBarChangeListener(OnArcSeekBarChangeListener onArcSeekBarChangeListener) {
        this.onArcSeekBarChangeListener = onArcSeekBarChangeListener;
    }

    public interface OnArcSeekBarChangeListener {
        public void onProgressChanged(ArcSeekBar arcSeekBar, float progress, boolean fromUser);

        public void onStartTrackingTouch(ArcSeekBar arcSeekBar);

        public void onStopTrackingTouch(ArcSeekBar arcSeekBar);
    }

    public void setSeekSize(int seekSize) {
        this.seekSize = seekSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setArcColor(int arcColor) {
        this.arcColor = arcColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public void setWidth(int width) {
        this.mWidth = width;
    }

    public void setHeight(int height) {
        this.mHeight = height;
    }
}
