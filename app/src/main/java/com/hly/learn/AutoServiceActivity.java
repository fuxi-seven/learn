package com.hly.learn;

import android.os.Bundle;

import com.hly.autoservicecommonlibrary.services.Services;
import com.hly.autoservicecommonlibrary.startmain.IStartMainContainer;

import androidx.appcompat.app.AppCompatActivity;

public class AutoServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 通过Services.load来加载已经标识IStartMainContainer.class的注解的类
        // 即在autoServiceMainModule里面的StartMainContainer类
        Services.load(IStartMainContainer.class).startMainActivity(this);

        finish();
    }
}
