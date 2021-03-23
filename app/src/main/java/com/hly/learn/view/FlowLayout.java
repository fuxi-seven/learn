package com.hly.learn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private final int mHorizontalSpace = 20;//每个item横向间距
    private final int mVerticalSpace = 25;//每行竖向间距

    private List<List<View>> mAllLines;//记录所有行，用于layout
    List<Integer> mLineHeights = new ArrayList<>();//记录每一行的行高，用于layout

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initMeasureParams() {
        mAllLines = new ArrayList<>();
        mLineHeights = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        initMeasureParams();

        //度量所有子view
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);//ViewGroup解析的宽度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);//ViewGroup解析的高度

        List<View> lineViews = new ArrayList<>();//用于保持一行的所有view
        int lineWidthUsed = 0;//记录已经使用了的宽度
        int lineHeight = 0;//记录行高

        int parentNeededWidth = 0;//measure过程中，子view要求的父ViewGroup的宽度
        int parentNeededHeight = 0;//measure过程中，子view要求的父ViewGroup的高度

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams childLP = childView.getLayoutParams();

            //先对子view进行measure()，否则获取不到子view宽高
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                    paddingLeft + paddingRight, childLP.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                    paddingTop + paddingBottom, childLP.height);
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            int childMeasureWidth = childView.getMeasuredWidth();//获取子view的宽度
            int childMeasureHeight = childView.getMeasuredHeight();//获取子view的高度

            //超过父view的宽度后，需要进行换行
            if (childMeasureWidth + lineWidthUsed > selfWidth) {
                //记录行及高度
                mAllLines.add(lineViews);
                mLineHeights.add(lineHeight);

                //记录当前最大行宽度和总高度(高度会进行相加，宽度会取最大值)
                parentNeededHeight += lineHeight + mVerticalSpace;
                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpace);

                //重置lineViews,记录下一行，重置已使用的宽度和高度
                lineViews = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;
            }

            //记录子view及已使用的宽度和最大行高
            lineViews.add(childView);
            lineWidthUsed += childMeasureWidth + mHorizontalSpace;
            lineHeight = Math.max(lineHeight, childMeasureHeight);

            //如果是最后一个子view，也需要记录处理[跟判断超过宽度一样]
            if (i == childCount - 1) {
                mAllLines.add(lineViews);
                mLineHeights.add(lineHeight);

                parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpace);
                parentNeededHeight += lineHeight + mVerticalSpace;
            }
        }

        //获取mode值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //根据mode值来判断是否是确定的宽高，如果是确定的[layout1]，直接用前面计算得到的self值，否则[layout2]使用计算得到的parentNeeded值
        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;

        //计算完毕进行设置父ViewGroup的宽高
        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curL = getPaddingLeft();
        int curT = getPaddingTop();
        int lineCount = mAllLines.size();//获取需要layout的所有行
        for (int i = 0 ; i < lineCount; i++) {
            List<View> lineViews = mAllLines.get(i);
            int lineHeight = mLineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);
                int left = curL;
                int top = curT;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                curL = right + mHorizontalSpace;//更新curL的值
            }
            curL = getPaddingLeft();
            curT += lineHeight + mVerticalSpace;//更新curT的值
        }
    }
}
