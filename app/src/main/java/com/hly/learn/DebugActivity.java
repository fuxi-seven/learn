package com.hly.learn;

import android.os.Bundle;

import com.hly.learn.lifecycle.DebugActivityLifecycleObserver;

import androidx.appcompat.app.AppCompatActivity;

public class DebugActivity extends AppCompatActivity {

    private DebugActivityLifecycleObserver mDebugActivityLifecycleObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        mDebugActivityLifecycleObserver = new DebugActivityLifecycleObserver();
        getLifecycle().addObserver(mDebugActivityLifecycleObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mDebugActivityLifecycleObserver);
    }
}
