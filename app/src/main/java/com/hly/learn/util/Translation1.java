package com.hly.learn.util;

import android.util.Log;

public class Translation1 {

    private int status;
    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public String show() {
        Log.e("Seven", "Translation1 out is: " + content.out);
        return content.out;
    }
}
