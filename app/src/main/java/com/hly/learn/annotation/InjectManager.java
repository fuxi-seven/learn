package com.hly.learn.annotation;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {

    public static void inject(Object obj1, Object obj2) {
        //控件注入
        injectField(obj1, obj2);
        //方法注入
        //injectOnClickMethod(obj1, obj2);
        //injectOnLongClickMethod(obj1, obj2);
        injectMethod(obj1, obj2);
    }

    private static void injectField(Object obj1, Object obj2) {
        Class clazz1 = obj1.getClass();
        Class clazz2 = obj2.getClass();
        Field[] declaredFields = clazz1.getDeclaredFields();
        //遍历class中所有的Field
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            //设置为可访问，暴力反射，就算是私有的也能访问到
            field.setAccessible(true);
            //获取Field上的注解对象
            BindView annotation = field.getAnnotation(BindView.class);
            //不是所有Filed上都有想要的注解,需要对annotation进行null判断
            if (annotation == null) {
                continue;
            }
            //获取注解中的值
            int id = annotation.value();
            //获取控件,通过反射获取
            try {
                Method findViewById = clazz2.getMethod("findViewById", int.class);
                findViewById.setAccessible(true);
                View view = (View) findViewById.invoke(obj2, id);
                //将view赋值给field
                field.set(obj1, view);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectOnClickMethod(final Object obj1, Object obj2) {
        Class clazz1 = obj1.getClass();
        Class clazz2 = obj2.getClass();
        //获取所有的方法（私有方法也可以获取到）
        Method[] declaredMethods = clazz1.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            final Method method = declaredMethods[i];
            //获取方法上面的注解
            OnClick annotation = method.getAnnotation(OnClick.class);
            if (annotation == null) {
                continue;
            }
            //传入需要响应的实例及方法
            OnClickListenerInvoke clickListenerInvoke = new OnClickListenerInvoke(obj1, method, "onClick");
            Object clickListener = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(),
                    new Class[]{View.OnClickListener.class}, clickListenerInvoke);
            int[] value = annotation.value();
            for (int j = 0; j < value.length; j++) {
                int id = value[j];
                //获取控件,通过反射获取
                try {
                    Method findViewById = clazz2.getMethod("findViewById", int.class);
                    findViewById.setAccessible(true);
                    View view = (View) findViewById.invoke(obj2, id);
                    Method setOnClickListenerMethod = view.getClass().getMethod(
                            "setOnClickListener", View.OnClickListener.class);
                    //通过动态代理实现设置setOnClickListener
                    setOnClickListenerMethod.invoke(view, clickListener);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectOnLongClickMethod(final Object obj1, Object obj2) {
        Class clazz1 = obj1.getClass();
        Class clazz2 = obj2.getClass();
        //获取所有的方法（私有方法也可以获取到）
        Method[] declaredMethods = clazz1.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            final Method method = declaredMethods[i];
            //获取方法上面的注解
            OnLongClick annotation = method.getAnnotation(OnLongClick.class);
            if (annotation == null) {
                continue;
            }
            OnClickListenerInvoke clickListenerInvoke = new OnClickListenerInvoke(obj1, method, "onLongClick");
            Object longClickListener = Proxy.newProxyInstance(View.OnLongClickListener.class.getClassLoader(),
                    new Class[]{View.OnLongClickListener.class}, clickListenerInvoke);
            int[] value = annotation.value();
            for (int j = 0; j < value.length; j++) {
                int id = value[j];
                //获取控件,通过反射获取
                try {
                    Method findViewById = clazz2.getMethod("findViewById", int.class);
                    findViewById.setAccessible(true);
                    View view = (View) findViewById.invoke(obj2, id);
                    Method setOnLongClickListenerMethod = view.getClass().getMethod(
                            "setOnLongClickListener", View.OnLongClickListener.class);
                    //通过动态代理实现设置setOnLongClickListener
                    setOnLongClickListenerMethod.invoke(view, longClickListener);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //统一处理所有自定义的注解
    private static void injectMethod(final Object obj1, Object obj2) {
        Class clazz1 = obj1.getClass();
        Class clazz2 = obj2.getClass();
        //获取所有的方法
        Method[] declaredMethods = clazz1.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            final Method method = declaredMethods[i];
            //获取到某一方法上的所有的Annotation
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //获取到某个Annotation对应的class
                Class<? extends Annotation> annotationTypeClass = annotation.annotationType();
                //获取到Annotation上的Annotation，来区分是自定义注解还是其他注解
                ClickEvent ano = annotationTypeClass.getAnnotation(ClickEvent.class);
                if (ano == null) {
                    continue;
                }
                //获取到Annotation上的Annotation对应的值
                String listenerSetting = ano.listenerSetting();
                Class listenerClass = ano.listenerClass();
                String eventName = ano.eventName();
                //创建动态代理对象
                OnClickListenerInvoke clickListenerInvoke = new OnClickListenerInvoke(obj1, method, eventName);
                Object clickListener = Proxy.newProxyInstance(listenerClass.getClassLoader(),
                        new Class[]{listenerClass}, clickListenerInvoke);
                try {
                    //进入该逻辑的annotation是OnClick或OnLongClick,但是通过annotation访问不到value(),可以强制转换，但是会增加处理逻辑，此处不合适
                    /*String name = annotation.annotationType().getSimpleName();
                    int[] ids;
                    if (name.equals("OnClick")) {
                        OnClick oc = (OnClick) annotation;
                        ids = oc.value();
                    } else {
                        OnLongClick olc = (OnLongClick) annotation;
                        ids = olc.value();
                    }*/
                    //所有此处通过反射获取到int[]
                    Method valueMethod = annotationTypeClass.getDeclaredMethod("value");
                    int[] ids = (int[]) valueMethod.invoke(annotation);
                    for (int id : ids) {
                        Method findViewById = clazz2.getMethod("findViewById", int.class);
                        findViewById.setAccessible(true);
                        View view = (View) findViewById.invoke(obj2, id);
                        Method setOnClickListenerMethod = view.getClass().getMethod(
                                listenerSetting, listenerClass);
                        //通过动态代理实现设置ClickListener
                        setOnClickListenerMethod.invoke(view, clickListener);
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
