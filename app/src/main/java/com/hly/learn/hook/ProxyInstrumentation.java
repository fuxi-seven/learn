package com.hly.learn.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyInstrumentation extends Instrumentation {

    private Instrumentation mBase;
    private PackageManager mPm;

    public ProxyInstrumentation(Instrumentation base, PackageManager pm) {
        mBase = base;
        mPm = pm;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Log.d("Seven", "------Hook打印execStartActivity------");

        List<ResolveInfo> resolveInfos = mPm.queryIntentActivities(intent, PackageManager.MATCH_ALL);

        //检查要跳转的activity是否在Manifest.xml里面注册
        if (resolveInfos == null || resolveInfos.size() == 0) {
            //把要跳转的activity记录下来，在接下来newActivity还原的时候要拿来还原
            intent.putExtra("intent_name", intent.getComponent().getClassName());
            //把要跳转的activity改成已经在Manifest.xml里面注册过的MainActivity
            intent.setClassName(who, "com.hly.learn.MainActivity");
        }

        // 开始调用Instrumentation原始的方法,由于这个方法是隐藏的,因此需要使用反射调用;
        try {
            //首先找到这个方法
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            //通过反射调用Instrumentation的execStartActivity方法
            return (ActivityResult) execStartActivity.invoke(mBase, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            throw new RuntimeException("do not support!");
        }
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        //取出上步记录下来的要跳转的activity并进行替换
        String intentName = intent.getStringExtra("intent_name");
        Log.d("Seven", "------newActivity内进行替换------");
        if (!TextUtils.isEmpty(intentName)) {
            return super.newActivity(cl, intentName, intent);
        }
        return super.newActivity(cl, className, intent);
    }
}
