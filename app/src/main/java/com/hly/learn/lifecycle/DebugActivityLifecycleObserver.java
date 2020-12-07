package com.hly.learn.lifecycle;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class DebugActivityLifecycleObserver implements LifecycleEventObserver {

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (source.getLifecycle().getCurrentState() == Lifecycle.State.CREATED) {
            Log.e("Seven", "------------onCreate");
        } else if (event == Lifecycle.Event.ON_START) {
            Log.e("Seven", "------------onStart");
        } else if (source.getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
            Log.e("Seven", "------------onResumed");
        } else if (event == Lifecycle.Event.ON_PAUSE) {
            Log.e("Seven", "------------onPause");
        } else if (source.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            Log.e("Seven", "------------onDestroy");
        } else if (event == Lifecycle.Event.ON_STOP) {
            Log.e("Seven", "------------onStop");
        }
    }
}
