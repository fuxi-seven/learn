package com.hly.learn.screenAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SelfDeterminedLayout extends RelativeLayout {
    public SelfDeterminedLayout(Context context) {
        super(context);
    }

    public SelfDeterminedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfDeterminedLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SelfDeterminedLayout(Context context, AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float scaleX = ScreenAdapterUtils.getInstance(getContext()).getHorValue();
        float scaleY = ScreenAdapterUtils.getInstance(getContext()).getVerValue();
        int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            layoutParams.width = (int) (layoutParams.width * scaleX);
            layoutParams.height = (int) (layoutParams.height * scaleX);
            layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleX);
            layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
            layoutParams.topMargin = (int) (layoutParams.topMargin * scaleX);
            layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleX);
        }
    }
}
