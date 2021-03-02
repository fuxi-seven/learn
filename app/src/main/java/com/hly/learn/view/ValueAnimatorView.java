package com.hly.learn.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ValueAnimatorView extends View {

    // 设置需要用到的变量
    public static final float RADIUS = 20f;// 圆的半径 = 20
    private Point currentPoint;// 当前点坐标
    private Paint mPaint;// 绘图画笔

    public ValueAnimatorView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    // 复写onDraw()从而实现绘制逻辑
    // 绘制逻辑:先在初始点画圆,通过监听当前坐标值(currentPoint)的变化,每次变化都调用onDraw()重新绘制圆,从而实现圆的平移动画效果
    @Override
    protected void onDraw(Canvas canvas) {
        // 如果当前点坐标为空(即第一次)
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            // 创建一个点对象(坐标是(20,20))

            // 在该点画一个圆:圆心 = (20,20),半径 = 20
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);


            // (重点关注)将属性动画作用到View中
            // 步骤1:创建初始动画时的对象点  & 结束动画时的对象点
            Point startPoint = new Point(RADIUS, RADIUS);// 初始点为圆心(20,20)
            Point endPoint = new Point(1000, 500);// 结束点为(1000,500)

            // 步骤2:创建动画对象 & 设置初始值 和 结束值
            ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
            // 参数说明
            // 参数1：TypeEvaluator 类型参数 - 使用自定义的PointEvaluator(实现了TypeEvaluator接口)
            // 参数2：初始动画的对象点
            // 参数3：结束动画的对象点

            // 设置动画时长
            anim.setDuration(5000);

            // 步骤3：通过 值 的更新监听器，将改变的对象手动赋值给当前对象
            // 此处是将 改变后的坐标值对象 赋给 当前的坐标值对象
            // 设置 值的更新监听器
            // 即每当坐标值（Point对象）更新一次,该方法就会被调用一次
            //[----以下取值再赋值的操作代表手动------]
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 将每次变化后的坐标值（估值器PointEvaluator中evaluate（）返回的Point对象值）到当前坐标值对象（currentPoint）
                    // 从而更新当前坐标值（currentPoint
                    currentPoint = (Point) animation.getAnimatedValue();

                    // 步骤4：每次赋值后就重新绘制，从而实现动画效果
                    // 调用invalidate()后,就会刷新View,即才能看到重新绘制的界面,即onDraw()会被重新调用一次
                    // 所以坐标值每改变一次,就会调用onDraw()一次
                    invalidate();
                }
            });
            // 启动动画
            anim.start();

        } else {
            // 如果坐标值不为0,则画圆
            // 所以坐标值每改变一次,就会调用onDraw()一次,就会画一次圆,从而实现动画效果

            // 在该点画一个圆:圆心 = (20,20),半径 = 20
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, RADIUS, mPaint);
        }
    }
}
