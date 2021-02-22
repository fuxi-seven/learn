package com.hly.learn.genericType;

/**
 * base common abstract class
 * @param <T> generic type, for different local implements
 */
public abstract class ServiceProxy<T extends IService> {

    protected T mService;

    public void method1() {
        init();
    }

    public void method2() {
        mService.commonMethod();
    }

    protected abstract void init();
}
