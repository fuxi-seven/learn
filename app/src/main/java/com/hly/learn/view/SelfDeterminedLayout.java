package com.hly.learn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hly.learn.R;

import java.util.ArrayList;
import java.util.List;

public class SelfDeterminedLayout extends RelativeLayout {

    private Context mContext;
    private List<View> mViews = new ArrayList<>();

    public SelfDeterminedLayout(Context context) {
        super(context);
        mContext = context;
    }

    public SelfDeterminedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SelfDeterminedLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public SelfDeterminedLayout(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    public void addView(int num) {
        removeAllViews();
        ImageView imageView1 = new ImageView(mContext);
        imageView1.setImageResource(R.drawable.ic_chuancai);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(imageView1);
        ImageView imageView2 = new ImageView(mContext);
        imageView2.setImageResource(R.drawable.ic_lucai);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(imageView2);
        if (num == 4) {
            ImageView imageView3 = new ImageView(mContext);
            imageView3.setImageResource(R.drawable.ic_xiangcai);
            imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(imageView3);
            ImageView imageView4 = new ImageView(mContext);
            imageView4.setImageResource(R.drawable.ic_huicai);
            imageView4.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(imageView4);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        mViews.clear();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            mViews.add(view);
        }
        int width;
        if (count == 2) {
            width = 1200;
        } else {
            width = 1000;
        }
        setMeasuredDimension(getMeasuredWidth(), width);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int left;
        int top;
        int width;
        int height;
        int count = mViews.size();
        left = 240;
        top = 50;
        if (count == 2) {
            width = 600;
            height = 480;
        } else {
            width = 400;
            height = 450;
        }
        for (int i = 0; i< count; i++) {
            View view = mViews.get(i);
            if (count == 4) {
                if (i == 0 || i == 2) {
                    left = 90;
                } else {
                    left = 600;
                }
                if (i == 2) {
                    top += height + 50;
                }
            }
            view.layout(left, top, left + width, top + height);
            if (count == 2) {
                top += height + 50;
            }
        }
    }

}
