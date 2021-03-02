package com.hly.learn.livedata;

import android.os.Bundle;
import android.widget.Toast;

import com.hly.learn.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class LiveDataTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenadapter_layout);
        observe();
    }

    private void observe() {
        // 使用withStick具有粘性，后注册也会收到之前的通知
        // 使用with可以通过hook消除粘性
        LiveDataBus.getInstance().with("key_test", String.class).observe(this,
                new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(LiveDataTestActivity.this, "receive message is " + s,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
