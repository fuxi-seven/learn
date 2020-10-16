package com.hly.learn.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.hly.learn.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
    private int colorActive;
    private int colorInactive;
    private static final float DP = Resources.getSystem().getDisplayMetrics().density;
    /**
     * Indicator stroke width.
     */
    private final float mIndicatorStrokeWidth = DP * 1.5f;

    /**
     * Indicator width.
     */
    private final float mIndicatorItemLength = DP * 30;
    /**
     * Padding between indicators.
     */
    private final float mIndicatorItemPadding = DP * 14;

    private float mIndicatorPaddingRight = DP * 30;
    private final float mIndicatorWidth = DP * 6f;
    private final float mIndicatorRoundCorner = DP * 3f;

    private final Paint mPaint = new Paint();

    public LinePagerIndicatorDecoration(Context context) {
        initPaint(context);
    }
    public LinePagerIndicatorDecoration(Context context,float marginRight) {
        this.mIndicatorPaddingRight=marginRight;
        initPaint(context);
    }


    private void initPaint(Context context) {
        mPaint.setStrokeWidth(mIndicatorStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        colorActive = context.getColor(R.color.color_pager_indicator_active);
        colorInactive = context.getResources().getColor(R.color.color_pager_indicator_inactive);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            int mOrientation = layoutManager.getOrientation();
            int itemCount;
            itemCount = parent.getAdapter().getItemCount();
            // center horizontally, calculate width and subtract half from center
            float totalLength = mIndicatorItemLength + mIndicatorWidth * (itemCount - 1);
            float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
            int activePosition = getCurrentPosition(parent);
            if (activePosition == RecyclerView.NO_POSITION) {
                return;
            }
            // find offset of active page (if the user is scrolling)
            View activeChild = layoutManager.findViewByPosition(activePosition);
            if (activeChild == null) {
                activePosition = layoutManager.findFirstVisibleItemPosition();
                activeChild = layoutManager.findViewByPosition(activePosition);
            }
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                float indicatorTotalHeight = totalLength + paddingBetweenItems;
                float indicatorStartX = parent.getWidth() - mIndicatorPaddingRight;
                float indicatorPosY = (parent.getHeight() - indicatorTotalHeight) / 2F;
                drawIndicatorsV(c, indicatorStartX, indicatorPosY, itemCount, activePosition);
            } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
                float indicatorStartX = parent.getMeasuredWidth() / 2 - totalLength / 2;
                float indicatorPosY = parent.getMeasuredHeight() - mIndicatorPaddingRight;
                drawIndicatorsH(c, indicatorStartX, indicatorPosY, itemCount, activePosition);
            }
        }

    }

    /**
     * get item position in center of viewpager
     */
    public int getCurrentPosition(RecyclerView rlv) {
        int curPosition;
        if (rlv.getLayoutManager().canScrollHorizontally()) {
            curPosition = getCenterXChildPosition(rlv);
        } else {
            curPosition = getCenterYChildPosition(rlv);
        }
        if (curPosition < 0) {
            curPosition = 0;
        }
        return curPosition;
    }

    private void drawIndicatorsV(Canvas c, float indicatorStartX, float indicatorPosY,
            int itemCount, int activePosition) {
        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {
            mPaint.setColor(colorInactive);
            if (i == activePosition) {
                mPaint.setColor(colorActive);
                c.drawRoundRect(start, indicatorPosY, start + mIndicatorWidth,
                        indicatorPosY + mIndicatorItemLength, mIndicatorRoundCorner,
                        mIndicatorRoundCorner, mPaint);
                indicatorPosY += mIndicatorItemLength + mIndicatorItemPadding;
                continue;
            }
            c.drawCircle(start + mIndicatorWidth / 2, indicatorPosY + mIndicatorWidth / 2,
                    mIndicatorRoundCorner, mPaint);
            indicatorPosY += mIndicatorWidth + mIndicatorItemPadding;
        }

    }

    private void drawIndicatorsH(Canvas c, float indicatorStartX, float indicatorPosY,
            int itemCount, int activePosition) {
        float start = indicatorPosY;
        for (int i = 0; i < itemCount; i++) {
            mPaint.setColor(colorInactive);
            if (i == activePosition) {
                mPaint.setColor(colorActive);
                c.drawRoundRect(indicatorStartX, start, indicatorStartX + mIndicatorItemLength,
                        start + mIndicatorWidth, mIndicatorRoundCorner, mIndicatorRoundCorner,
                        mPaint);
                indicatorStartX += mIndicatorItemLength + mIndicatorItemPadding;
                continue;
            }
            c.drawCircle(indicatorStartX + mIndicatorWidth / 2, start + mIndicatorWidth / 2,
                    mIndicatorRoundCorner, mPaint);
            indicatorStartX += mIndicatorWidth + mIndicatorItemPadding;
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = 0;
    }

    /**
     * Get position of center child in X Axes
     */
    public static int getCenterXChildPosition(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterX(recyclerView, child)) {
                    return recyclerView.getChildAdapterPosition(child);
                }
            }
        }
        return childCount;
    }

    /**
     * Get position of center child in Y Axes
     */
    public static int getCenterYChildPosition(RecyclerView recyclerView) {
        int childCount = recyclerView.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                if (isChildInCenterY(recyclerView, child)) {
                    return recyclerView.getChildAdapterPosition(child);
                }
            }
        }
        return childCount;
    }

    public static boolean isChildInCenterX(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        int[] lvLocationOnScreen = new int[2];
        int[] vLocationOnScreen = new int[2];
        recyclerView.getLocationOnScreen(lvLocationOnScreen);
        int middleX = lvLocationOnScreen[0] + recyclerView.getWidth() / 2;
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen);
            if (vLocationOnScreen[0] <= middleX
                    && vLocationOnScreen[0] + view.getWidth() >= middleX) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChildInCenterY(RecyclerView recyclerView, View view) {
        int childCount = recyclerView.getChildCount();
        int[] lvLocationOnScreen = new int[2];
        int[] vLocationOnScreen = new int[2];
        recyclerView.getLocationOnScreen(lvLocationOnScreen);
        int middleY = lvLocationOnScreen[1] + recyclerView.getHeight() / 2;
        if (childCount > 0) {
            view.getLocationOnScreen(vLocationOnScreen);
            if (vLocationOnScreen[1] <= middleY
                    && vLocationOnScreen[1] + view.getHeight() >= middleY) {
                return true;
            }
        }
        return false;
    }
}
