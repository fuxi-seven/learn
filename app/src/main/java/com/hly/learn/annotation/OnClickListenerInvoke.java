package com.hly.learn.annotation;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OnClickListenerInvoke implements InvocationHandler {
    private Object mObject;
    private Method mMethod;
    private String mEventName;

    public OnClickListenerInvoke(Object obj, Method method, String eventName) {
        mMethod = method;
        mObject = obj;
        mEventName = eventName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //此处的method是onClick或者onLongClick,需要执行的是onViewClick或onViewLongClick方法
        if (method.getName().equals(mEventName)) {
            return mMethod.invoke(mObject, args);
        }
        return null;
    }
}
