package com.hly.learn.fragments;

import android.view.View;
import android.widget.Button;

import com.hly.learn.R;
import com.hly.learn.genericType.ServiceProxyImplOne;
import com.hly.learn.genericType.ServiceProxyImplTwo;

public class GenericTypeFragment extends BaseFragment implements View.OnClickListener{


    @Override
    public int getLayoutId() {
        return R.layout.generic_layout;
    }

    @Override
    public void initData(View view) {
        Button btn1 = view.findViewById(R.id.generic_btn1);
        Button btn2 = view.findViewById(R.id.generic_btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.generic_btn1) {
            ServiceProxyImplOne serviceProxyImplOne = ServiceProxyImplOne.getInstance();
            serviceProxyImplOne.method1();
            serviceProxyImplOne.method2();
            serviceProxyImplOne.selfMethod();
        } else if (v.getId() == R.id.generic_btn2) {
            ServiceProxyImplTwo serviceProxyImplTwo = ServiceProxyImplTwo.getInstance();
            serviceProxyImplTwo.method1();
            serviceProxyImplTwo.method2();
            serviceProxyImplTwo.selfMethod();
        }
    }
}
