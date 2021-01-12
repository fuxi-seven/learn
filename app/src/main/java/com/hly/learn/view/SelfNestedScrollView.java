package com.hly.learn.view;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class SelfNestedScrollView extends NestedScrollView implements
        View.OnScrollChangeListener {

    private ViewPager2 viewPager2;
    private int velocityY;
    private boolean isStartFling;
    private int totalDy;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)
    // Fling friction
    private static float mFlingFriction = ViewConfiguration.getScrollFriction();
    private static float mPhysicalCoeff;
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));

    public SelfNestedScrollView(@NonNull Context context) {
        super(context);
        initData(context);
    }

    public SelfNestedScrollView(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public SelfNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();
        float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
        mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.37f // inch/meter
                * ppi
                * 0.84f;
        setOnScrollChangeListener(this);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
            int type) {
        int headerViewHeight = ((ViewGroup) getChildAt(0)).getChildAt(0).getMeasuredHeight();
        //向上滑动，若当前bannerView可见，先将bannerView滑至不可见，然后再响应recyclerview的滑动
        boolean isNeedToHideTop = dy > 0 && getScrollY() < headerViewHeight;
        if (isNeedToHideTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        this.velocityY = velocityY;
        if (velocityY > 0) {
            isStartFling = true;
            totalDy = 0;
        }
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        totalDy += scrollY - oldScrollY;
        if (scrollY == ((ViewGroup) getChildAt(0)).getChildAt(0).getMeasuredHeight()) {
            if (velocityY != 0) {
                Double splineFlingDistance = getSplineFlingDistance(velocityY);
                if (splineFlingDistance > totalDy) {
                    viewPager2 = getChildView(this, ViewPager2.class);
                    if (viewPager2 != null) {
                        RecyclerView childRecyclerView = getChildView(
                                ((ViewGroup) viewPager2.getChildAt(0)).getChildAt(
                                        viewPager2.getCurrentItem()), RecyclerView.class);
                        if (childRecyclerView != null) {
                            childRecyclerView.fling(0,getVelocityByDistance(splineFlingDistance));
                        }
                    }
                }
            }
            totalDy = 0;
            velocityY = 0;
        }
    }

    private <T> T getChildView(View viewGroup, Class<T> targetClass) {
        if (viewGroup != null && viewGroup.getClass() == targetClass) {
            return (T) viewGroup;
        }

        if (viewGroup instanceof ViewGroup) {
            for(int i = 0; i< ((ViewGroup)viewGroup).getChildCount(); i++) {
                View view = ((ViewGroup)viewGroup).getChildAt(i);
                if (view instanceof ViewGroup) {
                    T result = getChildView(view, targetClass);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    private double getSplineDeceleration(int velocity) {
        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    private static double getSplineDecelerationByDistance(double distance) {
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return decelMinusOne * (Math.log(distance / (mFlingFriction * mPhysicalCoeff)))
                / DECELERATION_RATE;
    }

    private double getSplineFlingDistance(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return mFlingFriction * mPhysicalCoeff * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    public static int getVelocityByDistance(double distance) {
        final double l = getSplineDecelerationByDistance(distance);
        int velocity = (int) (Math.exp(l) * mFlingFriction * mPhysicalCoeff / INFLEXION);
        return Math.abs(velocity);
    }

    /* Returns the duration, expressed in milliseconds */
    private int getSplineFlingDuration(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return (int) (1000.0 * Math.exp(l / decelMinusOne));
    }
}
