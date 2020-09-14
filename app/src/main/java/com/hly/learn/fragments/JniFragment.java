package com.hly.learn.fragments;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hly.learn.R;

public class JniFragment extends BaseFragment {
    static {
        System.loadLibrary("Test");
    }
    public native String getFromJNI();

    public native String setFromJava();

    public String getFromJava() {
        return "This string is from java";
    }

    @Override
    public int getLayoutId() {
        return R.layout.jni_layout;
    }

    @Override
    public void initData(View view) {
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(getFromJNI());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, setFromJava(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
