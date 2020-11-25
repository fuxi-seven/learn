package com.hly.learn.hook;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtils {

    /**
     * hook 核心代码
     * 用自己的点击事件，替换掉View的点击事件
     * @param view view
     */
    public static void hookOnClickListener(View view) {

        //反射执行View类的getListenerInfo()方法，拿到view的mListenerInfo对象，这个对象是点击事件mOnClickListener的持有者
        try {
            Class<?> viewClz = Class.forName("android.view.View");
            Method method = viewClz.getDeclaredMethod("getListenerInfo");
            //由于getListenerInfo()方法并不是public的，所以要加这个代码来保证访问权限
            method.setAccessible(true);
            //拿到mListenerInfo，也就是点击事件的持有者
            Object listenerInfo = method.invoke(view);

            //找到mListenerInfo持有的点击事件对象mOnClickListener
            //内部类的表示方法:android.view.View$ListenerInfo
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field onClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            final View.OnClickListener onClickListenerInstance = (View.OnClickListener) onClickListener.get(
                    listenerInfo);

            //创建自己点击事件的代理类
            //方式1：自己实现代理类,将原始的View.OnClickListener对象onClickListenerInstance作为参数传入
            //ProxyOnClickListener proxyOnClickListener = new ProxyOnClickListener(onClickListenerInstance);

            //方式2：由于View.OnClickListener是一个接口，所以可以直接用动态代理模式
            //参数：类的加载器，要代理实现的接口（用Class数组表示，支持多接口），代理类的实际逻辑封装在new出来的InvocationHandler内
            final Object proxyOnClickListener = Proxy.newProxyInstance(
                    View.OnClickListener.class.getClassLoader(),
                    new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                        private int clickCount = 0;
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args)
                                throws Throwable {
                            clickCount++;
                            Log.d("Seven", "Hook OnClickListener 1 click count " + clickCount);
                            return method.invoke(onClickListenerInstance, args);
                        }
                    });
            //将持有者拥有的点击事件替换成代理对象[将listenerInfo里面的onClickListener变量替换为proxyOnClickListener]
            onClickListener.set(listenerInfo, proxyOnClickListener);
        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException | NoSuchFieldException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static class ProxyOnClickListener implements View.OnClickListener{

        private View.OnClickListener listener;
        private int clickCount = 0;
        ProxyOnClickListener(View.OnClickListener listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            clickCount++;
            Log.d("Seven", "Hook OnClickListener 2 click count " + clickCount);
            if(this.listener != null){
                this.listener.onClick(v);
            }
        }
    }

    public static void hookInstrumentation(Context context) {
        try {
            //先获取到当前的ActivityThread对象， 该对象是mInstrumentation的持有者
            Class<?> activityThreadClz = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            //从ActivityThread里面拿到原始的mInstrumentation
            Field instrumentation = activityThreadClz.getDeclaredField("mInstrumentation");
            instrumentation.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) instrumentation.get(currentActivityThread);

            //创建代理对象
            Instrumentation proxyInstrumentation = new ProxyInstrumentation(mInstrumentation, context.getPackageManager());

            //将持有的对象替换成代理对象[将currentActivityThread里面的instrumentation变量替换为proxyInstrumentation]
            instrumentation.set(currentActivityThread, proxyInstrumentation);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
