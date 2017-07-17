package com.beginlu.qqlistitem;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by lujm on 17/7/17.
 */

public class QQListItem extends RelativeLayout {

    private int mButton1Color = 0xFFC8C7CE;
    private int mButton2Color = 0xFFFF9C37;
    private int mButton3Color = 0xFFFF3739;
    private int mButton1Width = 200;
    private int mButton2Width = 300;
    private int mButton3Width = 200;
    private String mButton1Text = "置顶";
    private String mButton2Text = "标为未读";
    private String mButton3Text = "删除";
    private int mTextColor = 0xFFFFFFFF;
    private boolean mButton1Hint = false;
    private boolean mButton2Hint = false;
    private boolean mButton3Hint = false;
    private RectF mRectF1;
    private RectF mRectF2;
    private RectF mRectF3;
    private OnClickListener onClickListener;

    private float mFirstEventX = 0;
    private float mFirstEventY = 0;
    private float mSize = 0;
    private boolean mButton1Click = false;
    private boolean mButton2Click = false;
    private boolean mButton3Click = false;
    private boolean mOtherClick = false;

    public QQListItem(Context context) {
        this(context, null);
    }

    public QQListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(0x00000000);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(-mSize, 0);
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        int centerY = getHeight() / 2;
        int centerX = 0;

        //Button1
        if (mButton1Hint) {
            mButton1Width = 0;
        } else {
            mButton1Width = (int) (paint.measureText(mButton1Text) + 100);
            paint.setColor(mButton1Color);
            mRectF1 = new RectF(getWidth(), 0, getWidth() + mButton1Width, getHeight());
            canvas.drawRect(mRectF1, paint);
            centerX = getWidth() + mButton1Width / 2;
            paint.setColor(mTextColor);
            canvas.drawText(mButton1Text, centerX - paint.measureText(mButton1Text) / 2, centerY - (paint.ascent() + paint.descent()) / 2, paint);
        }

        //Button2
        if (mButton2Hint) {
            mButton2Width = 0;
        } else {
            mButton2Width = (int) (paint.measureText(mButton2Text) + 100);
            paint.setColor(mButton2Color);
            mRectF2 = new RectF(getWidth() + mButton1Width, 0, getWidth() + mButton1Width + mButton2Width, getHeight());
            canvas.drawRect(mRectF2, paint);
            centerX = getWidth() + mButton1Width + mButton2Width / 2;
            paint.setColor(mTextColor);
            canvas.drawText(mButton2Text, centerX - paint.measureText(mButton2Text) / 2, centerY - (paint.ascent() + paint.descent()) / 2, paint);
        }


        //Button3
        if (mButton3Hint) {
            mButton3Width = 0;
        } else {
            mButton3Width = (int) (paint.measureText(mButton3Text) + 100);
            paint.setColor(mButton3Color);
            mRectF3 = new RectF(getWidth() + mButton1Width + mButton2Width, 0, getWidth() + mButton1Width + mButton2Width + mButton3Width, getHeight());
            canvas.drawRect(mRectF3, paint);
            centerX = getWidth() + mButton1Width + mButton2Width + mButton3Width / 2;
            paint.setColor(mTextColor);
            canvas.drawText(mButton3Text, centerX - paint.measureText(mButton3Text) / 2, centerY - (paint.ascent() + paint.descent()) / 2, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstEventX = event.getX();
                mFirstEventY = event.getY();
                if (mSize > 0) {
                    if (mRectF1.contains(mFirstEventX + mSize, mFirstEventY))
                        mButton1Click = true;
                    else if (mRectF2.contains(mFirstEventX + mSize, mFirstEventY))
                        mButton2Click = true;
                    else if (mRectF3.contains(mFirstEventX + mSize, mFirstEventY))
                        mButton3Click = true;
                    else
                        mOtherClick = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mFirstEventX - event.getX()) > Math.abs(event.getY() - mFirstEventY)) {
                    if (event.getX() < mFirstEventX) {
                        mSize = mFirstEventX - event.getX();
                        if (mSize > mButton1Width + mButton2Width + mButton3Width)
                            mSize = mButton1Width + mButton2Width + mButton3Width;
                    } else {
                        mSize = (mButton1Width + mButton2Width + mButton3Width) - (event.getX() - mFirstEventX);
                        if (mSize < 0)
                            mSize = 0;
                    }
                    mButton1Click = false;
                    mButton2Click = false;
                    mButton3Click = false;
                    mOtherClick = false;
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mButton1Click) {
                    onClick(0);
                    mButton1Click = false;
                } else if (mButton2Click) {
                    onClick(1);
                    mButton2Click = false;
                } else if (mButton3Click) {
                    onClick(2);
                    mButton3Click = false;
                } else if (mOtherClick) {
                    moveToEnd(false);
                    mOtherClick = false;
                } else if (mSize > (mButton1Width + mButton2Width + mButton3Width) / 2)
                    moveToEnd(true);
                else
                    moveToEnd(false);
                break;
        }
        return true;
    }

    private void onClick(int position) {
        if (onClickListener != null)
            onClickListener.OnClick(position);
        moveToEnd(false);
    }

    /**
     * 使用加速插值器，使动画更为合理。
     *
     * @param b true为移至展开状态，false为移值关闭状态。
     */
    private void moveToEnd(Boolean b) {
        ValueAnimator valueAnimator;
        if (b)
            valueAnimator = ValueAnimator.ofFloat(mSize, mButton1Width + mButton2Width + mButton3Width);
        else
            valueAnimator = ValueAnimator.ofFloat(mSize, 0);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSize = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    public interface OnClickListener {
        void OnClick(int position);
    }
}
