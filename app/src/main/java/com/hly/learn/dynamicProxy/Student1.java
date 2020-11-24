package com.hly.learn.dynamicProxy;

import android.util.Log;

/**
 * student class need implement person interface
 */
public class Student1 implements Person1 {

    @Override
    public void submitMoney() {
        Log.e("Seven", "-------submit money!");
    }
}
