package com.hly.learn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hly.apt_sdk.ViewBind;
import com.hly.learn.annotation.BindView;
import com.hly.learn.lifecycle.HookActivityLifecycleObserver;
import com.hly.learn.livedata.LiveDataBus;
import com.hly.learn.livedata.LiveDataTestActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class HookActivity extends Activity implements LifecycleOwner {

    private LifecycleRegistry mRegistry;
    private HookActivityLifecycleObserver mHookActivityLifecycleObserver;

    @BindView(R.id.tv_1)
    TextView mTv1;

    @BindView(R.id.tv_2)
    TextView mTv2;

    private TextView mTv3;
    private MutableLiveData<String> mLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        mHookActivityLifecycleObserver = new HookActivityLifecycleObserver();
        mRegistry = new LifecycleRegistry(this);
        //注册需要监听的 Observer
        mRegistry.addObserver(mHookActivityLifecycleObserver);
        ViewBind.bind(this);
        //mTv1.setText("APT是Annotation Processing Tool的简称");
        //mTv2.setText("APT可以根据注解，在编译时生成代码");

        mTv3 = findViewById(R.id.tv_3);
        mTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试LiveData对LifeCycleOwner生命周期的检测，处于前台后，会立刻收到通知
                testDataObserverLifecycle();

                //测试LiveData的粘性，即先发送，后注册的owner也会收到,注册者需要使用withStick()
                LiveDataBus.getInstance().with("key_test", String.class).setValue("liveDataBus with stick");

                //测试消除粘性后，是否正常
                testDataRemoveStick();
                startActivity();
            }
        });

        mLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(HookActivity.this, "receive message is " + s,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mRegistry;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistry.removeObserver(mHookActivityLifecycleObserver);
    }

    private void startActivity() {
        Intent i = new Intent();
        i.setClass(this, LiveDataTestActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void testDataObserverLifecycle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mLiveData.postValue("test lifecycle observe");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void testDataRemoveStick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    LiveDataBus.getInstance().with("key_test", String.class).postValue(
                            "LiveDataBus remove stick");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
