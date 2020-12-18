package com.hly.learn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hly.learn.R;

import java.util.ArrayList;
import java.util.List;

public class SelfDeterminedLayout extends RelativeLayout {

    private static final int CONTENT_VIEW_TOP = 100;//第一行内容的top
    private static final int LINE_HEIGHT = 64;//行高
    private static final int ITEM_GAPS = 0;
    private static final int TOTAL_WIDTH = 960;
    private final List<View> mListView0 = new ArrayList<>();
    private final List<View> mListView1 = new ArrayList<>();
    private final List<View> mListView2 = new ArrayList<>();
    private final List<View> mListView3 = new ArrayList<>();
    private final List<View> mListView4 = new ArrayList<>();
    private final List<View> mListView5 = new ArrayList<>();
    private Context mContext;

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

    public void addView(List<String> list) {
        if (list == null || list.size() == 0) {
            setVisibility(View.GONE);
            return;
        }
        removeAllViews();
        addHeaderView();
        for (String text : list) {
            TextView textView = new TextView(mContext);
            textView.setPadding(30, 18, 30, 18);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 36);
            textView.setIncludeFontPadding(false);
            textView.setText(text);
            textView.setTextColor(mContext.getColorStateList(R.color.colorPrimary));
            addView(textView);
        }
    }

    private void addHeaderView() {
        TextView textView = new TextView(mContext);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 48);
        textView.setIncludeFontPadding(false);
        textView.setText("热词搜索");
        textView.setTextColor(mContext.getColor(R.color.color_red));
        addView(textView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int width;
        mListView0.clear();
        mListView1.clear();
        mListView2.clear();
        mListView3.clear();
        mListView4.clear();
        int index = 0;
        int mTotalWidth;
        if (1 < count) {
            mListView0.add(getChildAt(0));
            mTotalWidth = 0;
            for (int i = 1; i < count; i++) {
                View view = getChildAt(i);
                width = view.getMeasuredWidth() + ITEM_GAPS;
                mTotalWidth += width;
                if (mTotalWidth < TOTAL_WIDTH) {
                    mListView1.add(view);
                } else {
                    index = i;
                    break;
                }
            }
        }
        if (mListView1.size() + 1 < count) {
            mTotalWidth = 0;
            for (int i = index; i < count; i++) {
                View view = getChildAt(i);
                width = view.getMeasuredWidth() + ITEM_GAPS;
                mTotalWidth += width;
                if (mTotalWidth < TOTAL_WIDTH) {
                    mListView2.add(view);
                } else {
                    index = i;
                    break;
                }
            }
        }
        if (mListView1.size() + mListView2.size() + 1 < count) {
            mTotalWidth = 0;
            for (int i = index; i < count; i++) {
                View view = getChildAt(i);
                width = view.getMeasuredWidth() + ITEM_GAPS;
                mTotalWidth += width;
                if (mTotalWidth < TOTAL_WIDTH) {
                    mListView3.add(view);
                } else {
                    index = i;
                    break;
                }
            }
        }
        if (mListView1.size() + mListView2.size() + mListView3.size() + 1 < count) {
            mTotalWidth = 0;
            for (int i = index; i < count; i++) {
                View view = getChildAt(i);
                width = view.getMeasuredWidth() + ITEM_GAPS;
                mTotalWidth += width;
                if (mTotalWidth < TOTAL_WIDTH) {
                    mListView4.add(view);
                } else {
                    mListView5.add(view);
                }
            }
        }
        //根据可展示的内容量调整View的高度
        int measureWidth = getMeasuredWidth();
        if (mListView4.size() > 0) {
            setMeasuredDimension(measureWidth, 364);
        } else if (mListView3.size() > 0) {
            setMeasuredDimension(measureWidth, 300);
        } else if (mListView2.size() > 0) {
            setMeasuredDimension(measureWidth, 236);
        } else if (mListView1.size() > 0) {
            setMeasuredDimension(measureWidth, 172);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int left = 30;
        int top = 18;
        int size = mListView0.size();
        for (int i = 0; i < size; i++) {
            View view = mListView0.get(i);
            int width = view.getMeasuredWidth();
            view.layout(left, top, left + width, top + view.getMeasuredHeight());
            left += width + ITEM_GAPS;
        }
        left = 0;
        size = mListView1.size();
        for (int i = 0; i < size; i++) {
            View view = mListView1.get(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            view.layout(left, CONTENT_VIEW_TOP, left + width, CONTENT_VIEW_TOP + height);
            left += width + ITEM_GAPS;
        }
        left = 0;
        size = mListView2.size();
        for (int i = 0; i < size; i++) {
            View view = mListView2.get(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            view.layout(left, CONTENT_VIEW_TOP + LINE_HEIGHT, left + width, CONTENT_VIEW_TOP + LINE_HEIGHT + height);
            left += width + ITEM_GAPS;
        }
        left = 0;
        size = mListView3.size();
        for (int i = 0; i < size; i++) {
            View view = mListView3.get(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            view.layout(left, CONTENT_VIEW_TOP + LINE_HEIGHT * 2, left + width, CONTENT_VIEW_TOP + LINE_HEIGHT * 2 + height);
            left += width + ITEM_GAPS;
        }
        left = 0;
        size = mListView4.size();
        for (int i = 0; i < size; i++) {
            View view = mListView4.get(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            view.layout(left, CONTENT_VIEW_TOP + LINE_HEIGHT * 3, left + width, CONTENT_VIEW_TOP + LINE_HEIGHT * 3 + height);
            left += width + ITEM_GAPS;
        }
        //剩余的不显示了
        size = mListView5.size();
        for (int i = 0; i < size; i++) {
            View view = mListView5.get(i);
            //两种方法都可以实现
            //view.setVisibility(GONE);
            removeView(view);
        }
    }
}
