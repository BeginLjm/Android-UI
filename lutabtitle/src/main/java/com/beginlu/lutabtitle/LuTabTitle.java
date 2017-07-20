package com.beginlu.lutabtitle;

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
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

/**
 * Created by lujm on 17/7/19.
 */

public class LuTabTitle extends View {
    private final Context mContext;
    private PagerAdapter mAdapter;
    private ViewPager mViewPager;

    private String[] texts = {"测试1", "测试2", "测试3", "测试4", "测试5", "测试6", "测试7", "测试8", "测试9", "测试10", "测试11", "测试12"};
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mTextWidth;
    private LinkedList<LuTabTitleItem> mLinkedList;

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
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setDither(true);

        mLinkedList = new LinkedList<>();
        for (String text : texts) {
            LuTabTitleItem luTabTitleItem = new LuTabTitleItem(text);
            mLinkedList.add(luTabTitleItem);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
        canvas.translate(translateSize, 0);
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();

        int textX = 0;
        mPaint.setTextSize(50);
        int textY = (int) (getHeight() / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
        int sizeWidth = 0;
        for (LuTabTitleItem luTabTitleItem : mLinkedList) {
            textX = sizeWidth + 10;
            canvas.drawText(luTabTitleItem.getText(), textX, textY, mPaint);
            sizeWidth += (int) (20 + mPaint.measureText(luTabTitleItem.getText()));
            luTabTitleItem.setRectF(new RectF(textX - 10, 0, sizeWidth, mHeight));
        }
        mTextWidth = 20 + (int) mPaint.measureText(mLinkedList.get(0).getText());

        canvas.clipRect(clickItem * mTextWidth, 0, (clickItem + 1) * mTextWidth, mHeight);
        sizeWidth = 0;
        mPaint.setColor(Color.RED);
        for (LuTabTitleItem luTabTitleItem : mLinkedList) {
            textX = sizeWidth + 10;
            canvas.drawText(luTabTitleItem.getText(), textX, textY, mPaint);
            sizeWidth += (int) (20 + mPaint.measureText(luTabTitleItem.getText()));
        }

        canvas.translate(-translateSize, 0);

        canvas.clipRect(0, 0, mWidth, mHeight, Region.Op.UNION);
        mPaint.setColor(0x33000000);
        canvas.drawRect(getWidth() - getHeight(), 0, getWidth(), getHeight(), mPaint);

        mPaint.setTextSize(80);
        mPaint.setColor(Color.BLACK);
        String text = "+";
        textX = (int) (getWidth() - getHeight() + getHeight() / 2 - mPaint.measureText(text) / 2);
        canvas.drawText(text, textX, textY, mPaint);

    }

    public void setupWithViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        this.mAdapter = viewPager.getAdapter();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    float oldX;
    float firstX;
    float translateSize = 0;
    int clickItem = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = event.getX();
                firstX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                translateSize += (event.getX() - oldX);
                if (translateSize > 0)
                    translateSize = 0;
                postInvalidate();
                oldX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getX() - firstX) < 10) {
                    for (int i = 0; i < mLinkedList.size(); i++) {
                        LuTabTitleItem luTabTitleItem = mLinkedList.get(i);
                        if (luTabTitleItem.getRectF().contains(event.getX() - translateSize, event.getY())) {
                            clickItem = i;
                            postInvalidate();
                            break;
                        }
                    }
                }
                break;
        }
        return true;
    }
}
