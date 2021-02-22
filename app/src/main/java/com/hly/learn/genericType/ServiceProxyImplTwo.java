package com.hly.learn.genericType;

/**
 * local proxy for related local implements
 */
public class ServiceProxyImplTwo extends ServiceProxy<ISecondService>{

    private static class InstanceHolder {
        private static ServiceProxyImplTwo sGenericProxyTwo = new ServiceProxyImplTwo();
    }

    public static ServiceProxyImplTwo getInstance() {
        return ServiceProxyImplTwo.InstanceHolder.sGenericProxyTwo;
    }

    @Override
    protected void init() {
        mService = new SecondService();
    }

    public void selfMethod() {
        mService.secondMethod();
    }
}
