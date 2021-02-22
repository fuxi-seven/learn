package com.hly.learn.genericType;

import android.util.Log;

/**
 * local implement for self determined interface
 */
public class FirstService implements IFirstService {

    @Override
    public void commonMethod() {
        Log.e("Seven", "FirstService--->commonMethod()");
    }

    @Override
    public void firstMethod() {
        Log.e("Seven", "FirstService--->firstMethod()");
    }
}
