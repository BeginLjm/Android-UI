package com.beginlu.lutabtitle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

/**
 * Created by lujm on 17/7/19.
 */

public class LuTabTitle extends View implements ViewPager.OnPageChangeListener {
    private final Context mContext;
    private PagerAdapter mAdapter;
    private ViewPager mViewPager;

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mPaddingItem = 10;
    private RectF mRedRectF = new RectF(0, 0, 0, 0);
    private LinkedList<LuTabTitleItem> mTitleList;
    private float mTranslateSize = 0;
    private int mClickItem = 0;
    private RectF mAddRectF;
    private OnAddClickListener mOnAddClickListener;

    private float mOldX;
    private float mFirstX;
    private int mSpan = 1;
    private float mOldPositionOffset = -1;

    public LuTabTitle(Context context) {
        this(context, null);
    }

    public LuTabTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuTabTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
        setWillNotDraw(false);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);
        mPaint.setTextSize(50);

        mTitleList = new LinkedList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST)
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(800, MeasureSpec.EXACTLY);
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST)
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(80, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mTranslateSize, 0);
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();

        int textX;
        mPaint.setTextSize(50);
        int textY = (int) (getHeight() / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
        int sizeWidth = 0;
        for (LuTabTitleItem luTabTitleItem : mTitleList) {
            textX = sizeWidth + mPaddingItem;
            canvas.drawText(luTabTitleItem.getText(), textX, textY, mPaint);
            sizeWidth += (int) (2 * mPaddingItem + mPaint.measureText(luTabTitleItem.getText()));
            luTabTitleItem.setRectF(new RectF(textX - mPaddingItem, 0, sizeWidth, mHeight));
        }

        canvas.clipRect(mRedRectF);
        sizeWidth = 0;
        mPaint.setColor(Color.RED);
        for (LuTabTitleItem luTabTitleItem : mTitleList) {
            textX = sizeWidth + mPaddingItem;
            canvas.drawText(luTabTitleItem.getText(), textX, textY, mPaint);
            sizeWidth += (int) (2 * mPaddingItem + mPaint.measureText(luTabTitleItem.getText()));
        }

        canvas.translate(-mTranslateSize, 0);

        canvas.clipRect(0, 0, mWidth, mHeight, Region.Op.UNION);
        mPaint.setColor(0x33000000);
        mAddRectF = new RectF(mWidth - mHeight, 0, mWidth, mHeight);
        canvas.drawRect(mAddRectF, mPaint);

        mPaint.setTextSize(80);
        mPaint.setColor(Color.BLACK);
        String text = "+";
        textX = (int) (getWidth() - getHeight() + getHeight() / 2 - mPaint.measureText(text) / 2);
        textY = (int) (getHeight() / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
        canvas.drawText(text, textX, textY, mPaint);
        mPaint.setTextSize(50);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        this.mAdapter = viewPager.getAdapter();
        for (int i = 0; i < mAdapter.getCount(); i++)
            mTitleList.add(new LuTabTitleItem(mAdapter.getPageTitle(i).toString()));
        mViewPager.addOnPageChangeListener(this);
        mClickItem = mViewPager.getCurrentItem();
        click();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOldX = event.getX();
                mFirstX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslateSize += (event.getX() - mOldX);
                if (mTranslateSize > 0)
                    mTranslateSize = 0;
                postInvalidate();
                mOldX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getX() - mFirstX) < 10) {
                    if (mAddRectF.contains(event.getX(), event.getY())) {
                        if (mOnAddClickListener != null)
                            mOnAddClickListener.onAddClick();
                        return true;
                    }
                    for (int i = 0; i < mTitleList.size(); i++) {
                        LuTabTitleItem luTabTitleItem = mTitleList.get(i);
                        if (luTabTitleItem.getRectF().contains(event.getX() - mTranslateSize, event.getY())) {
                            mSpan = Math.abs(i - mClickItem);
                            if (mViewPager != null)
                                mViewPager.setCurrentItem(i);
                            break;
                        }
                    }
                }
                break;
        }
        return true;
    }

    private void click() {
        float left = 0;
        for (int i = 0; i < mClickItem; i++)
            left += mPaint.measureText(mTitleList.get(i).getText());
        left += 2 * mClickItem * mPaddingItem;
        float right = left + mPaint.measureText(mTitleList.get(mClickItem).getText()) + mPaddingItem;
        mRedRectF = new RectF(left, 0, right, getHeight());
        postInvalidate();
    }

    private void move() {
        if (mRedRectF.left + mTranslateSize < 0) {
            mTranslateSize = -mRedRectF.left;
        } else if (mRedRectF.right + mTranslateSize - (mWidth - mHeight) > 0) {
            mTranslateSize = -(mRedRectF.right - (mWidth - mHeight));
        }
        if (mTranslateSize > 0)
            mTranslateSize = 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset == 0) {
            mOldPositionOffset = -1;
            mClickItem = mViewPager.getCurrentItem();
            mSpan = 1;
            click();
            return;
        }
        if (mOldPositionOffset == -1) {
            mOldPositionOffset = positionOffset;
            return;
        }
        float offSet = mSpan * (mPaint.measureText(mTitleList.get(mClickItem).getText()) + mPaddingItem * 2) * (positionOffset - mOldPositionOffset);
        mRedRectF.left += offSet;
        mRedRectF.right += offSet;
        mOldPositionOffset = positionOffset;
        move();
        postInvalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        this.mOnAddClickListener = onAddClickListener;
    }

    public interface OnAddClickListener {
        void onAddClick();
    }
}
