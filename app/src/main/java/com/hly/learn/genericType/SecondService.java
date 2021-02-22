package com.hly.learn.genericType;

import android.util.Log;

/**
 * local implement for self determined interface
 */

public class SecondService implements ISecondService {

    @Override
    public void secondMethod() {
        Log.e("Seven", "SecondService--->secondMethod()");
    }

    @Override
    public void commonMethod() {
        Log.e("Seven", "SecondService--->commonMethod()");
    }
}
