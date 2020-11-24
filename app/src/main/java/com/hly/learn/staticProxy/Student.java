package com.hly.learn.staticProxy;

import android.util.Log;

public class Student implements Person {

    @Override
    public void submitMoney() {
        Log.e("Seven", "-------submit money!");
    }
}
