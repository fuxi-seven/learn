package com.hly.learn.livedata;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public final class LiveDataBus {

    private final Map<String, MutableLiveData<Object>> bus;

    private static class InstanceHolder {
        public static LiveDataBus sInstance = new LiveDataBus();
    }

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    public static synchronized LiveDataBus getInstance() {
        return InstanceHolder.sInstance;
    }

    public <T> MutableLiveData<T> with(String target, Class<T> type) {
        if (!bus.containsKey(target)) {
            bus.put(target, new BusMutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(target);
    }

    public MutableLiveData<Object> with(String target) {
        return with(target, Object.class);
    }

    //使用此方法来测试LiveData的粘性，即先发送，后注册的owner也会收到
    public <T> MutableLiveData<T> withStick(String target, Class<T> type) {
        if (!bus.containsKey(target)) {
            bus.put(target, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(target);
    }

    private static class BusMutableLiveData<T> extends MutableLiveData<T> {

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            try {
                //通过设置mLastVersion = mVersion在执行considerNotify()时返回，消除粘性
                hook(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void hook(@NonNull Observer<?> observer) {
            Class<?> liveDataClass = LiveData.class;
            try {
                Field mObserversField = liveDataClass.getDeclaredField("mObservers");
                mObserversField.setAccessible(true);
                Object mObservers = mObserversField.get(this);
                Class<?> mObserversClass = mObservers.getClass();

                Method getMethod = mObserversClass.getDeclaredMethod("get", Object.class);
                getMethod.setAccessible(true);
                Object entry = getMethod.invoke(mObservers, observer);
                Object observerWrapper = ((Map.Entry) entry).getValue();
                Class<?> observerClass = observerWrapper.getClass().getSuperclass();

                Field mLastVersionField = observerClass.getDeclaredField("mLastVersion");
                mLastVersionField.setAccessible(true);
                Field mVersionField = liveDataClass.getDeclaredField("mVersion");
                mVersionField.setAccessible(true);
                Object mVersionValue = mVersionField.get(this);
                mLastVersionField.set(observerWrapper, mVersionValue);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
