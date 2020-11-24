package com.hly.learn.fragments;

import android.view.View;
import android.widget.Button;

import com.hly.learn.R;
import com.hly.learn.dynamicProxy.Person1;
import com.hly.learn.dynamicProxy.StuInvocationHandler;
import com.hly.learn.dynamicProxy.Student1;
import com.hly.learn.staticProxy.Person;
import com.hly.learn.staticProxy.Student;
import com.hly.learn.staticProxy.StudentProxy;

import java.lang.reflect.Proxy;

public class ProxyFragment extends BaseFragment implements View.OnClickListener{

    @Override
    public int getLayoutId() {
        return R.layout.proxy_layout;
    }

    @Override
    public void initData(View view) {
        Button sBtn = view.findViewById(R.id.st_btn);
        Button dBtn = view.findViewById(R.id.dy_btn);
        sBtn.setOnClickListener(this);
        dBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.st_btn) {
            staticProxy();
        } else if (v.getId() == R.id.dy_btn) {
            dynamicProxy();
        }
    }

    private void staticProxy() {
        //被代理的对象学生tom
        Person tom = new Student();
        //代理对象，并将tom传给代理对象
        Person monitor = new StudentProxy(tom);
        //代理对象执行操作
        monitor.submitMoney();
    }

    private void dynamicProxy() {
        //创建实例对象，该对象就是被代理的对象
        Person1 jack = new Student1();
        //创建一个与代理对象相关联的InvocationHandler
        StuInvocationHandler stuInvocationHandler = new StuInvocationHandler<>(jack);
        //创建一个代理对象stuProxy来代理jack，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        Person1 stuProxy = (Person1) Proxy.newProxyInstance(Person1.class.getClassLoader(),
                new Class<?>[]{Person1.class}, stuInvocationHandler);
        //代理对象执行操作
        stuProxy.submitMoney();
    }
}
