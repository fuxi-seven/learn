package com.hly.learn;

import android.app.Activity;
import android.os.Bundle;

import com.hly.learn.lifecycle.HookActivityLifecycleObserver;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class HookActivity extends Activity implements LifecycleOwner {

    private LifecycleRegistry mRegistry;
    private HookActivityLifecycleObserver mHookActivityLifecycleObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        mHookActivityLifecycleObserver = new HookActivityLifecycleObserver();
        mRegistry = new LifecycleRegistry(this);
        //注册需要监听的 Observer
        mRegistry.addObserver(mHookActivityLifecycleObserver);
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
