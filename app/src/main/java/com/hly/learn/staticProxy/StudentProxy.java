package com.hly.learn.staticProxy;

import android.util.Log;

/**
 * proxy class, also need implement person interface
 */
public class StudentProxy implements Person {

    Student stu;

    public StudentProxy(Person st) {
        stu = (Student) st;
    }

    @Override
    public void submitMoney() {
        Log.e("Seven", "------this student grows up");
        stu.submitMoney();
    }
}
