package com.hly.learn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class ObjectAnimatorView extends View {

    // 设置需要用到的变量
    public static final float RADIUS = 50f;// 圆的半径 = 100
    private Paint mPaint;// 绘图画笔

    // 设置背景颜色属性
    private String color;

    public ObjectAnimatorView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    // 设置背景颜色的get()方法
    // 初始化时，如果属性的初始值没有提供[ObjectAnimator.ofObject()中的property没有赋值]，则调用属性的get（）进行取值
    /**
    private void setupValue(Object target, Keyframe kf) {
        if (mProperty != null) {
            kf.setValue(mProperty.get(target));
            // 初始化时，如果属性的初始值没有提供，则调用该属性的get（）进行取值
        }
        kf.setValue(mGetter.invoke(target));
    }*/
    public String getColor() {
        return color;
    }

    // 设置背景颜色的set()方法
    // 在ColorEvaluator里面的evaluate有返回值就会调用set()方法
    // [----以下自动赋值的操作代表自动------]
    /**更新动画值
      当动画下一帧来时（即动画更新的时候），setAnimatedValue（）都会被调用
    void setAnimatedValue(Object target) {
        if (mProperty != null) {
            mProperty.set(target, getAnimatedValue());
            // 内部调用对象该属性的set（）方法，从而从而将新的属性值设置给对象属性
        }
     */
    public void setColor(String color) {
        this.color = color;
        Log.e("ObjectAnimatorView", "----------------setColor-----------");
        // 将画笔的颜色设置成方法参数传入的颜色
        mPaint.setColor(Color.parseColor(color));
        // 调用了invalidate()方法,即画笔颜色每次改变都会刷新视图，然后调用onDraw()方法重新绘制圆
        // 而因为每次调用onDraw()方法时画笔的颜色都会改变,所以圆的颜色也会改变
        invalidate();
    }

    // 复写onDraw()从而实现绘制逻辑
    // 绘制逻辑:先在初始点画圆,通过监听当前坐标值(currentPoint)的变化,每次变化都调用onDraw()重新绘制圆,从而实现圆的平移动画效果
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(500, 300, RADIUS, mPaint);
    }

}
