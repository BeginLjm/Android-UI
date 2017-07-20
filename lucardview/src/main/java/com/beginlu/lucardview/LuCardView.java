package com.beginlu.lucardview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by BeginLu on 2017/2/24.
 */

public class LuCardView extends RelativeLayout {
    private final String TAG = "CardView";
    private final RelativeLayout viewContent;
    private final View viewTitle;
    private final View view;
    private final RelativeLayout supportView;
    private final TextView mTvTitle;
    private final TextView mTvSummary;
    private int viewContentHeight;
    private int viewContentWidth;
    private final View mIvArrows;
    private boolean CONTENT_OPEN = true;
    private boolean CONTENT_CLOSE = false;
    private boolean type = CONTENT_CLOSE;
    private String title;
    private String summary;

    public LuCardView(Context context) {
        this(context, null);
    }

    public LuCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //这个函数主要就是完成了一下基本视图的获取和初始化
    public LuCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取基本视图
        this.view = View.inflate(context, R.layout.view_cardview, null);
        supportView = (RelativeLayout) view;
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //添加到View中
        this.addView(view);
        //获取控件
        this.viewTitle = view.findViewById(R.id.view_title);
        this.viewContent = (RelativeLayout) view.findViewById(R.id.view_content);
        this.mIvArrows = view.findViewById(R.id.iv_arrows);
        this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        this.mTvSummary = (TextView) view.findViewById(R.id.tv_summary);
        //设置上面这一条Title的点击事件，主要是用于点击展开。
        viewTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换视图的状态
                boolean t = type == CONTENT_CLOSE ? CONTENT_OPEN : CONTENT_CLOSE;
                setType(t);
            }
        });
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CardView);
        String title = array.getString(R.styleable.CardView_title);
        setTitle(title);
        String summary = array.getString(R.styleable.CardView_summary);
        setSummary(summary);
        this.type = array.getBoolean(R.styleable.CardView_type, false);
        if (type)
            viewContent.setVisibility(VISIBLE);
    }

    /**
     * 设置状态的函数
     *
     * @param type 视图状态，true为展开，false为关闭。
     */
    public void setType(boolean type) {
        this.type = type;
        if (type == CONTENT_CLOSE) {
            animatorCardViewClose(viewContent);
        } else {
            animatorCardViewOpen(viewContent);
        }
    }

    /**
     * 设置视图title的文字内容
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
        mTvTitle.setText(title);
    }

    /**
     * 设置summary的文字内容
     *
     * @param summary summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
        mTvSummary.setText(summary);
    }

    //重写onMeasure方法，在这里将子View全部移到ViewContent中去。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            //获取到所有的子View
            View view = getChildAt(i);
            //判断子View是否为自己的视图框架
            if (view.getId() != R.id.card_view) {
                //不是的话就将子View移动到ViewContent中去。
                removeView(view);
                viewContent.addView(view);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (viewContent.getChildCount() == 1) {
            //重新测量下ViewContent的大小
            final int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            final int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            View view = viewContent.getChildAt(0);
            view.measure(w, h);
            this.viewContentWidth = view.getMeasuredWidth();
            this.viewContentHeight = view.getMeasuredHeight();
        }
    }

    /**
     * 卡片展开动画
     *
     * @param view 传入ViewContent
     */
    private void animatorCardViewOpen(final View view) {
        //设置一个ObjectAnimator动画，将Title上的箭头旋转90°
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvArrows, "rotation", 0, 90);
        objectAnimator.setDuration(500);
        objectAnimator.start();

        //设置一个ValueAnimator
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 50);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取到当前的Value值
                //修改自定义View的高度
                LayoutParams layoutParams = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) (viewTitle.getHeight() + (int) animation.getAnimatedValue() * (viewContentHeight / 50.0) + 0.5));
                //修改自定义View的margin值
                layoutParams.setMargins(
                        0,
                        (int) animation.getAnimatedValue(),
                        0,
                        (int) animation.getAnimatedValue());
                supportView.setLayoutParams(layoutParams);
                Log.d(TAG, "supportViewHeight:" + supportView.getHeight() + "  contentHeight:" + viewContentHeight + "   titleHeight:" + viewTitle.getHeight());
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup.LayoutParams layoutParams = supportView.getLayoutParams();
                layoutParams.width = LayoutParams.MATCH_PARENT;
                layoutParams.height = LayoutParams.WRAP_CONTENT;
                supportView.setLayoutParams(layoutParams);
                Log.d(TAG, "supportViewHeight:" + supportView.getHeight() + "  contentHeight:" + viewContentHeight + "   titleHeight:" + viewTitle.getHeight());
            }
        });
        valueAnimator.start();
        view.setVisibility(VISIBLE);
    }

    /**
     * 卡片关闭动画
     *
     * @param view 传入ViewContent
     */
    private void animatorCardViewClose(final View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvArrows, "rotation", 90, 0);
        objectAnimator.setDuration(500);
        objectAnimator.start();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(50, 0);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) (viewTitle.getHeight() + (int) animation.getAnimatedValue() * (viewContentHeight / 50.0) + 0.5));
                layoutParams.setMargins(0, (int) animation.getAnimatedValue(), 0, (int) animation.getAnimatedValue());
                supportView.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //注意这里，必须要先执行完动画后才能隐藏ViewContent
                view.setVisibility(GONE);
            }
        });
        valueAnimator.start();
    }

}
