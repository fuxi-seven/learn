package com.hly.learn.genericType;

/**
 * local proxy for related local implements
 */
public class ServiceProxyImplOne extends ServiceProxy<IFirstService> {

    private static class InstanceHolder {
        private static ServiceProxyImplOne sGenericProxyOne = new ServiceProxyImplOne();
    }

    public static ServiceProxyImplOne getInstance() {
        return InstanceHolder.sGenericProxyOne;
    }

    @Override
    protected void init() {
        mService = new FirstService();
    }

    public void selfMethod() {
        mService.firstMethod();
    }
}
