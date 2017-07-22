package com.beginlu.qqlistitem;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import java.util.LinkedList;

/**
 * Created by lujm on 17/7/17.
 */

public class QQListItem extends RelativeLayout {

    private LinkedList<QQListItemButton> mButtons;
    private OnClickListener onClickListener;

    private float mFirstEventX = 0;
    private float mFirstEventY = 0;
    private float mSize = 0;
    private boolean mOtherClick = false;
    private boolean isMove = false;
    private int mWidth = 0;
    private boolean isClick = false;

    public QQListItem(Context context) {
        this(context, null);
    }

    public QQListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mButtons = new LinkedList<>();
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
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        int centerY = getHeight() / 2;
        int centerX = 0;
        mWidth = 0;

        for (QQListItemButton mButton : mButtons) {
            if (!mButton.isHint()) {
                paint.setTextSize(mButton.getTextSize());
                int width = (int) (paint.measureText(mButton.getText()) + mButton.getPadding());
                mButton.rectF = new RectF(getWidth() + mWidth, 0, getWidth() + mWidth + width, getHeight());
                paint.setColor(mButton.getBackgroundColor());
                canvas.drawRect(mButton.rectF, paint);
                centerX = getWidth() + mWidth + width / 2;
                paint.setColor(mButton.getTextColor());
                canvas.drawText(mButton.getText(), centerX - paint.measureText(mButton.getText()) / 2, centerY - (paint.ascent() + paint.descent()) / 2, paint);
                mWidth += width;
            }
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstEventX = event.getX();
                mFirstEventY = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                if (mSize > 0)
                    return true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mFirstEventX - event.getX()) > Math.abs(event.getY() - mFirstEventY)) {
                    if (event.getX() < mFirstEventX && mSize != mWidth) {
                        isMove = true;
                    } else if (event.getX() > mFirstEventX && mSize != 0) {
                        isMove = true;
                    } else {
                        isMove = false;
                    }
                }
                getParent().requestDisallowInterceptTouchEvent(isMove);
                if (!isMove)
                    if (mSize > (mWidth) / 2)
                        moveToEnd(true);
                    else
                        moveToEnd(false);
                return isMove;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mSize > 0) {
                    for (QQListItemButton mButton : mButtons) {
                        if (mButton.rectF.contains(mFirstEventX + mSize, mFirstEventY)) {
                            mButton.isClick = true;
                            break;
                        }
                        mOtherClick = true;
                    }
                } else {
                    isClick = true;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(mFirstEventX - event.getX()) > Math.abs(event.getY() - mFirstEventY)) {
                    if (event.getX() < mFirstEventX && mSize != mWidth) {
                        isMove = true;
                        mSize = mFirstEventX - event.getX();
                        if (mSize > mWidth)
                            mSize = mWidth;
                    } else if (event.getX() > mFirstEventX && mSize != 0) {
                        isMove = true;
                        mSize = mWidth - (event.getX() - mFirstEventX);
                        if (mSize < 0)
                            mSize = 0;
                    } else {
                        isMove = false;
                    }
                    for (QQListItemButton mButton : mButtons) {
                        mButton.isClick = false;
                    }
                    mOtherClick = false;
                    isClick = false;
                    postInvalidate();
                    return isMove;
                }
                return false;
            case MotionEvent.ACTION_UP:
                isMove = false;
                for (int i = 0; i < mButtons.size(); i++) {
                    QQListItemButton button = mButtons.get(i);
                    if (button.isClick) {
                        button.isClick = false;
                        onClick(i);
                        return true;
                    }
                }
                if (mOtherClick)
                    moveToEnd(false);
                else if (mSize > mWidth / 2)
                    moveToEnd(true);
                else
                    moveToEnd(false);
                if (isClick && onClickListener != null)
                    onClickListener.onClick();
                mOtherClick = false;
                isClick = false;
                break;
        }
        return true;
    }

    private void onClick(int position) {
        if (onClickListener != null)
            onClickListener.onButtonClick(position, mButtons.get(position));
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
            valueAnimator = ValueAnimator.ofFloat(mSize, mWidth);
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
        void onButtonClick(int position, QQListItemButton button);

        void onClick();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setButtons(LinkedList<QQListItemButton> mButtons) {
        this.mButtons = mButtons;
    }

    public LinkedList<QQListItemButton> getButtons() {
        return mButtons;
    }
}
