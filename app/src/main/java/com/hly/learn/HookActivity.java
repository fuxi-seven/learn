package com.hly.learn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.hly.apt_sdk.ViewBind;
import com.hly.learn.annotation.BindView;
import com.hly.learn.lifecycle.HookActivityLifecycleObserver;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class HookActivity extends Activity implements LifecycleOwner {

    private LifecycleRegistry mRegistry;
    private HookActivityLifecycleObserver mHookActivityLifecycleObserver;

    @BindView(R.id.tv_1)
    TextView mTv1;

    @BindView(R.id.tv_2)
    TextView mTv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        mHookActivityLifecycleObserver = new HookActivityLifecycleObserver();
        mRegistry = new LifecycleRegistry(this);
        //注册需要监听的 Observer
        mRegistry.addObserver(mHookActivityLifecycleObserver);
        ViewBind.bind(this);
        mTv1.setText("APT是Annotation Processing Tool的简称");
        mTv2.setText("APT可以根据注解，在编译时生成代码");
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistry.removeObserver(mHookActivityLifecycleObserver);
    }
}
