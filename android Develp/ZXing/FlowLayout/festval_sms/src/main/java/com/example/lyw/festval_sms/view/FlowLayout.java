package com.example.lyw.festval_sms.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYW on 2016/11/1.
 */

public class FlowLayout extends ViewGroup {

    /**
     * 存储所有的View
     */
    private List<List<View>> mAllViews = new ArrayList<>();

    private List<Integer> mLineHeight = new ArrayList<>();

    private static final String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量值
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //测量模式
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //如果父布局是math_parent或者精确值则sizewidth 就是精确的宽/高
        //如果是wrap_content的情况下的宽度和高度
        int width = 0;
        int height = 0;

        int lineHeight = 0;
        int lineWidth = 0;

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            //子view占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp
                    .rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp
                    .bottomMargin;
            //换行 sizeWidth此时是多少? 为父容器指定的大小
            if (lineWidth + childWidth > sizeWidth) {
                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        Log.d(TAG, "onMeasure: " + sizeWidth);
        Log.d(TAG, "onMeasure: " + sizeHeight);

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth :
                        width,
                modeHeight == MeasureSpec.AT_MOST ? sizeHeight : height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        //当前viewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        //每一行的view
        List<View> mLineViews = new ArrayList<>();
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //这个是自己测量出来的么？
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            //换行
            if (childWidth + lp.rightMargin + lp.leftMargin + lineWidth >
                    width) {
                //记录行高
                mLineHeight.add(lineHeight);
                mAllViews.add(mLineViews);
                //重置
                lineWidth = 0;
                lineHeight = childHeight + lp.bottomMargin + lp.topMargin;
                mLineViews.add(child);
                mLineViews = new ArrayList<View>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.bottomMargin +
                    lp.topMargin);
            mLineViews.add(child);
        }
        //最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(mLineViews);

        //设置子VIEW的位置
        int top = getPaddingTop();
        int left = getPaddingLeft();

        int lineNum = mAllViews.size();
        for (int i = 0; i < lineNum; i++) {

            mLineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < mLineViews.size(); j++) {
                View child = mLineViews.get(j);
                //判断子view的状态
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();
                int lc = left + lp.leftMargin ;
                int tc = top + lp.topMargin;
                int rc = lc +child.getMeasuredWidth();
                int rb = tc +child.getMeasuredHeight();
                //为子view进行布局
                child.layout(lc,tc,rc,rb);
                left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
               left = 0;
               top += lineHeight;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        Log.d(TAG, "generateLayoutParams: ");
        return new MarginLayoutParams(getContext(), attrs);
    }
}
