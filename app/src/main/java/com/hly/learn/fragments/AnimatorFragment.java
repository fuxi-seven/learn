package com.hly.learn.fragments;

import android.animation.ObjectAnimator;
import android.view.View;

import com.hly.learn.R;
import com.hly.learn.view.ColorEvaluator;
import com.hly.learn.view.ObjectAnimatorView;

public class AnimatorFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.animator_layout;
    }

    @Override
    public void initData(View view) {
        ObjectAnimatorView oav= (ObjectAnimatorView) view.findViewById(R.id.objectAnimatorView);
        ObjectAnimator anim = ObjectAnimator.ofObject(oav, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        // 设置自定义View对象、背景颜色属性值 & 颜色估值器
        // 本质逻辑：
        // 步骤1：根据颜色估值器不断 改变 值
        // 步骤2：调用set（）设置背景颜色的属性值（实际上是通过画笔进行颜色设置）
        // 步骤3：调用invalidate()刷新视图，即调用onDraw（）重新绘制，从而实现动画效果
        anim.setDuration(8000);
        anim.start();
    }
}
