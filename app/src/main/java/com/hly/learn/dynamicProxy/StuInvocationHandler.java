package com.hly.learn.dynamicProxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StuInvocationHandler<T> implements InvocationHandler {
    //被代理对象实例
    T target;

    public StuInvocationHandler(T t) {
        target = t;
    }

    /**
     * proxy:代表动态代理对象
     * method：代表正在执行的方法
     * args：代表调用目标方法时传入的实参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e("Seven", "proxy execute " + method.getName() + " method start");
        Object result = method.invoke(target, args);
        Log.e("Seven", "proxy execute " + method.getName() + " method end");
        return result;
    }
}
