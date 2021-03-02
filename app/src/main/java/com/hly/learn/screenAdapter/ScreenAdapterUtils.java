package com.hly.learn.screenAdapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class ScreenAdapterUtils {

    private Context mContext;

    //标准值
    private static final float STANDARD_WIDTH = 1080f;
    private static final float STANDARD_HEIGHT = 1920f;

    //实际值
    private static float mDisplayMetricsWidth;
    private static float mDisplayMetricsHeight;

    //单例模式
    private static ScreenAdapterUtils sInstance;

    public synchronized static ScreenAdapterUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ScreenAdapterUtils(context);
        }
        return sInstance;
    }

    private ScreenAdapterUtils(Context context) {
        mContext = context;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (mDisplayMetricsWidth == 0.0f || mDisplayMetricsHeight == 0.0f) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int statusBarHeight = getStatusBarHeight(context);
            if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                //横屏
                mDisplayMetricsWidth = displayMetrics.heightPixels;
                mDisplayMetricsHeight = displayMetrics.widthPixels - statusBarHeight;
            } else {
                //竖屏
                mDisplayMetricsWidth = (float) displayMetrics.widthPixels;
                mDisplayMetricsHeight = (float) displayMetrics.heightPixels - statusBarHeight;
            }
        }
    }

    private static final String DIME_CLASS = "com.android.internal.R$dimen";
    //用于反射的系统属性
    private int getStatusBarHeight(Context context) {
        return getValue(context, DIME_CLASS, "status_bar_height", 48);
    }

    private int getValue(Context context, String dimenClass, String barHeight, int i) {
        Class<?> clz = null;
        try {
            clz = Class.forName(dimenClass);
            Object obj = clz.newInstance();
            //反射属性
            Field field = clz.getField(barHeight);
            int id = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return i;
    }

    public float getHorValue() {
        return ((float)mDisplayMetricsWidth/STANDARD_WIDTH);
    }

    public float getVerValue() {
        return ((float)mDisplayMetricsHeight/(STANDARD_HEIGHT - getStatusBarHeight(mContext)));
    }
}

